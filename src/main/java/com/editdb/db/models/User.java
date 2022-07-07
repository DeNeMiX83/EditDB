package com.editdb.db.models;

import com.editdb.db.DataBaseHahdler;
import com.editdb.services.base;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;


public class User {
    private int id;
    private String login;
    private String password;
    private boolean is_admin=false;
    private ArrayList<User> subordinates = new ArrayList<>();
    private ArrayList<Integer> rolesId = new ArrayList<>();

    public User(int id, String login, String password) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.setSubordinates(getSubordinatesFromDB());
        this.setRolesId(getRolesIdFromDB());
    }

    public static User create(String login, String password) throws SQLIntegrityConstraintViolationException{
        password = base.hashPass(password);
        String insert = "INSERT INTO user (login, password) VALUES (?, ?)";
        int id = 0;

        try {
            PreparedStatement prSt = DataBaseHahdler.getDbConnection().prepareStatement(
                    insert, Statement.RETURN_GENERATED_KEYS);
            prSt.setString(1, login);
            prSt.setString(2, password);
            prSt.execute();

            ResultSet generatedId = prSt.getGeneratedKeys();
            generatedId.next();
            id = generatedId.getInt(1);
        } catch (SQLIntegrityConstraintViolationException e){
            throw e;
        }catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return new User(id, login, password);
    }

    public static User get(int id) {
        ResultSet resSet = null;

        String select = "SELECT * FROM user WHERE id = ?";

        try {
            PreparedStatement prSt = DataBaseHahdler.getDbConnection().prepareStatement(select);
            prSt.setInt(1, id);
            resSet = prSt.executeQuery();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            assert resSet != null;
            resSet.next();
            return getCurrentFromResSet(resSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void update(HashMap<String, String> values) throws SQLIntegrityConstraintViolationException {
        String valuesStr = "";
        for (String key : values.keySet()){
            valuesStr += key + " = ?,";
        }
        valuesStr = valuesStr.substring(0, valuesStr.length() - 1);
        String insert = "UPDATE user SET " +
                valuesStr + " WHERE id = ?";

        try {
            PreparedStatement prSt = DataBaseHahdler.getDbConnection().prepareStatement(insert);
            int n = 1;
            for (String key : values.keySet()){
                prSt.setString(n, values.get(key));
                n++;
            }
            prSt.setInt(n, id);
            prSt.executeUpdate();
        } catch (SQLIntegrityConstraintViolationException e){
            throw e;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        String login = values.get("login");
        String password = values.get("password");

        if (login != null) this.setLogin(login);
        if (password != null) this.setPassword(password);
    }

    public static User auth(String login, String password) {
        ResultSet resSet = null;
        password = base.hashPass(password);

        String select = "SELECT * FROM user WHERE login = ? and password = ?";

        try {
            PreparedStatement prSt = DataBaseHahdler.getDbConnection().prepareStatement(select);
            prSt.setString(1, login);
            prSt.setString(2, password);
            resSet = prSt.executeQuery();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        User user = null;
        int count = 0;
        int id;
        boolean is_admin;
        while (true){
            try {
                if (!resSet.next()) break;
                id = resSet.getInt("id");
                is_admin = resSet.getBoolean("is_admin");
                user = new User(id, login, password);
                user.setSubordinates(user.getSubordinatesFromDB());
                if (is_admin == true){
                    user.doAdmin();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            count ++;
            if (count > 1){
                break;
            }
        }

        if (count != 1) {
            return null;
        }

        return user;
    }

    public ArrayList<User> getSubordinatesFromDB(){
        ResultSet resSet = null;
        String select = "SELECT * FROM verifier WHERE chief=?";

        try {
            PreparedStatement prSt = DataBaseHahdler.getDbConnection().prepareStatement(select);
            prSt.setInt(1, id);
            resSet = prSt.executeQuery();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        ArrayList<User> arr = new ArrayList<>();
        try {
            while (resSet.next()) {
                int id = resSet.getInt("person");
                arr.add(get(id));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return arr;
    }

    public ArrayList<Integer> getRolesIdFromDB(){
        ResultSet resSet = null;
        String select = "SELECT * FROM user_role WHERE user_id=?";

        try {
            PreparedStatement prSt = DataBaseHahdler.getDbConnection().prepareStatement(select);
            prSt.setInt(1, id);
            resSet = prSt.executeQuery();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        ArrayList<Integer> arr = new ArrayList<>();
        try {
            while (resSet.next()) {
                int role_id = resSet.getInt("role_id");
                arr.add(role_id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return arr;
    }

    public static User getCurrentFromResSet(ResultSet resSet) {
        try {
            int id = resSet.getInt("id");
            String login = resSet.getString("login");
            String password = resSet.getString("password");
            return new User(id, login, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void doAdmin(){
        this.is_admin = true;
    }

    public Boolean is_admin(){
        return is_admin;
    }

    public int getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public ArrayList<User> getSubordinates() {
        return subordinates;
    }

    public ArrayList<Integer> getRolesId() {
        return rolesId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setSubordinates(ArrayList<User> subordinates) {
        this.subordinates = subordinates;
    }

    public void setRolesId(ArrayList<Integer> rolesId) {
        this.rolesId = rolesId;
    }
}


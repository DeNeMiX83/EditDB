package com.editdb.db.models;

import com.editdb.db.DataBaseHahdler;
import com.editdb.services.base;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashMap;


public class User {
    private int id;
    private String login;
    private String password;
    private boolean is_admin=false;

    public User(int id, String login, String password) {
        this.id = id;
        this.login = login;
        this.password = password;
    }

    public static User create(String login, String password) throws SQLIntegrityConstraintViolationException{
        password = base.hashPass(password);
        String insert = "INSERT INTO user (login, password) VALUES (?, ?)";
        int id = 0;

        try {
            PreparedStatement prSt = DataBaseHahdler.getDbConnection().prepareStatement(insert);
            prSt.setString(1, login);
            prSt.setString(2, password);
            prSt.execute();

            ResultSet generatedId = prSt.getGeneratedKeys();
            id = generatedId.getInt(1);
        } catch (SQLIntegrityConstraintViolationException e){
            throw e;
        }catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return new User(id, login, password);
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

//    public static User get(String login, String password) {
//        ResultSet resSet = null;
//
//        String select = "SELECT * FROM user WHERE login = ? and password = ?";
//
//        try {
//            PreparedStatement prSt = DataBaseHahdler.getDbConnection().prepareStatement(select);
//            prSt.setString(1, login);
//            prSt.setString(2, password);
//            resSet = prSt.executeQuery();
//        } catch (SQLException | ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//
//        try {
//            assert resSet != null;
//            resSet.next();
//            return getCurrentFromResSet(resSet);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }

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

    public void doAdmin(){
        this.is_admin = true;
    }

    public Boolean is_admin(){
        return is_admin;
    }

//    public static User getCurrentFromResSet(ResultSet resSet) {
//        try {
//            int id = resSet.getInt("id");
//            String login = resSet.getString("login");
//            String password = resSet.getString("password");
//            return new User(id, login, password);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }

    public int getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
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
}


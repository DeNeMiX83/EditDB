package com.editdb.db.models;

import com.editdb.db.DataBaseHahdler;
import com.editdb.services.base;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class User {
    private int id;
    private String login;
    private String password;

    public User(int id, String login, String password) {
        this.id = id;
        this.login = login;
        this.password = password;
    }

    public static User create(String login, String password) {
        String insert = "INSERT INTO user (login, password) VALUES (?, ?)";

        try {
            PreparedStatement prSt = DataBaseHahdler.getDbConnection().prepareStatement(insert);
            prSt.setString(1, login);
            prSt.setString(2, base.hashPass(password));
            prSt.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return get(login, password);
    }

    public static User get(String login, String password) {
        ResultSet resSet = null;

        String select = "SELECT * FROM user WHERE login = ? and password = ?";

        try {
            PreparedStatement prSt = DataBaseHahdler.getDbConnection().prepareStatement(select);
            prSt.setString(1, login);
            prSt.setString(2, password);
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

    public static User auth(String login, String password) {
        ResultSet resSet = null;

        String select = "SELECT * FROM user WHERE login = ? and password = ?";

        try {
            PreparedStatement prSt = DataBaseHahdler.getDbConnection().prepareStatement(select);
            prSt.setString(1, login);
            prSt.setString(2, base.hashPass(password));
            resSet = prSt.executeQuery();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        User user = null;
        int count = 0;
        int id;
        while (true){
            try {
                if (!resSet.next()) break;
                id = resSet.getInt("id");
                user = new User(id, login, password);
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

    public int getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }
}


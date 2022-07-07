package com.editdb;

import com.editdb.db.DataBaseHahdler;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class Configs {
    public static String dbHost = "std-mysql";
    public static String dbPort = "3306";
    public static String dbUser = "std_1961_coursework";
    public static String dbPass = "123_0353";
    public static String dbName = "std_1961_coursework";
    public static HashMap<Integer, String> roles = getRole();

    private static HashMap<Integer, String> getRole() {
        HashMap<Integer, String> roles = new HashMap<>();

        ResultSet resSet = null;

        String select = "SELECT * FROM role";

        try {
            PreparedStatement prSt = DataBaseHahdler.getDbConnection().prepareStatement(select);
            resSet = prSt.executeQuery();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            while (resSet.next()) {
                int id = resSet.getInt("id");
                String name = resSet.getString("name");
                roles.put(id, name);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return roles;
    }

}

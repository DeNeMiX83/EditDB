package com.editdb.db;

import com.editdb.Configs;
import com.editdb.services.base;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

import static com.editdb.services.base.hashPass;

public class DataBaseHahdler{
    static Connection dbConnection;

    public static Connection getDbConnection()
            throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        if (dbConnection == null) {
            String connectionString = "jdbc:mysql://" + Configs.dbHost + ":" + Configs.dbPort + "/" + Configs.dbName;


            dbConnection = DriverManager.getConnection(connectionString, Configs.dbUser, Configs.dbPass);
        }

        return dbConnection;
    }
}

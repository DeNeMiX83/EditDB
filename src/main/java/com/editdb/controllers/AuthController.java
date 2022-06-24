package com.editdb.controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.editdb.animations.Shape;
import com.editdb.db.DataBaseHahdler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AuthController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button authSignInButton;

    @FXML
    private Button backButton;

    @FXML
    private TextField login_field;

    @FXML
    private PasswordField password_field;

    @FXML
    void initialize() {
        backButton.setOnAction(event -> {
            backButton.getScene().getWindow().hide();

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/com/editdb/sample.fxml"));
            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Parent root = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        });

        authSignInButton.setOnAction(event -> {
            String login = login_field.getText().trim();
            String pass = password_field.getText().trim();

            if (login.equals("") && pass.equals("")) {
                System.out.println("Пусто");
            } else {
                loginUser(login, pass);
            }
//            MessageDigest md5 = null;
//            try {
//                md5 = MessageDigest.getInstance("MD5");
//            } catch (NoSuchAlgorithmException e) {
//                e.printStackTrace();
//            }
//            assert md5 != null;
//            byte[] bytes = md5.digest(pass.getBytes());
//            StringBuilder hashPass = new StringBuilder();
//            for (byte b: bytes){
//                hashPass.append(String.format("%02X", b));
//            }
//            System.out.println(login);
//            System.out.println(pass);
        });
    }

    private void loginUser(String login, String pass) {
        DataBaseHahdler dbHahdler = new DataBaseHahdler();
        ResultSet users = dbHahdler.getUser(login, pass);

        int count = 0;
        while (true){
            try {
                if (!users.next()) break;
            } catch (SQLException e) {
                e.printStackTrace();
            }
            count ++;
            if (count > 1){
                break;
            }
        }

        if (count == 1){
            authSignInButton.getScene().getWindow().hide();

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/com/editdb/app.fxml"));
            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Parent root = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        }
        else {
            Shape shapeLogin = new Shape(login_field);
            Shape shapePass = new Shape(password_field);
            shapeLogin.playAnimation();
            shapePass.playAnimation();
        }
    }
}
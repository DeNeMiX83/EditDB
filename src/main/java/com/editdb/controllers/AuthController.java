package com.editdb.controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.editdb.Resources;
import com.editdb.animations.Shape;
import com.editdb.db.DataBaseHahdler;
import com.editdb.db.models.User;
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
        });
    }

    private void loginUser(String login, String pass) {
        User user = User.auth(login, pass);

        if (user != null){
            authSignInButton.getScene().getWindow().hide();
            Resources.user = user;

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
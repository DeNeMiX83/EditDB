package com.editdb.controllers;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Arrays;
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

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class Controller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button authSignInButton;

    @FXML
    private Button guestSignInButton;

    @FXML
    private Button loginSignInButton;

    @FXML
    private TextField login_field;

    @FXML
    private PasswordField password_field;

    @FXML
    void initialize() {
        Resources.user = null;
        Resources.countQuotes = 0;

        authSignInButton.setOnAction(event -> {
            authSignInButton.getScene().getWindow().hide();

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/com/editdb/auth.fxml"));
            Stage stage = new Stage();
            try {
                stage.setScene(new Scene(loader.load()));
            } catch (IOException e) {
                e.printStackTrace();
            }
            stage.show();
        });

        loginSignInButton.setOnAction(event -> {
            String login = login_field.getText().trim();
            String pass = password_field.getText().trim();

            if (login.equals("") && pass.equals("")) {
                Shape shapeLogin = new Shape(login_field);
                Shape shapePass = new Shape(password_field);
                shapeLogin.playAnimation();
                shapePass.playAnimation();
            }
            else {
                Shape shapeLogin = new Shape(login_field);
                try {
                    Resources.user = User.create(login, pass);
                    authSignInButton.getScene().getWindow().hide();
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("/com/editdb/app.fxml"));
                    Stage stage = new Stage();
                    try {
                        stage.setScene(new Scene(loader.load()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    stage.show();
                } catch (SQLIntegrityConstraintViolationException e) {
                    shapeLogin.playAnimation();
                }
            }
        });

        guestSignInButton.setOnAction(event -> {
            guestSignInButton.getScene().getWindow().hide();
            Resources.user = null;

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/com/editdb/appGuest.fxml"));
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
    }

}

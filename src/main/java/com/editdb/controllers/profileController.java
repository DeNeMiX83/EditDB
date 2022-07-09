package com.editdb.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.editdb.Resources;
import com.editdb.services.base;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class profileController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button backButton;

    @FXML
    private Button editLoginButton;

    @FXML
    private Button editPasswordButton;

    @FXML
    private Label loginLabel;

    @FXML
    void initialize() {
        loginLabel.setText(Resources.user.getLogin());

        editLoginButton.setOnAction(event -> {
            editLoginButton.getScene().getWindow().hide();

            FXMLLoader loader = base.showWindow(this, "profileEditLogin.fxml");
        });

        editPasswordButton.setOnAction(event -> {
            editPasswordButton.getScene().getWindow().hide();

            FXMLLoader loader = base.showWindow(this, "profileEditPassword.fxml");
        });

        backButton.setOnAction(event -> {
            backButton.getScene().getWindow().hide();

            FXMLLoader loader = base.showWindow(this, "app.fxml");
        });
    }

    public Label getLoginLabel() {
        return loginLabel;
    }
}

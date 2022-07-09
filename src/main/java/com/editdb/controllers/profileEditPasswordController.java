package com.editdb.controllers;

import com.editdb.Resources;
import com.editdb.animations.Shape;
import com.editdb.services.base;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashMap;
import java.util.ResourceBundle;

public class profileEditPasswordController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button backButton;

    @FXML
    private Button editButton;

    @FXML
    private TextField newPasswordField;

    @FXML
    private TextField newPasswordRepeatField;

    @FXML
    private TextField oldPasswordField;

    @FXML
    void initialize() {
        backButton.setOnAction(event -> {
            backButton.getScene().getWindow().hide();

            FXMLLoader loader = base.showWindow(this, "profile.fxml");
        });

        editButton.setOnAction(event -> {
            String oldPassword = oldPasswordField.getText().trim();
            String newPassword = newPasswordField.getText().trim();
            String newPasswordRepeat = newPasswordRepeatField.getText().trim();

            Shape shapeOldPassword = new Shape(oldPasswordField);
            Shape shapeNewPassword = new Shape(newPasswordField);
            Shape shapeNewPasswordRepeat = new Shape(newPasswordRepeatField);
            Shape shapeEditButton = new Shape(editButton);

            if (oldPassword.equals("") ||
                    !base.hashPass(oldPassword).equals(Resources.user.getPassword())) {
                System.out.println(Resources.user.getPassword());
                System.out.println(base.hashPass(oldPassword));
                System.out.println(oldPassword);
                shapeOldPassword.playAnimation();
            } else if (newPassword.equals("")) {
                shapeNewPassword.playAnimation();
            } else if (newPasswordRepeat.equals("") || !newPassword.equals(newPasswordRepeat)) {
                shapeNewPasswordRepeat.playAnimation();
            } else {
                HashMap<String, String> values = new HashMap<>();
                values.put("password", base.hashPass(newPassword));
                try {
                    Resources.user.update(values);
                    editButton.getScene().getWindow().hide();

                    FXMLLoader loader = base.showWindow(this, "profile.fxml");
                } catch (SQLIntegrityConstraintViolationException e) {
                    shapeEditButton.playAnimation();
                }
            }
        });
    }


}

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

public class profileEditLoginController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button backButton;

    @FXML
    private Button editButton;

    @FXML
    private TextField loginField;

    @FXML
    void initialize() {
        backButton.setOnAction(event -> {
            backButton.getScene().getWindow().hide();

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/com/editdb/profile.fxml"));
            Stage stage = new Stage();
            try {
                stage.setScene(new Scene(loader.load()));
            } catch (IOException e) {
                e.printStackTrace();
            }
            stage.show();
        });

        editButton.setOnAction(event -> {
            String login = loginField.getText().trim();

            Shape shapeLogin = new Shape(loginField);
            if (login.equals("")) {
                shapeLogin.playAnimation();
            } else {
                HashMap<String, String> values = new HashMap<>();
                values.put("login", login);
                try {
                    Resources.user.update(values);
                    editButton.getScene().getWindow().hide();

                    FXMLLoader loader = base.showWindow(this, "profile.fxml");
                } catch (SQLIntegrityConstraintViolationException e) {
                    shapeLogin.playAnimation();
                }
            }
        });
    }

}

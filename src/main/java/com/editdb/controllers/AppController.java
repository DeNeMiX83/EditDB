package com.editdb.controllers;

import com.editdb.Resources;
import com.editdb.animations.Shape;
import com.editdb.db.models.QuotesTeacher;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AppController extends AppQuestController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button addButton;

    @FXML
    private Button deleteButton;

    @FXML
    private Button editButton;


    @FXML
    private TableView<QuotesTeacher> table;

    @FXML
    private TableColumn<QuotesTeacher, String> quote;

    @FXML
    private TableColumn<QuotesTeacher, String> subject;

    @FXML
    private TableColumn<QuotesTeacher, String> teacher;

    @FXML
    private TableColumn<QuotesTeacher, String> date;

    @FXML
    void initialize() {
        start();

        addButton.setOnAction(event -> {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/com/editdb/addQuote.fxml"));
            Stage stage = new Stage();
            try {
                stage.setScene(new Scene(loader.load()));
            } catch (IOException e) {
                e.printStackTrace();
            }

            stage.show();

            AddQuoteController addQuoteController = loader.getController();
            addQuoteController.setTable(table);
        });

        editButton.setOnAction(event -> {
            QuotesTeacher current = getCurrentQuotes();
            if (current == null || current.getAuthorId() != Resources.user.getId()) {
                Shape button = new Shape(editButton);
                button.playAnimation();
                return;
            }

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/com/editdb/editQuote.fxml"));
            Stage stage = new Stage();
            try {
                stage.setScene(new Scene(loader.load()));
            } catch (IOException e) {
                e.printStackTrace();
            }

            EditQuoteController editQuoteController = loader.getController();
            editQuoteController.setQuote(current);
            stage.show();
            editQuoteController.setTable(table);
        });

        deleteButton.setOnAction(event -> {
            QuotesTeacher current = getCurrentQuotes();
            if (current == null || current.getAuthorId() != Resources.user.getId()) {
                Shape button = new Shape(deleteButton);
                button.playAnimation();
                return;
            }
            table.getItems().removeAll(current);
            current.delete();
        });
    }


    public QuotesTeacher getCurrentQuotes() {
        return table.getSelectionModel().getSelectedItem();
    }
}

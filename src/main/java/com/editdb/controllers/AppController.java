package com.editdb.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.editdb.animations.Shape;
import com.editdb.db.models.QuotesTeacher;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class AppController {

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
            if (current == null) {
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
            if (current == null) {
                Shape button = new Shape(deleteButton);
                button.playAnimation();
                return;
            }
            table.getItems().removeAll(current);
            current.delete();
        });
    }

    public void start() {
        quote.setCellValueFactory(new PropertyValueFactory<>("quote"));
        teacher.setCellValueFactory(new PropertyValueFactory<>("teacher"));
        subject.setCellValueFactory(new PropertyValueFactory<>("subject"));
        date.setCellValueFactory(new PropertyValueFactory<>("date"));
        ObservableList<QuotesTeacher> data = FXCollections.observableArrayList(QuotesTeacher.getAll());
        table.setItems(data);
    }

    public QuotesTeacher getCurrentQuotes() {
        return table.getSelectionModel().getSelectedItem();
    }
}

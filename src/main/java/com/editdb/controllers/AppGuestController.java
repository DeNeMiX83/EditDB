package com.editdb.controllers;

import com.editdb.Resources;
import com.editdb.db.models.QuotesTeacher;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AppGuestController {
    ArrayList<QuotesTeacher> quoteList;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button backButton;

    @FXML
    private TableColumn<QuotesTeacher, String> quote;

    @FXML
    private TableColumn<QuotesTeacher, String> subject;

    @FXML
    private TableColumn<QuotesTeacher, String> teacher;

    @FXML
    private TableColumn<QuotesTeacher, String> date;

    @FXML
    private TableView<QuotesTeacher> table;

    @FXML
    void initialize() {
        start();
    }

    public void start() {
        table.setRowFactory(tv -> new TableRow<QuotesTeacher>() {
            @Override
            public void updateItem(QuotesTeacher item, boolean empty) {
                super.updateItem(item, empty) ;
                if (item == null) {
                    setStyle("");
                } else if (Resources.user == null) {
//                    setStyle("-fx-background-color: tomato;");
                } else if (item.getAuthorId() == Resources.user.getId()) {
                    setStyle("-fx-background-color: green;");
                } else {
//                    setStyle("-fx-background-color: tomato;");
                }
            }
        });
        quote.setCellValueFactory(new PropertyValueFactory<>("quote"));
        teacher.setCellValueFactory(new PropertyValueFactory<>("teacher"));
        subject.setCellValueFactory(new PropertyValueFactory<>("subject"));
        date.setCellValueFactory(new PropertyValueFactory<>("date"));

        ObservableList<QuotesTeacher> data = FXCollections.observableArrayList(
                quoteList = QuotesTeacher.getAll());
        table.setItems(data);

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
    }

}

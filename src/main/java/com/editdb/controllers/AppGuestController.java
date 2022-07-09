package com.editdb.controllers;

import com.editdb.Resources;
import com.editdb.db.models.QuotesTeacher;
import com.editdb.services.base;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

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
                } else if (
//                        item.getAuthorId() == Resources.user.getId()
                        base.checkAccessForQuote(item)
                ) {
                    setStyle("-fx-background-color: #C0C0C0;");
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

            FXMLLoader loader = base.showWindow(this, "sample.fxml");
        });
    }

}

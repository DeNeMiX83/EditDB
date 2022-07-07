package com.editdb.controllers;

import com.editdb.Resources;
import com.editdb.animations.Shape;
import com.editdb.db.models.QuotesTeacher;
import com.editdb.services.base;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static com.editdb.Resources.countQuotes;

public class AppController extends AppGuestController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label countQuotesLabel;

    @FXML
    private Button addButton;

    @FXML
    private Button deleteButton;

    @FXML
    private Button editButton;

    @FXML
    private Button profileButton;


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
            addQuoteController.setTable(this);
        });

        editButton.setOnAction(event -> {
            QuotesTeacher current = getCurrentQuotes();
            if (!base.checkAccessForQuote(current)) {
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
            stage.show();
            editQuoteController.setQuote(current);
            editQuoteController.setTable(table);
        });

        deleteButton.setOnAction(event -> {
            QuotesTeacher current = getCurrentQuotes();
            if (!base.checkAccessForQuote(current)) {
                Shape button = new Shape(deleteButton);
                button.playAnimation();
                return;
            }
            table.getItems().removeAll(current);
            current.delete();
            if (current.getAuthorId() == Resources.user.getId()){
                countQuotesLabel.setText(String.valueOf(--countQuotes));
            }
            table.refresh();
        });

        profileButton.setOnAction(event -> {
            profileButton.getScene().getWindow().hide();

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
    }


    public QuotesTeacher getCurrentQuotes() {
        return table.getSelectionModel().getSelectedItem();
    }

    public void start() {
        super.start();
        for (QuotesTeacher quote : quoteList) {
            if (quote.getAuthorId() == Resources.user.getId()) {
                countQuotes++;
            }
        }
        countQuotesLabel.setText(String.valueOf(countQuotes));
    }

    public TableView<QuotesTeacher> getTable() {
        return table;
    }

    public Label getCountQuotesLabel() {
        return countQuotesLabel;
    }
}

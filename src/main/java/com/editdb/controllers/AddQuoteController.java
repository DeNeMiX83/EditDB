package com.editdb.controllers;

import com.editdb.Resources;
import com.editdb.animations.Shape;
import com.editdb.db.models.QuotesTeacher;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class AddQuoteController {
    AppController parent;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button addButton;

    @FXML
    private DatePicker dateField;

    @FXML
    private Button exitButton;

    @FXML
    private TextField quoteField;

    @FXML
    private TextField subjectField;

    @FXML
    private TextField teacherField;

    @FXML
    void initialize() {
        dateField.setValue(LocalDate.now());

        exitButton.setOnAction(event -> {
            exitButton.getScene().getWindow().hide();
        });

        addButton.setOnAction(event -> {
            String quote = quoteField.getText();
            String teacher = teacherField.getText();
            String subject = subjectField.getText();
            String date = String.valueOf(dateField.getValue());

            QuotesTeacher newQuote = QuotesTeacher.create(quote, teacher, subject, date);
            if (newQuote != null){
                addButton.getScene().getWindow().hide();
                parent.getTable().getItems().add(newQuote);
                parent.getCountQuotesLabel().setText(String.valueOf(++Resources.countQuotes));
            } else {
                Shape button = new Shape(addButton);
                button.playAnimation();
            }
        });
    }

    public void setTable(AppController parent){
        this.parent = parent;
    }

}

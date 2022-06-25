package com.editdb.controllers;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import com.editdb.db.models.QuotesTeacher;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class EditQuoteController {
    AppController parent;
    QuotesTeacher quoteTeacher;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private DatePicker dateField;

    @FXML
    private Button editButton;

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
        exitButton.setOnAction(event -> {
            exitButton.getScene().getWindow().hide();
        });

        editButton.setOnAction(event -> {
            String quote = quoteField.getText();
            String teacher = teacherField.getText();
            String subject = subjectField.getText();
            String date = String.valueOf(dateField.getValue());

            quoteTeacher.update(quote, teacher, subject, date);

            editButton.getScene().getWindow().hide();
            parent.start();
        });

    }

    public void setParent(AppController parent, QuotesTeacher quoteTeacher){
        this.parent = parent;

        this.quoteTeacher = quoteTeacher;

        quoteField.setText(quoteTeacher.getQuote());
        teacherField.setText(quoteTeacher.getTeacher());
        subjectField.setText(quoteTeacher.getSubject());
        dateField.setValue(LocalDate.parse(quoteTeacher.getDate()));
    }
}

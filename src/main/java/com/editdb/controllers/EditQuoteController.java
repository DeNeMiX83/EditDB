package com.editdb.controllers;

import java.net.URL;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.ResourceBundle;

import com.editdb.Resources;
import com.editdb.animations.Shape;
import com.editdb.db.models.QuotesTeacher;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class EditQuoteController {
    TableView<QuotesTeacher> table;
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

            HashMap<String, String> values = new HashMap<>();
            values.put("quote", quote);
            values.put("teacher", teacher);
            values.put("subject", subject);
            values.put("date", date);

            Object updateQuote = quoteTeacher.update(values);
            if (updateQuote != null){
                editButton.getScene().getWindow().hide();
                table.refresh();
            } else {
                Shape button = new Shape(editButton);
                button.playAnimation();
            }
        });

    }

    public void setQuote(QuotesTeacher quoteTeacher){
        this.quoteTeacher = quoteTeacher;

        quoteField.setText(quoteTeacher.getQuote());
        teacherField.setText(quoteTeacher.getTeacher());
        subjectField.setText(quoteTeacher.getSubject());
        dateField.setValue(LocalDate.parse(quoteTeacher.getDate()));
    }

    public void setTable(TableView<QuotesTeacher> table){
        this.table = table;
    }
}

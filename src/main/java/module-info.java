module com.example.editdb {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.java;


    opens com.editdb to javafx.fxml;
    exports com.editdb;
    exports com.editdb.controllers;
    opens com.editdb.controllers to javafx.fxml;
    opens com.editdb.db.models to javafx.base;
}
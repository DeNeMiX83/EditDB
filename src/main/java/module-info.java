module com.example.editdb {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.editdb to javafx.fxml;
    exports com.editdb;
    exports com.editdb.controllers;
    opens com.editdb.controllers to javafx.fxml;
}
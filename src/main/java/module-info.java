module com.example.editdb {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.editdb to javafx.fxml;
    exports com.editdb;
    exports com.editdb.controllers;
    opens com.editdb.controllers to javafx.fxml;
}
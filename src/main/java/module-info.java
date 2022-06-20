module com.example.editdb {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.editdb to javafx.fxml;
    exports com.editdb;
}
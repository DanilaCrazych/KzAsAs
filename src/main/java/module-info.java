module com.example.kzasas {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.kzasas to javafx.fxml;
    exports com.example.kzasas;
}
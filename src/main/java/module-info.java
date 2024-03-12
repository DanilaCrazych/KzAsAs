module com.example.kzasas {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.kzasas to javafx.fxml;
    exports com.example.kzasas;
}
package com.example.kzasas;

import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class HelloController implements Initializable {
    public Connection connection;
    ConBd conBd;
    @FXML
    private AnchorPane back_page;
    @FXML
    private Button login_button;
    @FXML
    private AnchorPane login_centre;
    @FXML
    private Label login_error;
    @FXML
    private TextField login_loginField;
    @FXML
    private AnchorPane login_page;
    @FXML
    private PasswordField login_passField;
    @FXML
    private Label main_sotrudniki;


    @FXML
    protected void Login(){
        String query = "SELECT login, password FROM users WHERE login LIKE '" + login_loginField.getText() + "'";
        String loginAuth = "";
        String passAuth = "";
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                loginAuth = resultSet.getString(1);
                passAuth = resultSet.getString(2);
            }
        } catch (Exception ex) {
            System.out.println("Connection failed...");
            System.out.println(ex);
        }
        if (login_loginField.getText().equals("") | login_passField.getText().equals("")) {
            login_error.setText("Введите логин и пароль!");
            login_error.setVisible(true);
        } else if (login_loginField.getText().equals(loginAuth)||login_passField.getText().equals(passAuth)) {
            login_page.setVisible(false);
        } else {
            login_error.setText("Неверный логин или пароль!");
            login_error.setVisible(true);}
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        conBd.ConnectBd();
    }
    @FXML
    protected void LabelStyle(){
        main_sotrudniki.setStyle("-fx-background-color: #2e2e2e; -fx-text-fill: #fff; -fx-background-radius: 3;");
    }

}
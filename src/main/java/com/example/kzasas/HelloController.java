package com.example.kzasas;

import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class HelloController implements Initializable {
    public Connection connection;
    @FXML
    private Label main_tasks;
    @FXML
    private Label podmain_sotrudniki_add;
    @FXML
    private Label podmain_tasks_new;
    @FXML
    private Label podmain_tasks_spisok;

    @FXML
    private Label podmain_sotrudniki_spisok;
    @FXML
    private Pane hidepane;
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
//        ConnectBd();
    }
    public void ConnectBd() {
        try {
             connection = DriverManager.getConnection("jdbc:mysql://virps.ru:3306/kzss", "danilas", "p@ssw0rd");
            System.out.println("Подключение к базе данных успешно установлено!");
        } catch (SQLException e) {
            System.out.println("Ошибка при подключении к базе данных:");
            printSQLException(e);
        }
    }
    public static void printSQLException(SQLException ex) {
        for (Throwable e : ex) {
            if (e instanceof SQLException) {
                e.printStackTrace(System.err);
                System.err.println("SQLException: " + ((SQLException) e).getSQLState());
                System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
                System.err.println("Message: " + e.getMessage());
                Throwable t = ex.getCause();
                while (t != null) {
                    System.out.println("Cause: " + t);
                    t = t.getCause();
                }
            }
        }
    }
    public void clearStyle(){
        main_sotrudniki.setStyle("");
        main_tasks.setStyle("");
    }
    public void defaultPosition(){
        main_tasks.setLayoutY(155);

    }
    public void styleOnClk(Label label){
        label.setStyle("-fx-background-color: #2e2e2e; -fx-text-fill: #fff; -fx-background-radius: 3;");
    }
    public void labelVisFalse(){
        podmain_sotrudniki_add.setVisible(false);
        podmain_sotrudniki_spisok.setVisible(false);
        podmain_tasks_new.setVisible(false);
        podmain_tasks_spisok.setVisible(false);
    }

    @FXML
    protected void sotrudnikiClk(){
        defaultPosition();
        clearStyle();
        labelVisFalse();
        styleOnClk(main_sotrudniki);
        main_tasks.setLayoutY(220);
        podmain_sotrudniki_spisok.setVisible(true);
        podmain_sotrudniki_add.setVisible(true);
    }
    @FXML
    protected void tasksClk(){
        defaultPosition();
        clearStyle();
        labelVisFalse();
        styleOnClk(main_tasks);
        podmain_tasks_new.setVisible(true);
        podmain_tasks_spisok.setVisible(true);


    }
    @FXML
    protected void backHome(){
        clearStyle();
        defaultPosition();
        labelVisFalse();
    }

}
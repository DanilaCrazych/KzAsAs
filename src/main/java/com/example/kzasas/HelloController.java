package com.example.kzasas;

import javafx.animation.FadeTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.util.Duration;

import java.io.File;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class HelloController implements Initializable {
    ObservableList<BdOtdel> listOtdel;

    ConBd cb =new ConBd();
    GetData getData = new GetData()
;    public Connection connection;
    ConBd conbd = new ConBd();
    @FXML
    private TableView<BdOtdel> pane_sotr_table_otdel;
    @FXML
    private TableColumn<BdOtdel, Integer> table_otdel_id;

    @FXML
    private TableColumn<BdOtdel, String > table_otdel_location;

    @FXML
    private TableColumn<BdOtdel, String > table_otdel_name;
    @FXML
    private Pane add_sotr;
    @FXML
    private AnchorPane main_page;
    @FXML
    private AnchorPane work_pers;
    @FXML
    private Label work_pers_add;
    @FXML
    private Label pane_sotr_spisok_otdel;
    @FXML
    private Label pane_sotr_spisok_sotr;
    @FXML
    private Label work_pers_addOtdel;
    @FXML
    private Label work_pers_addSotr;
    @FXML
    private Pane pane_sotr;
    @FXML
    private ScrollPane scrol_pane_sotr;
    @FXML
    private Label main_view_addsotr;
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
    private File selectedFile;
    @FXML
    private TextField add_sotr_fio;
    @FXML
    private ImageView add_sotr_image;
    @FXML
    private TextField add_sotr_login;
    @FXML
    private TextField add_sotr_mail;
    @FXML
    private TextField add_sotr_password;
    @FXML
    private TextField add_sotr_otdel;

    @FXML
    protected void Login() {
        String query = "SELECT login, password FROM users WHERE login LIKE '" + login_loginField.getText() + "'";
        String loginAuth = "";
        String passAuth = "";
        try {
            Statement statement = cb.connection.createStatement();
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
        } else if (login_loginField.getText().equals(loginAuth) || login_passField.getText().equals(passAuth)) {
            login_page.setVisible(false);
            main_page.setVisible(true);
        } else {
            login_error.setText("Неверный логин или пароль!");
            login_error.setVisible(true);
        }
    }

    @FXML
    protected void choiseImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
        selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            Image image = new Image(selectedFile.toURI().toString());
            add_sotr_image.setImage(image);
        }
    }

    @FXML
    protected void addsotrserver() {
        String fio = add_sotr_fio.getText();
        String login = add_sotr_login.getText();
        String password = add_sotr_password.getText();
        String mail = add_sotr_mail.getText();
        String otdel = add_sotr_otdel.getText();
        try {
            String quary = "INSERT INTO `kzss`.`staff` (`fio`, `otdel`, `mail`, `login`, `password`) " +
                    "VALUES ('" + fio + "', '" + otdel + "', '" + mail + "', '" + login + "', '" + password + "');\n";
            Statement statement = connection.createStatement();
            statement.executeUpdate(quary);
            System.out.println("Успешно");
        } catch (Exception e) {
            System.out.println("Неуспешно");
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cb.ConnectBd();
    }

    public void clearStyle() {
        main_sotrudniki.setStyle("");
        podmain_sotrudniki_spisok.setStyle("");
        podmain_sotrudniki_add.setStyle("");

        main_tasks.setStyle("");
        podmain_tasks_spisok.setStyle("");
        podmain_tasks_new.setStyle("");

        pane_sotr_spisok_otdel.setStyle("");
        pane_sotr_spisok_sotr.setStyle("");
    }

    public void defaultPosition() {
        main_tasks.setLayoutY(155);
    }

    public void styleOnClk(Label label) {
        label.setStyle("-fx-background-color: #2e2e2e; -fx-text-fill: #fff; -fx-background-radius: 3;");
    }

    public void defaultStyle(Label label) {
        label.setStyle(" ");
    }

    public void labelVisFalse() {
        podmain_sotrudniki_add.setVisible(false);
        podmain_sotrudniki_spisok.setVisible(false);
        podmain_tasks_new.setVisible(false);
        podmain_tasks_spisok.setVisible(false);
        work_pers_addOtdel.setVisible(false);
        work_pers_addSotr.setVisible(false);
    }

    public void paneVisFalse() {
        pane_sotr.setVisible(false);
        work_pers.setVisible(false);
        pane_sotr_table_otdel.setVisible(false);
    }

    @FXML
    protected void sotrudnikiClk() {
        defaultPosition();
        clearStyle();
        labelVisFalse();
        styleOnClk(main_sotrudniki);
        main_tasks.setLayoutY(220);
        podmain_sotrudniki_spisok.setVisible(true);
        podmain_sotrudniki_add.setVisible(true);
    }

    @FXML
    protected void tasksClk() {
        defaultPosition();
        clearStyle();
        labelVisFalse();
        styleOnClk(main_tasks);
        podmain_tasks_new.setVisible(true);
        podmain_tasks_spisok.setVisible(true);
    }

    @FXML
    protected void spisokSotr() {
        styleOnClk(podmain_sotrudniki_spisok);
        defaultStyle(podmain_sotrudniki_add);
        paneVisFalse();
        pane_sotr.setVisible(true);
    }

    @FXML
    protected void backHome() {
        paneVisFalse();
        clearStyle();
        defaultPosition();
        labelVisFalse();
    }

    @FXML
    protected void workPers() {
        paneVisFalse();
        styleOnClk(podmain_sotrudniki_add);
        defaultStyle(podmain_sotrudniki_spisok);
        defaultStyle(work_pers_add);
        work_pers.setVisible(true);
    }

    @FXML
    protected void workPersAddSotr() {
        styleOnClk(work_pers_add);
        work_pers_addOtdel.setVisible(true);
        work_pers_addSotr.setVisible(true);
    }

    @FXML
    protected void addsotrview() {
        add_sotr.setVisible(true);
    }
    @FXML
    protected void spispokOtdel(){
        styleOnClk(pane_sotr_spisok_otdel);
        pane_sotr_table_otdel.getItems().clear();
        listOtdel= getData.listOtdel;
        pane_sotr_table_otdel.setVisible(true);
        getData.getDataOtdel();
        try {
            table_otdel_id.setCellValueFactory(new PropertyValueFactory<BdOtdel, Integer>("id"));
            table_otdel_name.setCellValueFactory(new PropertyValueFactory<BdOtdel, String>("name"));
            table_otdel_location.setCellValueFactory(new PropertyValueFactory<BdOtdel, String>("location"));
            pane_sotr_table_otdel.setItems(listOtdel);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }





}
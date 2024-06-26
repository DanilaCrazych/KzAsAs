package com.example.kzasas;

import javafx.animation.FadeTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.util.Duration;

import java.io.File;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class HelloController implements Initializable {
    ObservableList<BdOtdel> listOtdel;
    ObservableList<BDStaff> listStaff;
    ObservableList<BDTasks> listTasks;

    ConBd cb = new ConBd();
    GetData getData = new GetData();
    public Connection connection;
    ConBd conbd = new ConBd();
    @FXML
    private TableView<BdOtdel> pane_sotr_table_otdel;
    @FXML
    private TableColumn<BdOtdel, Integer> table_otdel_id;
    @FXML
    private TableColumn<BdOtdel, String> table_otdel_location;
    @FXML
    private TableColumn<BdOtdel, String> table_otdel_name;


    @FXML
    private TableView<BDStaff> pane_sotr_table_staff;
    @FXML
    private TableColumn<BDStaff, String> table_staff_fio;
    @FXML
    private TableColumn<BDStaff, Integer> table_staff_id;
    @FXML
    private TableColumn<BDStaff, String> table_staff_mail;
    @FXML
    private TableColumn<BDStaff, String> table_staff_otdel;

    @FXML
    private TableView<BDTasks> table_zadachi;
    @FXML
    private TableColumn<BDTasks, String > table_zadachi_description;
    @FXML
    private TableColumn<BDTasks, Integer> table_zadachi_id;
    @FXML
    private TableColumn<BDTasks, String> table_zadachi_staff;

    @FXML
    private TableColumn<BDTasks, String> table_zadachi_task;

    @FXML
    private Pane add_otdel, pane_new_task,pane_sotr, pane_tasks;
    @FXML
    private ComboBox combo_new_task;
    @FXML
    private DatePicker new_task_finishdate;
    @FXML
    private AnchorPane work_left_pane;
    @FXML
    private TextArea new_task_description;



    @FXML
    private Button add_otdel_btn,new_task_btn;

    @FXML
    private TextField add_otdel_location,new_task_task,new_task_comit;

    @FXML
    private TextField add_otdel_name;
    @FXML
    private Pane add_sotr, pane_reports;
    @FXML
    private AnchorPane main_page;
    @FXML
    private AnchorPane work_pers;
    @FXML
    private Label work_pers_add, main_report,add_otdel_error, podmain_report_formirovanie, pane_sotr_spisok_otdel,pane_sotr_spisok_sotr,work_pers_addOtdel,
            work_pers_addSotr,main_view_addsotr,main_tasks,podmain_sotrudniki_add,podmain_tasks_new,podmain_tasks_spisok,podmain_sotrudniki_spisok;
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

    Alert alert = new Alert(Alert.AlertType.INFORMATION);


    //функция авторизации
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

//    Выбор картинки
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

//    Добавить в базу данных
    @FXML
    protected void addsotrserver() {
        String fio = add_sotr_fio.getText();
        String login = add_sotr_login.getText();
        String password = add_sotr_password.getText();
        String mail = add_sotr_mail.getText();
        String otdel = add_sotr_otdel.getText();
        if (add_sotr_fio.getText().equals("") || add_sotr_otdel.getText().equals("") || add_sotr_mail.getText().equals("")
                || add_sotr_mail.getText().equals("") || add_sotr_password.getText().equals("")) {
            alert.setTitle("Ошибка");
            alert.setHeaderText(null);
            alert.setContentText("Заполните пустые поля!");
            alert.showAndWait();
        } else {
            try {
                String quary = "INSERT INTO `kzss`.`staff` (`fio`, `otdel`, `mail`, `login`, `password`) " +
                        "VALUES ('" + fio + "', '" + otdel + "', '" + mail + "', '" + login + "', '" + password + "');\n";
                Statement statement = cb.connection.createStatement();
                statement.executeUpdate(quary);
                alert.setTitle("Запись");
                alert.setHeaderText(null);
                alert.setContentText("Запись выполнена успешно");
                alert.showAndWait();
            } catch (Exception e) {
                alert.setTitle("Запись");
                alert.setHeaderText(null);
                alert.setContentText("Запись не выполнена");
                alert.showAndWait();
            }
        }
    }

    @FXML
    protected void addOtdelBD() {
        String name = add_otdel_name.getText();
        String loc = add_otdel_location.getText();
        if (add_otdel_name.getText().equals("") || add_otdel_location.getText().equals("")) {
            alert.setTitle("Ошибка");
            alert.setHeaderText(null);
            alert.setContentText("Заполните пустые поля!");
            alert.showAndWait();
        } else {
            try {
                Statement statement = cb.connection.createStatement();
                statement.executeUpdate("INSERT INTO `kzss`.`otdel` (`name`, `location`) VALUES ('" + name + "', '" + loc + "');");
                alert.setTitle("Запись");
                alert.setHeaderText(null);
                alert.setContentText("Запись выполнена успешно");
                alert.showAndWait();
            } catch (Exception e) {
                alert.setTitle("Запись");
                alert.setHeaderText(null);
                alert.setContentText("Запись не выполнена");
                alert.showAndWait();
            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cb.ConnectBd();
    }

//    Очистить стили
    public void clearStyle() {
        main_sotrudniki.setStyle("");
        podmain_sotrudniki_spisok.setStyle("");
        podmain_sotrudniki_add.setStyle("");

        main_tasks.setStyle("");
        podmain_tasks_spisok.setStyle("");
        podmain_tasks_new.setStyle("");

        pane_sotr_spisok_otdel.setStyle("");
        pane_sotr_spisok_sotr.setStyle("");

        work_pers_addSotr.setStyle("");
        work_pers_addOtdel.setStyle("");

        main_report.setStyle("");
        podmain_report_formirovanie.setStyle("");
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
        podmain_report_formirovanie.setVisible(false);
    }

    public void paneVisFalse() {
        pane_reports.setVisible(false);
        pane_sotr.setVisible(false);
        work_pers.setVisible(false);
        pane_sotr_table_otdel.setVisible(false);
        pane_sotr_table_staff.setVisible(false);
        add_otdel.setVisible(false);
        add_sotr.setVisible(false);
        pane_new_task.setVisible(false);
        pane_tasks.setVisible(false);
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
    public void newTaskClk(){
        styleOnClk(podmain_tasks_new);
        paneVisFalse();
        pane_tasks.setVisible(true);
        pane_new_task.setVisible(true);
        getData.getDataStaff();
        listStaff= getData.listStaff;
        combo_new_task.getItems().clear();
//        combo_new_task.setValue(null);
        for(int i=0; i<=listStaff.size();i++){
            combo_new_task.getItems().addAll(listStaff.get(i).fio);
        }
    }

    public void newTaskBtn(){
    String staff = combo_new_task.getValue().toString();
    String task = new_task_task.getText();
    String comit = new_task_comit.getText();
    String description = new_task_description.getText();
    String query = "INSERT INTO `kzss`.`tasks` (`staff`, `task`, `comit`, `description`) VALUES ('"+staff+"', '"+task+"', '"+comit+"', '"+description+"');";
//    String date = new_task_finishdate
        if (new_task_task.getText().equals(null)||new_task_comit.getText().equals(null)||new_task_description.getText().equals(null)) {
            alert.setTitle("Ошибка");
            alert.setHeaderText(null);
            alert.setContentText("Заполните пустые поля!");
            alert.showAndWait();
        } else {
        try {
            Statement statement = cb.connection.createStatement();
            statement.executeUpdate(query);
            alert.setTitle("Запись");
            alert.setHeaderText(null);
            alert.setContentText("Запись выполнена успешно");
            alert.showAndWait();
        } catch (Exception e) {
            alert.setTitle("Запись");
            alert.setHeaderText(null);
            alert.setContentText("Запись не выполнена");
            alert.showAndWait();
            throw new RuntimeException(e);
        }
        }
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
        work_left_pane.setVisible(true);
    }

    @FXML
    protected void workPersAddSotr() {
        styleOnClk(work_pers_add);
        work_pers_addOtdel.setVisible(true);
        work_pers_addSotr.setVisible(true);
    }

    @FXML
    protected void addsotrview() {
        defaultStyle(work_pers_addOtdel);
        styleOnClk(work_pers_addSotr);
        add_otdel.setVisible(false);
        add_sotr.setVisible(true);
    }

    @FXML
    protected void spispokOtdel() {
        styleOnClk(pane_sotr_spisok_otdel);
        defaultStyle(pane_sotr_spisok_sotr);
        pane_sotr_table_otdel.getItems().clear();
        listOtdel = getData.listOtdel;
        pane_sotr_table_staff.setVisible(false);
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

    @FXML
    protected void addOtdel() {
        styleOnClk(work_pers_addOtdel);
        defaultStyle(work_pers_addSotr);
        add_otdel.setVisible(true);
        add_sotr.setVisible(false);

    }

    @FXML
    protected void sotrudnikiSpisok() {
        pane_sotr_table_otdel.setVisible(false);
        pane_sotr_table_staff.setVisible(true);
        defaultStyle(pane_sotr_spisok_otdel);
        styleOnClk(pane_sotr_spisok_sotr);
        fillStaffTable();
    }
    public void reports(){
        defaultPosition();
        clearStyle();
        labelVisFalse();
        styleOnClk(main_report);
        defaultStyle(main_tasks);
        defaultStyle(main_sotrudniki);
        podmain_report_formirovanie.setVisible(true);
    }
    public void formirovanie_reports(){
        paneVisFalse();
        pane_reports.setVisible(true);
        styleOnClk(podmain_report_formirovanie);
    }
    public void fillStaffTable(){
        getData.getDataStaff();
        listStaff = getData.listStaff;
        try {
            table_staff_id.setCellValueFactory(new PropertyValueFactory<BDStaff, Integer>("id"));
            table_staff_fio.setCellValueFactory(new PropertyValueFactory<BDStaff, String>("fio"));
            table_staff_otdel.setCellValueFactory(new PropertyValueFactory<BDStaff, String>("otdel"));
            table_staff_mail.setCellValueFactory(new PropertyValueFactory<BDStaff, String>("mail"));
            pane_sotr_table_staff.setItems(listStaff);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public void spisokZadach (){
        getData.getDatatasks();
        listTasks = getData.listTasks;
        try {
            table_zadachi_id.setCellValueFactory(new PropertyValueFactory<BDTasks, Integer>("id"));
            table_zadachi_staff.setCellValueFactory(new PropertyValueFactory<BDTasks, String>("staff"));
            table_zadachi_task.setCellValueFactory(new PropertyValueFactory<BDTasks, String>("task"));
            table_zadachi_description.setCellValueFactory(new PropertyValueFactory<BDTasks, String>("description"));
            pane_sotr_table_staff.setItems(listStaff);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}



//сделать изменение и удаление (Можно поиск).
// Сделать лейбл на который будет выводиться информация о выполнении работы Пример: Запись выполнена успешно.
// Посмотреть как делается статистика и загрузка изображений на сервер. Также узнать у "Михаила" как он сделал красивый список.

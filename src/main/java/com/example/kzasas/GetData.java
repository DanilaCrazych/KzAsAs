package com.example.kzasas;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class GetData {
    ConBd cb = new ConBd();
    public ObservableList<BdOtdel> listOtdel = FXCollections.observableArrayList();
    public ObservableList<BDStaff> listStaff = FXCollections.observableArrayList();
    public ObservableList<BDTasks> listTasks = FXCollections.observableArrayList();


    public void getDataOtdel() {
        listOtdel.clear();
        cb.ConnectBd();
        try {
            Statement statement = cb.connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM otdel");
            while (resultSet.next()) {
                listOtdel.add(new BdOtdel(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3)));
            }
        } catch (SQLException e) {
            System.out.println("Connection failed...");
            System.out.println(e);
        }
    }
    public void getDataStaff(){
        listStaff.clear();
        cb.ConnectBd();
        try {
            Statement statement = cb.connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM staff");
            while(resultSet.next()){
                listStaff.add(new BDStaff(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4),
                        resultSet.getString(5), resultSet.getString(6)));
            }
        } catch (Exception e) {
            System.out.println("Connection failed...");
            System.out.println(e);
        }
    }

    public void getDatatasks(){
        listTasks.clear();
        cb.ConnectBd();
        try {
            Statement statement = cb.connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM tasks");
            while(resultSet.next()){
                listTasks.add(new BDTasks(resultSet.getInt(1), resultSet.getString(2),
                        resultSet.getString(3), resultSet.getString(4), resultSet.getString(5)));
            }
        } catch (Exception e) {
            System.out.println("Connection failed...");
            System.out.println(e);
        }
    }


}

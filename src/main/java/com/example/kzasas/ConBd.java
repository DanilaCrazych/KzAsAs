package com.example.kzasas;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConBd {
public Connection connection;
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
}

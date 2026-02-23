package com.edutech.progressive.config;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnectionManager {
    static Properties props = new Properties();

    private static void loadProperties(){
        try {
            FileInputStream fis = new FileInputStream("application.properties");
            props.load(fis);
        } catch (Exception e) {
            e.printStackTrace();
        }    
    }

    public static Connection getConnection() throws SQLException{
        loadProperties();
        return DriverManager.getConnection(props.getProperty("spring.datasource.url"), props.getProperty("spring.datasource.username"), props.getProperty("spring.datasource.password"));

    }
}

package com.project.project.components;

import com.project.project.enums.DataBaseSelection;
import org.aspectj.weaver.ast.Test;

import java.sql.*;


public class ConnectionToDB extends TestBase {
    private static String excelNameConnectionDB = "/testdata/DataBaseConnectionProperties.xlsx";
    private String host;
    private String dataBaseUrl;
    private String adminDB;
    private String passwordDB;

    private Connection connection;
    private PreparedStatement statement;

    private boolean connected;

    public ConnectionToDB(DataBaseSelection databaseselection) throws SQLException {
        ExcelLibrary excelread = new ExcelLibrary();
        int i = 0;
        if (databaseselection == DataBaseSelection.DataBaseNumber1) {
            i = 1;
        }
        if (databaseselection == DataBaseSelection.DataBaseNumber2) {
            i = 2;
        }
        dataBaseUrl = excelread.readFromExcel(i, 0, excelNameConnectionDB);
        adminDB = excelread.readFromExcel(i, 1, excelNameConnectionDB);
        passwordDB = excelread.readFromExcel(i, 2, excelNameConnectionDB);
        String dbDriver = excelread.readFromExcel(i, 3, excelNameConnectionDB);
//        String url = "jdbc:oracle:thin:@"+dataBaseUrl;
//        String url = dbDriver + dataBaseUrl;
        connection = DriverManager.getConnection(dataBaseUrl, adminDB, passwordDB);
        connected = false;
    }

    public void connect() {
        try {
            //"?jdbcCompliantTruncation=false&autoReconnect=true"
            connection = DriverManager.getConnection(dataBaseUrl, adminDB, passwordDB);
            logger.info("Successfully connected with " + this.dataBaseUrl);
            connected = true;
        } catch (Exception e) {
            logger.info("Unable to connect with " + dataBaseUrl + ".");
            connected = false;
        }
    }


    public ResultSet executeQuery(String query) {
        try {

            if (!connected()) return null;

            statement = connection.prepareStatement(query);
            ResultSet results = statement.executeQuery();
            return results;
        } catch (Exception e) {
            logger.error(String.valueOf(e));
        }
        return null;
    }

    public int executeUpdate(String query) {
        try {

            if (!connected()) return 0;

            statement = connection.prepareStatement(query);
            return statement.executeUpdate();
        } catch (Exception e) {
            logger.error(String.valueOf(e));
        }

        return 0;
    }

    public boolean connected() {
        return connected;
    }

    public PreparedStatement statement() {
        return statement;
    }
}
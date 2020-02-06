package dbHandler;

import connections.DatabaseConnection;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DBFunctions {

    DatabaseConnection dbConn = new DatabaseConnection();
    Connection conn = null;
    //String url = "jdbc:sqlite:APIDB.sqlite";

    //Method to create database table with 1 column "Primary KEY)"
    public void createTable(String tableName, String sqlStmt) throws SQLException {

        //Call connect to database to create connection
        conn = dbConn.connectToDatabase();

        // SQL statement for creating a new table
        String sql = "CREATE TABLE IF NOT EXISTS " + tableName + " (\n" + "id integer PRIMARY KEY," + "\n" + sqlStmt + ");";

        System.out.println(sql);
        try {
            Statement stmt = conn.createStatement();
            stmt.execute(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addColumn(String tableName, String columnName, String columnDataType) {

        //Call connect to database to create connection
        conn = dbConn.connectToDatabase();

        //SQL statement for creating new column
        String sql = "alter table " + tableName + "\n" + "\nadd " + columnName + " " + columnDataType;

        try {
            Statement stmt = conn.createStatement();
            stmt.execute(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addRow() {
        //Call DB Connection
        conn = dbConn.connectToDatabase();

        //SQL statement
        String sql = "insert into ";


    }
}


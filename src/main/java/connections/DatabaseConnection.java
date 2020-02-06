package connections;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection {

    //Class Variables
    String url = "jdbc:sqlite:APIDB.sqlite";
    Connection conn = null;

    //Method to connect to the database
    public Connection connectToDatabase() {
        try {
            // create a connection to the database
            conn = DriverManager.getConnection(url);
            System.out.println("Connection to SQLite has been established.");

        } catch (Exception e) {
            e.printStackTrace();

        }
        return conn;
    }
}
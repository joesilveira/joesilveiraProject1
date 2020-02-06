package dbHandler;

import connections.DatabaseConnection;

import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseHandler {

    //Class variables
    DBFunctions dbFunc = new DBFunctions();
    DatabaseConnection dbConn = new DatabaseConnection();
    Connection conn = null;

    //Method to initialize the database
    public void initDatabase() throws SQLException {

        //Instantiate Table
        String sql = "jobID string," + "\n" + "jobType string," +
                "gitHub_Url string," + "\n" + "job_Created_TimeStamp string," + "\n" +
                "company string," + "\n" + "company_url string," + "\n" +
                "job_location string," + "\n" + "job_title string," + "\n" +
                "job_description string," + "\n" + "how_to_apply string," + "\n" +
                "company_logo string";

        dbFunc.createTable("Jobs", sql);

    }
}

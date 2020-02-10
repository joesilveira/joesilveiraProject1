package dbHandler;

import java.sql.*;

public class DBFunctions {

    Connection conn = null;
    String url = "jdbc:sqlite:APIDB.sqlite";


    //Joe silveira
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

    //Joe Silveria
    //Method to create database table with 1 column "Primary KEY)"
    public void createTable(String tableName, String sqlStmt) throws SQLException {

        //Call connect to database to create connection
        conn = connectToDatabase();

        // SQL statement for creating a new table
        String sql = "CREATE TABLE IF NOT EXISTS " + tableName + " (\n" + "id integer PRIMARY KEY AUTOINCREMENT," + "\n" + sqlStmt + ");";

        //System.out.println(sql);
        try {
            Statement stmt = conn.createStatement();
            stmt.execute(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Joe Silveira
    //Method to add column to a database Table
    public void addColumn(String tableName, String columnName, String columnDataType) {

        //Call connect to database to create connection
        conn = connectToDatabase();

        //SQL statement for creating new column
        String sql = "alter table " + tableName + "\n" + "\nadd " + columnName + " " + columnDataType;

        try {
            Statement stmt = conn.createStatement();
            stmt.execute(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Joe Silveira
    //General method to add row to a database table
    public void addRow(String tableName, String sql) {
        //Call DB Connection
        conn = connectToDatabase();

        //SQL statement
        sql = "insert into ";
    }

    //Joe Silveira
    //Method to add just job title to database
    public void addTitleToJobsDatabaseTable(String title) throws SQLException {
        //call databse connection
        conn = connectToDatabase();

        try {
            String sqlStatement = "INSERT INTO Job_Titles(job_title) VALUES(?) ";
            System.out.println(sqlStatement);
            PreparedStatement pStatement = conn.prepareStatement(sqlStatement);
            pStatement.setString(1, title);
            pStatement.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Joe silveira
    //Method to add job full parameters to database
    //Not used in project 2
    public void addRowToJobsDatabase(String jobID, String jobType, String gitHub_Url, String job_Created_TimeStamp, String company, String company_url,
                                     String job_location, String job_title, String job_description, String how_to_apply, String company_logo) {


        String sql = "INSERT INTO Jobs " + "(jobID,jobType,gitHub_Url,job_Created_TimeStamp,company,company_url,jobLocation," +
                "job_title,job_description,how_to_apply,company_logo)" + "\n" + "VALUES" +
                "(" +
                jobID + "," +
                jobType + "," +
                gitHub_Url + "," +
                job_Created_TimeStamp + "," +
                company + "," +
                company_url + "," +
                job_location + "," +
                job_title + "," +
                job_description + ","
                + how_to_apply + "," +
                company_logo + ")" +
                ";";
        conn = connectToDatabase();
        try {
            Statement stmt = conn.createStatement();
            stmt.execute(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}


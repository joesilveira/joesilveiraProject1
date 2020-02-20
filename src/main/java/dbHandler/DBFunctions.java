package dbHandler;

import ResponseTypes.RSSFeed;
import ResponseTypes.StackOverFlowRSSFeed;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class DBFunctions {

    Connection conn = null;

    public int getValidString() {
        return validString;
    }

    int validString = 0;
    String url = "jdbc:sqlite:APIDB.sqlite";


    //Joe silveira
    //Method to connect to the database
    //Returns the database connection
    public Connection connectToDatabase() {
        try {
            // create a connection to the database
            conn = DriverManager.getConnection(url);
            //System.out.println("Connection to SQLite has been established.");

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

    public void addJobToStackOverFlowTable(String guid, String link, String name, String category, String title, String description,
                                           String pubDate, String updated, String location) {
        conn = connectToDatabase();


        try {
            String sqlStatement = "INSERT INTO StackOverFlowJobs(guid,link,name,category,title,description," +
                    "pubDate,updated,location,insertion_time) VALUES(?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement pStatement = conn.prepareStatement(sqlStatement);


            pStatement.setString(1, guid);
            pStatement.setString(2, link);
            pStatement.setString(3, name);
            pStatement.setString(4, category);
            pStatement.setString(5, title);
            pStatement.setString(6, description);
            pStatement.setString(7, pubDate);
            pStatement.setString(8, updated);
            pStatement.setString(9, location);
            pStatement.setString(10, LocalDateTime.now().toString());
            pStatement.execute();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    //Joe silveira
    //Method to add job full parameters to database
    //Not used in project 2
    public void addJobToGithubJobsTable(String jobID, String jobType, String gitHub_Url, String job_Created_TimeStamp, String company, String company_url,
                                        String job_location, String job_title, String job_description, String how_to_apply, String company_logo) {


        int allValid = 0;

        ArrayList<String> strings = new ArrayList<>();
        strings.add(jobID);
        strings.add(jobType);
        strings.add(gitHub_Url);
        strings.add(job_Created_TimeStamp);
        strings.add(company);
        strings.add(company_url);
        strings.add(job_location);
        strings.add(job_title);
        strings.add(job_description);
        strings.add(how_to_apply);
        strings.add(company_logo);

        for (String string : strings) {
            if (string != null) {
                allValid = stringCheck(string);
            }
        }

        if (allValid == 0) {

            conn = connectToDatabase();

            try {
                String sqlStatement = "INSERT INTO Jobs(jobID,jobType,gitHub_Url,job_Created_TimeStamp,company,company_url,job_Location," +
                        "job_title,job_description,how_to_apply,company_logo,insertion_time) VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";
                PreparedStatement pStatement = conn.prepareStatement(sqlStatement);
                pStatement.setString(1, jobID);
                pStatement.setString(2, jobType);
                pStatement.setString(3, gitHub_Url);
                pStatement.setString(4, job_Created_TimeStamp);
                pStatement.setString(5, company);
                pStatement.setString(6, company_url);
                pStatement.setString(7, job_location);
                pStatement.setString(8, job_title);
                pStatement.setString(9, job_description);
                pStatement.setString(10, how_to_apply);
                pStatement.setString(11, company_logo);
                pStatement.setString(12, LocalDateTime.now().toString());
                pStatement.execute();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    //Joe Silveira
    //Method to make sure string does not contain any harmful data
    //Return 1 if string contains a bad string
    private int stringCheck(String string) {
        int contains = 0;
        if (!string.equals("null")) {
            if (string.contains("INSERT INTO") || string.contains("SELECT * FROM") || string.contains("*)")
                    || string.contains("VALUES")) {
                contains = 1;
            }
        }
        return contains;
    }

    //********************Striclty Used for testing purposes*************

    //Joe Silveira
    //Method to add just job title to database
    public void addTitleToJobsDatabaseTable(String title) throws SQLException {
        //call database connection
        conn = connectToDatabase();

        validString = stringCheck(title);

        //if the string is good
        if (validString == 0) {
            try {
                String sqlStatement = "INSERT INTO Job_Titles(job_title) VALUES(?) ";
                PreparedStatement pStatement = conn.prepareStatement(sqlStatement);
                pStatement.setString(1, title);
                pStatement.execute();
            } catch (SQLException ignored) {

            }
        } else {
            validString = 1;
        }
    }
}




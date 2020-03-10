package dbHandler;

import java.sql.SQLException;
import java.util.ArrayList;

public class DatabaseHandler {

    //Class variables
    DBFunctions dbFunc = new DBFunctions();
    ArrayList<String> jobTableColumns = new ArrayList<>();

    public void initAllTables() throws SQLException {
        initStackOverFLowJobsTable();
        initGeocodeTable();
        initGitHubJobsTable();
    }

    //Joe silveira
    //Method to initialize the database with a table and all job paramaters
    public void initGitHubJobsTable() throws SQLException {

        //Instantiate Table
        String sql = "jobID string," + "\n" + "jobType string," +
                "gitHub_Url string," + "\n" + "job_Created_TimeStamp string," + "\n" +
                "company string," + "\n" + "company_url string," + "\n" +
                "job_location string," + "\n" + "job_title string," + "\n" +
                "job_description string," + "\n" + "how_to_apply string," + "\n" +
                "company_logo string," + "\n" + "insertion_Time CURRENT_TIMESTAMP";

        dbFunc.createTable("Jobs", sql);
    }

    //Joe silveira
    //Method to initialize the database with a table and all job paramaters
    public void initStackOverFLowJobsTable() throws SQLException {

        //Instantiate Table
        String sql = "guid string," + "\n" + "link string," +
                "name string," + "\n" + "category string," + "\n" +
                "title string," + "\n" + "description string," + "\n" +
                "pubDate string," + "\n" + "updated string," + "\n" +
                "location string," + "\n" + "insertion_Time CURRENT_TIMESTAMP";

        dbFunc.createTable("StackOverFlowJobs", sql);
    }


    public void initGeocodeTable() throws SQLException {
        //Instantiate Table
        String sql = "city string," + "\n" + "lat string," + "\n" + "lng string," + "\n" + "insertion_time string";

        dbFunc.createTable("geocodes", sql);
    }

    //Joe Silveira
    //Method to initialize array list with all job parameters
    //Not used
    public void initArrayList() {
        jobTableColumns.add("jobID");
        jobTableColumns.add("jobType");
        jobTableColumns.add("github_URL");
        jobTableColumns.add("job_Created_TimeStamp");
        jobTableColumns.add("company");
        jobTableColumns.add("company_url");
        jobTableColumns.add("job_location");
        jobTableColumns.add("job_title");
        jobTableColumns.add("job_description");
        jobTableColumns.add("how_to_apply");
        jobTableColumns.add("company_logo");
    }


    //*************************Depricated Methods*****************
    // Joe silveira
    //    //Method to initialize the database with a table with just job titles
    //    public void initJobsTitleTable() throws SQLException {
    //        String sql = "job_title string";
    //        dbFunc.createTable("Job_Titles", sql);
    //    }
}

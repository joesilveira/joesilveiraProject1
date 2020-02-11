package testing;

import connections.HTTPRequest;
import dbHandler.DBFunctions;
import runner.runtimeHandler;

import java.sql.*;

public class testMethods {

    HTTPRequest http = new HTTPRequest();
    DBFunctions dbFunc = new DBFunctions();
    Connection conn = null;
    String api = "https://jobs.github.com/positions.json?page=";
    String fileName = "jobsAPI.txt";

    //Joe Silveira
    //Method to ping api and return num of jobs
    //Returns the total number of jobs
    public int pingAPI() {
        int callRequest = 1;
        int pageNum = 1;
        int totalNumJobs = 0;
        while (callRequest == 1) {


            String url = api + pageNum;

            //Make Request
            http.makeGetRequest(url);

            pageNum++;

            if (http.getNumJobsOnPage() < 50) {
                callRequest = 0;
            }
        }

        totalNumJobs = http.getJobsTitleList().size();

        return totalNumJobs;
    }

    //Joe Silveira
    //method to get file name
    //Returns the file name
    public String getFileName() {
        return this.fileName;
    }

    //joe silveira
    //Check String method
    //Returns 1 if the jobName is in the database
    public int checkString(String jobName) {
        conn = dbFunc.connectToDatabase();
        int match = 0;
        try {
            String sql = "SELECT job_title FROM Job_Titles";
            PreparedStatement pStatement = conn.prepareStatement(sql);
            ResultSet rSet = pStatement.executeQuery();

            while (rSet.next()) {
                if (rSet.getString("job_title").equals(jobName)) {
                    match = 1;
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return match;
    }

    //joe silveira
    //Check table method
    //Returns 1 if the tableName is in the database
    public int checkTable(String tableName) throws SQLException {
        conn = dbFunc.connectToDatabase();
        int exists = 0;
        DatabaseMetaData dbMeta = conn.getMetaData();
        ResultSet rs = dbMeta.getTables(null, null, tableName, null);
        if (rs.next()) {
            exists = 1;
        } else {
            exists = 0;
        }

        return exists;
    }

    //joe silveira
    //Method to check the save function works
    //Returns 1 if the data is saved without any errors
    public int checkSave(String string) {
        int saved = 0;
        try {
            dbFunc.addTitleToJobsDatabaseTable(string);
            if (dbFunc.getValidString() == 0) {
                saved = 1;
            } else {
                saved = 0;
            }

        } catch (Exception e) {
        }
        return saved;
    }
}

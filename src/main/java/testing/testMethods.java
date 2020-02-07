package testing;

import connections.HTTPRequest;
import runner.runtimeHandler;

public class testMethods {

    HTTPRequest http = new HTTPRequest();
    String api = "https://jobs.github.com/positions.json?page=";
    String fileName = "jobsAPI.txt";

    //Joe Silveira
    //Method to ping api and return num of jobs
    //For test
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

    public String getFileName() {
        return this.fileName;
    }
}

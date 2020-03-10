/*
Joe Silveira

Class to handle all http requests and responses
 */

package connectionRequests;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import dbHandler.DBFunctions;
import dataTypes.GithubModel;

import javax.swing.*;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Collection;

public class GithubJSONRequest {

    //Java class variables
    HttpClient client;

    //Class Variables
    ArrayList<GithubModel> responseJobsList;
    ArrayList<GithubModel> jobsList = new ArrayList<>();
    DBFunctions dbFun = new DBFunctions();
    int numJobsOnPage = 0;
    int totalJobs = 0;

    public int getRecordAdded() {
        return recordAdded;
    }

    int recordAdded = 0;


    public void fetchAllGithubJobs(String api) {
        int callRequest = 1;
        int pageNum = 1;

        //Loop to loop through each page until there are less than 50 jobs on page
        while (callRequest == 1) {

            //Initialize url
            String url = api + pageNum;

            //Make Request
            makeGetRequest(url);

            //Progress bar updates
//            numJobs += http.getNumJobsOnPage();
//            progressBar.setMinimum(0);
//            progressBar.setMaximum(numJobs);
//            for (int i = 0; i < numJobs; i++) {
//                progressBar.setValue(i);
//            }
            pageNum++;

            //Close frame after completion
            if (getNumJobsOnPage() < 50) {
                callRequest = 0;
            }

        }
    }

    //joe silveira
    //Method to ping link and get response from the page
    //The method then takes the json results and adds them to an array list "jobsList"
    public void makeGetRequest(String link) {
        try {

            /*
            Lines starting from 'client' to 'body' were accomplished with help from
            https://github.com/jsantore/GsonDemo1/blob/master/src/main/java/bsu/comp152/DataHandler.java
             */

            //Instantiate Connection
            client = HttpClient.newHttpClient();
            var requestBuilder = HttpRequest.newBuilder();
            var dataRequest = requestBuilder.uri(URI.create(link)).build();

            //Handle HTTP Response
            HttpResponse<String> response;
            response = client.send(dataRequest, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                JOptionPane.showMessageDialog(null, "No connection! Try Again!");
                System.exit(0);
            }
            // System.out.println(response);
            var body = response.body();

            //Convert body to gson objects using class
            /*
            gson conversion was accomplished with help from
            https://stackoverflow.com/questions/9598707/gson-throwing-expected-begin-object-but-was-begin-array
             */


            GsonBuilder builder = new GsonBuilder();
            builder.serializeNulls();
            Gson g = builder.create();


            Type collectionType = new TypeToken<Collection<GithubModel>>() {
            }.getType();
            responseJobsList = g.fromJson(body, collectionType);

            countJobs();
            addJobsToArray();

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Connection Error. Please check your connection and run again.");
            System.exit(0);
        }

    }

    //Joe Silveira
    //this method adds the complete job to the array
    public void addJobsToGitHubDB() {
        for (GithubModel GithubModel : jobsList) {
            try {
                dbFun.addJobToGithubJobsTable(GithubModel.getId(), GithubModel.getType(), GithubModel.getUrl(),
                        GithubModel.getCreated_at(), GithubModel.getCompany(), GithubModel.getCompany_url(),
                        GithubModel.getLocation(), GithubModel.getTitle(), GithubModel.getDescription(),
                        GithubModel.getHow_to_apply(), GithubModel.getCompany_logo());
                recordAdded++;
            } catch (Exception e) {
                e.printStackTrace();
                e.getCause();
            }
        }
    }

    //Joe Silveira
    //Method to add the response jobs list to jobs list
    private void addJobsToArray() {
        jobsList.addAll(responseJobsList);
    }

    //Joe silveira
    //Method to print the job paramters
    public void printJob() {
        totalJobs = 0;
        for (GithubModel GithubModel : jobsList) {
            System.out.println("Job:" + totalJobs + "\n" +
                    "   " + "Job ID: " + GithubModel.getId() + "\n" +
                    "   " + "Job Type: " + GithubModel.getType() + "\n" +
                    "   " + "Github URL: " + GithubModel.getUrl() + "\n" +
                    "   " + "Created At: " + GithubModel.getCreated_at() + "\n" +
                    "   " + "Company: " + GithubModel.getCompany() + "\n" +
                    "   " + "Company URL: " + GithubModel.getCompany_url() + "\n" +
                    "   " + "Job Location: " + GithubModel.getLocation() + "\n" +
                    "   " + "Job Title: " + GithubModel.getTitle() + "\n" +
                    "   " + "Job Description: " + "\n" +
                    "   " + "How to apply: " + "\n" +
                    "   " + "Company Logo: " + GithubModel.getCompany_logo() + "\n"
            );
            totalJobs++;
        }
    }

    //Joe Silveira
    //Method to count the num of jobs and set totalJobs =
    public void countJobs() {
        numJobsOnPage = 0;
        for (int i = 0; i < responseJobsList.size(); i++) {
            numJobsOnPage++;
            totalJobs++;
        }
    }

    //Joe silveira
    //Getter for total number of jobs on each page
    public int getNumJobsOnPage() {
        return this.numJobsOnPage;
    }

    public int getTotalJobs() {
        return this.totalJobs;
    }

    //Joe silveira
    //Getter for the jobslist
    public ArrayList<GithubModel> getJobsList() {
        return jobsList;
    }

}


/*
Joe Silveira

Class to handle all http requests and responses
 */

package connections;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import dbHandler.DBFunctions;
import json.ResponseData;

import javax.swing.*;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Collection;

public class HTTPRequest {

    //Java class variables
    HttpClient client;

    //Class Variables
    ArrayList<ResponseData> responseJobsList;
    ArrayList<ResponseData> jobsList = new ArrayList<>();
    DBFunctions dbFun = new DBFunctions();
    int numJobsOnPage = 0;
    int totalJobs = 0;


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
            HttpResponse<String> response = null;
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


            Type collectionType = new TypeToken<Collection<ResponseData>>() {
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
    public void addJobToDB() {
        for (int i = 0; i < jobsList.size(); i++) {
            try {
                dbFun.addJobJobsDatabase(jobsList.get(i).getId(), jobsList.get(i).getType(), jobsList.get(i).getUrl(),
                        jobsList.get(i).getCreated_at(), jobsList.get(i).getCompany(), jobsList.get(i).getCompany_url(),
                        jobsList.get(i).getLocation(), jobsList.get(i).getTitle(), jobsList.get(i).getDescription(),
                        jobsList.get(i).getHow_to_apply(), jobsList.get(i).getCompany_logo());
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
        for (int i = 0; i < jobsList.size(); i++) {
            System.out.println("Job:" + totalJobs + "\n" +
                    "   " + "Job ID: " + jobsList.get(i).getId() + "\n" +
                    "   " + "Job Type: " + jobsList.get(i).getType() + "\n" +
                    "   " + "Github URL: " + jobsList.get(i).getUrl() + "\n" +
                    "   " + "Created At: " + jobsList.get(i).getCreated_at() + "\n" +
                    "   " + "Company: " + jobsList.get(i).getCompany() + "\n" +
                    "   " + "Company URL: " + jobsList.get(i).getCompany_url() + "\n" +
                    "   " + "Job Location: " + jobsList.get(i).getLocation() + "\n" +
                    "   " + "Job Title: " + jobsList.get(i).getTitle() + "\n" +
                    "   " + "Job Description: " + "\n" +
                    "   " + "How to apply: " + "\n" +
                    "   " + "Company Logo: " + jobsList.get(i).getCompany_logo() + "\n"
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
    public ArrayList<ResponseData> getJobsList() {
        return jobsList;
    }

    //**********************************Depricated Methods and Variables******************

    //ArrayList<String> jobsTitleList = new ArrayList<String>();

    //joe Silveira
    //This method takes just the job "title" from the array list of jobs and adds them to the arraylist
    //"jobsTitleList"
//    public void addTitleToArray() {
//        numJobsOnPage = 0;
//        for (int i = 0; i < jobsList.size(); i++) {
//            jobsTitleList.add(jobsList.get(i).getTitle());
//            numJobsOnPage++;
//            totalJobs++;
//        }
//    }

    //joe silveira
    //this method adds each job title to an array
//    public void addtoDB() throws SQLException {
////        //Loop to add jobs to database
////        for (int i = 0; i < jobsTitleList.size(); i++) {
////            dbfun.addTitleToJobsDatabaseTable(jobsTitleList.get(i));
////        }
////    }

    //Joe Silveira
    //Method to get jobs title list
//    public ArrayList<String> getJobsTitleList() {
//        return this.jobsTitleList;
//    }
}


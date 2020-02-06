/*
Joe Silveira

Class to handle all http requests and responses
 */

package connections;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import json.ResponseData;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Collection;

public class HTTPRequest {

    //Java class variables
    URL url;
    HttpURLConnection connection = null;
    HttpClient client;
    BufferedReader buffRead;
    InputStreamReader readIn;

    //Class Variables
    StringBuffer stringBuff = new StringBuffer();
    ArrayList<String> jobsTitleList = new ArrayList<String>();
    ArrayList<ResponseData> jobsList;
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
            client = (HttpClient) java.net.http.HttpClient.newHttpClient();
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

            Gson g = new Gson();
            Type collectionType = new TypeToken<Collection<ResponseData>>() {
            }.getType();
            jobsList = g.fromJson(body, collectionType);

            //Call method to add job titles to arrayList
            addTitleToArray();


        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Connection Error. Please check your connection and run again.");
            System.exit(0);
        }
    }

    //joe Silveira
    //This method takes just the job "title" from the array list of jobs and adds them to the arraylist
    //"jobsTitleList"
    public void addTitleToArray() {
        numJobsOnPage = 0;
        for (int i = 0; i < jobsList.size(); i++) {
            jobsTitleList.add(jobsList.get(i).getTitle());
            numJobsOnPage++;
            totalJobs++;
        }
    }

    //Joe silveira
    //Method to print the total number of jobs there are
    public void printResults() {
        for (int i = 0; i < jobsTitleList.size(); i++) {
            System.out.println(jobsTitleList.get(i));
        }
    }

    //Joe silveira
    //Getter for total number of jobs on each page
    public int getNumJobsOnPage() {
        return this.numJobsOnPage;
    }

    //Joe Silveira
    //Method to get jobs title list
    public ArrayList<String> getJobsTitleList() {
        return this.jobsTitleList;
    }
}


/*
Joe Silveira

Class to handle all http requests and responses
 */

package Connections;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

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

    //Connection Variables
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

    /*
    **Joe silveira**
    Method to ping link and get response from the page
    The method then takes the json results and adds them to an array list "jobsList"
     */
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

            System.out.println("\n" + "Total number of Jobs on page: " + numJobsOnPage);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /*
    **Joe Silveira**
    This method takes just the job "title" from the array list of jobs and adds them to the arraylist
    "jobsTitleList"
     */
    public void addTitleToArray() {
        numJobsOnPage = 0;
        for (int i = 0; i < jobsList.size(); i++) {
            jobsTitleList.add(jobsList.get(i).getTitle());
            numJobsOnPage++;
            totalJobs++;
        }
    }

    /*
    **Joe silveira**
    Method to print the total number of jobs there are
     */
    public void printResults() {
        for (int i = 0; i < jobsTitleList.size(); i++) {
            System.out.println(jobsTitleList.get(i));
        }
        System.out.println("Total number of jobs: " + totalJobs);
    }

    /*
    **Joe silveira**
    Getter for total number of jobs on each page
     */
    public int getNumJobsOnPage() {
        return this.numJobsOnPage;
    }

}

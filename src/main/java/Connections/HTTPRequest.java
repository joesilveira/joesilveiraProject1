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
    int responseCode;
    java.lang.String inputLine;


    public void makeGetRequest(String link) {
        try {
            //Instantiate Connection
            client = (HttpClient) java.net.http.HttpClient.newHttpClient();
            var requestBuilder = HttpRequest.newBuilder();
            var dataRequest = requestBuilder.uri(URI.create(link)).build();

            //Handle HTTP Response
            HttpResponse<String> response = null;
            response = client.send(dataRequest, HttpResponse.BodyHandlers.ofString());
            // System.out.println(response);
            var body = response.body();

            //Print for testing
            System.out.println(body);
            //System.out.println("GSON");

            //Convert body to gson objects using class
            Gson g = new Gson();
            Type collectionType = new TypeToken<Collection<ResponseData>>() {
            }.getType();
            ArrayList<ResponseData> jobsList = g.fromJson(body, collectionType);

            int numJobs = 0;
            for (int i = 0; i < jobsList.size(); i++) {
                jobsTitleList.add(jobsList.get(i).getTitle());
                //System.out.println(jobsList.get(i).getTitle());
                numJobs++;
            }

            System.out.println("\n" + "Total number of Jobs: " + numJobs);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void printResults() {
        for (int i = 0; i < jobsTitleList.size(); i++) {
            System.out.println(jobsTitleList.get(i));
        }
    }

}

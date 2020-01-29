/*
    Joe Silveira

    Main class to handle runtime class
    This class will execute all necessary methods and functions in other classes
    for the program to run
 */

import Connections.HTTPRequest;

import java.util.ArrayList;

//main method
//Joe Silveira
public class main {

    public static void main(String args[]) {


        String url = "https://jobs.github.com/positions.json";

        HTTPRequest http = new HTTPRequest();
        http.makeGetRequest(url);

        int jobsCount = 0;

        for (int i = 0; i < jobsCount; i++) {

        }

        http.printResults();

    }
}

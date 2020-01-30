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

        int pageNum = 1;


        HTTPRequest http = new HTTPRequest();

        //http.makeGetRequest(url);

        for (int i = 0; i < 5; i++) {
            System.out.println("WHILE LOOP" + i);
            String url = "https://jobs.github.com/positions.json?page=" + pageNum;
            System.out.println(url);
            http.makeGetRequest(url);
            pageNum++;
            //System.out.println(http.getNumJobsOnPage());
        }


        http.printResults();

    }
}

/*
    Joe Silveira

    Main class to handle runtime class
    This class will execute all necessary methods and functions in other classes
    for the program to run
 */

import Connections.HTTPRequest;
import FileIO.FileResource;

public class main {

    /*
    Runner class with main method
     */
    public static void main(String args[]) {

        //Class Variables
        int pageNum = 1;
        int callRequest = 1;
        HTTPRequest http = new HTTPRequest();
        FileResource fileIO = new FileResource();

        //http.makeGetRequest(url);

        /*
        Loop to ping api
         */
        int i = 1;
        while (callRequest == 1) {
            System.out.println("WHILE LOOP " + i);
            String url = "https://jobs.github.com/positions.json?page=" + pageNum;
            System.out.println(url);
            http.makeGetRequest(url);
            pageNum++;


            if (http.getNumJobsOnPage() < 50) {
                callRequest = 0;
            }
            i++;
        }

//        for (int i = 0; i < 5; i++) {
//            System.out.println("WHILE LOOP" + i);
//            String url = "https://jobs.github.com/positions.json?page=" + pageNum;
//            System.out.println(url);
//            http.makeGetRequest(url);
//            pageNum++;
//            //System.out.println(http.getNumJobsOnPage());
//        }

        //Print results for testing purposes
        http.printResults();

        //Show save dialog
        fileIO.createNewFile();
    }
}

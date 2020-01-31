/*
    Joe Silveira

    Main class to handle runtime class
    This class will execute all necessary methods and functions in other classes
    for the program to run
 */

import Connections.HTTPRequest;
import FileIO.FileResource;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class main {

    /*
    Runner class with main method
     */
    public static void main(String args[]) throws IOException {

        //Class Variables
        int pageNum = 1;
        int callRequest = 1;
        int numJobs = 0;
        String fileName = "jobsAPI.txt";
        HTTPRequest http = new HTTPRequest();
        FileResource fileIO = new FileResource();
        JProgressBar progressBar = new JProgressBar();
        JFrame frame = new JFrame();

        //http.makeGetRequest(url);
        JOptionPane.showMessageDialog(null, "Welcome to the Github Jobs Fetcher");

        //Progress bar
        progressBar.setStringPainted(true);
        progressBar.setBorderPainted(true);
        progressBar.setForeground(Color.green.darker());

        frame.setSize(500, 200);
        frame.setTitle("Fetching Jobs...");
        frame.add(progressBar);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);


        /*
        Loop to ping api
         */
        while (callRequest == 1) {
            //System.out.println("WHILE LOOP " + i);
            String url = "https://jobs.github.com/positions.json?page=" + pageNum;
            //System.out.println(url);
            http.makeGetRequest(url);

            numJobs += http.getNumJobsOnPage();
            //progressBar.setMinimum(0);
            progressBar.setMaximum(numJobs);
            for (int i = 0; i < numJobs; i++) {
                progressBar.setValue(i);
            }
            pageNum++;


            if (http.getNumJobsOnPage() < 50) {
                callRequest = 0;
                frame.dispose();
            }

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
        //http.printResults();

        //Show save dialog
        fileIO.createFile(fileName);
        fileIO.writeToFile(http.getJobsTitleList());

        System.exit(0);
    }
}

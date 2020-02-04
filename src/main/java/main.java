/*
    Joe Silveira

    Main class to handle runtime class
    This class will execute all necessary methods and functions in other classes
    for the program to run
 */

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class main {


    public static void main(String args[]) throws IOException {

        //Instance Variables
        JProgressBar progressBar = new JProgressBar();
        JFrame frame = new JFrame();
        Handler d = new Handler();

        //Quick message to user
        JOptionPane.showMessageDialog(null, "Welcome to the Github Jobs Fetcher");

        //Progress bar init
        progressBar.setStringPainted(true);
        progressBar.setBorderPainted(true);
        progressBar.setForeground(Color.green.darker());

        //frame init
        frame.setSize(500, 200);
        frame.setTitle("Fetching Jobs...");
        frame.add(progressBar);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);

        //Make Ping
        d.pingAPI(progressBar, frame);

        //Show the save
        d.showSaveDialog();

        //Close program
        System.exit(0);
    }
}

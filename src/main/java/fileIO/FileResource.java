/*
Joe Silveira

Class to handle file i/o
 */

package fileIO;


import connections.HTTPRequest;
import json.ResponseData;
import screens.mainScreen;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class FileResource {

    //Class Variables
    String fileDirectory;
    File userFile;
    File programFile;

    //Java class variables
    JFileChooser fileChooser = new JFileChooser();
    HTTPRequest http = new HTTPRequest();
    mainScreen ms = new mainScreen();
    BufferedWriter writer;
    JFrame frame = new JFrame();

    //Joe Silveira
    //Method to create file using jfilechooser
    public void createFile(String fileName) throws IOException {
        int selected = 1;

        //loop to make sure user selects a directory
        while (selected == 1) {
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            fileChooser.setDialogTitle("Select File Save Location");
            int result = fileChooser.showDialog(frame, "Choose Directory");
            if (result == JFileChooser.CANCEL_OPTION) {
                JOptionPane.showMessageDialog(null, "Please select a directory to save jobs list");
            } else {
                selected = 0;
            }
        }

        fileDirectory = fileChooser.getSelectedFile().getAbsolutePath();
        this.userFile = new File(fileDirectory + '\\' + fileName);

        if (this.userFile.exists()) {
            int clear = ms.askToClearFile();
            if (clear == 0) {
                userFile.delete();
            }
        }
        if (this.userFile.createNewFile()) {
        }
    }

    //Joe Silveira
    //Method to create file in program for testing purposes
    public void createProgramFile(String fileName) throws IOException {
        this.programFile = new File(fileName);
        if (programFile.createNewFile()) {
            //System.out.println("file created");
        } else {
            //System.out.println("File not created");
        }
    }

    //Joe Silveira
    //Method to write contents of jobs title to file
    public void writeJobsToFile(ArrayList<ResponseData> list, File file) throws IOException {

        writer = new BufferedWriter(new FileWriter(file, true));

        //Bunch of pretty formatting
        writer.append("***********************************************************************************");
        writer.newLine();
        writer.append("Request Date: ").append(String.valueOf(LocalDateTime.now()));
        writer.newLine();
        writer.append("***********************************************************************************");
        writer.newLine();
        writer.append("Total Number of Jobs: ").append(String.valueOf(list.size()));
        writer.newLine();
        writer.newLine();

        //loop to write arraylist to file
        for (int i = 0; i < list.size(); i++) {
            writer.append("Job: ").append(String.valueOf(i + 1));
            writer.newLine();
            writer.append(list.get(i).toString());
            writer.newLine();
        }

        writer.append("***********************************************************************************");
        writer.close();


//        //Open the file if supported
//        if (Desktop.isDesktopSupported()) {
//            Desktop.getDesktop().open(file);
//        }

    }


    //Joe silveira
    //Method to get user file path
    //Returns the user file path
    public File getUserFilePath() {
        return this.userFile;
    }

    //Joe silveira
    //Method to get program file path
    //Returns the program file path
    public File getProgramFile() {
        return this.programFile;
    }
}

/*
Joe Silveira

Class to handle file i/o
 */

package fileIO;


import connections.HTTPRequest;

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

        if (this.userFile.createNewFile()) {
            //System.out.println("file created");
        } else {
            //System.out.println("File esists");
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
    public void writeToFile(ArrayList<String> list, File file) throws IOException {
        //Bunch of pretty formatting
        writer = new BufferedWriter(new FileWriter(file, true));
        writer.append("***********************************************************************************");
        writer.newLine();
        writer.append("Request Date: " + LocalDateTime.now());
        writer.newLine();
        writer.append("***********************************************************************************");
        writer.newLine();
        writer.append("Total Number of Jobs: " + (list.size()));
        writer.newLine();
        writer.newLine();

        //loop to write arraylist to file
        for (int i = 0; i < list.size(); i++) {
            writer.append(list.get(i));
            writer.newLine();
        }
        writer.append("***********************************************************************************");
        //System.out.println(file.getAbsolutePath());
        writer.close();
        JOptionPane.showMessageDialog(null, "All Jobs written to file" + "\n" + "File Location: " + file.getAbsolutePath());

        //Open the file if supported
        if (Desktop.isDesktopSupported()) {
            Desktop.getDesktop().open(file);
        }

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

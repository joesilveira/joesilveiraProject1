/*
Joe Silveira

Class to handle file i/o
 */

package FileIO;


import Connections.HTTPRequest;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.http.HttpRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class FileResource {

    //Class Variables
    File file;
    String fileDirectory;
    JFrame frame = new JFrame();
    JFileChooser fileChooser = new JFileChooser();
    HTTPRequest http;

    BufferedWriter writer;

    public void createFile(String fileName) throws IOException {
        int selected = 1;
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
        file = new File(fileDirectory + '\\' + fileName);

        //System.out.println(file);

        if (file.createNewFile()) {
            //System.out.println("file created");
        } else {
            //System.out.println("File esists");
        }
    }

    public void writeToFile(ArrayList<String> list) throws IOException {
        writer = new BufferedWriter(new FileWriter(file, true));
        writer.append("***********************************************************************************");
        writer.newLine();
        writer.append("Request Date: " + LocalDateTime.now());
        writer.newLine();
        writer.append("***********************************************************************************");
        writer.newLine();
        writer.append("Total Number of Jobs: " + (list.size() + 1));
        writer.newLine();
        writer.newLine();

        for (int i = 0; i < list.size(); i++) {
            writer.append(list.get(i));
            writer.newLine();
        }
        writer.append("***********************************************************************************");
        //System.out.println(file.getAbsolutePath());
        writer.close();
        JOptionPane.showMessageDialog(null, "All Jobs written to file");

        if (Desktop.isDesktopSupported()) {
            Desktop.getDesktop().open(file);
        }

    }
}

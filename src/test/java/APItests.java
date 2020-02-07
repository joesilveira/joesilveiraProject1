//joe silveira

import org.junit.Assert;
import org.junit.Test;
import runner.runtimeHandler;

import javax.swing.*;
import java.io.*;

public class APItests {

    runtimeHandler handler = new runtimeHandler();


    //joe silveira
    //Method to test that there are more than 100 jobs
    @Test
    public void runJobNumTest() {
        System.setProperty("java.awt.headless", "true");
        int num = handler.pingAPI();
        //System.out.println(num);

        try {
            Assert.assertTrue(num > 100);
            //System.out.println("There are more than 100 jobs! Test passed.");
        } catch (Exception e) {
            //System.out.println("There are less than 100 jobs. Test Failed.");
        }

    }

    //joe silveira
    //Method to test a string that is in the file
    @Test
    public void runStringTest() throws IOException {
        System.setProperty("java.awt.headless", "true");
        /*
        Depricated for auto testing
         */
        //Initiliaze file chooser
//        JFileChooser chooser = new JFileChooser();
//        JFrame frame = new JFrame();
//        chooser.showOpenDialog(frame);
//        File file = chooser.getSelectedFile();
//        System.out.println(file);

        File file = new File(handler.fileName);
        //Initiliaze file reader
        FileReader fReader = new FileReader(file);
        BufferedReader reader = new BufferedReader(fReader);

        //Depricated for auto testing
        //String userEntry = JOptionPane.showInputDialog("Please Enter the name of the job to see if it is in the file");

        String checkString = "Software Developer";


        String line = "";
        int loop = 0;
        boolean containsString = true;

        while ((line = reader.readLine()) != null && loop == 0) {

            //convert both to lower case
            line = line.toLowerCase();
            checkString = checkString.toLowerCase();

            //try comparing
            if (line.equals(checkString)) {
                loop = 1;
                containsString = true;
            } else {
                containsString = false;
            }

        }

        //For testing
        try {
            Assert.assertTrue(containsString);
            System.out.println("The file contains the job: " + checkString + " that you entered. Test Passed!");
        } catch (AssertionError e) {
            System.out.println("The file does not contain the job that you entered. Please check to make sure " +
                    "you entered the job in correctly");
            Assert.fail();
        }
    }
}

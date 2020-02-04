//joe silveira

import org.junit.Assert;
import org.junit.Test;

import javax.swing.*;
import java.io.*;

public class APItests {

    Handler d = new Handler();

    @Test
    public void runJobNumTest() {

        int num = d.pingAPI();
        //System.out.println(num);

        try {
            Assert.assertTrue(num > 100);
            System.out.println("There are more than 100 jobs! Test passed.");
        } catch (Exception e) {
            System.out.println("There are less than 100 jobs. Test Failed.");
        }

    }

    @Test
    public void runStringTest() throws IOException {

        //Initiliaze file chooser
        JFileChooser chooser = new JFileChooser();
        JFrame frame = new JFrame();
        chooser.showOpenDialog(frame);
        File file = chooser.getSelectedFile();
        System.out.println(file);

        //Initiliaze file reader
        FileReader fReader = new FileReader(file);
        BufferedReader reader = new BufferedReader(fReader);


        String userEntry = JOptionPane.showInputDialog("Please Enter the name of the job to see if it is in the file");
        String checkString = userEntry;


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
            System.out.println("The file contains the job: " + userEntry + " that you entered. Test Passed!");
        } catch (AssertionError e) {
            System.out.println("The file does not contain the job that you entered. Please check to make sure " +
                    "you entered the job in correctly");
            Assert.fail();
        }
    }
}

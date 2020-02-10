//joe silveira

import org.junit.Assert;
import org.junit.Test;
import runner.runtimeHandler;
import testing.testMethods;

import javax.swing.*;
import java.io.*;
import java.sql.SQLException;

public class APItests {


    testMethods test = new testMethods();

    //joe silveira
    //Method to test that there are more than 100 jobs
    //Depricated in project 2
    @Test
    public void jobNumTest() {

//        int num = test.pingAPI();
//        //System.out.println(num);
//
//        try {
//            Assert.assertTrue(num > 100);
//            System.out.println("There are more than 100 jobs! Test passed.");
//        } catch (Exception e) {
//            System.out.println("There are less than 100 jobs. Test Failed.");
//        }

    }

    //joe silveira
    //Method to test a string that is in the file
    //depricated in project 2
    @Test
    public void fileStringTest() throws IOException {

        /*
        Depricated for auto testing
         */
        //Initiliaze file chooser
//        JFileChooser chooser = new JFileChooser();
//        JFrame frame = new JFrame();
//        chooser.showOpenDialog(frame);
//        File file = chooser.getSelectedFile();
//        System.out.println(file);

//        File file = new File(test.getFileName());
//        //Initiliaze file reader
//        FileReader fReader = new FileReader(file);
//        BufferedReader reader = new BufferedReader(fReader);
//
//        //Depricated for auto testing
//        //String userEntry = JOptionPane.showInputDialog("Please Enter the name of the job to see if it is in the file");
//
//        String checkString = "Software Developer";
//
//
//        String line = "";
//        int loop = 0;
//        boolean containsString = true;
//
//        while ((line = reader.readLine()) != null && loop == 0) {
//
//            //convert both to lower case
//            line = line.toLowerCase();
//            checkString = checkString.toLowerCase();
//
//            //try comparing
//            if (line.equals(checkString)) {
//                loop = 1;
//                containsString = true;
//            } else {
//                containsString = false;
//            }
//
//        }
//
//        try {
//            Assert.assertTrue(containsString);
//            System.out.println("The file contains the job: " + checkString + " that you entered. Test Passed!");
//        } catch (AssertionError e) {
//            System.out.println("The file does not contain the job that you entered. Please check to make sure " +
//                    "you entered the job in correctly");
//            Assert.fail();
//        }
    }

    //Joe Silveira
    //Method to test that the data was stored properly
    @Test
    public void dbStringTest() {
        String jobName = "Site Reliability Engineers";
        int matchTest = test.checkString(jobName);
        try {
            //System.out.println(matchTest);
            Assert.assertEquals(1, matchTest);
            System.out.println("The job name " + jobName + " is in the database");
        } catch (AssertionError e) {
            System.out.println("The job name " + jobName + " is not in the database");
            Assert.fail();
        }
    }

    //Joe Silveira
    //Test to check that a given table name exists in the database
    @Test
    public void dbTableCheck() throws SQLException {
        String tableName = "Job_Titles";
        int tableExists = test.checkTable(tableName);
        try {
            Assert.assertEquals(1, tableExists);
            System.out.println("The table " + tableName + " exists in the database");
        } catch (AssertionError e) {
            System.out.println("The table " + tableName + " does not exist in the database");
        }
    }
}


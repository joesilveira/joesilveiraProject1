//joe silveira

import org.junit.Assert;
import org.junit.Test;
import runner.runtimeHandler;
import testing.testMethods;

import javax.swing.*;
import java.io.*;
import java.sql.SQLException;
import java.util.ArrayList;

public class APItests {


    testMethods test = new testMethods();

    //joe silveira
    //Method to test that there are more than 100 jobs
    //Depricated in project 2
//    @Test
//    public void jobNumTest() {

//        int num = test.pingAPI();
//        //System.out.println(num);
//
//        try {
//            Assert.assertTrue(num > 100);
//            System.out.println("There are more than 100 jobs! Test passed.");
//        } catch (Exception e) {
//            System.out.println("There are less than 100 jobs. Test Failed.");
//        }

//    }

    //joe silveira
    //Method to test a string that is in the file
    //depricated in project 2
//    @Test
//    public void fileStringTest() throws IOException {

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
//    }

    //Joe Silveira
    //Method to test that the data was stored properly
    @Test
    public void dbStringTest() {
        System.out.println();
        System.out.println("****Preforming database entry check*****");
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
        System.out.println();
        System.out.println("****Preforming database table check*****");
        String tableName = "Job_Titles";
        int tableExists = test.checkTable(tableName);
        try {
            Assert.assertEquals(1, tableExists);
            System.out.println("The table " + tableName + " exists in the database");
        } catch (AssertionError e) {
            System.out.println("The table " + tableName + " does not exist in the database");
        }
    }

    //Joe Silveira
    //Test to make sure the function to save to the database works properly
    @Test
    public void dbSaveCheck() {
        System.out.println();
        System.out.println("****Preforming database save check*****");
        ArrayList<String> goodStrings = new ArrayList<>();
        ArrayList<String> badStrings = new ArrayList<>();


        //Add good data to good strings
        goodStrings.add("Joes database");
        goodStrings.add("New Job");
        goodStrings.add("My job");

        //Add bad data to bad strings
        badStrings.add("INSERT INTO");
        badStrings.add("*");
        badStrings.add("*)");


        //Test good strings
        for (int i = 0; i < goodStrings.size(); i++) {
            try {
                int stringTest1 = test.checkSave(goodStrings.get(i));
                Assert.assertEquals(1, stringTest1);
                System.out.println(goodStrings.get(i) + " is a valid entry. Test Passed");
            } catch (AssertionError e) {
                Assert.fail(goodStrings.get(i) + " is not a valid entry. Test Failed");
            }
        }

        //test bad strings
        for (int i = 0; i < badStrings.size(); i++) {
            try {
                int stringTest2 = test.checkSave(badStrings.get(i));
                Assert.assertEquals(0, stringTest2);
                System.out.println(badStrings.get(i) + " is not valid entry but was caught. Test Passed");
            } catch (AssertionError e) {
                Assert.fail(badStrings.get(i) + " is not a valid entry but was not caught. Test Failed");
            }
        }
    }

    //Joe Silveira
    //Test to make sure the primary key in the table exists and is not null
    @Test
    public void primaryKeyCheck() {
        System.out.println();
        System.out.println("****Preforming Primary key null value check*****");
        try {
            int notNull = test.checkPrimary();
            Assert.assertEquals(0, notNull);
            System.out.println("Primary key column 'id' does not contain a null value. Test Passed!");
        } catch (AssertionError e) {
            Assert.fail("Primary key column 'id' contains a null value. Test Failed");
        }
    }
}


/*
    Joe Silveira

    Main class to handle runtime class
    This class will execute all necessary methods and functions in other classes
    for the program to run
 */

import runner.runtimeHandler;

import java.io.IOException;
import java.sql.SQLException;

public class main {


    public static void main(String args[]) throws IOException, SQLException {

        runtimeHandler handler = new runtimeHandler();
        handler.startProgram();

        //try db
        //DBFunctions dbFun = new DBFunctions();
        //DatabaseHandler dbHandler = new DatabaseHandler();
        //dbHandler.initDatabase();
        //dbFun.createTable("jobs","");
        //dbFun.addColumn("jobs","jobID","int");

        //Close program
        System.exit(0);
    }
}

/*
    Joe Silveira

    Main class to handle runtime class
    This class will execute all necessary methods and functions in other classes
    for the program to run
 */

import runner.runtimeHandler;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.sql.SQLException;

public class main {


    public static void main(String[] args) throws IOException, SQLException, XMLStreamException {

        //Start program
        runtimeHandler handler = new runtimeHandler();
        handler.startProgram();

        //Close program
        System.exit(0);
    }
}

/*
    Joe Silveira

    Main class to handle runtime class
    This class will execute all necessary methods and functions in other classes
    for the program to run
 */

import dbHandler.DBFunctions;
import dbHandler.DatabaseHandler;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class main extends Application {


    public static void main(String[] args) throws Exception {
        DatabaseHandler dbHandler = new DatabaseHandler();
        dbHandler.initAllTables();
        DBFunctions dbFunc = new DBFunctions();
        //dbFunc.addJobLatLng("My City",100.00,100);
        //dbFunc.checkLocationExistsInDB("RocklandMA", 10.20, 11.00, "geocodes");
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("startView.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
        primaryStage.setResizable(true);
        primaryStage.sizeToScene();
        primaryStage.show();


    }
}

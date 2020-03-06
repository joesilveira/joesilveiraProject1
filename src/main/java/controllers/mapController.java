package controllers;

import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.MapComponentInitializedListener;
import com.lynden.gmapsfx.javascript.object.*;
import dbHandler.DBFunctions;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ResourceBundle;

public class mapController implements Initializable, MapComponentInitializedListener {

    @FXML
    private AnchorPane mapView;

    @FXML
    private Label totalResultsLabel;

    @FXML
    private ComboBox<String> titleSearch;

    @FXML
    private GoogleMapView gMap;

    @FXML
    private ComboBox<String> locationSearch;

    private GoogleMap map;

    //Arrays for job names
    ArrayList<String> gJobNames = new ArrayList<>();
    ArrayList<String> stackJobNames = new ArrayList<>();
    ArrayList<String> allJobNames = new ArrayList<>();

    //Arrays for job locations

    ArrayList<String> gJobsLocations = new ArrayList<>();
    ArrayList<String> stackJobLocations = new ArrayList<>();
    ArrayList<String> allJobLocations = new ArrayList<>();

    private DBFunctions dbFunc = new DBFunctions();


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        gMap.addMapInializedListener(this);
        populateSearchBox();

    }

    @Override
    public void mapInitialized() {
        LatLong joeSmithLocation = new LatLong(47.6197, -122.3231);
        LatLong joshAndersonLocation = new LatLong(47.6297, -122.3431);
        LatLong bobUnderwoodLocation = new LatLong(47.6397, -122.3031);
        LatLong tomChoiceLocation = new LatLong(47.6497, -122.3325);
        LatLong fredWilkieLocation = new LatLong(47.6597, -122.3357);


        //Set the initial properties of the map.
        MapOptions mapOptions = new MapOptions();

        mapOptions.center(new LatLong(47.6097, -122.3331))
                .overviewMapControl(false)
                .panControl(false)
                .rotateControl(false)
                .scaleControl(false)
                .streetViewControl(false)
                .zoomControl(false)
                .zoom(12);

        map = gMap.createMap(mapOptions);

        //Add markers to the map
        MarkerOptions markerOptions1 = new MarkerOptions();
        markerOptions1.position(joeSmithLocation);

        MarkerOptions markerOptions2 = new MarkerOptions();
        markerOptions2.position(joshAndersonLocation);

        MarkerOptions markerOptions3 = new MarkerOptions();
        markerOptions3.position(bobUnderwoodLocation);

        MarkerOptions markerOptions4 = new MarkerOptions();
        markerOptions4.position(tomChoiceLocation);

        MarkerOptions markerOptions5 = new MarkerOptions();
        markerOptions5.position(fredWilkieLocation);

        Marker joeSmithMarker = new Marker(markerOptions1);
        Marker joshAndersonMarker = new Marker(markerOptions2);
        Marker bobUnderwoodMarker = new Marker(markerOptions3);
        Marker tomChoiceMarker = new Marker(markerOptions4);
        Marker fredWilkieMarker = new Marker(markerOptions5);

        map.addMarker(joeSmithMarker);
        map.addMarker(joshAndersonMarker);
        map.addMarker(bobUnderwoodMarker);
        map.addMarker(tomChoiceMarker);
        map.addMarker(fredWilkieMarker);

        InfoWindowOptions infoWindowOptions = new InfoWindowOptions();
        infoWindowOptions.content("<h2>Fred Wilkie</h2>"
                + "Current Location: Safeway<br>"
                + "ETA: 45 minutes");

        com.lynden.gmapsfx.javascript.object.InfoWindow fredWilkeInfoWindow = new InfoWindow(infoWindowOptions);
        fredWilkeInfoWindow.open(map, fredWilkieMarker);
    }

    //Joe Silveira
    //Method to populate the combo box with job names and companys
    private void populateSearchBox() {
        //totalResultsLabel = new Label("Label");
        gJobNames = dbFunc.getGithubJobNames();
        stackJobNames = dbFunc.getStackOverFlowJobNames();

        //add github jobs list to all jobs list
        allJobNames.addAll(gJobNames);

        //add stack overflow jobs to all jobs list
        allJobNames.addAll(stackJobNames);

        //beautify all jobs array
        Collections.sort(allJobNames);

        //loop to add all jobs to combo box
        for (int i = 0; i < allJobNames.size(); i++) {
            titleSearch.getItems().addAll(allJobNames.get(i));
        }

        //Edit label text
        totalResultsLabel.setText("Total Jobs on map: " + allJobNames.size());
    }

    //Joe Silviera
    //Method to populate the combo box with job locations
    private void populateLocationBox() {

    }

    @FXML
    void searchNameByInput(ActionEvent event) {

    }

    @FXML
    void searchLocationByInput(ActionEvent event) {

    }

}

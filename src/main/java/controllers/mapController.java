package controllers;

import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.MapComponentInitializedListener;
import com.lynden.gmapsfx.javascript.JavascriptObject;
import com.lynden.gmapsfx.javascript.event.UIEventHandler;
import com.lynden.gmapsfx.javascript.event.UIEventType;
import com.lynden.gmapsfx.javascript.object.*;
import com.lynden.gmapsfx.service.geocoding.GeocodingService;
import com.lynden.gmapsfx.service.geocoding.GeocodingServiceCallback;
import connectionRequests.GeoCoding;
import dataTypes.AllJobsModel;
import dataTypes.GeoCodeModel;
import dbHandler.DBFunctions;
import dbHandler.Jobfunctions;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import netscape.javascript.JSObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class mapController extends JavascriptObject implements Initializable, MapComponentInitializedListener {

    private static final Logger LOG = LoggerFactory.getLogger(GeocodingService.class);

    public GeocodingServiceCallback callback;

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

    @FXML
    private ProgressIndicator progressIndicator;

    @FXML
    private Text progressPaneLabel;

    @FXML
    private AnchorPane progressPane;

    //Arrays for job names
    ArrayList<String> gJobNames = new ArrayList<>();
    ArrayList<String> stackJobNames = new ArrayList<>();
    ArrayList<String> allJobNames = new ArrayList<>();

    //Arrays for job locations

    ArrayList<GeoCodeModel> geoCodes = new ArrayList<>();
    ArrayList<AllJobsModel> allJobs = new ArrayList<>();
    ArrayList<Marker> markers = new ArrayList<>();

    private DBFunctions dbFunc = new DBFunctions();
    Jobfunctions jobFun = new Jobfunctions();
    private GeoCoding location = new GeoCoding();
    String geoCode = "http://www.mapquestapi.com/geocoding/v1/address?key=xGA2gfYEJmplL7GrATFYpONUR1dGkPxx&location=1600+Pennsylvania+Ave+NW,Washington,DC,20500";
    private UIEventHandler EventHandler;
    private Object MouseEvent;
    Thread thread;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //populateSearchBox();
        System.out.println("loading Geo Codes");
        try {
            loadJobsAndGeoCodes(this);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void mapInitialized() {

        int c = 0;
        for (int i = 0; i < allJobs.size(); i++) {
            for (GeoCodeModel code : geoCodes) {
                String location = allJobs.get(i).getJobLocation();
                location = location.replace(" ", "");

                if (location.contains("|")) {
                    location = location.substring(0, location.indexOf("|"));
                }
                if (location.contains(code.getCity())) {
                    c = i;

                    LatLong geo = new LatLong(Double.parseDouble(code.getLat()), Double.parseDouble(code.getLng()));

                    MarkerOptions markerOptions = new MarkerOptions();
                    markerOptions.position(geo);
                    markerOptions.title(allJobs.get(i).getJobTitle());
                    markerOptions.label(allJobs.get(i).getJobTitle());


                    Marker marker = new Marker(markerOptions);

                    markers.add(marker);

                }
            }
        }


        //Set the initial properties of the map.
        MapOptions mapOptions = new MapOptions();
        mapOptions.center(new LatLong(47.6097, -122.3331))
                .overviewMapControl(false)
                .panControl(false)
                .rotateControl(false)
                .scaleControl(false)
                .streetViewControl(false)
                .zoomControl(false)
                .zoom(2);

        map = gMap.createMap(mapOptions);


        for (int i = 0; i < markers.size(); i++) {
            map.addMarker(markers.get(i));
            markers.get(i).getJSObject();
            int finalI = i;
            map.addUIEventHandler(markers.get(i), UIEventType.click, new UIEventHandler() {
                @Override
                public void handle(JSObject obj) {
                    MarkerOptions markerOptions = new MarkerOptions();
                    try {
                        InfoWindowOptions infoWindowOptions = new InfoWindowOptions();
                        infoWindowOptions.content("<h2>" + allJobs.get(finalI).getJobTitle() + "</h2>" +
                                allJobs.get(finalI).getJobCompany());
                        com.lynden.gmapsfx.javascript.object.InfoWindow infoWindow = new InfoWindow(infoWindowOptions);
                        infoWindow.open(map, markers.get(finalI));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            });
        }
    }

    //Joe Silveira
    //Method to populate the combo box with job names and companys
    private void populateSearchBox() {

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

    private void loadJobsAndGeoCodes(MapComponentInitializedListener listener) throws SQLException {
        Thread thread;
        progressPane.setVisible(true);

        Task task = new Task<Void>() {
            @Override
            public Void call() throws IOException, XMLStreamException, SQLException {
                progressPaneLabel.setText("Loading Jobs and GeoCodes From Database. Please Wait...");
                geoCodes = dbFunc.getGeoCodes();
                allJobs = jobFun.getArrayOfJobs();
                progressPaneLabel.setText("Loading Map view. Please Wait...");
                gMap.addMapInializedListener(listener);
                return null;
            }

        };

        task.setOnSucceeded(event1 -> {
            System.out.println("Done");
            progressIndicator.progressProperty().unbind();
            progressIndicator.setProgress(100);
            progressPane.setVisible(false);
            gMap.setVisible(true);

        });
        progressIndicator.progressProperty().bind(task.progressProperty());
        thread = new Thread(task);
        thread.start();
    }

}


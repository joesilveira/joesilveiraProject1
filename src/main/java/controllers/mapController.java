package controllers;

import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.MapComponentInitializedListener;
import com.lynden.gmapsfx.javascript.JavascriptObject;
import com.lynden.gmapsfx.javascript.event.UIEventHandler;
import com.lynden.gmapsfx.javascript.event.UIEventType;
import com.lynden.gmapsfx.javascript.object.*;
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
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import netscape.javascript.JSObject;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class mapController extends JavascriptObject implements Initializable, MapComponentInitializedListener {


    //Anchor Panes
    @FXML
    private AnchorPane progressPane;

    @FXML
    private AnchorPane mapView;

    //Combo Boxes
    @FXML
    private ComboBox<String> combo_JobType;

    @FXML
    private ComboBox<String> distanceFromComboBox;

    @FXML
    private ComboBox<String> locationSearch;

    @FXML
    private ComboBox<String> searchByDate;

    public GeocodingServiceCallback callback;

    //Map stuff
    @FXML
    private GoogleMapView gMap;


    private GoogleMap map;

    //Other stuff
    @FXML
    private ProgressIndicator progressIndicator;

    @FXML
    private Text progressPaneLabel;

    @FXML
    private Label totalResultsLabel;

    @FXML
    private TextField locationEntry;

    @FXML
    private Button applyFiltersbutton;

    //User Variables
    int value;
    double jDistance;
    String geoCode = "http://www.mapquestapi.com/geocoding/v1/address?key=xGA2gfYEJmplL7GrATFYpONUR1dGkPxx&location=1600+Pennsylvania+Ave+NW,Washington,DC,20500";

    //Class Variables
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d-MMM-yyyy");
    LocalDate today = LocalDate.parse(LocalDate.now().toString());
    Period daysBefore;
    String jobTypeSelected;
    String distanceSelected;
    LocalDate userEndDate;
    String entryLat;
    String entryLng;

    ArrayList<String> jobTypes = new ArrayList<>();
    Map<String, Integer> hashMap = new HashMap<>();
    Map<String, List<String>> n = new HashMap<>();

    ArrayList<GeoCodeModel> geoCodes = new ArrayList<>();
    ArrayList<AllJobsModel> allJobs = new ArrayList<>();
    ArrayList<Marker> markers = new ArrayList<>();
    ArrayList<String> dates = new ArrayList<>();
    ArrayList<String> distances = new ArrayList<>();

    //Class instances
    private DBFunctions dbFunc = new DBFunctions();
    private Jobfunctions jobFun = new Jobfunctions();
    private GeoCoding geoLocation = new GeoCoding();
    private UIEventHandler EventHandler;
    private Object MouseEvent;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            loadComponents(this);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void mapInitialized() {

        //Set the initial properties of the map.
        MapOptions mapOptions = new MapOptions();
        mapOptions.center(new LatLong(41.9904, -70.9751))
                .overviewMapControl(false)
                .panControl(false)
                .rotateControl(false)
                .scaleControl(false)
                .streetViewControl(false)
                .zoomControl(false)
                .zoom(8);

        map = gMap.createMap(mapOptions);

        LatLong bridgewater = new LatLong(41.9904, -70.9751);
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(bridgewater);
        Marker m1 = new Marker(markerOptions);
        map.addMarker(m1);

        InfoWindowOptions infoWindowOptions = new InfoWindowOptions();
        infoWindowOptions.content("<h2> Bridgewater MA</h2>");
        com.lynden.gmapsfx.javascript.object.InfoWindow infoWindow = new InfoWindow(infoWindowOptions);
        infoWindow.open(map, m1);

    }


    //Return end date
    @FXML
    void filterByDate(ActionEvent event) throws ParseException {

        String getSelected = searchByDate.getValue();
        System.out.println(getSelected);


        if (!getSelected.equals("")) {

            //set end date variable based on user choice
            switch (getSelected) {
                case "Last 3 Days":
                    daysBefore = Period.ofDays(3);
                    break;
                case "Last 7 Days":
                    daysBefore = Period.ofDays(7);
                    break;
                case "last 14 Days":
                    daysBefore = Period.ofDays(14);
                    break;
                case "Last 21 Days":
                    daysBefore = Period.ofDays(21);
                    break;
                case "Last 30 Days":
                    daysBefore = Period.ofDays(30);
                    break;
                case "Last 60 Days":
                    daysBefore = Period.ofDays(60);
                    break;
                case "Last 90 Days":
                    daysBefore = Period.ofDays(90);
                    break;
                case "Last 180 Days":
                    daysBefore = Period.ofDays(180);
                    break;
                case "Last 360 Days":
                    daysBefore = Period.ofDays(360);
                    break;
            }
            userEndDate = today.minus(daysBefore);
        } else {
            userEndDate = null;
        }
    }

    //Return Type selected
    @FXML
    void filterByType(ActionEvent event) {

        String type = "";
        type = combo_JobType.getValue();

        if (!type.equals("")) {
            jobTypeSelected = type;
            jobTypeSelected = jobTypeSelected.toLowerCase();
        } else {
            jobTypeSelected = null;
        }


    }

    //Return distance selected
    @FXML
    void filterByLocation(ActionEvent event) throws SQLException {

        distanceSelected = distanceFromComboBox.getValue();
        String userEntry = locationEntry.getText();

        if (userEntry.equals("")) {
            userEntry = null;
        }

        if (distanceSelected.equals("")) {
            distanceSelected = null;
            entryLng = null;
            entryLat = null;
        }

        if (distanceSelected != null && userEntry != null) {
            value = 0;
            switch (distanceSelected) {

                case "20 Miles":
                    value = 20;
                    break;
                case "40 Miles":
                    value = 40;
                    break;
                case "60 Miles":
                    value = 60;
                    break;
                case "100 Miles":
                    value = 100;
                    break;
                case "150 Miles":
                    value = 150;
                    break;
                case "200 Miles":
                    value = 200;
                    break;
                case "400 Miles":
                    value = 400;
                    break;
                case "800 Miles":
                    value = 800;
                    break;
                case "1000 or more miles":
                    value = 1000;
                    break;
            }
            setEnteredLocationLatLong(userEntry);
        }
    }

    @FXML
    void applyFilters(ActionEvent event) throws SQLException {
        getFilters();
    }


    //initliaze all fields
    private void loadComponents(MapComponentInitializedListener listener) throws SQLException {
        Thread thread;
        progressPane.setVisible(true);

        Task task = new Task<Void>() {
            @Override
            public Void call() throws IOException, XMLStreamException, SQLException {
                progressPaneLabel.setText("Loading Jobs and GeoCodes From Database. Please Wait...");

                geoCodes = dbFunc.getGeoCodes();
                allJobs = jobFun.getArrayOfJobs();

                progressPaneLabel.setText("Loading Filter Components. Please Wait...");

                loadComboBox_JobType();
                loadComboBox_DateRange();
                loadDistanceComboBox();


                Collections.sort(jobTypes);
                combo_JobType.getItems().setAll(jobTypes);
                searchByDate.getItems().setAll(dates);

                progressPaneLabel.setText("Loading Map view. Please Wait...");
                return null;
            }
        };

        task.setOnSucceeded(event1 -> {
            progressIndicator.progressProperty().unbind();
            progressIndicator.setProgress(100);
            progressPane.setVisible(false);

        });
        progressIndicator.progressProperty().bind(task.progressProperty());
        thread = new Thread(task);
        thread.start();
        gMap.addMapInializedListener(listener);
        gMap.setVisible(true);
    }


    //init the job type combo box with fields
    private void loadComboBox_JobType() {
        jobTypes.add("");
        jobTypes.add("Computer Programmer");
        jobTypes.add("Hardware");
        jobTypes.add("Software Developer");
        jobTypes.add("Systems Manager");
        jobTypes.add("Web Developer");
        jobTypes.add("Software Engineer");
        jobTypes.add("Database");
        jobTypes.add("Sytems Analyst");
        jobTypes.add("Security");
        jobTypes.add("Information");
        jobTypes.add("Video Game Developer");
        jobTypes.add("Health");
        jobTypes.add("Data");
        jobTypes.add("Web Designer");
        jobTypes.add("Engineer");
        jobTypes.add("Python");
        jobTypes.add("Java");
        jobTypes.add("front-end");
        jobTypes.add("back-end");
        jobTypes.add("programmer");
        jobTypes.add("front end");
        jobTypes.add("back end");
        jobTypes.add("Full Stack");
        jobTypes.add("Mobile");
        jobTypes.add("Developer");
        jobTypes.add("Senior");
        jobTypes.add("Javascript");
        jobTypes.add("django");

        for (String s : jobTypes) {
            hashMap.put(s, 0);
        }

        for (String type : jobTypes) {
            List<String> tempList = new ArrayList<String>();
            n.put(type, tempList);
        }

        for (AllJobsModel allJob : allJobs) {
            for (String jobType : jobTypes) {
                //Just for internal testing
                if (allJob.getJobTitle().toLowerCase().contains(jobType.toLowerCase())) {
                    int key = hashMap.get(jobType);
                    hashMap.replace(jobType, key, (key + 1));
                }
            }
        }
    }

    //init the date combo box with fields
    private void loadComboBox_DateRange() {
        dates.add("");
        dates.add("Last 3 Days");
        dates.add("Last 7 Days");
        dates.add("last 14 Days");
        dates.add("Last 21 Days");
        dates.add("Last 30 Days");
        dates.add("Last 60 Days");
        dates.add("Last 90 Days");
        dates.add("Last 180 Days");
        dates.add("Last 360 Days");

        convertDate();
    }

    private void loadDistanceComboBox() {
        distances.add("");
        distances.add("20 Miles");
        distances.add("40 Miles");
        distances.add("60 Miles");
        distances.add("100 Miles");
        distances.add("150 Miles");
        distances.add("200 Miles");
        distances.add("400 Miles");
        distances.add("800 Miles");
        distances.add("1000 or more miles");

        distanceFromComboBox.getItems().setAll(distances);

    }


    //method to create markers on the map
    private void createMarkers(ArrayList<Marker> markers) {

        for (int l = 0; l < markers.size(); l++) {
            map.addMarker(markers.get(l));
            markers.get(l).getJSObject();
            int finalI = l;
            map.addUIEventHandler(markers.get(l), UIEventType.click, new UIEventHandler() {
                @Override
                public void handle(JSObject obj) {
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

    //Method to add a marker to the array of markers
    private void addMarker(AllJobsModel job) {
        for (GeoCodeModel code : geoCodes) {

            String location = job.getJobLocation();
            location = location.replace(" ", "");

            if (location.contains("|")) {
                location = location.substring(0, location.indexOf("|"));
            }
            if (location.contains(code.getCity())) {

                LatLong geo = new LatLong(Double.parseDouble(code.getLat()), Double.parseDouble(code.getLng()));
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(geo);
                markerOptions.title(job.getJobTitle());
                markerOptions.label(job.getJobTitle());
                Marker marker = new Marker(markerOptions);
                markers.add(marker);
                break;
            }
        }
    }

    //method to convert job dates to a dd-mm-yyyy string format
    private void convertDate() {

        for (AllJobsModel job : allJobs) {
            String pubDate = job.getPubDate();

            //String parsing
            if (pubDate.contains(",")) {
                pubDate = pubDate.replace(",", "");
                job.setPubDate(pubDate);
            }

            //remove utc from github jobs
            if (pubDate.contains("UTC")) {
                pubDate = pubDate.replace("UTC", "");
                job.setPubDate(pubDate);
            }

            //remove z from stack jobs
            if (pubDate.contains("Z")) {
                pubDate = pubDate.replace("Z", "");
                job.setPubDate(pubDate);
            }

            //remove time stamps
            if (pubDate.contains(":")) {
                String timeStamp = pubDate.substring((pubDate.indexOf(":") - 3), pubDate.lastIndexOf(":") + 3);
                pubDate = pubDate.replaceAll(timeStamp, "");
                //System.out.println(timeStamp);
            }

            //remove double spaces for parsing
            if (pubDate.contains("  ")) {
                pubDate = pubDate.replace("  ", " ");
                job.setPubDate(pubDate);
            }
            //System.out.println(pubDate);

            String[] dateArray = pubDate.split(" ");
            for (int k = 0; k < dateArray.length; k++) {


                //convert all to a generic format dd-mm-yyyy
                String tempMonth;
                String tempDay;

                if (dateArray[1].equals("Jan")) {
                    tempMonth = dateArray[1];
                    tempDay = dateArray[2];

                    dateArray[1] = tempDay;
                    dateArray[2] = tempMonth;
                } else if (dateArray[1].equals("Feb")) {
                    tempMonth = dateArray[1];
                    tempDay = dateArray[2];

                    dateArray[1] = tempDay;
                    dateArray[2] = tempMonth;
                } else if (dateArray[1].equals("Mar")) {
                    tempMonth = dateArray[1];
                    tempDay = dateArray[2];

                    dateArray[1] = tempDay;
                    dateArray[2] = tempMonth;
                } else if (dateArray[1].equals("Apr")) {
                    tempMonth = dateArray[1];
                    tempDay = dateArray[2];

                    dateArray[1] = tempDay;
                    dateArray[2] = tempMonth;
                } else if (dateArray[1].equals("May")) {
                    tempMonth = dateArray[1];
                    tempDay = dateArray[2];

                    dateArray[1] = tempDay;
                    dateArray[2] = tempMonth;
                } else if (dateArray[1].equals("Jun")) {
                    tempMonth = dateArray[1];
                    tempDay = dateArray[2];

                    dateArray[1] = tempDay;
                    dateArray[2] = tempMonth;
                } else if (dateArray[1].equals("Jul")) {
                    tempMonth = dateArray[1];
                    tempDay = dateArray[2];

                    dateArray[1] = tempDay;
                    dateArray[2] = tempMonth;
                } else if (dateArray[1].equals("Aug")) {
                    tempMonth = dateArray[1];
                    tempDay = dateArray[2];

                    dateArray[1] = tempDay;
                    dateArray[2] = tempMonth;
                } else if (dateArray[1].equals("Sep")) {
                    tempMonth = dateArray[1];
                    tempDay = dateArray[2];

                    dateArray[1] = tempDay;
                    dateArray[2] = tempMonth;
                } else if (dateArray[1].equals("Oct")) {
                    tempMonth = dateArray[1];
                    tempDay = dateArray[2];

                    dateArray[1] = tempDay;
                    dateArray[2] = tempMonth;
                } else if (dateArray[1].equals("Nov")) {
                    tempMonth = dateArray[1];
                    tempDay = dateArray[2];

                    dateArray[1] = tempDay;
                    dateArray[2] = tempMonth;
                } else if (dateArray[1].equals("Dec")) {
                    tempMonth = dateArray[1];
                    tempDay = dateArray[2];

                    dateArray[1] = tempDay;
                    dateArray[2] = tempMonth;
                }

                //convert months to format
//                switch (dateArray[2]) {
//                    case "jan":
//                        dateArray[2] = "01";
//
//                        break;
//                    case "feb":
//                        dateArray[2] = "02";
//
//                        break;
//                    case "mar":
//                        dateArray[2] = "03";
//
//                        break;
//                    case "apr":
//                        dateArray[2] = "04";
//
//                        break;
//                    case "may":
//                        dateArray[2] = "05";
//
//                        break;
//                    case "jun":
//                        dateArray[2] = "06";
//
//                        break;
//                    case "jul":
//                        dateArray[2] = "07";
//
//                        break;
//                    case "aug":
//                        dateArray[2] = "08";
//
//                        break;
//                    case "sep":
//                        dateArray[2] = "09";
//
//                        break;
//                    case "oct":
//                        dateArray[2] = "10";
//
//                        break;
//                    case "nov":
//                        dateArray[2] = "11";
//
//                        break;
//                    case "dec":
//                        dateArray[2] = "12";
//                        break;
//                }

                pubDate = dateArray[1] + "-" + dateArray[2] + "-" + dateArray[3];
                job.setPubDate(pubDate);
            }
        }
    }

    //Method to calculate the distance between two latlongs
    //https://www.geeksforgeeks.org/program-distance-two-points-earth/
    private Double calcDistance(Double jobLat, Double jobLng, Double locationLat, Double locationLng) {
        jobLat = Math.toRadians(jobLat);
        jobLng = Math.toRadians(jobLng);

        locationLat = Math.toRadians(locationLat);
        locationLng = Math.toRadians(locationLng);

        double distLng = locationLng - jobLng;
        double distLat = locationLat - jobLat;

        double ans = Math.pow(Math.sin(distLat / 2), 2) + Math.cos(jobLat) * Math.cos(locationLat) * Math.pow(Math.sin(distLng / 2), 2);

        double c = 2 * Math.asin(Math.sqrt(ans));

        double r = 3956;

        return (c * r);
    }

    //Method get filter data
    private void getFilters() throws SQLException {

        try {
            markers.clear();
            map.clearMarkers();
        } catch (Exception ignored) {

        }

        //All Filters Applied
        if (jobTypeSelected != null && userEndDate != null && entryLat != null && entryLng != null) {
            for (AllJobsModel Job : allJobs) {
                String dateIn = Job.getPubDate();
                LocalDate jDate = LocalDate.parse(dateIn, formatter);
                getJobParam(Job);
                if (Job.getJobTitle().toLowerCase().contains(jobTypeSelected) && jDate.isAfter(userEndDate) && jDistance < value) {
                    addMarker(Job);
                }
            }
            createMarkers(markers);

            //Job Type and Job Age Applied
        } else if (jobTypeSelected != null && userEndDate != null) {
            for (AllJobsModel Job : allJobs) {
                String dateIn = Job.getPubDate();
                LocalDate jDate = LocalDate.parse(dateIn, formatter);
                if (Job.getJobTitle().toLowerCase().contains(jobTypeSelected) && jDate.isAfter(userEndDate)) {
                    addMarker(Job);
                }
            }
            createMarkers(markers);

            //Job Type and distance applied
        } else if (jobTypeSelected != null && entryLat != null && entryLng != null) {
            for (AllJobsModel Job : allJobs) {
                getJobParam(Job);
                if (Job.getJobTitle().toLowerCase().contains(jobTypeSelected) && jDistance < value) {
                    addMarker(Job);
                }
            }
            createMarkers(markers);

            //Job Age and distance applied
        } else if (userEndDate != null && entryLat != null && entryLng != null) {
            for (AllJobsModel Job : allJobs) {
                String dateIn = Job.getPubDate();
                LocalDate jDate = LocalDate.parse(dateIn, formatter);
                getJobParam(Job);
                if (jDate.isAfter(userEndDate) && jDistance < value) {
                    addMarker(Job);
                }
            }
            createMarkers(markers);


            //Just Type applied
        } else if (jobTypeSelected != null) {
            for (AllJobsModel Job : allJobs) {
                if (Job.getJobTitle().toLowerCase().contains(jobTypeSelected)) {
                    addMarker(Job);
                }
            }
            createMarkers(markers);

            //Just job age applied
        } else if (userEndDate != null) {
            for (AllJobsModel Job : allJobs) {
                String dateIn = Job.getPubDate();
                LocalDate jDate = LocalDate.parse(dateIn, formatter);
                if (jDate.isAfter(userEndDate)) {
                    addMarker(Job);
                }
            }
            createMarkers(markers);

            //Just distance applied
        } else if (entryLat != null && entryLng != null) {
            System.out.println("distance applied");
            System.out.println(entryLat + entryLng);
            for (AllJobsModel Job : allJobs) {
                getJobParam(Job);
                if (jDistance < value) {
                    addMarker(Job);
                }
            }
            createMarkers(markers);
        }

        totalResultsLabel.setText("Showing " + markers.size() + " On Map");
    }

    private void setEnteredLocationLatLong(String enteredLocation) throws SQLException {
        String location = enteredLocation;
        location = location.replace(" ", "");

        if (location.contains("|")) {
            location = location.substring(0, location.indexOf("|"));
        }
        //Loop through geo codes and try to find the user location entered
        for (GeoCodeModel code : geoCodes) {
            if (location.toLowerCase().contains(code.getCity().toLowerCase())) {
                entryLat = code.getLat();
                entryLng = code.getLng();
                break;
            }
        }

        //If user location is not in database, make the api request to get the lat lng and add it to db
        if (entryLat.equals("") && entryLng.equals("")) {
            geoLocation.geoCode(enteredLocation);
            entryLat = geoLocation.getLattitude();
            entryLng = geoLocation.getLongitude();
            dbFunc.addJobLatLng(enteredLocation, entryLat, entryLng);
        }
    }

    private void getJobParam(AllJobsModel Job) {

        String jLat = "";
        String jLng = "";
        String location = Job.getJobLocation();
        location = location.replace(" ", "");

        if (location.contains("|")) {
            location = location.substring(0, location.indexOf("|"));
        }

        for (GeoCodeModel geo : geoCodes) {
            if (geo.getCity().toLowerCase().contains(location.toLowerCase())) {
                jLat = geo.getLat();
                jLng = geo.getLng();
                break;
            }
        }

        double jobLat = Double.parseDouble(jLat);
        double jobLng = Double.parseDouble(jLng);

        double uEntryLat = Double.parseDouble(entryLat);
        double uEntryLng = Double.parseDouble(entryLng);

        jDistance = calcDistance(jobLat, jobLng, uEntryLat, uEntryLng);
    }
}








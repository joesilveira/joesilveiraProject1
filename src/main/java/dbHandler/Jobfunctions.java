package dbHandler;

import connectionRequests.GeoCoding;
import connectionRequests.GithubJSONRequest;
import connectionRequests.StackOverflowRSSRequest;
import dataTypes.*;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;

public class Jobfunctions {

    DBFunctions dbFunc = new DBFunctions();

    GithubJSONRequest gitHubJobActions = new GithubJSONRequest();
    StackOverflowRSSRequest stackOverFlowJobActions = new StackOverflowRSSRequest();
    GeoCoding geoCoding = new GeoCoding();

    private int jobCount = 0;
    ArrayList<String> allJobNames = new ArrayList<>();
    ArrayList<AllJobsModel> allJobs = new ArrayList<>();

    public ArrayList<GeoCodeModel> getGeoCodes() {
        return geoCodes;
    }

    public void setGeoCodes(ArrayList<GeoCodeModel> geoCodes) {
        this.geoCodes = geoCodes;
    }

    ArrayList<GeoCodeModel> geoCodes = new ArrayList<>();
    StoreRSSFeed feed;

    public void loadAllJobsIntoDB() throws IOException, XMLStreamException, SQLException {

        //write jobs
        gitHubJobActions.addJobsToGitHubDB();
        stackOverFlowJobActions.addJobsToDB(feed.getJobs());

    }

    public void fetchAllJobs(String gitHubAPI, String stackOverFLowLink) throws IOException, XMLStreamException {
        //Fetch jobs
        gitHubJobActions.fetchAllGithubJobs(gitHubAPI);
        feed = stackOverFlowJobActions.makeRequest(stackOverFLowLink);
    }

    public void loadAllJobNamesFromDB() throws SQLException {
        ArrayList<String> gitHubJobs = new ArrayList<>();
        ArrayList<String> stackJobs = new ArrayList<>();

        gitHubJobs = dbFunc.getGithubJobNames();
        stackJobs = dbFunc.getStackOverFlowJobNames();

        allJobNames.addAll(gitHubJobs);
        allJobNames.addAll(stackJobs);

        Collections.sort(allJobNames);
    }

    public void loadAllJobsFromDB() throws SQLException {

        ArrayList<GithubModel> gitHubJobs = new ArrayList<>();
        ArrayList<StackOverFlowModel> stackJobs = new ArrayList<>();

        gitHubJobs = dbFunc.getGithubDBjobs();
        stackJobs = dbFunc.getStackJobs();


        //Add git hub jobs to array
        for (GithubModel gJob : gitHubJobs) {
            AllJobsModel job = new AllJobsModel(gJob.getTitle(), gJob.getType(), gJob.getCompany(),
                    gJob.getLocation(), gJob.getCompany_url(), gJob.getDescription(),
                    gJob.getHow_to_apply());
            allJobs.add(job);
        }

        //Add stack over flow jobs to array
        for (StackOverFlowModel stackJob : stackJobs) {
            AllJobsModel job = new AllJobsModel(stackJob.getTitle(), stackJob.getCategory(), stackJob.getName(), stackJob.getLocation(),
                    stackJob.getLink(), stackJob.getDescription(), "Apply Online");
            allJobs.add(job);
        }

    }

    public void loadGeoCodesIntoDB() throws SQLException {
        System.out.println("loading geo codes");
        for (int i = 0; i < allJobs.size(); i++) {
            if (allJobs.get(i).getJobLocation() != null) {
                String location = allJobs.get(i).getJobLocation();
                location = location.replace(" ", "");

                if (location.contains("|")) {
                    location = location.substring(0, location.indexOf("|"));
                }

                try {

                    if (dbFunc.checkLocationExistsInDB(location, "geocodes")) {
                        System.out.println("Exists");
                    }
                    // geoCoding.geoCode(location);
                    // dbFunc.addJobLatLng(location, geoCoding.getLattitude(), geoCoding.getLongitude());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void loadAllGeoCodesFromDB() throws SQLException {
        System.out.println("Fetching geo codes from database");

        geoCodes = dbFunc.getGeoCodes();

    }

    public ArrayList<AllJobsModel> getArrayOfJobs() throws SQLException {

        ArrayList<GithubModel> gitHubJobs = new ArrayList<>();
        ArrayList<StackOverFlowModel> stackJobs = new ArrayList<>();

        gitHubJobs = dbFunc.getGithubDBjobs();
        stackJobs = dbFunc.getStackJobs();


        //Add git hub jobs to array
        for (GithubModel gJob : gitHubJobs) {
            AllJobsModel job = new AllJobsModel(gJob.getTitle(), gJob.getType(), gJob.getCompany(),
                    gJob.getLocation(), gJob.getCompany_url(), gJob.getDescription(),
                    gJob.getHow_to_apply());
            allJobs.add(job);
        }

        //Add stack over flow jobs to array
        for (StackOverFlowModel stackJob : stackJobs) {
            AllJobsModel job = new AllJobsModel(stackJob.getTitle(), stackJob.getCategory(), stackJob.getName(), stackJob.getLocation(),
                    stackJob.getLink(), stackJob.getDescription(), "Apply Online");
            allJobs.add(job);
        }
        return this.allJobs;
    }

    public void addJobsToArray() throws SQLException {

        ArrayList<GithubModel> gitHubJobs = new ArrayList<>();
        ArrayList<StackOverFlowModel> stackJobs = new ArrayList<>();

        gitHubJobs = dbFunc.getGithubDBjobs();
        stackJobs = dbFunc.getStackJobs();


        //Add git hub jobs to array
        for (GithubModel gJob : gitHubJobs) {
            AllJobsModel job = new AllJobsModel(gJob.getTitle(), gJob.getType(), gJob.getCompany(),
                    gJob.getLocation(), gJob.getCompany_url(), gJob.getDescription(),
                    gJob.getHow_to_apply());
            allJobs.add(job);
        }

        //Add stack over flow jobs to array
        for (StackOverFlowModel stackJob : stackJobs) {
            AllJobsModel job = new AllJobsModel(stackJob.getTitle(), stackJob.getCategory(), stackJob.getName(), stackJob.getLocation(),
                    stackJob.getLink(), stackJob.getDescription(), "Apply Online");
            allJobs.add(job);
        }
    }

    public void getJobSizes() {

    }
}

package dbHandler;

import connectionRequests.GithubJSONRequest;
import connectionRequests.StackOverflowRSSRequest;
import dataTypes.AllJobsModel;
import dataTypes.GithubModel;
import dataTypes.StackOverFlowModel;
import dataTypes.StoreRSSFeed;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;

public class Jobfunctions {

    DBFunctions dbFunc = new DBFunctions();

    GithubJSONRequest gitHubJobActions = new GithubJSONRequest();
    StackOverflowRSSRequest stackOverFlowJobActions = new StackOverflowRSSRequest();


    ArrayList<String> allJobNames = new ArrayList<>();
    ArrayList<AllJobsModel> allJobs = new ArrayList<>();

    public void loadAllJobsIntoDB(String gitHubAPI, String stackOverFLowLink) throws IOException, XMLStreamException {

        //Fetch github jobs and add to Database
        gitHubJobActions.fetchAllGithubJobs(gitHubAPI);
        gitHubJobActions.addJobsToGitHubDB();

        //Fetch Stack Overflow jobs and add to database
        StoreRSSFeed feed = stackOverFlowJobActions.makeRequest(stackOverFLowLink);
        stackOverFlowJobActions.addJobsToDB(feed.getJobs());


    }

    public void loadAllJobNamesFromDB() {
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
}

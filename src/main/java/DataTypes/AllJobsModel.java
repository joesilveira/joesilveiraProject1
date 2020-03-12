package dataTypes;

public class AllJobsModel implements Comparable<AllJobsModel> {

    String jobTitle;
    String jobType;
    String jobCompany;
    String jobLocation;
    String jobURL;
    String jobDescription;
    String howToApply;
    String id;

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    String pubDate;

    public String getId() {
        return id;
    }

    public AllJobsModel(String jobTitle, String jobType, String jobCompany, String jobLocation, String jobURL, String jobDescription, String howToApply, String pubDate) {
        this.jobTitle = jobTitle;
        this.jobType = jobType;
        this.jobCompany = jobCompany;
        this.jobLocation = jobLocation;
        this.jobURL = jobURL;
        this.jobDescription = jobDescription;
        this.howToApply = howToApply;
        this.pubDate = pubDate;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getJobType() {
        return jobType;
    }

    public void setJobType(String jobType) {
        this.jobType = jobType;
    }

    public String getJobCompany() {
        return jobCompany;
    }

    public void setJobCompany(String jobCompany) {
        this.jobCompany = jobCompany;
    }

    public String getJobLocation() {
        return jobLocation;
    }

    public void setJobLocation(String jobLocation) {
        this.jobLocation = jobLocation;
    }

    public String getJobURL() {
        return jobURL;
    }

    public void setJobURL(String jobURL) {
        this.jobURL = jobURL;
    }

    public String getJobDescription() {
        return jobDescription;
    }

    public void setJobDescription(String jobDescription) {
        this.jobDescription = jobDescription;
    }

    public String getHowToApply() {
        return howToApply;
    }

    public void setHowToApply(String howToApply) {
        this.howToApply = howToApply;
    }

    @Override
    public String toString() {
        return "Job:" +
                "jobTitle='" + jobTitle + '\'' +
                ", jobType='" + jobType;
    }

    @Override
    public int compareTo(AllJobsModel otherJob) {
        return this.getJobTitle().compareTo(otherJob.getJobTitle());
    }
}

package dataTypes;

import java.util.ArrayList;
import java.util.List;

public class StoreRSSFeed {


    String guid;
    String link;
    String name;
    String category;
    String title;
    String description;
    String pubDate;
    String updated;
    String location;


    List<StackOverFlowModel> jobs = new ArrayList<>();

    public StoreRSSFeed(String guid, String link, String name, String category, String title, String description, String pubDate, String updated, String location) {
        this.guid = guid;
        this.link = link;
        this.name = name;
        this.category = category;
        this.title = title;
        this.description = description;
        this.pubDate = pubDate;
        this.updated = updated;
        this.location = location;
    }

    public List<StackOverFlowModel> getJobs() {
        return jobs;
    }

    public void setJobs(List<StackOverFlowModel> jobs) {
        this.jobs = jobs;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void printJobs() {
        for (int i = 0; i < jobs.size(); i++) {
            System.out.println(jobs.get(i));
        }
    }


    @Override
    public String toString() {
        return "RSSFeed{" +
                "guid='" + guid + '\'' +
                ", link='" + link + '\'' +
                ", name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", pubDate='" + pubDate + '\'' +
                ", updated='" + updated + '\'' +
                ", location='" + location + '\'' +
                '}';
    }
}

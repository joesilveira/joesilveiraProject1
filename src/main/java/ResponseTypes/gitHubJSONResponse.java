/*
Joe silveira

Class to handle json data
 */

package ResponseTypes;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class gitHubJSONResponse {
    @Expose
    String id;
    @Expose
    String type;
    @Expose
    String url;
    @Expose
    String created_at;
    @Expose
    String company;
    @Expose
    String company_url;
    @Expose
    String location;
    @Expose
    String title;
    @Expose
    String description;
    @Expose
    String how_to_apply;
    @Expose
    String company_logo;

    //Getters for variables

    @SerializedName("ID")
    public String getId() {
        return this.id;
    }

    @SerializedName("Type")
    public String getType() {
        return this.type;
    }

    @SerializedName("URL")
    public String getUrl() {
        return this.url;
    }

    @SerializedName("Created At")
    public String getCreated_at() {
        return created_at;
    }

    @SerializedName("Company")
    public String getCompany() {
        return this.company;
    }

    @SerializedName("Company URL")
    public String getCompany_url() {

        return this.company_url;
    }

    @SerializedName("Location")
    public String getLocation() {
        return this.location;
    }

    @SerializedName("Description")
    public String getDescription() {
        description = removeHTML(this.description);
        description = removeNullLine(this.description);
        description = description.trim();
        return this.description;
    }

    @SerializedName("how_to_apply")
    public String getHow_to_apply() {
        how_to_apply = removeHTML(this.how_to_apply);
        how_to_apply = removeNullLine(this.how_to_apply);
        how_to_apply = how_to_apply.trim();
        return this.how_to_apply;
    }

    @SerializedName("company_logo")
    public String getCompany_logo() {
        return this.company_logo;
    }

    @SerializedName("title")
    public String getTitle() {
        return this.title;
    }

    //joe silveira
    //remove html from string
    private String removeHTML(String string) {
        string = string.replaceAll("<.*?>", "");
        string = string.replaceAll(".*?-", "");
        return string;
    }

    private String removeNullLine(String string) {
        string = string.replaceAll("(?m)^\\s", "");
        return string;
    }

    @Override
    public String toString() {
        return
                "   " + "Job ID: " + getId() + "\n" +
                        "   " + "Job Type: " + getType() + "\n" +
                        "   " + "Github URL: " + getUrl() + "\n" +
                        "   " + "Created At: " + getCreated_at() + "\n" +
                        "   " + "Company: " + getCompany() + "\n" +
                        "   " + "Company URL: " + getCompany_url() + "\n" +
                        "   " + "Job Location: " + getLocation() + "\n" +
                        "   " + "Job Title: " + getTitle() + "\n" +
                        "   " + "Job Description: " + getDescription() + "\n" +
                        "   " + "How to apply: " + getHow_to_apply() + "\n" +
                        "   " + "Company Logo: " + getCompany_logo() + "\n";
    }
}
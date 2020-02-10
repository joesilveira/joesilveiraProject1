/*
Joe silveira

Class to handle json data
 */

package json;

import java.net.URL;


public class ResponseData {
    String id;
    String type;
    String url;
    String created_at;
    String company;
    String company_url;
    String location;
    String title;
    String description;
    String how_to_apply;
    String company_logo;


    public String getId() {
        return this.id;
    }

    public String getType() {
        return this.type;
    }

    public String getUrl() {
        return this.url;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getCompany() {
        return this.company;
    }

    public String getCompany_url() {
        return this.company_url;
    }

    public String getLocation() {
        return this.location;
    }

    public String getDescription() {
        return this.description;
    }

    public String getHow_to_apply() {
        return this.how_to_apply;
    }

    public String getCompany_logo() {
        return this.company_logo;
    }


    /*
    Getter to get title
     */
    public String getTitle() {
        return this.title;
    }

    /*
    **For testing purposes**
    To String method to print class
     */
    @Override
    public String toString() {
        return title;
    }
}

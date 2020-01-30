/*
Joe silveira

Class to handle json data
 */

package Connections;

import java.net.URL;


public class ResponseData {
    String id;
    String type;
    URL url;
    String time;
    String company;
    String company_url;
    String location;
    String title;
    String description;
    String how_to_apply;
    String company_logo;

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

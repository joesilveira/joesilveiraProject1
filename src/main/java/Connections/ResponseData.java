package Connections;

import java.net.URL;
import java.util.ArrayList;

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

    public String getTitle() {
        return this.title;
    }

    @Override
    public String toString() {
        return title;
    }
}

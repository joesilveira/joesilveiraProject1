package connections;

import ResponseTypes.RSSFeed;
import ResponseTypes.StackOverFlowRSSFeed;
import dbHandler.DBFunctions;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.events.*;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class StackOverflowRSSRequest {

    //Java Class Variables
    URL url;


    //Class Variables
    DBFunctions dbFunc = new DBFunctions();
    static final String TITLE = "title";
    static final String DESCRIPTION = "description";
    static final String CHANNEL = "channel";
    static final String LANGUAGE = "language";
    static final String COPYRIGHT = "copyright";
    static final String LINK = "link";
    static final String AUTHOR = "author";
    static final String ITEM = "item";
    static final String PUB_DATE = "pubDate";
    static final String GUID = "guid";
    static final String CATEGORY = "category";
    static final String UPDATED = "updated";
    static final String LOCATION = "location";
    static final String NAME = "name";
    static final String IMAGE = "";
    StackOverFlowRSSFeed message = new StackOverFlowRSSFeed();

    public List<StackOverFlowRSSFeed> getJobs() {
        return jobs;
    }

    List<StackOverFlowRSSFeed> jobs = new ArrayList<>();


    public void makeRequest(String link1) throws MalformedURLException {

        //Initiliaze the class response type
        RSSFeed job = null;

        //https://www.vogella.com/tutorials/RSSFeed/article.html
        url = new URL(link1);

        //try to read the request
        try {

            //feed header
            boolean isFeedHeader = true;

            //set header values empty
            String description = "";
            String title = "";
            String link = "";
            String language = "";
            String copyright = "";
            String author = "";
            String name = "";
            String category = "";
            String pubdate = "";
            String updated = "";
            String location = "";
            String guid = "";

            //Class to read xml
            XMLInputFactory xmlInput = XMLInputFactory.newInstance();

            //open xml stream
            InputStream inputReader = url.openStream();

            //instantiate xml reader class
            XMLEventReader eventReader = xmlInput.createXMLEventReader(inputReader);


            //loop throught the contents of the page
            while (eventReader.hasNext()) {

                //Create the xml event
                XMLEvent event = eventReader.nextEvent();

                //if the event is the start element
                if (event.isStartElement()) {

                    //Get the local part of the xml feed
                    String localPart = event.asStartElement().getName().getLocalPart();

                    //print it for testing
                    //System.out.println(localPart);


                    switch (localPart) {

                        case ITEM:

                            if (isFeedHeader) {
                                isFeedHeader = false;
                                job = new RSSFeed(guid, link, name, category, title, description, pubdate, updated, location);

                            }
                            event = eventReader.nextEvent();
                            break;


                        case TITLE:
                            event = eventReader.nextEvent();
                            title = event.asCharacters().getData();
                            break;

                        case GUID:
                            event = eventReader.nextEvent();
                            guid = event.asCharacters().getData();
                            break;

                        case LINK:
                            event = eventReader.nextEvent();
                            link = event.asCharacters().getData();
                            break;

                        case NAME:
                            event = eventReader.nextEvent();
                            name = event.asCharacters().getData();
                            break;


                        case CATEGORY:
                            event = eventReader.nextEvent();
                            category = event.asCharacters().getData();
                            break;

                        case DESCRIPTION:
                            event = eventReader.nextEvent();
                            description = event.asCharacters().getData();
                            break;

                        case UPDATED:
                            event = eventReader.nextEvent();
                            updated = event.asCharacters().getData();
                            break;

                        case LOCATION:
                            event = eventReader.nextEvent();
                            location = event.asCharacters().getData();
                            break;


                        case PUB_DATE:
                            event = eventReader.nextEvent();
                            pubdate = event.asCharacters().getData();
                            break;
                    }

                    //If the event is not the start element, it is the end element
                } else if (event.isEndElement()) {

                    if (event.asEndElement().getName().getLocalPart() == (ITEM)) {

                        message.setTitle(title);
                        message.setCategory(category);
                        message.setDescription(description);
                        message.setName(name);
                        message.setLocation(location);
                        message.setUpdated(updated);
                        message.setPubDate(pubdate);
                        message.setGuid(guid);
                        message.setLink(link);
                        jobs.add(message);
                        event = eventReader.nextEvent();
                        continue;
                    }

                }
            }

            //end try
        } catch (Exception e) {

        }


    }

    public void printJobs() {
        for (int i = 0; i < jobs.size(); i++) {
            System.out.println(jobs.get(i));
        }
    }

    public void addJobsToDB() {
        System.out.println(jobs.toString());

    }


}


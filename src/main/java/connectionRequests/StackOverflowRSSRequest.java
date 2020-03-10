package connectionRequests;

import dataTypes.StackOverFlowModel;
import dataTypes.StoreRSSFeed;
import dbHandler.DBFunctions;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.XMLEvent;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;

public class StackOverflowRSSRequest {

    //Java Class Variables
    URL url;

    public int getRecordAdded() {
        return recordAdded;
    }

    int recordAdded = 0;


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


    //Joe Silveira
    //Method to ping url that returns rss feed and add responses to list
    public StoreRSSFeed makeRequest(String link1) throws IOException, XMLStreamException {

        StoreRSSFeed feed = null;

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
            String guid = "GUID";

            //Class to read xml
            XMLInputFactory xmlInput = XMLInputFactory.newInstance();


            //open xml stream
            InputStream inputReader = url.openStream();

            //instantiate xml reader class
            XMLEventReader eventReader = xmlInput.createXMLEventReader(inputReader);


            //loop throught the contents of the page
            while (eventReader.hasNext()) {

                //System.out.println("Num Loop: " + numLoop);

                //System.out.println("While: event has next: true");

                //Create the xml event
                XMLEvent event = eventReader.nextEvent();

                //if the event is the start element
                if (event.isStartElement()) {

                    //System.out.println("Is start element: true");

                    //Get the local part of the xml feed
                    String localPart = event.asStartElement().getName().getLocalPart();

                    //System.out.println(localPart);

                    switch (localPart) {

                        case ITEM:

                            //System.out.println("Case Item: true");

                            if (isFeedHeader) {

                                //System.out.println("Is Feed Header: true");

                                isFeedHeader = false;
                                feed = new StoreRSSFeed(guid, link, name, category, title, description, pubdate,
                                        updated, location);

                                //System.out.println("New Class Created: true");
                            }
                            break;

                        case TITLE:
                            //System.out.println("Case Title: true");
                            title = getCharacterData(event, eventReader);
                            break;

                        case GUID:
                            //System.out.println("Case GUID: true");
                            event = eventReader.nextEvent();
                            guid = event.asCharacters().getData();
                            //System.out.println(guid);
                            break;

                        case LINK:
                            //System.out.println("Case Link: true");
                            link = getCharacterData(event, eventReader);
                            break;

                        case NAME:
                            //System.out.println("Case Name: true");
                            name = getCharacterData(event, eventReader);
                            break;

                        case CATEGORY:
                            //System.out.println("Case Category: true");
                            category = getCharacterData(event, eventReader);
                            break;

                        case DESCRIPTION:
                            //System.out.println("Case Description: true");
                            description = eventReader.getElementText().trim();
                            description = removeHtmlTags(description);
                            //System.out.println(description);
                            break;

                        case UPDATED:
                            //System.out.println("Case Updated: true");
                            updated = getCharacterData(event, eventReader);
                            break;

                        case LOCATION:
                            //System.out.println("Case Location: true");
                            location = getCharacterData(event, eventReader);
                            break;

                        case PUB_DATE:
                            //System.out.println("Case pubDate: true");
                            pubdate = getCharacterData(event, eventReader);
                            break;
                    }

                    //If the event is not the start element, it is the end element
                } else if (event.isEndElement()) {

                    //System.out.println("Is end element: true");

                    if (event.asEndElement().getName().getLocalPart().equals(ITEM)) {
                        StackOverFlowModel message = new StackOverFlowModel();
                        message.setTitle(title);
                        message.setCategory(category);
                        message.setDescription(description);
                        message.setName(name);
                        message.setLocation(location);
                        message.setUpdated(updated);
                        message.setPubDate(pubdate);
                        message.setGuid(guid);
                        message.setLink(link);
                        feed.getJobs().add(message);

                    }
                }
            }
            //end try
        } catch (Exception e) {
            e.printStackTrace();
        }

        return feed;
    }

    //Joe silveira
    //Method to get the value from the rss feed data
    private String getCharacterData(XMLEvent event, XMLEventReader eventReader) throws XMLStreamException {
        String result = "";
        event = eventReader.nextEvent();
        if (event instanceof Characters) {
            result = event.asCharacters().getData();
        }
        return result;
    }


    //Joe Silveira
    //Method to add all jobs to the database
    public void addJobsToDB(List<StackOverFlowModel> jobs) throws SQLException {
        for (int i = 0; i < jobs.size(); i++) {
            dbFunc.addJobToStackOverFlowTable(jobs.get(i).getGuid(), jobs.get(i).getLink(), jobs.get(i).getName(),
                    jobs.get(i).getCategory(), jobs.get(i).getTitle(), jobs.get(i).getDescription(), jobs.get(i).getPubDate(),
                    jobs.get(i).getUpdated(), jobs.get(i).getLocation());
            recordAdded++;
            //System.out.println("Job Added to DB");
        }
    }


    //https://stackoverflow.com/questions/21940554/removing-html-tags-in-rss-feed
    public String removeHtmlTags(String inStr) {
        int index = 0;
        int index2 = 0;
        while (index != -1) {
            index = inStr.indexOf("<");
            index2 = inStr.indexOf(">", index);
            if (index != -1 && index2 != -1) {
                inStr = inStr.substring(0, index).concat(inStr.substring(index2 + 1));
            }
        }

        inStr = inStr.replaceAll("&nbsp;", "");
        return inStr;
    }
}



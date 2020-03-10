package runner;//joe silveira

import java.io.File;

public class runtimeHandler {

    //Class Variables
    int openFile = 0;
    File userFile;
    File programFile;


    public String getApi() {
        return api;
    }

    public void setApi(String api) {
        this.api = api;
    }

    public String getRssUrl() {
        return rssUrl;
    }

    public void setRssUrl(String rssUrl) {
        this.rssUrl = rssUrl;
    }

    String api = "https://jobs.github.com/positions.json?page=";
    String rssUrl = "https://stackoverflow.com/jobs/feed";
    String geoCode = "http://www.mapquestapi.com/geocoding/v1/address?key=xGA2gfYEJmplL7GrATFYpONUR1dGkPxx&location=1600+Pennsylvania+Ave+NW,Washington,DC,20500";
    String fileName = "jobsAPI.txt";

    //Java class variables

    //Program class varialbes
    //FileResource fileIO = new FileResource();

    //Joe silveira
    //Method to run the program with all necessary method calls
    public void startProgram() throws Exception {

//        //ask to open file
//        openFile = ms.askToOpenFile();
//        if (openFile == 0) {
//            //Open the file if supported
//            if (Desktop.isDesktopSupported()) {
//                Desktop.getDesktop().open(fileIO.getUserFilePath());
//            }
//        }

//        /*
//        Methods calls for testing purposes
//         */
//
//        //*********File Stuff*******
//        //Create local file for test and write the exact same contents of the user file to that file
//
//        //Create file in program
//        fileIO.createProgramFile(fileName);
//
//        //Set the program file path
//        programFile = fileIO.getProgramFile();
//
//        //Write to the program file
//        fileIO.writeJobsToFile(http.getJobsList(), programFile);
    }

}

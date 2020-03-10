package connectionRequests;

import org.apache.commons.lang3.StringUtils;

import javax.swing.*;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


public class GeoCoding {

    HttpClient client;

    public String getLattitude() {
        return lattitude;
    }

    public void setLattitude(String lattitude) {
        this.lattitude = lattitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    private String lattitude;
    private String longitude;

    public void geoCode(String location) {
        String geoCode = "http://www.mapquestapi.com/geocoding/v1/address?key=sRDKetLp46KDBGODAHK12sFf4LPYftl2&location=" + location;
        //System.out.println(geoCode);
        try {

            //Instantiate Connection
            client = HttpClient.newHttpClient();
            var requestBuilder = HttpRequest.newBuilder();
            var dataRequest = requestBuilder.uri(URI.create(geoCode)).build();

            //Handle HTTP Response
            HttpResponse<String> response;
            response = client.send(dataRequest, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                JOptionPane.showMessageDialog(null, "Exceeded Monthly Limit of Transactions!");
            }

            var body = response.body();

            getLatLongFromResponse(body);

        } catch (Exception e) {
            System.out.println("error");
            e.printStackTrace();
        }
    }

    private void getLatLongFromResponse(String httpResponse) {

        String latLong = StringUtils.substringBetween(httpResponse, "latLng", "}");
        latLong = latLong.replace(":{", "");
        String lat = StringUtils.substringBetween(latLong, ":", ",");
        String lng = latLong.substring(latLong.lastIndexOf(":"));
        lng = lng.replace(":", "");

        lattitude = lat;
        longitude = lng;

    }
}

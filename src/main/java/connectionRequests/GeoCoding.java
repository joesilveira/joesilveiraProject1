package connectionRequests;

import org.apache.commons.lang3.StringUtils;

import javax.swing.*;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


public class GeoCoding {

    HttpClient client;

    public double getLattitude() {
        return lattitude;
    }

    public void setLattitude(double lattitude) {
        this.lattitude = lattitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    private double lattitude = 0;
    private double longitude = 0;

    public void geoCode(String location) {
        try {

            //Instantiate Connection
            client = HttpClient.newHttpClient();
            var requestBuilder = HttpRequest.newBuilder();
            var dataRequest = requestBuilder.uri(URI.create(location)).build();

            //Handle HTTP Response
            HttpResponse<String> response;
            response = client.send(dataRequest, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                JOptionPane.showMessageDialog(null, "No connection! Try Again!");
                System.exit(0);
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

        lattitude = Double.parseDouble(lat);
        longitude = Double.parseDouble(lng);

    }
}

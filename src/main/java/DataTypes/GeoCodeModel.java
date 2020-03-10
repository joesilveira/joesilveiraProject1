package dataTypes;

public class GeoCodeModel {

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    String city;
    String lat;
    String lng;

    @Override
    public String toString() {
        return "City: " + city + "\n" + "   Lat: " + lat + "\n" + "    Lng: " + lng;

    }
}

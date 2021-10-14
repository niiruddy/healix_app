package com.example.app_healix;

public class location_helper {
    public String Longitude;
    public String Latitude;

    public location_helper(String longitude, String latitude) {
        Longitude = longitude;
        Latitude = latitude;
    }

    public String getLongitude() {
        return Longitude;
    }

    public void setLongitude(String longitude) {
        Longitude = longitude;
    }

    public String getLatitude() {
        return Latitude;
    }

    public void setLatitude(String latitude) {
        Latitude = latitude;
    }
}

package com.example.gpstrackerapp;

public class CreateChild {
    public CreateChild(){};

    public CreateChild(String name) {
        this.name = name;
        this.latitude = null;
        this.longitude = null;
        this.id = null;
    }

    ;

    public CreateChild(String name, String latitude, String longitude, String id) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.id = id;
    }
    public String name;
    public String latitude;
    public String longitude;
    public String id;
}

package com.example.gpstrackerapp;

import java.util.Stack;

public class Child {
    public Child() {
    }

    ;

    public Child(String name) {
        this.name = name;
    }

    ;

    public Child(String name, String latitude, String longitude, String relationship) {
        this.name = name;
        this.locations.push(new location(longitude,latitude));
        this.relationship = relationship;
    }

    public String name;
    public String relationship;
    public Stack locations = new Stack<location>();
    public class location {

        public location() {
        }

        ;

        public location(String longtitude, String latitude) {
            this.latitude = latitude;
            this.longtitude = longtitude;
        }

        public String longtitude;
        public String latitude;
    }
}

package com.example.gpstrackerapp;

import java.util.Stack;

public class Child {
    public String mName;
    public String mRelationship;
    public Stack mLocations = new Stack<ChildLocation>();

    public Child() {
    }

    public Child(String mName) {
        this.mName = mName;
    }

    public Child(String name, String latitude, String longitude, String relationship) {
        this.mName = name;
        this.mLocations.push(new ChildLocation(longitude, latitude));
        this.mRelationship = relationship;
    }

    public class ChildLocation {
        public String mLongtitude;
        public String mLatitude;

        public ChildLocation() {
        }

        public ChildLocation(String longtitude, String latitude) {
            this.mLatitude = latitude;
            this.mLongtitude = longtitude;
        }
    }
}

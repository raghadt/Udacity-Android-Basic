package com.example.raghadtaleb.project5_guidtour;


import java.util.ArrayList;

public class Resources {
    String name;
    String resourceType;

    public Resources(String name, String resourceType) {
        this.name = name;
        this.resourceType = resourceType;
    }


    public String getResourceType() {

        return resourceType;
    }

    public String getName() {
        return name;
    }

}

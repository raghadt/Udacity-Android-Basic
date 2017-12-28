package com.example.raghadtaleb.project5_guidtour;


public class Resources {
    String name;
    String resourceType;
    int imageResourceId;

    public Resources(String name, String resourceType, int imageResourceId) {
        this.name = name;
        this.resourceType = resourceType;
        this.imageResourceId = imageResourceId;
    }


    public String getResourceType() {
        return resourceType;
    }

    public String getName() {
        return name;
    }

    public int getImageResourceId(){
        return imageResourceId;
    }

}

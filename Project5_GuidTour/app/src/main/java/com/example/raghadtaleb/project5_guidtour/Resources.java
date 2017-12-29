package com.example.raghadtaleb.project5_guidtour;


public class Resources {

    private static final int NO_IMAGE_PROVIDED = -1;
    String name;
    String resourceType;
    private int imageResourceId = NO_IMAGE_PROVIDED;

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

    public int getImageResourceId() {
        return imageResourceId;
    }

    public boolean hasImage() {
        return imageResourceId != NO_IMAGE_PROVIDED;
    }
}
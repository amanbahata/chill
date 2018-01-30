package com.chillbox.app.network.model;

/**
 * Created by aman1 on 01/01/2018.
 */

public final class TrackingData {

    private final String id;
    private final String name;
    private final String type;

    private TrackingData(String id, String name, String type) {
        this.id = id;
        this.name = name;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public static TrackingData create(String id, String name, String type) {
        return new TrackingData(id, name, type);
    }
}

package com.example.myapplication.Model;

public class WatchlistModel {
    private String id;
    private String name;
    private String type;
    private String path;

    public WatchlistModel(String id, String name, String type, String path) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.path = path;
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

    public String getPath() {
        return path;
    }
}

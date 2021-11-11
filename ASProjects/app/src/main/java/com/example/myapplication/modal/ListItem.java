package com.example.myapplication.modal;

public class ListItem {
    private String id;
    private String resultTypeTextV;
    private String resultRateTextV;
    private String resultNameTextV;
    private String resultImageViewUrl;
    private String type;
    private String path;

    // Constructor
    public ListItem(String resultTypeTextV, String resultRateTextV, String resultNameTextV, String getResultImageViewUrl, String id,String type,String path) {
        this.resultTypeTextV = resultTypeTextV;
        this.resultRateTextV = resultRateTextV;
        this.resultNameTextV = resultNameTextV;
        this.resultImageViewUrl = getResultImageViewUrl;
        this.id = id;
        this.type = type;
        this.path = path;
    }

    // Getters
    public String getResultTypeTextV() {
        return resultTypeTextV;
    }

    public String getResultRateTextV() {
        return resultRateTextV;
    }

    public String getResultNameTextV() {
        return resultNameTextV;
    }

    public String getResultImageViewUrl() {
        return resultImageViewUrl;
    }

    public String getId() {
        return id;
    }
    public String getType() {
        return type;
    }

    public String getPath() {
        return path;
    }
}

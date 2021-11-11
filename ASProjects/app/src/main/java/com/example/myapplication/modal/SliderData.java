package com.example.myapplication.modal;

public class SliderData {

    // image url is used to
    // store the url of image
    private String imgUrl;
    private String id;
    private String type;
    private String name;

    // Constructor method.
    public SliderData(String imgUrl,String id, String type,String name) {
        this.imgUrl = imgUrl;
        this.id = id;
        this.type = type;
        this.name = name;
    }

    // Getter method
    public String getImgUrl() {
        return imgUrl;
    }
    public String getId() {
        return id;
    }
    public String getType() {
        return type;
    }

    // Setter method
    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getName() {
        return name;
    }


}

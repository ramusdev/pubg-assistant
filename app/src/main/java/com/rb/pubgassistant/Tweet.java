package com.rb.pubgassistant;

import java.util.ArrayList;

public class Tweet {

    public String text;
    public String created_at;
    public String id;
    public ArrayList<String> imageKeys = new ArrayList<String>();
    public ArrayList<String> images = new ArrayList<String>();

    public String toString() {
        return "text: " + text + "\n" +
                "created_at: " + created_at + "\n" +
                "id: " + id + "\n" +
                "image: " + images + "\n\n";
    }

    public void setImageKeys(String imageKey) {
        this.imageKeys.add(imageKey);
    }

    public void setImage(String url) {
        this.images.add(url);
    }

    public void setImages(ArrayList<String> images) {
        this.images = images;
    }

    public ArrayList<String> getImageKeys() {
        return imageKeys;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setDate(String date) {
        this.created_at = date;
    }

    public void setText(String text) {
        this.text = text;
    }

    public ArrayList<String> getImages() {
        return images;
    }

    public String getDate() {
        return created_at;
    }

    public String getText() {
        return text;
    }

    public String getId() {
        return id;
    }

}

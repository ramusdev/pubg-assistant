package com.rb.pubgassistant;

public class Wallpaper {
    public String image;

    public Wallpaper() {

    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Link: " + image;
    }
}

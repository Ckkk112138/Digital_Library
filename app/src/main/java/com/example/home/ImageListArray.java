package com.example.home;

public class ImageListArray {
    private String name,author;
    private int imageID;

    public ImageListArray(String name, String author,int imageId)
    {
        this.name = name;
        this.author = author;
        this.imageID = imageId;
    }

    public String getName()
    {
        return name;
    }

    public int getImageID() {
        return imageID;
    }

    public String getAuthor() {
        return author;
    }

}

package com.example.meditake.models;

/***
 "Created by  TETEREOU Aboudourazakou on "11/20/2022
 "Project name "MediTake
 */
public class MenuItem {

    private  String title;
    private int  image;

    public MenuItem(String title,int image) {
        this.title = title;
        this.image = image;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}

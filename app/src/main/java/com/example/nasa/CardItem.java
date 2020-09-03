package com.example.nasa;

public class CardItem {
    private String title, description;
    public CardItem(String t, String desc){
        title = t;
        description = desc;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
}

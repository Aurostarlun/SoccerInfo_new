package com.example.lenovo.soccerinfo;

/**
 * Created by lenovo on 2019/6/15.
 */

public class TitleList {

    private int id;
    private String title;

    public TitleList(String title) {
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}

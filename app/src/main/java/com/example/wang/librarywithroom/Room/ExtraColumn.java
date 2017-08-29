package com.example.wang.librarywithroom.Room;

/**
 * Created by wang on 2017/8/28.
 */

public class ExtraColumn {

    private String tag;
    private String title;

    @Override
    public String toString() {
        return "ExtraColumn{" +
                "tag='" + tag + '\'' +
                ", title='" + title + '\'' +
                '}';
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}

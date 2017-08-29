package com.example.wang.librarywithroom.Room;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by wang on 2017/8/25.
 */
@Entity(tableName = "school")
public class School {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "schoolName")
    private String schoolName;
    @ColumnInfo(name = "city")
    private String city;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}

package com.example.wang.librarywithroom.Room;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by wang on 2017/8/25.
 */
@Entity(tableName = "computer")
class Computer {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "com_name")
    private String name;
    @ColumnInfo(name = "last_update")
    private String last_update;

    public String getLast_update() {
        return last_update;
    }

    public void setLast_update(String last_update) {
        this.last_update = last_update;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

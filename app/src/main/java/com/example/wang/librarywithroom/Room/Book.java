package com.example.wang.librarywithroom.Room;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

/**  表
 * Created by wang on 2017/8/23.
 */
@Entity(tableName = "book") //tableName定义表名 默认类名 indices 索引列表，默认可以不写 索引有助于查询性能
public class Book {
    @PrimaryKey(autoGenerate = true)// PrimaryKey 为 表的主键   必须为有一个主键不然报错 autoGenerate不写 默认为false=不是自增
    private int id;
    @ColumnInfo(name = "book_name")//ColumnInfo 可以没有 默认列的命名就是字段名字  有按name 的Value
    private String bookName;
    @ColumnInfo(name = "author")
    private String author;
    @ColumnInfo(name = "data")
    private String date;
    @Ignore //忽略  就是此字段 在表里没有此列
    private String alias;
    @Embedded   //给一些字段 装一个类方便管理  这个和把ExtraColumn的字段放在外面是一个意思，但封装起来就必须次注解
    private ExtraColumn mExtraColumn;

    public ExtraColumn getExtraColumn() {
        return mExtraColumn;
    }

    public void setExtraColumn(ExtraColumn extraColumn) {
        mExtraColumn = extraColumn;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", bookName='" + bookName + '\'' +
                ", author='" + author + '\'' +
                ", date='" + date + '\'' +
                ", alias='" + alias + '\'' +
                ", mExtraColumn=" +  mExtraColumn.toString()+
                '}';
    }
}

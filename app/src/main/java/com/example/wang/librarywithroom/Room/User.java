package com.example.wang.librarywithroom.Room;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by wang on 2017/8/24.
 */
@Entity(foreignKeys = @ForeignKey(entity = Book.class, onDelete = ForeignKey.CASCADE, onUpdate = ForeignKey.CASCADE,
        parentColumns = "id", childColumns = "bookId"), indices = @Index("bookId"))
//上面注解详解  foreignKeys 外键  entity 代表父级表，parentColumns 和childColumn 代表父级和子级产生关联的列，
//onDelete=ForeignKey.CASCADE 代表 当父级的parentColumns 删除的时候对应子级需要跟着删除对应childColumn的所有数据，
//当然还有别的处理方式 ForeignKey.RESTRICT等等。onUpdate = ForeignKey.CASCADE同样的道理。点进OnDelete 自行查阅各个值的含义。
public class User {
    @PrimaryKey
    @ColumnInfo(name = "column_name")
    private String name;
    private int age;
    @ColumnInfo(name = "bookId")
    private int bookId;

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

}

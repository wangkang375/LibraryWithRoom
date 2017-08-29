package com.example.wang.librarywithroom.Room;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

/**
 *
 * Created by wang on 2017/8/24.
 */
@Dao
public interface UserDAO {
    @Query("SELECT * FROM user")
    List<User> query();

    //onConflict = OnConflictStrategy.REPLACE  代表当然插入数据时发生适用的约束违规时就替换上个数据，
    // （比如插入一个主键一样的数据 违背主键唯一性，此时我们的措施就是替换该数据）
    //还有数值参考 https://sqlite.org/lang_conflict.html
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<User> users);
}

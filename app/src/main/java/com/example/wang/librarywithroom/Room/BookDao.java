package com.example.wang.librarywithroom.Room;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.RoomWarnings;
import android.arch.persistence.room.Update;
import java.util.List;
import io.reactivex.Flowable;


/**
 *  操作数据库
 * Created by wang on 2017/8/23.
 */
@Dao
public interface BookDao {
    @Insert //插入数据
    void saveBook(Book book);

    @SuppressWarnings("unused")
    @Update //更新 数据  主要是依照主键
    void updateBook(Book book);

    @Query("SELECT * FROM book") //查询 book 表下的数据 此注解 写任何SQLite语句，方法返回值也不只是List
    List<Book> query();

    @SuppressWarnings("unused")
    @Query("SELECT * FROM book WHERE book_name = :bookName ") //当用到外部 条件查询的时候 可以用：加参数
    List<Book> queryWhere(String bookName);

    @SuppressWarnings("unused")
    @Delete //删除 返回值为影响的行数
    int delete(Book book);

    @Query("DELETE FROM book WHERE book_name = :bookName")

    int delete(String bookName);

    @Query("SELECT * FROM book ORDER BY data DESC")
    List<Book> selectDesc();

    @Query("SELECT tag,title FROM book")   //查询的Column不一定需要返回为整个类集合 当有把一些字段 封装的另个类里 也用返回封装类的集合
    List<ExtraColumn> queryExtraColumn();

    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)//此注解为 查询的Column 和 返回的类的字段不能完全匹配 会报警告
    @Query("SELECT * FROM book")
    List<ExtraColumn> queryExtraColumnWithAll();


    @Query("SELECT tag,title FROM book WHERE tag= :tag")
    List<ExtraColumn> queryWhereExtraColumn(String tag);


    /**
     * 结合RxJava2的运用
     **/
    @Query("SELECT * FROM book")
    Flowable<List<Book>> queryAll();
}

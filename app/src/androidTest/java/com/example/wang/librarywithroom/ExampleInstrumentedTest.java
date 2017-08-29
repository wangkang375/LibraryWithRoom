package com.example.wang.librarywithroom;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.example.wang.librarywithroom.Room.Book;
import com.example.wang.librarywithroom.Room.LibraryDataBase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    private LibraryDataBase mDataBase;

    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.example.wang.librarywithroom", appContext.getPackageName());
    }

    @Before
    public void initDB() {
        mDataBase = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(), LibraryDataBase.class).build();
    }
    @Test
    public void checkDate(){
        Book book = new Book();
        book.setId(1);
        book.setBookName("java");
        book.setAuthor("wk");
        mDataBase.getBookDao().saveBook(book);

        List<Book> query = mDataBase.getBookDao().query();
        Book bookDB = query.get(0);
        assertEquals(bookDB.getBookName(),book.getBookName());
    }
    @After
    public void closeDb(){
        mDataBase.close();
    }


}

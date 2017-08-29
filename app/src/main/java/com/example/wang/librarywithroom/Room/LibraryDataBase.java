package com.example.wang.librarywithroom.Room;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.migration.Migration;
import android.content.Context;

/**
 * 数据库
 * Created by wang on 2017/8/23.
 */
@Database(entities = {Book.class,User.class}, version = 1 ,exportSchema = false)
public abstract class LibraryDataBase extends RoomDatabase {
    public abstract BookDao getBookDao();

    public abstract UserDAO getUserDao();

    private static LibraryDataBase sLibraryDataBase;

    public static LibraryDataBase getLibraryDataBase(Context context) {
        if (sLibraryDataBase == null) {
            synchronized (LibraryDataBase.class) {
                if (sLibraryDataBase == null) {
                    sLibraryDataBase = Room.databaseBuilder(context
                            , LibraryDataBase.class, "Library").build();//参数二：创建数据库的类，参数三：数据库的名字。
                }
            }
        }
        return sLibraryDataBase;
    }

    private static final Migration migration1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("CREATE TABLE IF NOT EXISTS `school` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `schoolName` TEXT, `city` TEXT)");
        }
    };

    private static final Migration migration_2_3 = new Migration(2, 3) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("CREATE TABLE IF NOT EXISTS `computer` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `com_name` TEXT)");
        }
    };

    private static final Migration migration_3_4 = new Migration(3, 4) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE computer ADD COLUMN last_update TEXT");
        }
    };
}

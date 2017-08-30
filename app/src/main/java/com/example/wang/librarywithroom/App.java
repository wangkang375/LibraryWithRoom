package com.example.wang.librarywithroom;

import android.app.Application;

import com.facebook.stetho.Stetho;

/**
 *
 * Created by wang on 2017/8/23.
 */

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        /*用 Chrome  查看数据库  浏览器输入  chrome://inspect  */
        Stetho.initializeWithDefaults(this);
    }
}

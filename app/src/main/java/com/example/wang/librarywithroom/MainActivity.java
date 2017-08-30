package com.example.wang.librarywithroom;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.wang.librarywithroom.Room.BookDao;
import com.example.wang.librarywithroom.Room.LibraryDataBase;
import com.example.wang.librarywithroom.Room.User;
import com.example.wang.librarywithroom.Room.UserDAO;
import com.example.wang.librarywithroom.fragment.BorrowFragment;
import com.example.wang.librarywithroom.fragment.MinFragment;
import com.example.wang.librarywithroom.fragment.QueryFragment;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private FrameLayout mContent;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    replaceFragment(0);

                    return true;
                case R.id.navigation_dashboard:
                    replaceFragment(1);

                    return true;
                case R.id.navigation_notifications:
                    replaceFragment(2);
                    return true;
            }
            return false;
        }

    };
    private BorrowFragment mBorrowFragment;
    private MinFragment mMinFragment;
    private QueryFragment mQueryFragment;
    private User mUser;
    private BookDao mBookDao;
    private UserDAO mUserDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        mBorrowFragment = BorrowFragment.newInstance();
        mMinFragment = MinFragment.newInstance();
        mQueryFragment = QueryFragment.newInstance();

        replaceFragment(0);

        initDAO();
        /*为测试外键做准备*/
//        saveUser();

    }

    private void initDAO() {
        mBookDao = LibraryDataBase.getLibraryDataBase(this).getBookDao();
        mUserDao = LibraryDataBase.getLibraryDataBase(this).getUserDao();
    }

    /**
     * 获得userDAO
     */
    public UserDAO getUserDao() {
        return mUserDao;
    }

    /**
     * 获得BookDao
     */
    public BookDao getBookDao() {
        return mBookDao;
    }


    private void replaceFragment(int position) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        switch (position) {
            case 0:
                transaction.replace(R.id.content, mQueryFragment);
                break;
            case 1:
                transaction.replace(R.id.content, mBorrowFragment);
                break;
            default:
                transaction.replace(R.id.content, mMinFragment);
                break;
        }

        transaction.commit();
    }

}

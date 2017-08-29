package com.example.wang.librarywithroom.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wang.librarywithroom.MainActivity;
import com.example.wang.librarywithroom.Room.LibraryDataBase;
import com.example.wang.librarywithroom.Room.User;
import com.example.wang.librarywithroom.Room.UserDAO;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 *
 * Created by wang on 2017/8/23.
 */

public class MinFragment extends Fragment {

    private UserDAO mUserDao;
    private User mUser;

    public static MinFragment newInstance() {

        Bundle args = new Bundle();

        MinFragment fragment = new MinFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Button button = new Button(getContext());
        button.setText("加入外键数据");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveUser();
            }
        });
        return button;
    }

    private void saveUser() {
        mUserDao = LibraryDataBase.getLibraryDataBase(getContext()).getUserDao();
        final List<User> userList = new ArrayList<>();
        mUser = new User();
        mUser.setBookId(1);
        mUser.setName("小明");
        userList.add(mUser);

        mUser = new User();
        mUser.setName("小王");
        mUser.setBookId(1);
        userList.add(mUser);

        mUser = new User();
        mUser.setName("小李");
        mUser.setBookId(3);
        userList.add(mUser);
        Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(ObservableEmitter<Object> e) throws Exception {
                mUserDao.insert(userList);
                e.onNext(1);
                e.onComplete();
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Object>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Object o) {
                        Toast.makeText(getActivity(), "保存User成功", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(getActivity(), "保存User失败", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}

package com.example.wang.librarywithroom.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wang.librarywithroom.MainActivity;
import com.example.wang.librarywithroom.R;
import com.example.wang.librarywithroom.Room.BookDao;
import com.example.wang.librarywithroom.Room.ExtraColumn;
import com.example.wang.librarywithroom.Room.LibraryDataBase;

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

public class BorrowFragment extends Fragment implements View.OnClickListener {

    private AppCompatEditText editTag;
    private AppCompatEditText editTitle;
    private AppCompatButton queryExtra;
    private TextView tvContent;
    private BookDao mBookDao;
    //是不是
    public static BorrowFragment newInstance() {
        Bundle args = new Bundle();
        BorrowFragment fragment = new BorrowFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_borrow, container, false);
        mBookDao = ((MainActivity) getActivity()).getBookDao();
        editTag = view.findViewById(R.id.edit_tag);
        editTitle = view.findViewById(R.id.edit_title);
        queryExtra = view.findViewById(R.id.query_extra);
        tvContent = view.findViewById(R.id.tv_content);
        queryExtra.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.query_extra:
                if (TextUtils.isEmpty(editTag.getText().toString())) {
                    queryExtraC();
                } else {
                    queryWhereExtraColumn(editTag.getText().toString());
                }
                break;
            default:
                break;
        }
    }

    private void queryWhereExtraColumn(final String tag) {
        Observable.create(new ObservableOnSubscribe<List<ExtraColumn>>() {
            @Override
            public void subscribe(ObservableEmitter<List<ExtraColumn>> e) throws Exception {
                e.onNext(mBookDao.queryWhereExtraColumn(tag));
                e.onComplete();
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<ExtraColumn>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<ExtraColumn> extraColumns) {
                        tvContent.setText(extraColumns.size() > 0 ? extraColumns.get(0).toString() : "");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(getContext(), "查询错误" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    private void queryExtraC() {
        Observable.create(new ObservableOnSubscribe<List<ExtraColumn>>() {
            @Override
            public void subscribe(ObservableEmitter<List<ExtraColumn>> e) throws Exception {
                e.onNext(mBookDao.queryExtraColumnWithAll());
                e.onComplete();
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<ExtraColumn>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<ExtraColumn> extraColumns) {
                        tvContent.setText(extraColumns.size() > 0 ? extraColumns.get(0).toString() : "");
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}

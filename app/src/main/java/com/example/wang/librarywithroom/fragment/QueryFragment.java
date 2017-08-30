package com.example.wang.librarywithroom.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.wang.librarywithroom.MainActivity;
import com.example.wang.librarywithroom.R;
import com.example.wang.librarywithroom.Room.Book;
import com.example.wang.librarywithroom.Room.BookDao;
import com.example.wang.librarywithroom.Room.ExtraColumn;
import com.example.wang.librarywithroom.Room.LibraryDataBase;
import com.example.wang.librarywithroom.adapter.BookAdapter;
import com.example.wang.librarywithroom.callback.OnItemClick;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 *
 * Created by wang on 2017/8/23.
 */

public class QueryFragment extends Fragment implements View.OnClickListener, OnItemClick<Book> {
    private AppCompatEditText mBookName;
    private AppCompatEditText mBookAuthor;
    private AppCompatEditText mAlias;
    private AppCompatButton mAddLibrary;
    private AppCompatButton mQueryBook;
    private AppCompatButton mDoDatabase;
    private AppCompatEditText mEditTag;
    private RecyclerView mRecycleView;
    private BookDao mBookDao;
    private Book mBook;
    private AppCompatButton mClearEdition;
    private String[] mItems = {"正序(结合RxJava2.x)", "倒序"};
    private List<Book> mBookList = new ArrayList<>();
    private BookAdapter mBookAdapter;

    public static QueryFragment newInstance() {
        Bundle args = new Bundle();
        QueryFragment fragment = new QueryFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_query, container, false);
        mBookName = view.findViewById(R.id.bookName);
        mBookAuthor = view.findViewById(R.id.bookAuthor);
        mAlias = view.findViewById(R.id.alias);
        mAddLibrary = view.findViewById(R.id.addLibrary);
        mQueryBook = view.findViewById(R.id.query_book);
        mRecycleView = view.findViewById(R.id.recycle_view);
        mClearEdition = view.findViewById(R.id.clear_edition);
        mDoDatabase = view.findViewById(R.id.do_database);
        mEditTag = view.findViewById(R.id.edit_tag);
        mDoDatabase.setOnClickListener(this);
        mAddLibrary.setOnClickListener(this);
        mQueryBook.setOnClickListener(this);
        mClearEdition.setOnClickListener(this);
        init();
        return view;
    }

    private void init() {
        mBookDao = ((MainActivity) getActivity()).getBookDao();

        mBookAdapter = new BookAdapter(mBookList, getActivity());
        mRecycleView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecycleView.setAdapter(mBookAdapter);
        mBookAdapter.setOnItemClick(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.addLibrary:
                addBook();
                break;
            case R.id.query_book:
                queryAllBooks();
                break;
            case R.id.clear_edition:
                mBookName.setText(null);
                mAlias.setText(null);
                mBookAuthor.setText(null);
                mEditTag.setText(null);
                break;
            case R.id.do_database:
                operationDatabase();
                break;
        }
    }

    /**
     * 操作数据库
     */
    private void operationDatabase() {
        new AlertDialog.Builder(getActivity()).setTitle("操作数据库")
                .setItems(mItems, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0://正序
                                rxJava2_Query();
                                break;
                            case 1://倒序
                                decs();
                                break;
                        }
                    }
                }).create().show();
    }

    /***
     * 结合RxJava 2 的Room 查询
     */
    private void rxJava2_Query() {
        mBookDao.queryAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Book>>() {
                    @Override
                    public void onSubscribe(Subscription s) {
                        s.request(Integer.MAX_VALUE);
                    }

                    @Override
                    public void onNext(List<Book> books) {
                        QueryFragment.this.notify(books);
                    }

                    @Override
                    public void onError(Throwable t) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void decs() {
        Observable.create(new ObservableOnSubscribe<List<Book>>() {
            @Override
            public void subscribe(ObservableEmitter<List<Book>> e) throws Exception {
                e.onNext(mBookDao.selectDesc());
                e.onComplete();
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Book>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<Book> books) {
                        QueryFragment.this.notify(books);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    private void notify(List<Book> books) {
        mBookList.clear();
        mBookList.addAll(books);
        mBookAdapter.notifyDataSetChanged();
    }

    /**
     * 查询所有书籍
     */
    private void queryAllBooks() {
        Observable.create(new ObservableOnSubscribe<List<Book>>() {
            @Override
            public void subscribe(ObservableEmitter<List<Book>> e) throws Exception {
                e.onNext(mBookDao.query());
                e.onComplete();
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Book>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<Book> books) {
                        QueryFragment.this.notify(books);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    /**
     * 删除书籍
     */
    private void deleteBook(final Book book) {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                e.onNext(mBookDao.delete(book.getBookName()));
                e.onComplete();
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        mBookList.remove(book);
                        String format = String.format(Locale.CHINA, "成功删除%s行", String.valueOf(integer));
                        mBookAdapter.notifyDataSetChanged();
                        Toast.makeText(getActivity(), format, Toast.LENGTH_SHORT).show();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Toast.makeText(getActivity(), "删除失败" + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {

                    }
                });

    }

    /**
     * 添加书籍。
     */
    public void addBook() {
        mBook = new Book();
        mBook.setAuthor(mBookAuthor.getText().toString());
        mBook.setBookName(mBookName.getText().toString());
        ExtraColumn extraColumn = new ExtraColumn();
        extraColumn.setTag(mEditTag.getText().toString());
        mBook.setExtraColumn(extraColumn);
        Date time = Calendar.getInstance().getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyy-MM-dd hh:mm:ss");
        String date = dateFormat.format(time);
        mBook.setDate(date);
        mBook.setAlias(mAlias.getText().toString());
        Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(ObservableEmitter<Object> e) throws Exception {
                mBookDao.saveBook(mBook);
                /* RxJava2.x  onNext 参数不可以为Null了。*/
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

                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(getActivity(), "保存失败", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete() {
                        mBookList.add(mBook);
                        mBookAdapter.notifyDataSetChanged();
                        Toast.makeText(getActivity(), "保存成功", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private String[] mStrings = {"删除"};

    @Override
    public void onItemClick(int position, final Book book) {
        new AlertDialog.Builder(getActivity()).setItems(mStrings, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        deleteBook(book);
                        break;
                    case 1:

                        break;
                }
            }
        }).create().show();
    }
}

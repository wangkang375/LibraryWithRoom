package com.example.wang.librarywithroom.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.wang.librarywithroom.R;
import com.example.wang.librarywithroom.Room.Book;
import com.example.wang.librarywithroom.callback.OnItemClick;

import java.util.List;

/**
 *
 * Created by wang on 2017/8/24.
 */

public class BookAdapter extends RecyclerView.Adapter<BookVH> {
    private List<Book> mBooks;
    private Context mContext;
    private OnItemClick mOnItemClick;
    public BookAdapter(List<Book> books, Context context) {
        mBooks = books;
        mContext = context;
    }
    public void setOnItemClick(OnItemClick onItemClick) {
        mOnItemClick = onItemClick;
    }
    @Override
    public BookVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.item_book_info, parent, false);
        return new BookVH(inflate);
    }

    @Override
    public void onBindViewHolder(final BookVH holder, int position) {
        final Book book = mBooks.get(position);
        holder.mTvBook.setText(book.toString());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClick.onItemClick(holder.getAdapterPosition(),book);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mBooks.size();
    }


    public void notify(List<Book> books) {
        mBooks.clear();
        mBooks.addAll(books);
        notifyDataSetChanged();
    }
}

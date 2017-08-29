package com.example.wang.librarywithroom.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.wang.librarywithroom.R;

/**
 * Created by wang on 2017/8/24.
 */

public class BookVH extends RecyclerView.ViewHolder {
    public TextView mTvBook;

    public BookVH(View itemView) {
        super(itemView);

        mTvBook = (TextView) itemView.findViewById(R.id.tv_book);

    }
}

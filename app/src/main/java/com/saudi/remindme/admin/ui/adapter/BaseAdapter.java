package com.saudi.remindme.admin.ui.adapter;

import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;

import com.saudi.remindme.admin.ui.interfaces.OnUserListener;

import java.util.List;

public abstract class BaseAdapter<T, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {
    protected OnUserListener onUserListener;
    protected Context mContext;
    protected List<T> itemList;

    public BaseAdapter(Context mContext, List<T> itemList, OnUserListener onUserListener) {
        this.mContext = mContext;
        this.itemList = itemList;
        this.onUserListener = onUserListener;
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }


}

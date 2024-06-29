package com.saudi.remindme.user.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.saudi.remindme.R;
import com.saudi.remindme.user.ui.model.BaseInfoModel;

import java.util.List;


public class BaseInfoAdapter extends RecyclerView.Adapter<BaseInfoAdapter.ViewHolder> {

    private final Context mContext;
    private final List<BaseInfoModel> infoModels;

    public BaseInfoAdapter(Context mContext, List<BaseInfoModel> infoModels) {
        this.mContext = mContext;
        this.infoModels = infoModels;

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(mContext).inflate(R.layout.info_item2, parent, false);
        return new ViewHolder(rootView);

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final BaseInfoModel info = infoModels.get(position);

        holder.generalTitleTextView.setText(info.getTitle());

        InfoAdapter infoAdapter = new InfoAdapter(mContext, info.getInfoList());
        holder.innerRecyclerView.setAdapter(infoAdapter);


    }

    @Override
    public int getItemCount() {
        return infoModels.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView generalTitleTextView;
        RecyclerView innerRecyclerView;


        ViewHolder(View itemView) {
            super(itemView);

            generalTitleTextView = itemView.findViewById(R.id.generalTitleTextView);

            innerRecyclerView = itemView.findViewById(R.id.inneral_recycler_view);

            innerRecyclerView.setLayoutManager(new LinearLayoutManager(itemView.getContext()));

        }
    }

}



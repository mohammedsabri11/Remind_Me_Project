package com.saudi.remindme.user.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.saudi.remindme.R;
import com.saudi.remindme.user.ui.interfaces.OnDeleteListener;
import com.saudi.remindme.user.ui.model.QueryItem;

import java.util.List;


public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {
    private final Context mContext;
    private final List<QueryItem> sQueryModels;
    private final OnDeleteListener onDeleteListener;


    public ChatAdapter(Context mContext, List<QueryItem> sQueryModel) {
        this.mContext = mContext;
        this.sQueryModels = sQueryModel;
        this.onDeleteListener = (OnDeleteListener) mContext;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View rootView = LayoutInflater.from(mContext).inflate(R.layout.item_chat, parent, false);
        return new ViewHolder(rootView);

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final QueryItem queryModel = sQueryModels.get(position);


        holder.queryReply.setText(queryModel.getReply());
        if (queryModel.getReply().isEmpty() || queryModel.getReply().equals("null")) {
            holder.queryReply.setText("waiting for Reply");
        }
        holder.replyDate.setText(queryModel.getReplyDate());//
        holder.queryDate.setText(queryModel.getQueryDate());
        holder.queryText.setText(queryModel.getText());
        holder.queryText.setOnLongClickListener(v -> {
            onDeleteListener.onDelete(queryModel.getId());
            return false;
        });


    }

    @Override
    public int getItemCount() {
        return sQueryModels.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView queryReply;
        TextView queryText;
        TextView replyDate;
        TextView queryDate;

        //ImageButton queryDelete;
        ViewHolder(View itemView) {
            super(itemView);

            this.queryReply = itemView.findViewById(R.id.replyText);
            this.queryText = itemView.findViewById(R.id.messageText);
            this.replyDate = itemView.findViewById(R.id.replyDate);
            // this.queryDelete = itemView.findViewById(R.id.query_delete);
            this.queryDate = itemView.findViewById(R.id.queryDate);
        }
    }

}



package com.saudi.remindme.consultant.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.saudi.remindme.R;
import com.saudi.remindme.user.ui.model.QueryItem;

import java.util.List;


public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {
    private final Context mContext;
    private final List<QueryItem> sQueryModels;
    private final QueryInterface queryInterface;


    public ChatAdapter(Context mContext, List<QueryItem> sQueryModel) {
        this.mContext = mContext;
        this.sQueryModels = sQueryModel;
        this.queryInterface = (QueryInterface) mContext;
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
            holder.queryReply.setText("Click here for Reply");
        }
        holder.replyDate.setText(queryModel.getReplyDate());
        holder.queryDate.setText(queryModel.getQueryDate());
        holder.queryText.setText(queryModel.getText());
        holder.queryReply.setOnClickListener(view -> queryInterface.onReply(queryModel.getId()));


    }

    @Override
    public int getItemCount() {
        return sQueryModels.size();
    }

    public interface QueryInterface {
        void onReply(String queryID);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView queryReply;
        TextView queryText;
        TextView replyDate;
        TextView queryDate;

        ViewHolder(View itemView) {
            super(itemView);

            this.queryReply = itemView.findViewById(R.id.replyText);
            this.queryText = itemView.findViewById(R.id.messageText);
            this.replyDate = itemView.findViewById(R.id.replyDate);
            this.queryDate = itemView.findViewById(R.id.queryDate);

        }
    }

}



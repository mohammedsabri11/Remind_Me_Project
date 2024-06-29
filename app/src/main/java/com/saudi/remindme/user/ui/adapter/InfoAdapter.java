package com.saudi.remindme.user.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.saudi.remindme.R;
import com.saudi.remindme.user.ui.model.InfoModel;

import java.util.List;


public class InfoAdapter extends RecyclerView.Adapter<InfoAdapter.ViewHolder> {

    private final Context mContext;
    private final List<InfoModel> infoModels;

    public InfoAdapter(Context mContext, List<InfoModel> infoModels) {
        this.mContext = mContext;
        this.infoModels = infoModels;

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(mContext).inflate(R.layout.info_item, parent, false);
        return new ViewHolder(rootView);

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final InfoModel info = infoModels.get(position);

        holder.title.setText(info.getTitle());


        holder.instructions.setText(info.getInstructions());

        holder.divider.setVisibility(position == (infoModels.size() - 1) ? View.GONE : View.VISIBLE);

    }

    @Override
    public int getItemCount() {
        return infoModels.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        View divider;
        TextView instructions;

        ViewHolder(View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.title);


            instructions = itemView.findViewById(R.id.instructions);
            divider = itemView.findViewById(R.id.divider);

        }
    }

}



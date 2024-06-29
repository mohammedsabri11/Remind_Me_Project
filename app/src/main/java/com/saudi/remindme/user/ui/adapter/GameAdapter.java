package com.saudi.remindme.user.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.saudi.remindme.R;
import com.saudi.remindme.consultant.ui.model.GameItem;

import java.util.List;


public class GameAdapter extends RecyclerView.Adapter<GameAdapter.ViewHolder> {
    private final Context mContext;
    private final List<GameItem> sGameItems;
    private final GameInterface gameInterface;


    public GameAdapter(Context mContext, List<GameItem> sUserModel, GameInterface gameInterface) {
        this.mContext = mContext;
        this.sGameItems = sUserModel;
        this.gameInterface = gameInterface;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        View rootView = LayoutInflater.from(mContext).inflate(R.layout.patient_game_item, parent, false);

        return new ViewHolder(rootView);

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final GameItem game = sGameItems.get(position);

        holder.gameNameTextView.setText(game.getName());

        holder.gameDescriptionTextView.setText(game.getDescription());
        holder.gamePlayButton.setOnClickListener(view -> gameInterface.onPlay(game.getLink()));


    }

    @Override
    public int getItemCount() {
        return sGameItems.size();
    }

    public interface GameInterface {
        void onPlay(String link);


    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView gameNameTextView;
        TextView gameDescriptionTextView;
        AppCompatButton gamePlayButton;


        ViewHolder(View itemView) {
            super(itemView);

            this.gameNameTextView = itemView.findViewById(R.id.gameNameTextView);

            this.gameDescriptionTextView = itemView.findViewById(R.id.gameDescriptionTextView);
            this.gamePlayButton = itemView.findViewById(R.id.gamePlayButton);


        }
    }

}



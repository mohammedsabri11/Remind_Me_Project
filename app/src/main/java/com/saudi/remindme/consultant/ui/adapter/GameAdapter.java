package com.saudi.remindme.consultant.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;

import com.saudi.remindme.R;
import com.saudi.remindme.consultant.ui.model.GameItem;

import java.util.List;


public class GameAdapter extends RecyclerView.Adapter<GameAdapter.ViewHolder> {
    private final Context mContext;
    private final List<GameItem> sGameItems;
    public OnMenuListener onMenuListener;


    public GameAdapter(Context mContext, List<GameItem> sUserModel) {
        this.mContext = mContext;
        this.sGameItems = sUserModel;

    }


    @Override
    public int getItemCount() {
        return sGameItems.size();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        View rootView = LayoutInflater.from(mContext).inflate(R.layout.game_item, parent, false);

        return new ViewHolder(rootView);

    }

    private void showPopupMenu(View view, GameItem game) {
        Context wrapper = new ContextThemeWrapper(mContext, R.style.menu_PopupMenu);
        PopupMenu popupMenu = new PopupMenu(wrapper, view);
        // PopupMenu popupMenu = new PopupMenu(mContext, view);
        popupMenu.getMenuInflater().inflate(R.menu.menu_game_options, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(item -> {

            switch (item.getItemId()) {
                case R.id.action_edit:
                    // Handle edit action
                    onMenuListener.onEdit(game);
                    return true;
                case R.id.action_game_delete:
                    // Handle delete action
                    onMenuListener.onDelete(game.getId());
                    return true;
                case R.id.action_share:
                    // Handle delete action
                    onMenuListener.onShare(game.getId());
                    return true;
                default:
                    return false;
            }
        });
        popupMenu.show();
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final GameItem game = sGameItems.get(position);

        holder.name.setText(game.getName());
        holder.description.setText(game.getDescription());

       /* holder.gameEdit.setOnClickListener(view -> onMenuListener.onEdit(game));

        holder.gameDelete.setOnClickListener(view -> onMenuListener.onDelete(game.getId()));
        holder.gameShare.setOnClickListener(view -> onMenuListener.onShare(game.getId()));*/
        holder.optionsButton.setOnClickListener(view -> showPopupMenu(holder.optionsButton, game));

    }


    public interface OnMenuListener {
        void onEdit(GameItem game);

        void onDelete(String gameID);

        void onShare(String gameID);

    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        TextView description;

        ImageButton optionsButton;

        ViewHolder(View itemView) {
            super(itemView);

            this.name = itemView.findViewById(R.id.gameNameTextView);
            this.description = itemView.findViewById(R.id.gameDescriptionTextView);
            this.optionsButton = itemView.findViewById(R.id.game_option);

        }
    }

}



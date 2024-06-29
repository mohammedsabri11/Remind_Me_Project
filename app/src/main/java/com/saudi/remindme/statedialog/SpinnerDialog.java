package com.saudi.remindme.statedialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatSpinner;

import com.saudi.remindme.R;
import com.saudi.remindme.admin.ui.model.PatientItem;

public class SpinnerDialog {
    static String selectedUserId;

    public static void showShareDialog(Context context, ArrayAdapter<PatientItem> userArrayAdapter, OnUserSelectedListener listener) {

        Dialog dialog;
        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.share_game_with_user);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        AppCompatSpinner userSpinner = dialog.findViewById(R.id.userSpinner);

        userSpinner.setAdapter(userArrayAdapter);

        userSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Handle item selection
                selectedUserId = userArrayAdapter.getItem(position).getId();
                // Perform any actions based on the selected user
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });
        dialog.findViewById(R.id.confirmButton).setOnClickListener(v -> {
            // Handle confirmation button click

            if (!selectedUserId.isEmpty()) {
                listener.onUserSelected(selectedUserId);
                dialog.dismiss();
            } else {
                ((TextView) userSpinner.getSelectedView()).setError("select user");

                // put the focus on  Spinner
                userSpinner.requestFocus();


            }


        });
        dialog.findViewById(R.id.cancelButton).setOnClickListener(v -> dialog.dismiss());
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        Window window = dialog.getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

    }

    public interface OnUserSelectedListener {
        void onUserSelected(String selectedUser);
    }
}

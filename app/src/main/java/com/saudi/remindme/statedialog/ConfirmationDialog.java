package com.saudi.remindme.statedialog;

import android.content.Context;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.view.ContextThemeWrapper;

import com.saudi.remindme.R;

public class ConfirmationDialog {
    public static void showConfirmationDialog(Context context, String title, String mess, OnDialogListener listener) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(new ContextThemeWrapper(context, R.style.CustomAlertDialogStyle));

        // String mess = context.getResources().getString(R.string.confirm_message) +"delete this Game ";
        alertDialogBuilder.setMessage(mess);
        alertDialogBuilder.setTitle(title);
        //alertDialogBuilder.se
        alertDialogBuilder.setIcon(context.getResources().getDrawable(R.drawable.ic_error_black_24dp));
        alertDialogBuilder.setPositiveButton("Confirm ", (dialog, which) ->
                listener.onConfirmClick()
        );

        alertDialogBuilder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());


        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

    }

    public interface OnDialogListener {
        void onConfirmClick();

    }
}

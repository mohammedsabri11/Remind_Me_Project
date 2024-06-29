package com.saudi.remindme;


import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.saudi.remindme.process.IResult;

public abstract class BaseFragment extends Fragment implements DialogInterface.OnDismissListener, IResult {


    private static AlertDialog dialog = null;

    public static void showProgressDialog(Context context, String message) {
        if (dialog == null) {
            int llPadding = 30;
            LinearLayout ll = new LinearLayout(context);
            ll.setOrientation(LinearLayout.HORIZONTAL);
            ll.setPadding(llPadding, llPadding, llPadding, llPadding);
            ll.setGravity(Gravity.CENTER);
            ll.setBackground(new ColorDrawable(Color.TRANSPARENT));
            LinearLayout.LayoutParams llParam = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            llParam.gravity = Gravity.CENTER;
            ll.setLayoutParams(llParam);

            ProgressBar progressBar = new ProgressBar(context);
            progressBar.setIndeterminate(true);
            progressBar.setPadding(0, 0, llPadding, 0);
            progressBar.setLayoutParams(llParam);

            llParam = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            llParam.gravity = Gravity.CENTER;
            TextView tvText = new TextView(context);
            tvText.setText(message);
            tvText.setTextColor(Color.parseColor("#000000"));
            tvText.setTextSize(14);
            tvText.setLayoutParams(llParam);

            ll.addView(progressBar);
            ll.addView(tvText);

            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setCancelable(false);
            builder.setView(ll);

            dialog = builder.create();
            dialog.show();
            Window window = dialog.getWindow();
            if (window != null) {
                WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
                layoutParams.copyFrom(dialog.getWindow().getAttributes());
                layoutParams.width = LinearLayout.LayoutParams.WRAP_CONTENT;
                layoutParams.height = LinearLayout.LayoutParams.WRAP_CONTENT;
                dialog.getWindow().setAttributes(layoutParams);
            }
        }
    }

    public static boolean isDialogVisible() {
        if (dialog != null) {
            return dialog.isShowing();
        } else {
            return false;
        }
    }

    public static void hideProgressDialog() {
        if (isDialogVisible()) {
            dialog.dismiss();
            dialog = null;
        }
    }


    @Override
    public void onStop() {
        super.onStop();
        hideProgressDialog();
    }


    public void showSuccessDialog(String message) {
        hideProgressDialog();
        Dialog dialog;
        dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        dialog.setContentView(R.layout.dialog_success);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        TextView textViewMessage = dialog.findViewById(R.id.message);
        textViewMessage.setText(message);
        dialog.setCancelable(true);
        dialog.findViewById(R.id.buttonClose).setOnClickListener(view -> dialog.dismiss());
        dialog.setOnDismissListener(this);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        Window window = dialog.getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

    }

    public void showFailedDialog(String message) {
        hideProgressDialog();
        final Dialog failsDialog;
        failsDialog = new Dialog(getContext(), R.style.dialog_theme);


        failsDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        failsDialog.setContentView(R.layout.dialog_faild);
        failsDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        TextView textViewMessage = failsDialog.findViewById(R.id.message);
        textViewMessage.setText(message);

        failsDialog.setCancelable(true);
        failsDialog.findViewById(R.id.buttonClose).setOnClickListener(view -> failsDialog.dismiss());

        failsDialog.show();
        Window window = failsDialog.getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }


    @Override
    public void onDismiss(DialogInterface dialogInterface) {

    }
}





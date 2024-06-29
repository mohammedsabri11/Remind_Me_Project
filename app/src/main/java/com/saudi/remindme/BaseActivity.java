package com.saudi.remindme;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


public class BaseActivity extends AppCompatActivity implements DialogInterface.OnDismissListener {

    private static AlertDialog dialog = null;
    Dialog successDialog;

    public static void showProgressDialog(Context context, String message) {
        if (dialog == null) {
            int llPadding = 30;
            LinearLayout ll = new LinearLayout(context);
            ll.setOrientation(LinearLayout.HORIZONTAL);
            ll.setPadding(llPadding, llPadding, llPadding, llPadding);
            ll.setGravity(Gravity.CENTER);
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
            tvText.setTextColor(Color.parseColor("#C9595858"));
            tvText.setTextSize(12);
            tvText.setLayoutParams(llParam);

            ll.addView(progressBar);
            ll.addView(tvText);

            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setCancelable(true);
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
        successDialog = new Dialog(this);
        successDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        successDialog.setContentView(R.layout.dialog_success);
        successDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        TextView textViewMessage = successDialog.findViewById(R.id.message);
        textViewMessage.setText(message);
        successDialog.setCancelable(true);
        successDialog.findViewById(R.id.buttonClose).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startActivity(new Intent(BaseActivity.this,MainActivity.class));
                successDialog.dismiss();
            }
        });

        successDialog.show();
        successDialog.setOnDismissListener(this);
        Window window = successDialog.getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    public void showFailDialog(String message) {
        hideProgressDialog();
        final Dialog dialog;
        dialog = new Dialog(this);


        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        dialog.setContentView(R.layout.dialog_faild);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        TextView textViewMessage = dialog.findViewById(R.id.message);
        textViewMessage.setText(message);

        dialog.setCancelable(true);
        dialog.findViewById(R.id.buttonClose).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
        Window window = dialog.getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        dialog.dismiss();
    }
}

package com.saudi.remindme.statedialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.material.imageview.ShapeableImageView;
import com.saudi.remindme.R;
import com.saudi.remindme.admin.ui.model.PatientItem;

public class PatientDetailsDialog extends Dialog implements View.OnClickListener {
    private final ShapeableImageView profile;
    private final TextView nameTextView;
    private final TextView healthTextView;
    private final TextView dobTextView;
    private final TextView genderTextView;
    private final TextView phoneTextView;
    private final ImageButton closeButton;


    public PatientDetailsDialog(@NonNull Context context, PatientItem patientItem) {
        super(context);


        setContentView(R.layout.dialog_user_profile);
        profile = findViewById(R.id.profile);
        nameTextView = findViewById(R.id.user_name);
        healthTextView = findViewById(R.id.health_details);
        dobTextView = findViewById(R.id.dob);
        genderTextView = findViewById(R.id.gender);
        phoneTextView = findViewById(R.id.phone);


        closeButton = findViewById(R.id.close);
        closeButton.setOnClickListener(this);
        setDetails(context, patientItem);
    }

    @SuppressLint("SuspiciousIndentation")
    private void setDetails(Context context, PatientItem patientItem) {


        if (patientItem.getGender().equalsIgnoreCase("Male")) {
            profile.setImageDrawable(context.getDrawable(R.drawable.c_m_profile));
        } else
            profile.setImageDrawable(context.getDrawable(R.drawable.female_profile));


        nameTextView.setText(patientItem.getName());
        healthTextView.setText(patientItem.getHealthStatusDetails());
        dobTextView.setText(patientItem.getDob());
        genderTextView.setText(patientItem.getGender());
        phoneTextView.setText(patientItem.getPhone());

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.close) {


            dismiss();
        }
    }

}

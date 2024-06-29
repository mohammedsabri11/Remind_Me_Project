package com.saudi.remindme.statedialog;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.Button;
import android.widget.DatePicker;

import androidx.core.content.ContextCompat;

import com.saudi.remindme.R;

import java.util.Calendar;

public class DatePickerHelper {

    public static void showDatePicker(Context context, OnDateSetListener listener) {
        final Calendar calendar = Calendar.getInstance();

        DatePickerDialog datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                String sDate = DateFormat.format("yyyy-MM-dd", calendar.getTime()).toString();

                listener.OnDateSet(sDate);


            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)) {
            @Override
            public void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);

                Button positiveButton = getButton(DatePickerDialog.BUTTON_POSITIVE);
                Button negButton = getButton(DatePickerDialog.BUTTON_NEGATIVE);


                // Set background color of the positive button
                // positiveButton.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorAccent));

                // Set text color of the positive button
                positiveButton.setTextColor(ContextCompat.getColor(getContext(), R.color.colorAccent));
                negButton.setTextColor(ContextCompat.getColor(getContext(), R.color.custom_negative_button_color));
                // Set background color of the negative button

            }
        };

        datePickerDialog.show();
    }

    public interface OnDateSetListener {
        void OnDateSet(String date);
    }
}

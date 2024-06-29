package com.saudi.remindme.statedialog;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;

import androidx.core.content.ContextCompat;

import com.saudi.remindme.R;

import java.util.Calendar;

public class DateTimePickerHelper {

    public static void showDateTimePicker(Context context, OnDateSetListener listener) {
        final Calendar calendar = Calendar.getInstance();

        DatePickerDialog datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                TimePickerDialog timePickerDialog = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        calendar.set(Calendar.MINUTE, minute);
                        String sDateTime = DateFormat.format("yyyy-MM-dd HH:mm", calendar.getTime()).toString();

                        listener.OnDateSet(sDateTime, calendar.getTimeInMillis());

                    }
                }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true) {
                    @Override
                    public void onCreate(Bundle savedInstanceState) {
                        super.onCreate(savedInstanceState);

                        Button positiveButton = getButton(DatePickerDialog.BUTTON_POSITIVE);
                        Button negButton = getButton(DatePickerDialog.BUTTON_NEGATIVE);

                        // Set text color of the positive button
                        positiveButton.setTextColor(ContextCompat.getColor(getContext(), R.color.colorAccent));
                        negButton.setTextColor(ContextCompat.getColor(getContext(), R.color.custom_negative_button_color));
                        // Set background color of the negative button

                        // Set background color of the negative button

                    }
                };

                timePickerDialog.show();
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)) {
            @Override
            public void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);

                Button positiveButton = getButton(DatePickerDialog.BUTTON_POSITIVE);
                Button negButton = getButton(DatePickerDialog.BUTTON_NEGATIVE);

                // Set text color of the positive button
                positiveButton.setTextColor(ContextCompat.getColor(getContext(), R.color.colorAccent));
                negButton.setTextColor(ContextCompat.getColor(getContext(), R.color.custom_negative_button_color));
                // Set background color of the negative button

                // Set background color of the negative button

            }
        };

        datePickerDialog.show();
    }

    public interface OnDateSetListener {
        void OnDateSet(String sDateTime, long triggerTime);
    }
}

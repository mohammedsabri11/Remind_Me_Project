package com.saudi.remindme.user;

import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.core.content.ContextCompat;

import com.saudi.remindme.AlarmReceiver;
import com.saudi.remindme.BaseActivity;

import com.saudi.remindme.R;
import com.saudi.remindme.Url;
import com.saudi.remindme.account.SessionManager;
import com.saudi.remindme.process.IResult;
import com.saudi.remindme.process.Server;
import com.saudi.remindme.statedialog.DateTimePickerHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class NewReminderActivity extends BaseActivity implements View.OnClickListener, IResult {

    private static final int ADD_REMINDER = 2;
    private static final int MY_PERMISSIONS_REQUEST_SET_ALARM = 44;
    private static final int MY_PERMISSIONS_POST_NOTIFICATIONS = 33;
    AppCompatEditText appointmentDetails, dateTime;
    long triggerTime;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_reminder);
        assert getSupportActionBar() != null;   //null check
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mContext = this;
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.SET_ALARM)!= PackageManager.PERMISSION_GRANTED){
            Log.d("Perm check:SET_ALARM", "Permission Denied");
            requestPermissions(new String[]{Manifest.permission.SET_ALARM},MY_PERMISSIONS_REQUEST_SET_ALARM);
        }else{
            Log.d("Perm check:SET_ALARM", "Permission Exists");
        }

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)!= PackageManager.PERMISSION_GRANTED){
          //  Log.d("Perm check:POST NOTIFICATIONS", "Permission Denied");
            requestPermissions(new String[]{Manifest.permission.POST_NOTIFICATIONS},MY_PERMISSIONS_POST_NOTIFICATIONS);
        }else{
            Log.d("Perm check:SET_ALARM", "Permission Exists");
        }
        init();
    }


    private void init() {

        dateTime = findViewById(R.id.editTextDateTime);
        appointmentDetails = findViewById(R.id.editTextAppointmentDetails);
        findViewById(R.id.buttonSaveReminder).setOnClickListener(this);
        findViewById(R.id.editTextDateTime).setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.editTextDateTime) {
            dateTimePicker();
        } else if (view.getId() == R.id.buttonSaveReminder) {
            validateInput();

        }
    }


    private void validateInput() {
        boolean isValid = true;
        isValid = isValid && isFieldNotEmpty(appointmentDetails, getResources().getString(R.string.error_empty_reminder_details));
        isValid = isValid && isFieldNotEmpty(dateTime, getResources().getString(R.string.error_empty_reminder_date));

        if (isValid) {
            addReminder();
        }
    }

    private boolean isFieldNotEmpty(EditText field, String error) {
        String value = field.getText().toString();
        if (value.isEmpty()) {
            field.setError(error);
            field.requestFocus();
            return false;
        }
        return true;
    }


    private void addReminder() {

        showProgressDialog(mContext, getResources().getString(R.string.do_operation_message));
        Server.getInstance(mContext).post(getParameters(), Url.URL_REMIND, ADD_REMINDER, this);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // The action bar home/up action should open or close the drawer.
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private Map<String, String> getParameters() {
        Map<String, String> params = new HashMap<>();
        params.put("appointmentdetails", appointmentDetails.getText().toString());
        params.put("datetime", dateTime.getText().toString());
        params.put("userId", SessionManager.getInstance(mContext).getKeyUserId());
        params.put("op", "Add");
        return params;
    }


    public void dateTimePicker() {
        DateTimePickerHelper.showDateTimePicker(this, (datedTime, triggerTime) -> {

            dateTime.setText(datedTime);
            this.triggerTime = triggerTime;

        });
    }


    @Override
    public void onServerSuccess(int requestId, JSONObject jsonObject) {


        try {


            if (requestId == ADD_REMINDER) {
                showSuccessDialog(jsonObject.getString("msg"));

            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onServerError(int requestId, String error) {
        showFailDialog(error);
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        setAlarm();
        Intent intent = new Intent(mContext, MainUserActivity2.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    public void setAlarm() {

        try {


        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(getApplicationContext(), AlarmReceiver.class);
        intent.putExtra("details", appointmentDetails.getText().toString());
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, triggerTime, pendingIntent);
        Toast.makeText(this,"set alaram",Toast.LENGTH_LONG).show();
        }catch (Exception error){
            Toast.makeText(this,"error in set alaram"+error.getMessage(),Toast.LENGTH_LONG).show();
            showFailDialog(error.toString());
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_SET_ALARM: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_DENIED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    onBackPressed();
                }
                return;
            }
            case MY_PERMISSIONS_POST_NOTIFICATIONS: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_DENIED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    onBackPressed();
                }
                return;
            }
            // other 'case' lines to check for other
            // permissions this app might request
        }
    }
}









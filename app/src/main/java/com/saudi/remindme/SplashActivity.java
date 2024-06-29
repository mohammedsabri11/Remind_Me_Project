package com.saudi.remindme;

import static com.saudi.remindme.process.Constant.ADMIN;
import static com.saudi.remindme.process.Constant.CONSULTANT;
import static com.saudi.remindme.process.Constant.USER;
import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.saudi.remindme.account.RegisterAsActivity;
import com.saudi.remindme.account.SessionManager;
import com.saudi.remindme.admin.MainAdminActivity;
import com.saudi.remindme.consultant.MainConsultantActivity;
import com.saudi.remindme.user.MainUserActivity2;

import java.util.Calendar;

@SuppressLint("CustomSplashScreen")
public class SplashActivity extends AppCompatActivity {

    private static final int MY_PERMISSIONS_REQUEST_INTERNET = 22;
    private ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        imageView= findViewById(R.id.logo);
        Animation animation= AnimationUtils.loadAnimation(this,R.anim.ro);
        imageView.startAnimation(animation);
        findViewById(R.id.buttonStart).setOnClickListener(v -> {
            Intent intent = new Intent(SplashActivity.this, RegisterAsActivity.class);
            startActivity(intent);
        });
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET)!= PackageManager.PERMISSION_GRANTED){
            Log.d("Perm check:INTERNET", "Permission Denied");
            requestPermissions(new String[]{Manifest.permission.INTERNET},MY_PERMISSIONS_REQUEST_INTERNET);
        }else{
            Log.d("Perm check:INTERNET", "Permission Exists");
        }
       // setAlarm();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_INTERNET: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_DENIED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
finish();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }
    @Override
    protected void onStart() {
        Intent intent = userInterface();
        if (intent != null) {

            startActivity(intent);

            finish();
        }
        super.onStart();

    }

    public Intent userInterface() {
        Intent intent = null;
        if (SessionManager.getInstance(getApplicationContext()).isLoggedIn()) {
            switch (SessionManager.getInstance(getApplicationContext()).getKeyUserType()) {

                case CONSULTANT:
                    intent = new Intent(getApplicationContext(), MainConsultantActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    break;

                case ADMIN:
                    intent = new Intent(getApplicationContext(), MainAdminActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    break;

                case USER:
                    intent = new Intent(getApplicationContext(), MainUserActivity2.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    break;
            }


        }
        return intent;
    }

}






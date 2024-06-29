package com.saudi.remindme;

import static android.content.Context.NOTIFICATION_SERVICE;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.saudi.remindme.NotifyService;
import com.saudi.remindme.R;
import com.saudi.remindme.SplashActivity;

public class AlarmReceiver extends BroadcastReceiver {

    public static final String NOTIFICATION_CHANNEL_ID = "10001";
    private final static String default_notification_channel_id = "default";

    @Override
    public void onReceive(Context context, Intent intent) {
        showNotification( context,  intent);
        Intent serviceIntent = new Intent(context, NotifyService.class);
        serviceIntent.putExtra("details", intent.getStringExtra("details"));
        Log.d("onReceive", "onReceive ");
       // context.startService(serviceIntent);
    }
    public void showNotification(Context context, Intent intent) {
        // Implement your background task here
        Intent notificationIntent = new Intent(context, SplashActivity.class);
        notificationIntent.putExtra("fromNotification", true);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, notificationIntent, PendingIntent.FLAG_IMMUTABLE);
        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, default_notification_channel_id);
        mBuilder.setContentTitle("Remind Me");
        mBuilder.setContentIntent(pendingIntent);
        mBuilder.setContentText(intent.getStringExtra("details"));
        mBuilder.setSmallIcon(R.drawable.baseline_alarm_on2_24);
        mBuilder.setAutoCancel(true);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel notificationChannel = new
                    NotificationChannel(NOTIFICATION_CHANNEL_ID, "NOTIFICATION_CHANNEL_NAME", importance);
            mBuilder.setChannelId(NOTIFICATION_CHANNEL_ID);
            assert mNotificationManager != null;
            mNotificationManager.createNotificationChannel(notificationChannel);
        }
        assert mNotificationManager != null;
        mNotificationManager.notify(1, mBuilder.build());

    }

}
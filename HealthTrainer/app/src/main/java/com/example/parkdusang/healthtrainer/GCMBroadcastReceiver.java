package com.example.parkdusang.healthtrainer;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;

public class GCMBroadcastReceiver  extends WakefulBroadcastReceiver {

    private Context mContext;

    @Override
    public void onReceive(Context context, Intent intent) {

        mContext = context;
        Log.e("Test", "got message");
        Log.e("Test", "intent " + intent.getDataString());

        String title = intent.getStringExtra("title");
        String message = intent.getStringExtra("message");
        String pushType = intent.getStringExtra("push_type");

        if(pushType.equals("my")) {

        }

        Log.e("Test", "title " + title);
        Log.e("Test", "message " + message);

        sendNotification(title, message);
    }

    private void sendNotification(String title, String message) {

        Intent intent = new Intent(mContext, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(mContext, 0, intent,
                PendingIntent.FLAG_ONE_SHOT);


        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(mContext)
                .setSmallIcon(R.drawable.ic_launcher)
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, notificationBuilder.build());
    }
}

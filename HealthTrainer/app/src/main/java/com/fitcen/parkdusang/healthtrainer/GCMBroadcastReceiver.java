package com.fitcen.parkdusang.healthtrainer;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
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

        Intent intent = new Intent(mContext, Login.class);
        if(message.substring(0,3).equals("트레이")){

            Log.i("testset", message.substring(0,3) + " 2");
            intent.putExtra("sign",Integer.parseInt("2"));
        }
        else{
            Log.i("testset", message.substring(0,3)+ " 1");
            intent.putExtra("sign",Integer.parseInt("1"));
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(mContext, 0, intent,
                PendingIntent.FLAG_ONE_SHOT);

        //Bitmap bm = BitmapFactory.decodeResource(Resources.getSystem(), R.mipmap.icon);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(mContext)
                .setSmallIcon(R.mipmap.icon)
                .setLargeIcon(BitmapFactory.decodeResource(mContext.getResources(), R.drawable.splash))
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, notificationBuilder.build());
    }
}

package com.fitcen.parkdusang.healthtrainer;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;

public class NetworkError extends AppCompatActivity {
    boolean checkb = true;
    Thread myThread;
    ImageView updown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.network_error);
        updown = (ImageView) findViewById(R.id.imageViewupdown);


        myThread = new Thread(new Runnable() {
            public void run() {
                updateimg();
            }
        });

        myThread.start();
    }

    @Override
    protected void onPause() {
        super.onPause(); //save state data (background color) for future use
        checkb = false;
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    public void updateimg()  {
        boolean setimg = true;
        while (checkb) {
            try {
                Thread.sleep(600);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (setimg) {
                Log.i("test1", "test1");
                setimg = false;
                runOnUiThread(new Runnable() {
                                  @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                                  @Override
                                  public void run() {
                                      updown.setBackground(getResources().getDrawable(R.drawable.up));
                                  }
                              }
                );
            } else {
                Log.i("test2", "test2");
                setimg = true;
                runOnUiThread(new Runnable() {
                                  @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                                  @Override
                                  public void run() {
                                      updown.setBackground(getResources().getDrawable(R.drawable.down));
                                  }
                              }
                );
            }
        }
    }


}

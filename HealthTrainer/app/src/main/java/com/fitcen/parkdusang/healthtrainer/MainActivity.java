package com.fitcen.parkdusang.healthtrainer;

import android.annotation.TargetApi;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button b1,b2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        b1 = (Button)findViewById(R.id.button);
        b2 = (Button)findViewById(R.id.button2);

        b1.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
                Intent myAct1 = new Intent(MainActivity.this, Login.class);
                myAct1.putExtra("sign",Integer.parseInt("1"));
                Bundle bndlanimation = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.main_button_customer, R.anim.main_button_customer_login).toBundle();
                startActivity(myAct1, bndlanimation);
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
                Intent myAct2 = new Intent(MainActivity.this, Login.class);
                myAct2.putExtra("sign",Integer.parseInt("2"));
                Bundle bndlanimation = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.main_button_trainer, R.anim.main_button_trainer_login).toBundle();
                startActivity(myAct2, bndlanimation);
            }
        });
    }
}
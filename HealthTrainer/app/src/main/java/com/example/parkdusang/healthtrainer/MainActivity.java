package com.example.parkdusang.healthtrainer;

import android.content.Intent;
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
            @Override
            public void onClick(View v) {
                Intent myAct1 = new Intent (getApplicationContext(), Login.class);
                myAct1.putExtra("sign",Integer.parseInt("1"));
                startActivity(myAct1);
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myAct2 = new Intent (getApplicationContext(), Login.class);
                myAct2.putExtra("sign",Integer.parseInt("2"));
                startActivity(myAct2);
            }
        });
    }
}

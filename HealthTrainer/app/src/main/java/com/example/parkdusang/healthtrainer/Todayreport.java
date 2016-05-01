package com.example.parkdusang.healthtrainer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Todayreport extends AppCompatActivity {
    TextView tx;
    Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.todayreport);
        tx = (TextView)findViewById(R.id.textView3_2);
        btn  = (Button)findViewById(R.id.button3_1);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //디비 불러와서 끝
    }
}

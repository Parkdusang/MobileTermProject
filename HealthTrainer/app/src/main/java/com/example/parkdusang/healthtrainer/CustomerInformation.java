package com.example.parkdusang.healthtrainer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomerInformation extends AppCompatActivity {
    String stitle, scontent;
    int simgid;
    TextView txtTitle;
    TextView txtContent;
    ImageView imgIcon;
    Button setexercise,report;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_information);

        Intent intent = getIntent();
        stitle = intent.getStringExtra("title1");
        scontent = intent.getStringExtra("content1");
        simgid = intent.getIntExtra("imgid", 10);

        txtTitle = (TextView)findViewById(R.id.txtTitle2);
        txtContent = (TextView)findViewById(R.id.txtContent2);
        imgIcon =  (ImageView)findViewById(R.id.imgIcon2);
        txtTitle.setText(stitle);
        txtContent.setText(scontent);
        imgIcon.setImageResource(simgid);

        setexercise = (Button)findViewById(R.id.setexercise);

        setexercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myAct = new Intent (getApplicationContext(), Setexercise.class);
                startActivity(myAct);
            }
        });

        report = (Button)findViewById(R.id.button8);
        report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myAct1 = new Intent (getApplicationContext(), Todayreport.class);
                startActivity(myAct1);
            }
        });
    }
}

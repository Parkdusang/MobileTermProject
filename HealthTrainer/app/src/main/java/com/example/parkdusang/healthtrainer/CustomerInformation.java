package com.example.parkdusang.healthtrainer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomerInformation extends AppCompatActivity {
    String stitle, scontent,trainrID;
    int simgid;
    TextView txtTitle;
    TextView txtContent;
    ImageView imgIcon;
    Button setexercise,report,inbody;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_information);

        Intent intent = getIntent();
        trainrID = intent.getStringExtra("trainrid"); // 트레이너아이뒤
        stitle = intent.getStringExtra("title1"); // 이름
        scontent = intent.getStringExtra("content1"); // 전화번호
        simgid = intent.getIntExtra("imgid", 10);

        txtTitle = (TextView)findViewById(R.id.txtTitle2);
        txtContent = (TextView)findViewById(R.id.txtContent2);
        imgIcon =  (ImageView)findViewById(R.id.imgIcon2);
        txtTitle.setText(stitle);
        txtContent.setText(scontent);
        imgIcon.setImageResource(simgid);

        setexercise = (Button)findViewById(R.id.setexercise);

        setexercise.setOnClickListener(new View.OnClickListener() { // 운동 지정
            @Override
            public void onClick(View v) {
                Intent myAct = new Intent(getApplicationContext(), Setexercise.class);
                myAct.putExtra("trainrid", trainrID);
                myAct.putExtra("title1", stitle);
                myAct.putExtra("content1", scontent);
                startActivity(myAct);
            }
        });

        report = (Button)findViewById(R.id.button8);
        report.setOnClickListener(new View.OnClickListener() { // 보고
            @Override
            public void onClick(View v) {
                Intent myAct1 = new Intent (getApplicationContext(), Todayreport.class);
                myAct1.putExtra("trainrid", trainrID);
                myAct1.putExtra("title1", stitle);
                myAct1.putExtra("content1", scontent);
                startActivity(myAct1);
            }
        });
        inbody = (Button)findViewById(R.id.inbodyinfo);
        inbody.setOnClickListener(new View.OnClickListener() { // 보고
            @Override
            public void onClick(View v) {
                Intent myAct2 = new Intent(getApplicationContext(), Inbodyinfo.class);
                myAct2.putExtra("trainrid", trainrID);
                myAct2.putExtra("title1", stitle);
                myAct2.putExtra("content1", scontent);
                startActivity(myAct2);
            }
        });
    }
}

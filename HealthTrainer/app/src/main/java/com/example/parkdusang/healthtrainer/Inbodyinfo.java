package com.example.parkdusang.healthtrainer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

public class Inbodyinfo extends AppCompatActivity {


    // 1. inbodyHeight 2. inbodyWeight 3. inbodyMuscle,
    // 4. inbodyFatAmount 5.inbodyFatRate 6.inbodyBMI 7.inbodyFatLv;

    EditText inbodyInfos1,inbodyInfos2,inbodyInfos3,inbodyInfos4,inbodyInfos5,inbodyInfos6,inbodyInfos7;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inbodyinfo);

        Intent inbodyIntent=getIntent();
        Bundle info = inbodyIntent.getExtras();

        //인텐트에서 얻어온부분
        inbodyIntent.getStringExtra("trainrid"); // 트레이너아이뒤
        inbodyIntent.getStringExtra("content1");


        inbodyInfos1 = (EditText)findViewById(R.id.inbodyHeight);
        inbodyInfos2 = (EditText)findViewById(R.id.inbodyWeight);
        inbodyInfos3 = (EditText)findViewById(R.id.inbodyMuscle);
        inbodyInfos4 = (EditText)findViewById(R.id.inbodyFatAmount);
        inbodyInfos5 = (EditText)findViewById(R.id.inbodyFatRate2);
        inbodyInfos6 = (EditText)findViewById(R.id.inbodyBMI);
        inbodyInfos7 = (EditText)findViewById(R.id.inbodyFatLv);







    }

}

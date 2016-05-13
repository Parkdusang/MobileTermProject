package com.example.parkdusang.healthtrainer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Setnumber extends AppCompatActivity {
    int checkposition;
    EditText e1,e2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setnumber);


        Intent intent = getIntent();
        checkposition = intent.getIntExtra("position", 10);

         e1 = (EditText)findViewById(R.id.exerciseset);
         e2 = (EditText)findViewById(R.id.exercisenumber);

        Button b1 = (Button)findViewById(R.id.exerciseoK);
        Button b2 = (Button)findViewById(R.id.exercisecanceL);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(e1.getText().toString().equals("") || e2.getText().toString().equals("")) {
                   //토스트 안됀다
                }
                else{
                    int set = Integer.parseInt(e1.getText().toString());
                    int number = Integer.parseInt(e2.getText().toString());
                    Intent intent = new Intent();
                    intent.putExtra("position", checkposition);
                    intent.putExtra("set",set);
                    intent.putExtra("number",number);
                    intent.putExtra("mode",1);
                    setResult(Activity.RESULT_OK, intent);
                    finish();
                }
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("mode",2);
                intent.putExtra("position", checkposition);
                setResult(Activity.RESULT_OK, intent);
                finish();

            }
        });
    }
}

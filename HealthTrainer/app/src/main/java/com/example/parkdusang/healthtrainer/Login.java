package com.example.parkdusang.healthtrainer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Login extends AppCompatActivity  implements View.OnClickListener{
    EditText e1,e2;
    Button b1,b2;
    int checkingmode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
            e1 = (EditText)findViewById(R.id.checkid);
            e2 = (EditText)findViewById(R.id.checkpassword);
            b1 = (Button)findViewById(R.id.button3);
            b2 = (Button)findViewById(R.id.button4);
            b1.setOnClickListener(this);
            b2.setOnClickListener(this);

        Intent intent = getIntent();
        checkingmode = intent.getIntExtra("sign",10);
    }

    public void onClick(View v){

        if(R.id.button3 == v.getId()){
            if(checkingmode == 1) {
                Intent myAct1 = new Intent(getApplicationContext(), Trainermode.class);
                startActivity(myAct1);
            }
            else{
                Intent myAct1 = new Intent(getApplicationContext(), customermode.class);
                startActivity(myAct1);
            }
        }
        else{
            Intent myAct1 = new Intent(getApplicationContext(), Register.class);
            startActivity(myAct1);
        }
    }
}

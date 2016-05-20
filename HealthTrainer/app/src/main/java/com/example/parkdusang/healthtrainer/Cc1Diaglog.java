package com.example.parkdusang.healthtrainer;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by yooyj on 2016-05-05.
 */

public class Cc1Diaglog extends Dialog implements View.OnTouchListener {
    EditText startweight, goalweight, currentweight;
    private Button dialog_btn1, dialog_btn2;
    private String sw, gw, cw;

    public Cc1Diaglog(Context context) {
        super(context);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cc1dialog);

        startweight = (EditText) findViewById(R.id.startweight);
        goalweight = (EditText) findViewById(R.id.goalweight);
        currentweight = (EditText) findViewById(R.id.currentweight);

        dialog_btn1 = (Button) findViewById(R.id.dialog_btn1);
        dialog_btn2 = (Button) findViewById(R.id.dialog_btn2);

        dialog_btn1.setOnTouchListener(this);
        dialog_btn2.setOnTouchListener(this);
    }

    public String getStartWeight() {
        return sw;
    }

    public String getGoalWeight() {
        return gw;
    }

    public String getCurrentWeight() {
        return cw;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (v.getId() == dialog_btn1.getId()) {
            sw = startweight.getText().toString();
            gw = goalweight.getText().toString();
            cw = currentweight.getText().toString();

            dismiss();
        } else if (v.getId() == dialog_btn2.getId()) {
            if (startweight.getText().toString().equals("") || goalweight.getText().toString().equals("") || currentweight.getText().toString().equals(""))
                Log.e("dddd", "dfd");
            else
                cancel();
        }
        return false;
    }
}

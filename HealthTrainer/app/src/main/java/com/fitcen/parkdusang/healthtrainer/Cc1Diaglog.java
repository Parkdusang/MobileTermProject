package com.fitcen.parkdusang.healthtrainer;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by yooyj on 2016-05-05.
 */

public class Cc1Diaglog extends Dialog {
    EditText  goalweight, currentweight;
    private Button dialog_btn1, dialog_btn2;
    private String sw, gw, cw;
    private String edtx1,edtx2;
    boolean check = true;
    public Cc1Diaglog(Context context) {
        super(context);
        Log.i("TAG", "Cc1Diaglog: ");
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cc1dialog);

        Log.i("TAG", "onCreate: ");
        goalweight = (EditText) findViewById(R.id.goalweight);
        currentweight = (EditText) findViewById(R.id.currentweight);

        dialog_btn1 = (Button) findViewById(R.id.dialog_btn1);
        dialog_btn2 = (Button) findViewById(R.id.dialog_btn2);

        dialog_btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtx1 = goalweight.getText().toString();
                edtx2 = currentweight.getText().toString();
                check = true;
                dismiss();
            }
        });
        dialog_btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check = false;
                cancel();
            }
        });
    }
    public String getedtxString1() {
        return edtx1; // goal
    }
    public String getedtxString2() {
        return edtx2; // current
    }
    public String getStartWeight() {
        return sw;
    }
    public void setStartWeight(String sw) {
        this.sw =  sw;
    }
    public String getGoalWeight() {
        return gw;
    }

    public String getCurrentWeight() {
        return cw;
    }
    public void setGoalWeight(String gw) {
        this.gw = gw;
    }

    public void setCurrentWeight(String cw) {
       this.cw = cw;
    }
    public boolean checkcancel(){
        return check;
    }

}

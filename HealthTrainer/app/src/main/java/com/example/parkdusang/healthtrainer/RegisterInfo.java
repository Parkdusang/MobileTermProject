package com.example.parkdusang.healthtrainer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by yooyj on 2016-05-21.
 */
public class RegisterInfo extends Activity{

    int editID[] = {R.id.ri_fweight,R.id.ri_gweight,R.id.ri_name};
    int btnID[]={R.id.ri_ok,R.id.ri_cancel};

    EditText[] ri_Edit = new EditText[editID.length];
    Button[] ri_Btn = new Button[btnID.length];

    String[] ri_info=new String[editID.length];
    //1. 초기 몸무게 2. 목표 몸무게 3. 이름
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registerinfo);

        for(int i=0;i <editID.length;i++)
        {
            ri_Edit[i]=(EditText)findViewById(editID[i]);
            ri_info[i]=ri_Edit[i].getText().toString();
        }
        for(int i=0;i <btnID.length;i++)
        {
            ri_Btn[i]=(Button)findViewById(btnID[i]);
        }

    }
    public void onOkClick(View v)
    {
        if(v.getId()==R.id.ri_ok) {
            Intent intent = new Intent(getApplicationContext(), Connectionwaiting.class);
            startActivity(intent);
        }
        else{
            for(int i=0;i<editID.length;i++)
            {
                ri_Edit[i].setText("");
            }
        }
    }
}

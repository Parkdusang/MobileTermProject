package com.example.parkdusang.healthtrainer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;

/**
 * Created by yooyj on 2016-05-21.
 */
public class RegisterInfo extends Activity{
    String url ="http://pesang72.cafe24.com/UpdateType.php";
    int editID[] = {R.id.ri_fweight,R.id.ri_gweight,R.id.ri_height};
    int btnID[]={R.id.ri_ok};
    EditText oneword;
    EditText[] ri_Edit = new EditText[editID.length];
    Button[] ri_Btn = new Button[btnID.length];
    String id;
    String[] ri_info=new String[editID.length];
    //1. 초기 몸무게 2. 목표 몸무게 3. 키
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registerinfo);

        Intent intent = getIntent();
        id= intent.getStringExtra("_id");
        for(int i=0;i <editID.length;i++)
        {
            ri_Edit[i]=(EditText)findViewById(editID[i]);
            ri_info[i]=ri_Edit[i].getText().toString();
        }
        for(int i=0;i <btnID.length;i++)
        {
            ri_Btn[i]=(Button)findViewById(btnID[i]);
        }
        oneword = (EditText)findViewById(R.id.one_coment);
    }
    public void onOkClick(View v)
    {
        if(v.getId()==R.id.ri_ok) {
            //ok눌렀을떄
            new Thread(new Runnable() {
                public void run() {
                    Updatetype2(1, id, ri_Edit[0].getText().toString(),ri_Edit[1].getText().toString(),ri_Edit[2].getText().toString(),oneword.getText().toString());
                }
            }).start();
            Intent myAct1 = new Intent(getApplicationContext(), MainTab.class);
            myAct1.putExtra("_id",id);
            startActivity(myAct1);

        }
    }
    public void Updatetype2(int check,String ids,String goal, String present1, String height,String oneword){
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("mode", "3"));
        nameValuePairs.add(new BasicNameValuePair("check", check+""));
        nameValuePairs.add(new BasicNameValuePair("_id", ids));
        nameValuePairs.add(new BasicNameValuePair("goal", goal));
        nameValuePairs.add(new BasicNameValuePair("present1", present1));
        nameValuePairs.add(new BasicNameValuePair("height", height));
        nameValuePairs.add(new BasicNameValuePair("oneword", oneword));

        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(url);
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            httpclient.execute(httppost);
            Log.e("pass1", "connection success ");
        } catch (Exception e) {
            Log.e("Fail1", e.toString());
            Toast.makeText(getApplicationContext(), "Invalid IP Address",
                    Toast.LENGTH_LONG).show();
        }
    }
}

package com.fitcen.parkdusang.healthtrainer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

public class CreateExercise extends AppCompatActivity {
    String trainrID, scontent;
    String url = "http://pesang72.cafe24.com/createexercise.php";
    Button ok, cancel;
    EditText ename, epart, eset, enumber, einfo;
    Intent intent;
    String url2 = "http://pesang72.cafe24.com/GCMservice.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_exercise);
        intent = getIntent();
        trainrID = intent.getStringExtra("trainrid"); // 트레이너아이뒤
        scontent = intent.getStringExtra("content1"); // 전화번호
        ename = (EditText) findViewById(R.id.exercisename);
        epart = (EditText) findViewById(R.id.exercisepart);
        eset = (EditText) findViewById(R.id.EXs);
        enumber = (EditText) findViewById(R.id.EXn);
        einfo = (EditText) findViewById(R.id.EXinfo);

        ok = (Button) findViewById(R.id.EXok);
        cancel = (Button) findViewById(R.id.EXcancel);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ename.getText().equals("")) {
                    Toast.makeText(getApplicationContext(),"운동 이름을 입력해주세요",Toast.LENGTH_SHORT).show();
                } else if (epart.getText().equals("")) {
                    Toast.makeText(getApplicationContext(),"운동 부위를 입력해주세요",Toast.LENGTH_SHORT).show();
                } else if (eset.getText().equals("")) {
                    Toast.makeText(getApplicationContext(),"운동 세트를 입력해주세요",Toast.LENGTH_SHORT).show();
                } else if (enumber.getText().equals("")) {
                    Toast.makeText(getApplicationContext(),"운동 세트당 횟수를 입력해주세요",Toast.LENGTH_SHORT).show();
                } else if (einfo.getText().equals("")) {
                    Toast.makeText(getApplicationContext(),"운동 설명을 입력해주세요",Toast.LENGTH_SHORT).show();
                } else {
                    new Thread(new Runnable() {
                        public void run() {
                            Updatetype(scontent, ename.getText().toString(), epart.getText().toString(), eset.getText()
                                    .toString(), enumber.getText().toString(), einfo.getText().toString());
                        }
                    }).start();
                    new Thread(new Runnable() {
                        public void run() {
                            pushmsg(scontent);
                        }
                    }).start();
                }
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("check", 2);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    public void pushmsg(String phone) {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("phone", phone));
        nameValuePairs.add(new BasicNameValuePair("check", "3"));

        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(url2);
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
            httpclient.execute(httppost);
            Log.e("pass1", "connection success ");
        } catch (Exception e) {
            Log.e("Fail1", e.toString());

        }
    }

    public void Updatetype(String phone, String ename, String epart, String eset, String enumber, String einfo) {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("phone", phone));

        nameValuePairs.add(new BasicNameValuePair("ename", ename));
        nameValuePairs.add(new BasicNameValuePair("epart", epart));
        nameValuePairs.add(new BasicNameValuePair("eset", eset));
        nameValuePairs.add(new BasicNameValuePair("enumber", enumber));
        nameValuePairs.add(new BasicNameValuePair("einfo", einfo));


        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(url);
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
            httpclient.execute(httppost);
            Log.e("pass1", "connection success ");
            intent.putExtra("check", 1);
            setResult(RESULT_OK, intent);
            finish();
        } catch (Exception e) {
            Log.e("Fail1", e.toString());
            Intent intent = new Intent(getApplicationContext(),NetworkError.class);
            startActivity(intent);
        }
    }
}

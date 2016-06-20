package com.fitcen.parkdusang.healthtrainer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.InputStream;
import java.util.ArrayList;

public class Answerclass extends AppCompatActivity {
    TextView tx;
    Button btn;
    String scontent,sreport;
    ScrollView repo_scroll,edit_scroll;
    EditText ed;
    InputStream is = null;
    String result = null;
    String line = null;
    String url2 = "http://pesang72.cafe24.com/answerreport.php";
    String url = "http://pesang72.cafe24.com/getstringtype.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answerclass);
        repo_scroll=(ScrollView)findViewById(R.id.repo_scroll);
        tx = (TextView)findViewById(R.id.answertx);
        tx.setMovementMethod(new ScrollingMovementMethod());
        ed = (EditText)findViewById(R.id.repo_edit113);
        btn = (Button)findViewById(R.id.button5_1);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String result = "트레이너 :"+ed.getText().toString() + "\n- - - - - - - - - - - - - - - - - -\n" + tx.getText().toString();

                new Thread(new Runnable() {
                    public void run() {
                        Updatetype(scontent,result);
                    }
                }).start();

                finish();
            }
        });

        Intent intent = getIntent();
        sreport = intent.getStringExtra("report");
        scontent = intent.getStringExtra("content1"); // 전화번호
        tx.setText(sreport);

    }

    public void Updatetype(String phone,String report){
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("phone",phone));
        nameValuePairs.add(new BasicNameValuePair("report", report));
        Log.i("TAG", report);
        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(url2);
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs,"UTF-8"));
            httpclient.execute(httppost);
            Log.e("pass1", "connection success ");

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(), "피드백 전송이 완료되었습니다. ", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            Log.e("Fail1", e.toString());
            Intent intent = new Intent(getApplicationContext(),NetworkError.class);
            startActivity(intent);
        }
    }

}

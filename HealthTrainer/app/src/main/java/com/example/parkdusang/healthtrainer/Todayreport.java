package com.example.parkdusang.healthtrainer;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Todayreport extends AppCompatActivity {
    String stitle, scontent,trainrID;
    TextView tx;
    Button btn,ansbtn;
    InputStream is = null;
    String result = null;
    String line = null;
    String url = "http://pesang72.cafe24.com/getstringtype.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.todayreport);

        Intent intent = getIntent();
        trainrID = intent.getStringExtra("trainrid"); // 트레이너아이뒤
        stitle = intent.getStringExtra("title1"); // 이름
        scontent = intent.getStringExtra("content1"); // 전화번호

        getData(url,trainrID,scontent);

        tx = (TextView)findViewById(R.id.textView3_2);

        ansbtn = (Button)findViewById(R.id.answerbtn);
        ansbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getApplicationContext(),Answerclass.class);
                startActivity(intent1);

            }
        });
        btn  = (Button)findViewById(R.id.button3_1);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //디비 불러와서 끝
    }

    public void getData(String url, String id, String phone) {
        class GetDataJSON extends AsyncTask<String, Void, String> {

            @Override
            protected String doInBackground(String... params) {
                String uri = params[0];
                String id = params[1];
                String phone = params[2];
                ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("trainrid", id));
                nameValuePairs.add(new BasicNameValuePair("phone", phone));

                try {
                    HttpClient httpclient = new DefaultHttpClient();
                    HttpPost httppost = new HttpPost(uri);
                    httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                    HttpResponse response = httpclient.execute(httppost);
                    HttpEntity entity = response.getEntity();
                    is = entity.getContent();
                    Log.e("pass 1", "connection success ");
                } catch (Exception e) {
                    Log.e("Fail 1", e.toString());

                }


                try {
                    BufferedReader reader = new BufferedReader
                            (new InputStreamReader(is, "iso-8859-1"), 8);
                    StringBuilder sb = new StringBuilder();
                    while ((line = reader.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    is.close();
                    result = sb.toString();
                    Log.e("1223", result);

                    return sb.toString().trim();
                } catch (Exception e) {
                    Log.e("Fail 2", e.toString());
                    return null;
                }


            }
            @Override
            protected void onPostExecute(String result) {
                tx.setText(result);
            }
        }
        GetDataJSON g = new GetDataJSON();
        g.execute(url, id, phone);
    }
}

package com.example.parkdusang.healthtrainer;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class Connectionwaiting extends AppCompatActivity {
    Button Cok,Ccancel;
    TextView txt;
    String id;
    int type;
    InputStream is = null;
    String line = null;
    String url = "http://pesang72.cafe24.com/UpdateType.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.connectionwaiting);


        Intent intent = getIntent();
        id= intent.getStringExtra("_id");
        type = intent.getIntExtra("type",10);
        txt = (TextView)findViewById(R.id.connecttxt);
        Cok = (Button)findViewById(R.id.connectbtno);
        Ccancel = (Button)findViewById(R.id.connectbtnc);
        Cok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ok눌렀을떄
            }
        });
        Ccancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //ㄴㄴ 눌렀을때

            }
        });
        if(type == 2){
            txt.setText("아직 트레이너의 신청이 오지 않았습니다");
            Cok.setVisibility(View.INVISIBLE);
            Ccancel.setVisibility(View.INVISIBLE);
        }
        else {
            getData(url, id);
        }
    }
    public void getData(String url, String id) {
        class GetDataJSON extends AsyncTask<String, Void, String> {

            @Override
            protected String doInBackground(String... params) {
                String uri = params[0];
                String ids = params[1];
                ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("_id", ids));
                nameValuePairs.add(new BasicNameValuePair("mode", "2"));
                // 코드해독불가
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
                    Log.e("pass 1", sb.toString());
                    return sb.toString().trim();
                } catch (Exception e) {
                    Log.e("Fail 2", e.toString());
                    return null;
                }

            }

            @Override
            protected void onPostExecute(String result) {
                txt.setText(result +" 트레이너와 공유하시겠습니까?");
            }
        }
        GetDataJSON g = new GetDataJSON();
        g.execute(url, id);
    }
}

package com.fitcen.parkdusang.healthtrainer;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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
    boolean check=true;
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
        tx.setMovementMethod(new ScrollingMovementMethod());
        ansbtn = (Button)findViewById(R.id.answerbtn);
        ansbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(check) {

                    Intent intent1 = new Intent(getApplicationContext(), Answerclass.class);
                    intent1.putExtra("report",tx.getText().toString());
                    intent1.putExtra("content1", scontent);
                    startActivity(intent1);
                }
                else{
                    Toast.makeText(getApplicationContext(), "아직 보고가 오지 않았습니다", Toast.LENGTH_SHORT).show();
                }

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
                    httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs,"UTF-8"));
                    HttpResponse response = httpclient.execute(httppost);
                    HttpEntity entity = response.getEntity();
                    is = entity.getContent();
                    Log.e("pass 1", "connection success ");
                } catch (Exception e) {
                    Log.e("Fail 1", e.toString());

                }


                try {
                    BufferedReader reader = new BufferedReader
                            (new InputStreamReader(is, "UTF-8"));
                    StringBuilder sb = new StringBuilder();
                    while ((line = reader.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    is.close();
                    result = sb.toString();
                    Log.e("122333", result);

                    return sb.toString().trim();
                } catch (Exception e) {
                    Log.e("Fail 2", e.toString());
                    Intent intent = new Intent(getApplicationContext(),NetworkError.class);
                    startActivity(intent);
                    return null;
                }


            }
            @Override
            protected void onPostExecute(String result) {
                if(result == null){

                }
                else if(result.substring(0,2).equals("아직")){
                    check =false;
                    tx.setText(result);
                }
                else {
                    tx.setText(result);
                }
            }
        }
        GetDataJSON g = new GetDataJSON();
        g.execute(url, id, phone);
    }


}

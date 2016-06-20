package com.fitcen.parkdusang.healthtrainer;

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
import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class ExerciseInformation extends AppCompatActivity {
    Button btn;
    TextView tx;

    String id,exname,excontent;
    String result = null;
    String line = null;
    InputStream is = null;
    JSONArray peoples = null;
    String url ="http://pesang72.cafe24.com/infoexercise.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_information);

        Intent intent = getIntent();

        id = intent.getStringExtra("_id");
        exname = intent.getStringExtra("name");
        excontent = intent.getStringExtra("content");

        btn = (Button)findViewById(R.id.infoexercise);
        tx =(TextView)findViewById(R.id.exinfomation);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });


        getData(url,id,exname,excontent);
    }

    public void getData(String url, String id,String exname , String excontent) {
        class GetDataJSON extends AsyncTask<String, Void, String> {

            @Override
            protected String doInBackground(String... params) {
                String uri = params[0];
                String ids = params[1];
                String name = params[2];
                String content = params[3];

                ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("check", "1"));
                nameValuePairs.add(new BasicNameValuePair("_id", ids));
                nameValuePairs.add(new BasicNameValuePair("name",name));
                nameValuePairs.add(new BasicNameValuePair("content",content));

                try {
                    HttpClient httpclient = new DefaultHttpClient();
                    HttpPost httppost = new HttpPost(uri);
                    httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
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

                    return sb.toString().trim();
                } catch (Exception e) {
                    Intent intent = new Intent(getApplicationContext(),NetworkError.class);
                    startActivity(intent);
                    return null;
                }

            }

            @Override
            protected void onPostExecute(String result) {
                if(result != null)
                tx.setText(result);
            }
        }


        GetDataJSON g = new GetDataJSON();
            g.execute(url, id , exname, excontent);

    }
}


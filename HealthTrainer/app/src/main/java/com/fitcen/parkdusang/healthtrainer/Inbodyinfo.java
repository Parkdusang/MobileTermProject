package com.fitcen.parkdusang.healthtrainer;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Inbodyinfo extends AppCompatActivity {


    // 1. inbodyHeight 2. inbodyWeight 3. inbodyMuscle,
    // 4. inbodyFatAmount 5.inbodyFatRate 6.inbodyBMI 7.inbodyFatLv;
    Button btn, btn2;
    String url = "http://pesang72.cafe24.com/Inbodyinfo.php";
    String id, phone, myJSON;
    InputStream is = null;
    String result = null;
    String line = null;
    JSONArray peoples = null;
    EditText inbodyInfos1, inbodyInfos2, inbodyInfos3, inbodyInfos4, inbodyInfos5, inbodyInfos6, inbodyInfos7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inbodyinfo);

        Intent inbodyIntent = getIntent();

        //인텐트에서 얻어온부분
        id = inbodyIntent.getStringExtra("trainrid"); // 트레이너아이뒤
        phone = inbodyIntent.getStringExtra("content1");


        inbodyInfos1 = (EditText) findViewById(R.id.inbodyHeight);
        inbodyInfos2 = (EditText) findViewById(R.id.inbodyWeight);
        inbodyInfos3 = (EditText) findViewById(R.id.inbodyMuscle);
        inbodyInfos4 = (EditText) findViewById(R.id.inbodyFatAmount);
        inbodyInfos5 = (EditText) findViewById(R.id.inbodyFatRate2);
        inbodyInfos6 = (EditText) findViewById(R.id.inbodyBMI);
        //inbodyInfos7 = (EditText)findViewById(R.id.inbodyFatLv);

        btn = (Button) findViewById(R.id.inbodyok);
        btn2 = (Button) findViewById(R.id.inbodycancel);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    public void run() {
                        Updatetype(phone, inbodyInfos1.getText().toString(), inbodyInfos2.getText().toString(), inbodyInfos3.getText().toString(),
                                inbodyInfos4.getText().toString(), inbodyInfos5.getText().toString(), inbodyInfos6.getText().toString());
                    }
                }).start();
                finish();
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        getData(url, phone);
    }

    public void Updatetype(String phone, String height, String weight, String muscle, String fatamount, String fatrate, String bmi) {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("check", "2"));
        nameValuePairs.add(new BasicNameValuePair("phone", phone));
        nameValuePairs.add(new BasicNameValuePair("height", height));
        nameValuePairs.add(new BasicNameValuePair("weight", weight));
        nameValuePairs.add(new BasicNameValuePair("muscle", muscle));
        nameValuePairs.add(new BasicNameValuePair("fatamount", fatamount));
        nameValuePairs.add(new BasicNameValuePair("fatrate", fatrate));
        nameValuePairs.add(new BasicNameValuePair("bmi", bmi));


        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(url);
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
            httpclient.execute(httppost);
            Log.e("pass1", "connection success ");
        } catch (Exception e) {
            Log.e("Fail1", e.toString());
            Toast.makeText(getApplicationContext(), "Invalid IP Address",
                    Toast.LENGTH_LONG).show();
        }
    }


    protected void showList() {
        try {
            JSONObject jsonObj = new JSONObject(myJSON);
            peoples = jsonObj.getJSONArray("exercise");

            for (int i = 0; i < peoples.length(); i++) {
                JSONObject c = peoples.getJSONObject(i);
                inbodyInfos1.setText(c.getDouble("height") + "");
                inbodyInfos2.setText(c.getDouble("weight") + "");
                inbodyInfos3.setText(c.getDouble("muscle") + "");
                inbodyInfos4.setText(c.getDouble("bodyfat") + "");
                inbodyInfos5.setText(c.getDouble("persentfat") + "");
                inbodyInfos6.setText(c.getDouble("BMI") + "");
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void getData(String url, String phone) {
        class GetDataJSON extends AsyncTask<String, Void, String> {

            @Override
            protected String doInBackground(String... params) {
                String uri = params[0];
                String phone = params[1];
                ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("check", "1"));
                nameValuePairs.add(new BasicNameValuePair("phone", phone));


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
                    Log.e("1223", result);

                    return sb.toString().trim();
                } catch (Exception e) {
                    Log.e("Fail 2", e.toString());
                    Intent intent = new Intent(getApplicationContext(), NetworkError.class);
                    startActivity(intent);
                    return null;
                }

            }

            @Override
            protected void onPostExecute(String result) {
                myJSON = result;
                if (myJSON != null)
                    showList();
            }
        }


        GetDataJSON g = new GetDataJSON();
        g.execute(url, phone);
    }

}

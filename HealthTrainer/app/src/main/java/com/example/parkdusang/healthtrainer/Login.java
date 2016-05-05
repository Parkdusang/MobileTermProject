package com.example.parkdusang.healthtrainer;

import android.content.Intent;
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

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Login extends AppCompatActivity implements View.OnClickListener {
    EditText e1, e2;
    Button b1, b2;
    int checkingmode;
    Boolean checkid = false;
    Boolean checkpwd = false;
    Boolean ifnotexist = false;

    InputStream is = null;
    String result = null;
    String line = null;
    private static String url = "http://pesang72.cafe24.com/compareId.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        e1 = (EditText) findViewById(R.id.checkid);
        e2 = (EditText) findViewById(R.id.checkpassword);
        b1 = (Button) findViewById(R.id.button3);
        b2 = (Button) findViewById(R.id.button4);
        b1.setOnClickListener(this);
        b2.setOnClickListener(this);

        Intent intent = getIntent();
        checkingmode = intent.getIntExtra("sign", 10);
    }

    public void onClick(View v) {

        if (R.id.button3 == v.getId()) {
            if (checkingmode == 1) { // tranir login
                int a = insert();

                if (a == 0) {
                    Intent myAct1 = new Intent(getApplicationContext(), Trainermode.class);
                    startActivity(myAct1);
                } else if (a == 1) {
                    Toast.makeText(getApplicationContext(), "Fail password", Toast.LENGTH_SHORT).show();

                } else if(a == 4){
                    Toast.makeText(getApplicationContext(), "먼저끝났음", Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(getApplicationContext(), "Fail id", Toast.LENGTH_SHORT).show();
            } else { // customer login
                Intent myAct1 = new Intent(getApplicationContext(), customermode.class);
                startActivity(myAct1);
            }
        } else {
            Intent myAct1 = new Intent(getApplicationContext(), Register.class);
            myAct1.putExtra("checktype", checkingmode);
            startActivity(myAct1);
        }
    }

    public int insert() {

        new Thread() {
            public void run() {
                ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

                nameValuePairs.add(new BasicNameValuePair("_id", e1.getText().toString()));
                nameValuePairs.add(new BasicNameValuePair("password", e2.getText().toString()));
                // nameValuePairs.add(new BasicNameValuePair("tp", checkingmode+""));

                try {
                    HttpClient httpclient = new DefaultHttpClient();
                    HttpPost httppost = new HttpPost(url);
                    httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                    HttpResponse response = httpclient.execute(httppost);
                    HttpEntity entity = response.getEntity();
                    is = entity.getContent();
                    Log.e("pass 1", "connection success ");
                } catch (Exception e) {
                    Log.e("Fail 1", e.toString());
                    Toast.makeText(getApplicationContext(), "Invalid IP Address",
                            Toast.LENGTH_LONG).show();
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
                    if (result.equals("success")) {
                        checkid = true;
                        checkpwd = true;
                        //Log.e("1223", result);
                        ifnotexist = true;
                    } else if (result.equals("failedpassword")) {
                        ifnotexist = true;
                        checkid = true;
                        // Log.e("1223", result);
                    } else {
                        ifnotexist = true;
                    }

                } catch (Exception e) {
                    Log.e("Fail 2", e.toString());
                }

//                    try {
//                        JSONObject json_data = new JSONObject(result);
//                        code = (json_data.getInt("code"));
//
//                        if (code == 1) {
//                            Toast.makeText(getBaseContext(), "Inserted Successfully",
//                                    Toast.LENGTH_SHORT).show();
//                        } else {
//                            Toast.makeText(getBaseContext(), "Sorry, Try Again",
//                                    Toast.LENGTH_LONG).show();
//                        }
//                    } catch (Exception e) {
//                        Log.e("Fail 3", e.toString());
//                    }
            }
        }.start();

        if(ifnotexist) {
            if (checkid) {
                if (checkpwd) {
                    return 0;
                } else {
                    return 1;
                }
            } else {
                return 2;
            }

        }
        else
            return 4;

    }
}

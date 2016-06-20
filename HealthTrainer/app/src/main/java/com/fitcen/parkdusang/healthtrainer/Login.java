package com.fitcen.parkdusang.healthtrainer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Login extends AppCompatActivity implements View.OnClickListener {
    EditText e1, e2;
    Button b1, b2,b3;
    int checkingmode;
    SharedPreferences sp;
    
    InputStream is = null;
    String result = null;
    String line = null;
    private static String url = "http://pesang72.cafe24.com/compareId.php";
    private static String url2 = "http://pesang72.cafe24.com/GCMservice.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        e1 = (EditText) findViewById(R.id.checkid);
        e2 = (EditText) findViewById(R.id.checkpassword);
        b1 = (Button) findViewById(R.id.button3);
        b2 = (Button) findViewById(R.id.button4);
        b3 = (Button) findViewById(R.id.findidpassword);
        b1.setOnClickListener(this);
        b2.setOnClickListener(this);
        b3.setOnClickListener(this);
        Intent intent = getIntent();
        checkingmode = intent.getIntExtra("sign", 10);
        sp=getSharedPreferences("Login",MODE_PRIVATE);
        
        e1.setText(sp.getString("ID",""));
        e2.setText(sp.getString("PW",""));
        
        
    }
    
    public void onClick(View v) {
        sharedPreferences(e1.getText().toString(), e2.getText().toString());
        if (R.id.button3 == v.getId()) {
            new Thread(new Runnable() {
                public void run() {
                    insert();
                }
            }).start();
        }else if(R.id.findidpassword == v.getId()) {
            Intent myAct1 = new Intent(getApplicationContext(), Findidpassword.class);
            startActivity(myAct1);
        }
        else {
            Intent myAct1 = new Intent(getApplicationContext(), Register.class);
            myAct1.putExtra("checktype", checkingmode);
            startActivity(myAct1);
        }
    }
    
    public void sharedPreferences(String id,String pw){
        sp=getSharedPreferences("Login",MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putString("ID",id);
        edit.putString("PW",pw);
        edit.commit();
        
    }

    public void Updatetype(String token,int i){
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair("_id",e1.getText().toString()));
            nameValuePairs.add(new BasicNameValuePair("token",token));
            nameValuePairs.add(new BasicNameValuePair("check", i + ""));
        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(url2);
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs,"UTF-8"));
            httpclient.execute(httppost);
            Log.e("pass1", "connection success ");
        } catch (Exception e) {
            Log.e("Fail1", e.toString());
            Toast.makeText(getApplicationContext(), "Invalid IP Address",
                    Toast.LENGTH_LONG).show();
        }
    }

    public void insert() {
        
        
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        
        nameValuePairs.add(new BasicNameValuePair("_id", e1.getText().toString()));
        nameValuePairs.add(new BasicNameValuePair("password", e2.getText().toString()));
        nameValuePairs.add(new BasicNameValuePair("tp", checkingmode + ""));
        
        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(url);
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
            if (result.substring(2, 4).equals("su")) {
                checkingmode = Integer.parseInt(result.substring(1,2).toString());
                Log.e("check", checkingmode+"");
                InstanceID instanceID = InstanceID.getInstance(this);
                String token = null;
                try {
                    String default_senderId = getString(R.string.gcm_defaultSenderId);
                    // GCM 기본 scope는 "GCM"이다.
                    String scope = GoogleCloudMessaging.INSTANCE_ID_SCOPE;
                    // Instance ID에 해당하는 토큰을 생성하여 가져온다.
                    token = instanceID.getToken(default_senderId, scope, null);
                    Log.i("TAG","token:"+token  );
                    Updatetype(token, 2);

                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (checkingmode == 1) { // 트레이너모드
                    Intent myAct1 = new Intent(getApplicationContext(), Trainermode.class);
                    Log.i("TAG", e1.getText().toString());
                    myAct1.putExtra("_id", e1.getText().toString());
                    startActivity(myAct1);
                } else if(checkingmode == 3 || checkingmode == 2) { // 고객이 등록수락 전 모드
                    Intent myAct1 = new Intent(getApplicationContext(), Connectionwaiting.class);
                    myAct1.putExtra("_id", e1.getText().toString());
                    myAct1.putExtra("type",checkingmode);
                    myAct1.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                    startActivity(myAct1);
                }else { // 고객이 트레이너와 맺어졌을때
                    Intent myAct1 = new Intent(getApplicationContext(), MainTab.class);
                    myAct1.putExtra("_id", e1.getText().toString());
                    startActivity(myAct1);
                }
            } else if (result.substring(2, 8).equals("failed")) { // 비밀번호 실패
                runOnUiThread(new Thread(new Runnable() {
                    public void run() {
                        Toast.makeText(getApplicationContext(), "비밀번호가 틀렸습니다", Toast.LENGTH_SHORT).show();
                    }
                }));
                
            } else if (result.substring(2, 4).equals("wr")) { // 모드선택 오류
                runOnUiThread(new Thread(new Runnable() {
                    public void run() {
                        Toast.makeText(getApplicationContext(), "다른모드의 아이뒤입니다", Toast.LENGTH_SHORT).show();
                    }
                }));
            } else {
                runOnUiThread(new Thread(new Runnable() {
                    public void run() {
                        Toast.makeText(getApplicationContext(), "아이뒤가 일치하지 않습니다", Toast.LENGTH_SHORT).show();
                    }
                }));
            }
            
        } catch (Exception e) {
            Intent intent = new Intent(getApplicationContext(),NetworkError.class);
            startActivity(intent);
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
    
}

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

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Findidpassword extends AppCompatActivity {
    Button idbtn,passwordbtn;
    EditText ETid1,ETid2,ETpass1,ETpass2,ETpass3;

    InputStream is = null;
    String result = null;
    String line = null;
    String url ="http://pesang72.cafe24.com/findidorpassword.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.findidpassword);

        ETid1 = (EditText)findViewById(R.id.findidphone); // phone
        ETid2 = (EditText)findViewById(R.id.findidemail); // email
        ETpass1 = (EditText)findViewById(R.id.findpassid); // password id
        ETpass2 = (EditText)findViewById(R.id.findpassphone);// password phone
        ETpass3 = (EditText)findViewById(R.id.findpassemail);// password email
        idbtn = (Button)findViewById(R.id.findid);
        passwordbtn = (Button)findViewById(R.id.findpassword);



        idbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ETid1.getText().length() != 0 && ETid2.getText().length() != 0){
                    compareid(url,ETid1.getText().toString(),ETid2.getText().toString());
                }
            }
        });

        passwordbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ETpass1.getText().length() != 0 && ETpass2.getText().length() != 0 && ETpass3.getText().length() != 0){
                    comparepassword(url, ETpass1.getText().toString(), ETpass2.getText().toString(),ETpass3.getText().toString());
                }
            }
        });
    }


    public void compareid(String url, String phone,String email) {
        class GetDataJSON extends AsyncTask<String, Void, String> {

            @Override
            protected String doInBackground(String... params) {
                String uri = params[0];
                String phone = params[1];
                String email = params[2];
                ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("phone", phone));
                nameValuePairs.add(new BasicNameValuePair("email", email));
                nameValuePairs.add(new BasicNameValuePair("check", "1"));

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
                    Log.e("1223", result);

                    return sb.toString().trim();
                } catch (Exception e) {
                    Intent intent = new Intent(getApplicationContext(),NetworkError.class);
                    startActivity(intent);
                    return null;
                }

            }

            @Override
            protected void onPostExecute(String result) {
                Log.i("TAG", "onPostExecute: "+ result);
                if(result.substring(0,4).equals("noti")){
                    Toast.makeText(getApplicationContext(),"전화번호가 존재하지 않습니다" ,Toast.LENGTH_SHORT).show();
                }
                else if(result.substring(0,4).equals("note")){
                    Toast.makeText(getApplicationContext(),"입력하신 정보가 맞지 않습니다." ,Toast.LENGTH_SHORT).show();
                }
                else if(result != null){
                    Toast.makeText(getApplicationContext(),"아이뒤는 " + result + " 입니다." ,Toast.LENGTH_SHORT).show();
                }
            }
        }


        GetDataJSON g = new GetDataJSON();
        g.execute(url, phone, email);

    }

    public void comparepassword(String url,String id, String phone,String email) {
        class GetDataJSON extends AsyncTask<String, Void, String> {

            @Override
            protected String doInBackground(String... params) {
                String uri = params[0];
                String id = params[1];
                String phone = params[2];
                String email = params[3];
                ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("phone", phone));
                nameValuePairs.add(new BasicNameValuePair("email", email));
                nameValuePairs.add(new BasicNameValuePair("_id", id));
                nameValuePairs.add(new BasicNameValuePair("check", "2"));
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
                    Log.e("1223", result);

                    return sb.toString().trim();
                } catch (Exception e) {
                    Log.e("Fail 2", e.toString());
                    return null;
                }

            }

            @Override
            protected void onPostExecute(String result) {
                if(result == null){

                }
                else if(result.substring(0,4).equals("noti")){
                    Toast.makeText(getApplicationContext(),"아이뒤가 존재하지 않습니다" ,Toast.LENGTH_SHORT).show();
                }
                else if(result.substring(0,4).equals("note")){
                    Toast.makeText(getApplicationContext(),"입력하신 정보가 맞지 않습니다." ,Toast.LENGTH_SHORT).show();
                }
                else{
                    int len = result.length();
                    result = result.substring(0 , len-2);
                    String encript =result+ "**";
                    Toast.makeText(getApplicationContext(),"비밀번호는 "+ encript + " 입니다." ,Toast.LENGTH_SHORT).show();
                }
            }
        }


        GetDataJSON g = new GetDataJSON();
        g.execute(url,id, phone,email);

    }
}

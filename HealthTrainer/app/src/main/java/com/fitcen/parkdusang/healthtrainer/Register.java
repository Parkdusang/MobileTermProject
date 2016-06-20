package com.fitcen.parkdusang.healthtrainer;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
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
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class Register extends AppCompatActivity {
    Button ok,cancel;
    EditText ETname, ETphone1,ETphone2,ETphone3, ETID, ETpassword, ETpasswordCheck,ETemail;
    RadioButton b1, b2;
    InputStream is = null;
    String result = null;
    String line = null;
    int code;
    int checkingmode;
    int sex;
    private static String url = "http://pesang72.cafe24.com/memberJoin.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        Intent intent = getIntent();
        checkingmode = intent.getIntExtra("checktype", 10);


        ETname = (EditText) findViewById(R.id.rg_edit4);
        ETID = (EditText) findViewById(R.id.rg_edit1);
        ETpassword = (EditText) findViewById(R.id.rg_edit2);
        ETpasswordCheck = (EditText) findViewById(R.id.rg_edit2c);
        ETphone1 = (EditText) findViewById(R.id.rg_edit3_1);
        ETphone2 = (EditText) findViewById(R.id.rg_edit3_2);
        ETphone3 = (EditText) findViewById(R.id.rg_edit3_3);
        ETemail = (EditText) findViewById(R.id.rg_edit2email);
        b1 = (RadioButton) findViewById(R.id.sex1);
        b2 = (RadioButton) findViewById(R.id.sex2);
        ok = (Button) findViewById(R.id.button5);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ETname.equals("")) {
                    Toast.makeText(getApplicationContext(), "이름을 입력하시오", Toast.LENGTH_SHORT).show();
                } else if (ETID.equals("") || ETID.getText().length() < 4) {
                    Toast.makeText(getApplicationContext(), "올바른 아이디를 입력하시오", Toast.LENGTH_SHORT).show();
                } else if (ETpassword.equals("") || ETpassword.getText().length() < 4) {
                    Toast.makeText(getApplicationContext(), "올바른 비밀번호를 입력하시오", Toast.LENGTH_SHORT).show();
                } else if (ETphone1.length() < 2 || ETphone2.length() < 3 || ETphone3.length() < 3 ) {
                    Toast.makeText(getApplicationContext(), "정확한 휴대폰 번호를 입력하시오", Toast.LENGTH_SHORT).show();
                } else if (b1.isChecked() == false && b2.isChecked() == false) {
                    Toast.makeText(getApplicationContext(), "성별을 체크하시오", Toast.LENGTH_SHORT).show();
                } else if (!ETpassword.getText().toString().equals(ETpasswordCheck.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "비밀번호가 다릅니다.", Toast.LENGTH_SHORT).show();
                } else if(!ETemail.getText().toString().contains("@") || !ETemail.getText().toString().contains(".") || ETemail.getText().length() <8){
                    Toast.makeText(getApplicationContext(), "이메일 형식이 잘못되었습니다.", Toast.LENGTH_SHORT).show();
                }else {

                    if (b1.isChecked()) {
                        sex = 1;
                    } else
                        sex = 2;
                    insert();
                }
            }
        });

        cancel = (Button)findViewById(R.id.button6);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void insert() {
        final String phone = ETphone1.getText().toString()+  ETphone2.getText().toString() +ETphone3.getText().toString();
        new Thread() {
            public void run() {
                ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

                nameValuePairs.add(new BasicNameValuePair("_id", ETID.getText().toString()));
                nameValuePairs.add(new BasicNameValuePair("password", ETpassword.getText().toString()));
                nameValuePairs.add(new BasicNameValuePair("name", ETname.getText().toString()));
                nameValuePairs.add(new BasicNameValuePair("sex", sex+""));
                nameValuePairs.add(new BasicNameValuePair("phonenumber",phone));
                nameValuePairs.add(new BasicNameValuePair("tp", checkingmode + ""));
                nameValuePairs.add(new BasicNameValuePair("email",ETemail.getText().toString()));

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
                    Toast.makeText(getApplicationContext(), "Invalid IP Address",
                            Toast.LENGTH_LONG).show();
                }

                try {
                    BufferedReader reader = new BufferedReader
                            (new InputStreamReader(is,"UTF-8"));
                    StringBuilder sb = new StringBuilder();
                    while ((line = reader.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    is.close();
                    result = sb.toString();
                    Log.e("pass 2", result);

                    if (result.substring(1, 3).equals("su")) {
                        runOnUiThread(new Thread(new Runnable() {
                            public void run() {
                                Toast.makeText(getApplicationContext(), "회원가입에 성공했습니다.", Toast.LENGTH_SHORT).show();
                            }
                        }));
                        finish();
                    }
                    else if(result.substring(1, 3).equals("id")){
                        runOnUiThread(new Thread(new Runnable() {
                            public void run() {
                                Toast.makeText(getApplicationContext(), "이미 아이뒤가 존재합니다", Toast.LENGTH_SHORT).show();
                            }
                        }));
                    }
                    else if(result.substring(1, 3).equals("ph")){
                        runOnUiThread(new Thread(new Runnable() {
                            public void run() {
                                Toast.makeText(getApplicationContext(), "이미 전화번호가 존재합니다.", Toast.LENGTH_SHORT).show();
                            }
                        }));
                    }
                    else {
                        runOnUiThread(new Thread(new Runnable() {
                            public void run() {
                                Toast.makeText(getApplicationContext(), "회원가입에 실패했습니다.", Toast.LENGTH_SHORT).show();
                            }
                        }));
                        finish();
                    }
                } catch (Exception e) {
                    Log.e("Fail 2", e.toString());
                    Intent intent = new Intent(getApplicationContext(),NetworkError.class);
                    startActivity(intent);
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
    }


    //
//    private class back extends AsyncTask<String, Integer,Bitmap> {
//
//        @Override
//        protected Bitmap doInBackground(String... urls) {
//            // TODO Auto-generated method stub
//            try{
//                URL myFileUrl = new URL(urls[0]);
//                HttpURLConnection conn = (HttpURLConnection)myFileUrl.openConnection();
//                conn.setDoInput(true);
//                conn.connect();
//                //String json = DownloadHtml("http://서버주소/appdata.php");
//                InputStream is = conn.getInputStream();
//
//                bmImg = BitmapFactory.decodeStream(is);
//
//
//            }catch(IOException e){
//                e.printStackTrace();
//            }
//            return bmImg;
//        }
//
//        protected void onPostExecute(Bitmap img){
//            imView.setImageBitmap(bmImg);
//        }
//    }
    private class phpDown extends AsyncTask<String, Integer, String> {


        @Override
        protected String doInBackground(String... urls) {
            StringBuilder jsonHtml = new StringBuilder();
            try {
                // 연결 url 설정
                URL url = new URL(urls[0]);
                // 커넥션 객체 생성
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                // 연결되었으면.
                if (conn != null) {
                    conn.setConnectTimeout(10000);
                    conn.setUseCaches(false);
                    // 연결되었음 코드가 리턴되면.
                    if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
                        for (; ; ) {
                            // 웹상에 보여지는 텍스트를 라인단위로 읽어 저장.
                            String line = br.readLine();
                            if (line == null) break;
                            // 저장된 텍스트 라인을 jsonHtml에 붙여넣음
                            jsonHtml.append(line + "\n");
                        }
                        br.close();
                    }
                    conn.disconnect();
                    String str = jsonHtml.toString();

                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return jsonHtml.toString();

        }

        protected void onPostExecute(String str) {

            Log.i("TAG", str);
            //
        }

    }
}

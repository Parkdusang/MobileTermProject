package com.fitcen.parkdusang.healthtrainer;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class CustomerInformation extends AppCompatActivity {
    String stitle, scontent,trainrID,CustomerId;
    int simgid;
    TextView txtTitle,setcumtomer,settrainr;
    TextView txtContent;
    ImageView imgIcon;
    Button setexercise,report,inbody,setword,record;
    String url = "http://pesang72.cafe24.com/customerinfo.php";
    InputStream is = null;
    String result = null;
    String line = null,myJSON= null;
    JSONArray peoples = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_information);

        Intent intent = getIntent();
        trainrID = intent.getStringExtra("trainrid"); // 트레이너아이뒤
        stitle = intent.getStringExtra("title1"); // 이름
        scontent = intent.getStringExtra("content1"); // 전화번호
        simgid = intent.getIntExtra("imgid", 10);

        Log.i("TAG", "onCreate: "+scontent);
        txtTitle = (TextView)findViewById(R.id.txtTitle2);
        txtContent = (TextView)findViewById(R.id.txtContent2);
        imgIcon =  (ImageView)findViewById(R.id.imgIcon2);
        setcumtomer = (TextView)findViewById(R.id.onewordcustomer);
        settrainr = (TextView)findViewById(R.id.settrainr);
        txtTitle.setText(stitle);
        txtContent.setText(scontent);
        imgIcon.setImageResource(simgid);
        setword = (Button)findViewById(R.id.setoneword);
        record = (Button)findViewById(R.id.recodeex);

        setword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(CustomerInformation.this);

                alert.setTitle("트레이너 공지");
                alert.setMessage("공지사항을 입력해주세요");


                final EditText name = new EditText(CustomerInformation.this);
                alert.setView(name);

                alert.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                        settrainr.setText(name.getText().toString());
                        CustomerInformation.this.testinput(name.getText().toString());

                    }
                });


                alert.setNegativeButton("no",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                    }
                });

                alert.show();

            }
        });

        record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getApplicationContext(),Recodeexercise.class);
                intent1.putExtra("_id",CustomerId);
                startActivity(intent1);
            }
        });
        setexercise = (Button)findViewById(R.id.setexercise);

        setexercise.setOnClickListener(new View.OnClickListener() { // 운동 지정
            @Override
            public void onClick(View v) {
                Intent myAct = new Intent(getApplicationContext(), Setexercise.class);
                myAct.putExtra("trainrid", trainrID);
                myAct.putExtra("title1", stitle);
                myAct.putExtra("content1", scontent);
                startActivity(myAct);
            }
        });

        report = (Button)findViewById(R.id.button8);
        report.setOnClickListener(new View.OnClickListener() { // 보고
            @Override
            public void onClick(View v) {
                Intent myAct1 = new Intent (getApplicationContext(), Todayreport.class);
                myAct1.putExtra("trainrid", trainrID);
                myAct1.putExtra("title1", stitle);
                myAct1.putExtra("content1", scontent);
                startActivity(myAct1);
            }
        });
        inbody = (Button)findViewById(R.id.inbodyinfo);
        inbody.setOnClickListener(new View.OnClickListener() { // 보고
            @Override
            public void onClick(View v) {
                Intent myAct2 = new Intent(getApplicationContext(), Inbodyinfo.class);
                myAct2.putExtra("trainrid", trainrID);
                myAct2.putExtra("title1", stitle);
                myAct2.putExtra("content1", scontent);
                startActivity(myAct2);
            }
        });

        getData(url,trainrID,scontent);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, 0, Menu.NONE, "만든사람들");
        menu.add(0, 1, Menu.NONE, "연결 해제");
        menu.add(0, 2, Menu.NONE, "로그아웃");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case 0:
                Intent intent = new Intent(getApplicationContext(), Developer.class);
                startActivity(intent);
                break;
            case 1:
                AlertDialog.Builder alt_bld = new AlertDialog.Builder(this);
                alt_bld.setMessage("고객과의 연결을 끊으시겠습니까 ?").setCancelable(
                        false).setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent intent2 = new Intent(getApplicationContext(), Destroyconnection.class);
                                intent2.putExtra("check", "1");
                                intent2.putExtra("id",trainrID);
                                intent2.putExtra("name",stitle);
                                intent2.putExtra("phone", scontent);
                                startService(intent2);

                                Intent intent3 = new Intent(getApplicationContext(), Trainermode.class);
                                intent3.putExtra("_id",trainrID);
                                intent3.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent3);
                            }
                        }).setNegativeButton("No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // Action for 'NO' Button
                                dialog.cancel();
                            }
                        });
                AlertDialog alert = alt_bld.create();
                // Title for AlertDialog
                alert.setTitle("고객 연결관리");
                // Icon for AlertDialog
                alert.setIcon(R.drawable.icon);
                alert.show();

                break;
            case 2:
                Intent intent1 = new Intent(getApplicationContext(), MainActivity.class);
                intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent1);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    public void testinput(final String txt){


        new Thread(new Runnable() {
            @Override
            public void run() {

                Updatetype(trainrID,txt,stitle);
            }
        }).start();
    }
    public void Updatetype(String ids,String txt,String name){
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("check", "2"));
        nameValuePairs.add(new BasicNameValuePair("_id", ids));
        nameValuePairs.add(new BasicNameValuePair("txt", txt));
        nameValuePairs.add(new BasicNameValuePair("name", name));

        Log.i("TAG11", ids + " " + txt + " " + name);
        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(url);
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs,"UTF-8"));
            httpclient.execute(httppost);
            Log.e("pass1", "connection success ");
        } catch (Exception e) {
            Log.e("Fail1", e.toString());
            Intent intent = new Intent(getApplicationContext(),NetworkError.class);
            startActivity(intent);
        }
    }
    protected void showList() {
        try {
            JSONObject jsonObj = new JSONObject(myJSON);
            peoples = jsonObj.getJSONArray("data");

            for (int i = 0; i < peoples.length(); i++) {
                JSONObject c = peoples.getJSONObject(i);

                String trainr = c.getString("trainr");
                String customer = c.getString("customer");

                if(customer.equals("")){
                    setcumtomer.setText("아직 입력사항이 없습니다.");
                }
                else{
                    setcumtomer.setText(stitle+ "님의 한마디!\n" +customer);
                }
                if(trainr.equals("")){
                    settrainr.setText("아직 입력사항이 없습니다.");
                }
                else{
                    settrainr.setText(trainr);
                }

            }
            peoples = jsonObj.getJSONArray("info");

            for (int i = 0; i < peoples.length(); i++) {
                JSONObject c = peoples.getJSONObject(i);

                CustomerId = c.getString("id");

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public void getData(String url, String id,String scontent) {
        class GetDataJSON extends AsyncTask<String, Void, String> {

            @Override
            protected String doInBackground(String... params) {
                String uri = params[0];
                String ids = params[1];
                String phone = params[2];
                ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("_id", ids));
                nameValuePairs.add(new BasicNameValuePair("check", "1"));
                nameValuePairs.add(new BasicNameValuePair("phone", phone));

                try {
                    HttpClient httpclient = new DefaultHttpClient();
                    HttpPost httppost = new HttpPost(uri);
                    httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs,"UTF-8"));
                    HttpResponse response = httpclient.execute(httppost);
                    HttpEntity entity = response.getEntity();
                    is = entity.getContent();
                    Log.i("TAG", ids);
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
                    Log.e("Fail 2", e.toString());
                    return null;
                }

            }

            @Override
            protected void onPostExecute(String result) {
                myJSON = result;
                if(myJSON != null)
                    showList();
            }
        }


        GetDataJSON g = new GetDataJSON();
        g.execute(url,id,scontent);
    }


}

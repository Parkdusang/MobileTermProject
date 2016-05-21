package com.example.parkdusang.healthtrainer;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

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
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class Setexercise extends AppCompatActivity {
    ListView listView;
    ArrayList<MyCustomDTO2> list;
    Button btnok, btnnewec;
    MyCustomAdapter2 adapter2;
    String url="http://pesang72.cafe24.com/insertexercise.php";
    String getifurl ="http://pesang72.cafe24.com/getexercise.php";
    String trainrID,scontent;
    String name,set,number;
    InputStream is = null;
    String line = null;
    JSONArray peoples = null;
    String myJSON;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setexercise);

        Intent intent = getIntent();
        trainrID = intent.getStringExtra("trainrid"); // 트레이너아이뒤
        scontent = intent.getStringExtra("content1"); // 전화번호

        btnok = (Button) findViewById(R.id.setexercisebtn);
        btnok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for (int i = 0; i < adapter2.getCount(); i++) {
                    if (list.get(i).getCheckboxt()){
                        name = list.get(i).getTitle();
                        set = list.get(i).getexercise();
                        number= list.get(i).getnumber();
                        inputthread(name,set,number);
                    }


                }

                finish();
            }
        });
//        btnnewec = (Button)findViewById(R.id.newexercisebtn);
//        btnnewec.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//
//            }
//        });


        // 이부분을 db 불러와서 저장해야됌 ㅇㅇ 겁나 빡실거임
        list = new ArrayList<MyCustomDTO2>();
        listView = (ListView) findViewById(R.id.listView2);
        adapter2 =
                new MyCustomAdapter2(
                        getApplicationContext(),
                        R.layout.list_row_exercise,
                        list);

        listView.setAdapter(adapter2);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (list.get(position).getCheckboxt()) {
                    list.get(position).setCheckboxt(false);

                } else {

                    Intent myAct1 = new Intent(getApplicationContext(), Setnumber.class);
                    myAct1.putExtra("position", position);
                    startActivityForResult(myAct1, 1);

                }
                adapter2.notifyDataSetChanged();


            }
        });


        getData(getifurl);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) // 액티비티가 정상적으로 종료되었을 경우
        {
            // CreateActivity에서 호출한 경우에만 처리
            if (requestCode == 1) {

                int mode = data.getIntExtra("mode", 10);
                int position = data.getIntExtra("position", 10);
                int set = data.getIntExtra("set", 10);
                int number = data.getIntExtra("number", 10);
                if (mode == 1) {
                    list.get(position).setnumber(number);
                    list.get(position).setexercise(set);
                    list.get(position).setCheckboxt(true);
                    adapter2.notifyDataSetChanged();

                }

            }
        }
    }
    public void inputthread(final String name1 , final String set1, final String number1){
        new Thread(new Runnable() {
            public void run() {
                Updatetype(trainrID,scontent,name1,set1,number1);
            }
        }).start();
    }
    public void Updatetype(String trainrid, String phone,String name,String set,String number){
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("phone",phone));
        nameValuePairs.add(new BasicNameValuePair("trainr_id", trainrid));
        nameValuePairs.add(new BasicNameValuePair("name", name));
        nameValuePairs.add(new BasicNameValuePair("set", set));
        nameValuePairs.add(new BasicNameValuePair("number", number));
        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(url);
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
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
            peoples = jsonObj.getJSONArray("data");

            for (int i = 0; i < peoples.length(); i++) {
                JSONObject c = peoples.getJSONObject(i);
                String name = c.getString("name");
                String content = c.getString("content");
                list.add(new MyCustomDTO2(false,name, content));

            }
            adapter2.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void getData(String url) {
        class GetDataJSON extends AsyncTask<String, Void, String> {

            @Override
            protected String doInBackground(String... params) {
                String uri = params[0];
                BufferedReader bufferedReader = null;
                try {
                    URL url = new URL(uri);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    StringBuilder sb = new StringBuilder();

                    bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream(), "iso-8859-1"));

                    String json;
                    while ((json = bufferedReader.readLine()) != null) {
                        sb.append(json + "\n");
                    }

                    return sb.toString().trim();

                } catch (Exception e) {
                    return null;


                }
//
            }

            @Override
            protected void onPostExecute(String result) {
                myJSON = result;
                showList();
            }
        }
        GetDataJSON g = new GetDataJSON();
        g.execute(url);
    }
}

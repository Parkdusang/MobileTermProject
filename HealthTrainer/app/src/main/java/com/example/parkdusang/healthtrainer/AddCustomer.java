package com.example.parkdusang.healthtrainer;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
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

public class AddCustomer extends AppCompatActivity {
    String myJSON;

    private static final String TAG_RESULTS = "data";
    private static final String TAG_PHONE = "phone";
    private static final String TAG_ID = "name";
    private static final String TAG_SEX = "sex";
    InputStream is = null;
    String line = null;
    JSONArray peoples = null;

    Spinner s1;
    String[] plants_arrays = {
            "이름", "성별", "나이", "전화번호"
    };
    ListView listView;
    MyCustomAdapter3 adapter3;
    ArrayList<MyCustomDTOAddCustim> list;
    Button btn2;
    String name, content;
    int imgid;
    int i;
    private String url = "http://pesang72.cafe24.com/connecttranirdb.php";
    private String url2 = "http://pesang72.cafe24.com/UpdateType.php";
    String trainrid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_customer);
        Intent intent = getIntent();
        trainrid = intent.getStringExtra("trainrid");


        btn2 = (Button) findViewById(R.id.btn1);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent returnintent = new Intent();
                for (i = 0; i < adapter3.getCount(); i++) {
                    if (list.get(i).getCheckboxt()) {
                        Log.i("testid", trainrid);
                        new Thread(new Runnable() {
                            public void run() {
                                Updatetype(list.get(i).getContent(),trainrid);
                            }
                        }).start();
                        break;
                    }
                }
                setResult(Activity.RESULT_OK, returnintent);
                finish();
            }
        });


        s1 = (Spinner) findViewById(R.id.spinnersort);
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, plants_arrays);
        s1.setAdapter(adapter);
        s1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0,
                                       View arg1, int arg2, long arg3) {
                int index = s1.getSelectedItemPosition();
                Toast.makeText(getBaseContext(),
                        "You have selected item : " + plants_arrays[index], Toast.LENGTH_SHORT).show();
            }

            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });


        list = new ArrayList<MyCustomDTOAddCustim>();
//        list.add(new MyCustomDTOAddCustim(false, "누군가", "홍길동 테스트!", R.drawable.user1));
//        list.add(new MyCustomDTOAddCustim(false, "미친존재감", "영희 테스트!", R.drawable.user2));
//        list.add(new MyCustomDTOAddCustim(false, "추가", "철수 테스트!", R.drawable.user1));

        listView = (ListView) findViewById(R.id.listView3);

        adapter3 =
                new MyCustomAdapter3(
                        getApplicationContext(),
                        R.layout.list_row_addcustomer,
                        list);

        listView.setAdapter(adapter3);

        getData(url);



        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
              //  finish();
                name = list.get(position).getTitle().toString();
                content = list.get(position).getContent().toString();
                imgid = list.get(position).getImgIcon();
                for (int i = 0; i < adapter3.getCount(); i++) {
                    if (i == position)
                        list.get(position).setCheckboxt(true);
                    else
                        list.get(i).setCheckboxt(false);

                }

                adapter3.notifyDataSetChanged();


            }
        });

    }
    public void Updatetype(String phone,String trainrid){
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("mode", "1"));
        nameValuePairs.add(new BasicNameValuePair("phone",phone));
        nameValuePairs.add(new BasicNameValuePair("trainr_id", trainrid));


        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(url2);
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
            peoples = jsonObj.getJSONArray(TAG_RESULTS);

            for (int i = 0; i < peoples.length(); i++) {
                JSONObject c = peoples.getJSONObject(i);
                String phone = c.getString(TAG_PHONE);
                String name = c.getString(TAG_ID);
                int sex = c.getInt(TAG_SEX);

                if (sex == 1) {
                    list.add(new MyCustomDTOAddCustim(false,name, phone, R.drawable.user1));
                } else {
                    list.add(new MyCustomDTOAddCustim(false,name, phone, R.drawable.user2));
                }
            }
            adapter3.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void getData(String url) {
        class GetDataJSON extends AsyncTask<String, Void, String> {

            @Override
            protected String doInBackground(String... params) {
                String uri = params[0];
                ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("mode", "add"));


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

                    return sb.toString().trim();
                } catch (Exception e) {
                    Log.e("Fail 2", e.toString());
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

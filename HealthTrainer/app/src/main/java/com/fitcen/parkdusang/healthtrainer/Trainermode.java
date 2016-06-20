package com.fitcen.parkdusang.healthtrainer;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

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
import java.util.HashMap;

public class Trainermode extends AppCompatActivity {
    // 처음 트레이너 모드로 로그인하면 들어오는 모드 list view 로 보여준다.

    String myJSON;

    private static final String TAG_PHONE = "phone";
    private static final String TAG_ID = "name";
    private static final String TAG_SEX = "sex";
    private static final String TAG_RESULTS = "data";
    JSONArray peoples = null;

    ArrayList<HashMap<String, String>> personList;


    ListView listView;
    FloatingActionButton btn;
    ArrayList<MyCustomDTO> list;
    MyCustomAdapter adapter;
    InputStream is = null;
    String result = null;
    String line = null;
    private String url = "http://pesang72.cafe24.com/connecttranirdb.php";
    String urlp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trainermode);
        Intent intent = getIntent();
        urlp = intent.getStringExtra("_id");
        btn = (FloatingActionButton) findViewById(R.id.fab);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myAct1 = new Intent(getApplicationContext(), AddCustomer.class);
                myAct1.putExtra("trainrid",urlp);
                startActivityForResult(myAct1, 1);

            }
        });

        list = new ArrayList<MyCustomDTO>();

        listView = (ListView) findViewById(R.id.listView1);
        adapter = new MyCustomAdapter(
                getApplicationContext(),
                R.layout.list_row,
                list);

        listView.setAdapter(adapter);


        getData(url,urlp);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(adapter.getinput()) {
                    Intent myAct = new Intent(getApplicationContext(), CustomerInformation.class);
                    String title = list.get(position).getTitle().toString();
                    String content = list.get(position).getContent().toString();
                    int imgid = list.get(position).getImgIcon();
                    myAct.putExtra("imgid", imgid);
                    myAct.putExtra("trainrid", urlp);
                    myAct.putExtra("title1", title);
                    myAct.putExtra("content1", content);
                    startActivity(myAct);
                }
            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, 0, Menu.NONE, "만든사람들");
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
            case 2:
                Intent intent1 = new Intent(getApplicationContext(), MainActivity.class);
                intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent1);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) // 액티비티가 정상적으로 종료되었을 경우
        {
            if (requestCode == 1) //  CreateActivity에서 호출한 경우에만 처리
            {

//                String name = data.getStringExtra("name");
//                String content = data.getStringExtra("content");
//                int imgid = data.getIntExtra("imgid", 10);
//
//                list.add(new MyCustomDTO(name, content, imgid));
//                adapter.notifyDataSetChanged();
            }
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
                    list.add(new MyCustomDTO(name, phone, R.drawable.user1));
                } else {
                    list.add(new MyCustomDTO(name, phone, R.drawable.user2));
                }
            }
            adapter.notifyDataSetChanged();
        } catch (JSONException e) {
            list.add(new MyCustomDTO("아직 추가된 유저가 없습니다.", "", R.drawable.user2));
            adapter.setinput(false);
            adapter.notifyDataSetChanged();
            e.printStackTrace();
        }
    }

    public void getData(String url, String id) {
        class GetDataJSON extends AsyncTask<String, Void, String> {

            @Override
            protected String doInBackground(String... params) {
                String uri = params[0];
                String ids = params[1];
                ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("_id", ids));
                nameValuePairs.add(new BasicNameValuePair("mode", "trainr"));

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
//                BufferedReader bufferedReader = null;


//                try {
//                    URL url = new URL(uri);
//                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
//                    StringBuilder sb = new StringBuilder();
//
//                    bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream(), "iso-8859-1"));
//
//                    String json;
//                    while ((json = bufferedReader.readLine()) != null) {
//                        sb.append(json + "\n");
//                    }
//
//                    return sb.toString().trim();
//
//                } catch (Exception e) {
//                    return null;
//
//
//                }
            }

            @Override
            protected void onPostExecute(String result) {
                myJSON = result;
                if(myJSON != null)
                showList();
            }
        }


        GetDataJSON g = new GetDataJSON();
        g.execute(url,id);
    }
}
package com.fitcen.parkdusang.healthtrainer;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
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
    String url = "http://pesang72.cafe24.com/insertexercise.php";
    String url2 = "http://pesang72.cafe24.com/GCMservice.php";
    String getifurl = "http://pesang72.cafe24.com/getexercise.php";

    String trainrID, scontent;
    String name, set, number;
    InputStream is = null;
    String line = null;
    JSONArray peoples = null;
    String myJSON;
    int position1 = 0;

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
                    if (list.get(i).getCheckboxt()) {
                        name = list.get(i).getTitle();
                        set = list.get(i).getexercise();
                        number = list.get(i).getnumber();
                        inputthread(name, set, number);
                    }

                }
                Toast.makeText(getApplicationContext(), "운동을 지정하셨습니다.!", Toast.LENGTH_SHORT).show();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        pushmsg(scontent);
                    }
                }).start();
                finish();
            }
        });
        btnnewec = (Button) findViewById(R.id.newexercisebtn);
        btnnewec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myAct = new Intent(getApplicationContext(), CreateExercise.class);
                myAct.putExtra("trainrid", trainrID);
                myAct.putExtra("content1", scontent);
                startActivityForResult(myAct, 2);
            }
        });


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
                position1 = position;
                if (list.get(position).getCheckboxt()) {
                    list.get(position).setCheckboxt(false);

                } else {

                    AlertDialog.Builder alert = new AlertDialog.Builder(Setexercise.this);

                    alert.setTitle("세트별 회수 지정");
                    alert.setIcon(R.drawable.icon);
                    alert.setMessage("개수를 입력하세요!");

                    final TextView set = new TextView(Setexercise.this);
                    set.setText("    세트 :");
                    set.setTextSize(15);
                    alert.setView(set);
                    final EditText name = new EditText(Setexercise.this);
                    name.setInputType(InputType.TYPE_CLASS_NUMBER);
                    alert.setView(name);

                    final TextView set2 = new TextView(Setexercise.this);
                    set2.setText("    개수 :");
                    set2.setTextSize(15);
                    alert.setView(set2);
                    final EditText name2 = new EditText(Setexercise.this);
                    name2.setInputType(InputType.TYPE_CLASS_NUMBER);
                    alert.setView(name2);

                    set.setWidth(200);
                    set2.setWidth(200);
                    name.setWidth(500);
                    name2.setWidth(500);

                    LinearLayout ll_1 = new LinearLayout(Setexercise.this);
                    ll_1.setOrientation(LinearLayout.HORIZONTAL);
                    ll_1.addView(set);
                    ll_1.addView(name);
                    LinearLayout ll_2 = new LinearLayout(Setexercise.this);
                    ll_2.setOrientation(LinearLayout.HORIZONTAL);
                    ll_2.addView(set2);
                    ll_2.addView(name2);
                    LinearLayout ll = new LinearLayout(Setexercise.this);
                    ll.setOrientation(LinearLayout.VERTICAL);
                    ll.addView(ll_1);
                    ll.addView(ll_2);
                    alert.setView(ll);

                    alert.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {


                            list.get(position1).setnumber(Integer.parseInt(name2.getText().toString()));
                            list.get(position1).setexercise(Integer.parseInt(name.getText().toString()));
                            list.get(position1).setCheckboxt(true);
                            adapter2.notifyDataSetChanged();
                        }
                    });


                    alert.setNegativeButton("no", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {

                        }
                    });

                    alert.show();

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
            if (requestCode == 2) {

                int check = data.getIntExtra("check", 10);
                if (check == 1) {

                    Toast.makeText(getApplicationContext(), "운동을 지정하셨습니다.!", Toast.LENGTH_SHORT).show();
                    finish();
                } else if (check == 2) {
                    finish();
                }
            }
        }
    }

    public void inputthread(final String name1, final String set1, final String number1) {
        new Thread(new Runnable() {
            public void run() {
                Updatetype(trainrID, scontent, name1, set1, number1);
            }
        }).start();
    }

    public void Updatetype(String trainrid, String phone, String name, String set, String number) {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("phone", phone));
        nameValuePairs.add(new BasicNameValuePair("trainr_id", trainrid));
        nameValuePairs.add(new BasicNameValuePair("name", name));
        nameValuePairs.add(new BasicNameValuePair("set", set));
        nameValuePairs.add(new BasicNameValuePair("number", number));
        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(url);
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
            httpclient.execute(httppost);
            Log.e("pass1", "connection success ");
        } catch (Exception e) {
            Log.e("Fail1", e.toString());
        }
    }

    public void pushmsg(String phone) {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("phone", phone));
        nameValuePairs.add(new BasicNameValuePair("check", "3"));

        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(url2);
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
            httpclient.execute(httppost);
            Log.e("pass1", "connection success ");
        } catch (Exception e) {
            Log.e("Fail1", e.toString());

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
                list.add(new MyCustomDTO2(false, name, content));

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

                    bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));

                    String json;
                    while ((json = bufferedReader.readLine()) != null) {
                        sb.append(json + "\n");
                    }

                    return sb.toString().trim();

                } catch (Exception e) {

                    Intent intent = new Intent(getApplicationContext(), NetworkError.class);
                    startActivity(intent);
                    return null;


                }
//
            }

            @Override
            protected void onPostExecute(String result) {
                myJSON = result;
                if (myJSON != null)
                    showList();
            }
        }
        GetDataJSON g = new GetDataJSON();
        g.execute(url);
    }
}
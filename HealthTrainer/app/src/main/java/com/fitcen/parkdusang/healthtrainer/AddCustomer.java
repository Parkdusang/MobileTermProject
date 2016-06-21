package com.fitcen.parkdusang.healthtrainer;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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
import java.util.Collections;
import java.util.Comparator;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

public class AddCustomer extends AppCompatActivity {
    String myJSON;
    String line = null;
    String name, content;
    private static final String TAG_RESULTS = "data";
    private static final String TAG_PHONE = "phone";
    private static final String TAG_ID = "name";
    private static final String TAG_SEX = "sex";
    private String url = "http://pesang72.cafe24.com/connecttranirdb.php";
    private String url2 = "http://pesang72.cafe24.com/UpdateType.php";
    private String url3 = "http://pesang72.cafe24.com/GCMservice.php";
    String trainrid;
    Boolean checkuser;

    InputStream is = null;
    JSONArray peoples = null;
    Spinner s1;
    String[] plants_arrays = {
            "전화번호", "이름"
    };
    ListView listView;
    MyCustomAdapter3 adapter3;
    ArrayList<MyCustomDTOAddCustim> list;
    Button btn2;
    EditText ed;

    int imgid;
    int i;
    int index=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_customer);
        Intent intent = getIntent();
        trainrid = intent.getStringExtra("trainrid");

        getData(url);
        btn2 = (Button) findViewById(R.id.btn1);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkuser) {
                    Toast.makeText(getApplicationContext(), "신청을 보냈습니다", Toast.LENGTH_SHORT).show();
                    Intent returnintent = new Intent();
                    for (i = 0; i < adapter3.getCount(); i++) {
                        if (list.get(i).getCheckboxt()) {
                            Log.i("testid", trainrid);
                            new Thread(new Runnable() {
                                public void run() {
                                    Updatetype(list.get(i).getContent(), trainrid);
                                }
                            }).start();
                            new Thread(new Runnable() {
                                public void run() {
                                    gcmpush(list.get(i).getContent(), trainrid);
                                }
                            }).start();
                            break;
                        }
                    }
                    setResult(Activity.RESULT_OK, returnintent);

                    finish();
                }
                else{
                    Toast.makeText(getApplicationContext(), "유저가 없습니다.", Toast.LENGTH_SHORT).show();
                    finish();
                }
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
                index = s1.getSelectedItemPosition();
                sorting(index);
                adapter3.notifyDataSetChanged();
            }

            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
        ed = (EditText)findViewById(R.id.search_name);

        ed.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String text = ed.getText().toString().toLowerCase(Locale.getDefault());
                adapter3.filter(text);
            }
        });

        list = new ArrayList<MyCustomDTOAddCustim>();
//

        listView = (ListView) findViewById(R.id.listView3);





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

    public void gcmpush(String phone , String trainerid){
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("check", "5"));
        nameValuePairs.add(new BasicNameValuePair("phone",phone));
        nameValuePairs.add(new BasicNameValuePair("trainr_id", trainerid));


        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(url3);
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs,"UTF-8"));
            httpclient.execute(httppost);
            Log.e("pass1", "connection success ");
        } catch (Exception e) {
            Log.e("Fail1", e.toString());
            Intent intent = new Intent(getApplicationContext(),NetworkError.class);
            startActivity(intent);
        }
    }



    public void Updatetype(String phone,String trainrid){
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("mode", "1"));
        nameValuePairs.add(new BasicNameValuePair("phone",phone));
        nameValuePairs.add(new BasicNameValuePair("trainr_id", trainrid));


        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(url2);
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
                sorting(index);
            }
            adapter3 =
                    new MyCustomAdapter3(
                            getApplicationContext(),
                            R.layout.list_row_addcustomer,
                            list);

            listView.setAdapter(adapter3);
            adapter3.notifyDataSetChanged();
            checkuser = true;
        } catch (JSONException e) {
            checkuser = false;
            adapter3 =
                    new MyCustomAdapter3(
                            getApplicationContext(),
                            R.layout.list_row_addcustomer,
                            list);
            listView.setAdapter(adapter3);


            list.add(new MyCustomDTOAddCustim(false, "아직 추가된 유저가 없습니다.", "", R.drawable.user1));
            list.get(0).setnullinput(true);
            adapter3.notifyDataSetChanged();
            e.printStackTrace();
        }

    }
    public void sorting(int i)
    {
        if(i==1){//이름으로 솔팅하는 것
            Collections.sort(list, new Comparator<MyCustomDTOAddCustim>() {
                @Override
                public int compare(MyCustomDTOAddCustim s1, MyCustomDTOAddCustim s2) {
                    return s1.getTitle().compareToIgnoreCase(s2.getTitle());
                }
            });
            Log.d("22", "이름으로 솔팅이됨");
            for(MyCustomDTOAddCustim p:list)
                Log.d("1111",p.getTitle());
        }
        else if(i==0){
            Collections.sort(list, new Comparator<MyCustomDTOAddCustim>() {
                @Override
                public int compare(MyCustomDTOAddCustim s1, MyCustomDTOAddCustim s2) {
                    return s1.getContent().compareToIgnoreCase(s2.getContent());
                }
            });
            Log.d("22", "전화번호로 솔팅이됨");
            for(MyCustomDTOAddCustim p:list)
                Log.d("1111",p.getContent());

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

                    return sb.toString().trim();
                } catch (Exception e) {
                    Intent intent = new Intent(getApplicationContext(),NetworkError.class);
                    startActivity(intent);
                    Log.e("Fail 2", e.toString());
                    return null;
                }
//
            }

            @Override
            protected void onPostExecute(String result) {

                myJSON = result;
                if(myJSON != null)
                showList();
            }
        }
        GetDataJSON g = new GetDataJSON();
        try {
            g.execute(url).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }
}

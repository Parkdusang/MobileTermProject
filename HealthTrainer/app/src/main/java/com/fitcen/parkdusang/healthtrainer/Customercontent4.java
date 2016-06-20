package com.fitcen.parkdusang.healthtrainer;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ScrollView;
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


public class Customercontent4 extends Fragment{
    ScrollView scr1;
    ListView listView;
    ArrayList<MyCustomDTO2> list;
    Button cc4btn;
    EditText cc4edit;
    Cc4Adapter adapter2;
    String id,myJSON;
    InputStream is = null;
    String result = null;
    String line = null;
    JSONArray peoples = null;
    String url = "http://pesang72.cafe24.com/Customercontent4.php";
    String url2 = "http://pesang72.cafe24.com/reportsubmit.php";
    String url3 = "http://pesang72.cafe24.com/GCMservice.php";

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        ViewGroup v = (ViewGroup)inflater.inflate(R.layout.customercontent4, container, false);
        scr1 = (ScrollView)v.findViewById(R.id.scrollc4);

        id = this.getArguments().getString("_id","None");
        cc4edit = (EditText)v.findViewById(R.id.cc4edit);
        cc4btn = (Button)v.findViewById(R.id.cc4btn);
        cc4btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String report="";
                for (int i = 0; i < adapter2.getCount(); i++) {
                    if (list.get(i).getCheckboxt()){
                       checkexercise(list.get(i).getTitle(),1);
                       report += list.get(i).getTitle()+"\n 세트: "+list.get(i).getexercise()+" 개수:"+list.get(i).getnumber()+"\n";
                    }


                }
                report += "- - - - - - - - - - - - - - - - - -\n"+cc4edit.getText().toString();
                checkexercise(report,2);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        pushmsg(id);
                    }
                }).start();
                Toast.makeText(getActivity(),"보고가 전송되었습니다.",Toast.LENGTH_SHORT).show();
            }
        });

        list = new ArrayList<MyCustomDTO2>();
        listView = (ListView)v.findViewById(R.id.cc4listView);
        adapter2 =
                new Cc4Adapter(
                        getActivity(),
                        R.layout.list_row_exercise,
                        list);

        listView.setAdapter(adapter2);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (list.get(position).getCheckboxt()) {
                    list.get(position).setCheckboxt(false);

                } else {
                    list.get(position).setCheckboxt(true);
                }
                adapter2.notifyDataSetChanged();

            }
        });

        listView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                scr1.requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });
        getData(url,id);

        return v;
    }
    public void pushmsg(String phone){
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("idt",id));
        nameValuePairs.add(new BasicNameValuePair("check", "4"));

        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(url3);
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs,"UTF-8"));
            httpclient.execute(httppost);
            Log.e("pass1", "connection success ");
        } catch (Exception e) {
            Log.e("Fail1", e.toString());

        }
    }

    public void checkexercise(final String name, final int check){
        new Thread(new Runnable() {
            public void run() {
                Updatetype(name,check);
            }
        }).start();
    }

    public void Updatetype(String report, int a){
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

        if(a == 1){
            Log.i("TAG11", report);
            nameValuePairs.add(new BasicNameValuePair("_id",id));
            nameValuePairs.add(new BasicNameValuePair("exercise",report));
        }
        else{
            Log.i("TAG22", report);
            nameValuePairs.add(new BasicNameValuePair("_id",id));
            nameValuePairs.add(new BasicNameValuePair("report",report));
        }

        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(url2);
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs,"UTF-8"));
            httpclient.execute(httppost);
            Log.e("pass1", "connection success ");
        } catch (Exception e) {
            Log.e("Fail1", e.toString());
            Toast.makeText(getActivity(), "네트워크 연결이 되지않습니다.",
                    Toast.LENGTH_LONG).show();
        }
    }


    protected void showList() {
        try {
            JSONObject jsonObj = new JSONObject(myJSON);
            peoples = jsonObj.getJSONArray("exercise");

            for (int i = 0; i < peoples.length(); i++) {
                JSONObject c = peoples.getJSONObject(i);

                String name = c.getString("exercise");
                String content = c.getString("content");
                int set = c.getInt("ECset");
                int number = c.getInt("ECnumber");
                int check = c.getInt("checkbl");

                if(check == 1){
                    list.add(new MyCustomDTO2(true,name, content));
                }
                else{
                    list.add(new MyCustomDTO2(false,name, content));
                }

                list.get(i).setexercise(set);
                list.get(i).setnumber(number);
            }
            adapter2.notifyDataSetChanged();

        } catch (JSONException e) {

            list.add(new MyCustomDTO2(false,"아직 입력사항이 없습니다", ""));
            list.get(0).setnullinput(true);
            adapter2.notifyDataSetChanged();
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
                    Intent intent = new Intent(getActivity(),NetworkError.class);
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
                //showList2();
            }
        }


        GetDataJSON g = new GetDataJSON();
        g.execute(url, id);
    }
}
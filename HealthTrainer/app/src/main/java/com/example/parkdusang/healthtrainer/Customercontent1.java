package com.example.parkdusang.healthtrainer;

import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
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


public class Customercontent1 extends Fragment {
    ListView cc1listView;
    Button cc1btn;
    ArrayList<MyCustomDTO2> cc1list;
    ProgressBar cc1pBar;
    TextView cc1txt1, cc1txt2, oneword;
    Cc1Adapter cc1adapter;
    Cc1Diaglog cc1Dialog;
    String crtWeight, stWeight, goalWeight;
    String id, myJSON;
    InputStream is = null;
    String result = null;
    String line = null;
    JSONArray peoples = null;
    String url = "http://pesang72.cafe24.com/Customercontent1.php";

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup v = (ViewGroup) inflater.inflate(R.layout.customercontent1, container, false);

        cc1listView = (ListView) v.findViewById(R.id.cc1listView);

        cc1txt1 = (TextView) v.findViewById(R.id.cc1txt1);
        cc1txt2 = (TextView) v.findViewById(R.id.cc1txt2);
        oneword = (TextView) v.findViewById(R.id.trainr_oneword);

        id = this.getArguments().getString("_id", "None");


        cc1pBar = (ProgressBar) v.findViewById(R.id.cc1pBar);

        cc1btn = (Button) v.findViewById(R.id.cc1btn);


        cc1Dialog = new Cc1Diaglog(getActivity());
        cc1Dialog.setTitle("목표를 입력해주세요!");
        //다이어로그를 열어서 입력한 값을 얻어오는곳
        //이것도 디비에 저장해둬야 할듯
        //아무튼 앱을 껏다가 다시켜도 다시 이 몸무게 정보가 보일 수 있도록
        //현재 몸무게는 계속 수정할 수 있도록

        cc1Dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                if (cc1Dialog.checkcancel()) {
                    goalWeight = cc1Dialog.getedtxString1();
                    crtWeight = cc1Dialog.getedtxString2();

                    if (goalWeight == null) {
                        goalWeight = "";
                    }
                    if (crtWeight == null) {
                        crtWeight = "";
                    }
                    if (goalWeight.equals("") || crtWeight.equals("")) {
                        Toast.makeText(getActivity(), "입력이 잘못되었습니다.", Toast.LENGTH_SHORT).show();
                    } else {
                        cc1Dialog.setGoalWeight(goalWeight);
                        cc1Dialog.setCurrentWeight(crtWeight);

                        cc1txt2.setText(goalWeight);
                        cc1pBar.setProgress((int) goalRate(crtWeight, cc1Dialog.getStartWeight(), goalWeight));

                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                Updatetype(goalWeight, crtWeight);
                            }
                        }).start();
                    }
                }
            }
        });

        cc1Dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                cc1btn.setText("목표 설정하시겠어요?");

            }
        });

        cc1btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cc1Dialog.show();
            }
        });
        cc1list = new ArrayList<MyCustomDTO2>();
        cc1adapter = new Cc1Adapter(getActivity(), R.layout.list_row_exercise, cc1list);

        cc1listView.setAdapter(cc1adapter);
        Log.i("TAG", "1: ");
        getData(url, id);
        return v;
    }

    private float goalRate(String c, String s, String g) {
        float cw, sw, gw;
        float result = 0;


        cw = Float.parseFloat(c); // 현재
        sw = Float.parseFloat(s); // 시작
        gw = Float.parseFloat(g); // 목표

        if (sw < gw) { // 시작이 작을때
            if (cw <= gw && cw >= sw) {
                result = ((cw - sw) / (gw - sw)) * 100;
            } else
                result = 1; //범위 밖에 있을 때
        } else if (sw > gw) { // 시작이 더 클때
            if (cw >= gw && cw <= sw) {
                result = ((sw - cw) / (sw - gw)) * 100;
            }
            else
                result = 100;
        }
        else {
            result = 100;
        }

        return result;
    }

    public void Updatetype(String goal, String current){
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("_id",id));
        nameValuePairs.add(new BasicNameValuePair("check","2"));
        nameValuePairs.add(new BasicNameValuePair("goal",goal));
        nameValuePairs.add(new BasicNameValuePair("current",current));

        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(url);
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            httpclient.execute(httppost);
            Log.e("pass1", "connection success ");
        } catch (Exception e) {
            Log.e("Fail1", e.toString());
            Toast.makeText(getActivity(), "Invalid IP Address",
                    Toast.LENGTH_LONG).show();
        }
    }

    protected void showList() {
        try {
            JSONObject jsonObj = new JSONObject(myJSON);
            peoples = jsonObj.getJSONArray("data");

            for (int i = 0; i < peoples.length(); i++) {
                JSONObject c = peoples.getJSONObject(i);


                //TODO 여기부분을 원하는 값으로 넣고 프로그레스바 움직이면됌
                cc1txt1.setText(c.getDouble("weight") + "");
                cc1txt2.setText(c.getDouble("goalweight") + ""); // 목표몸무게);
                crtWeight = c.getDouble("presentweight") + "";// 현제 몸무게
                cc1pBar.setProgress((int) goalRate(crtWeight, c.getDouble("weight") + "", c.getDouble("goalweight") + ""));

                cc1Dialog.setStartWeight(c.getDouble("weight") + "");
                cc1Dialog.setGoalWeight(c.getDouble("goalweight") + "");
                cc1Dialog.setCurrentWeight(c.getDouble("presentweight") + "");
            }

            peoples = jsonObj.getJSONArray("info");

            for (int i = 0; i < peoples.length(); i++) {
                JSONObject c = peoples.getJSONObject(i);
                if (c.getString("trainr").equals("")) {
                    oneword.setText("아직 입력사항이 없습니다.");
                } else {
                    oneword.setText(c.getString("trainr"));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            JSONObject jsonObj = new JSONObject(myJSON);
            peoples = jsonObj.getJSONArray("exercise");

            for (int i = 0; i < peoples.length(); i++) {
                JSONObject c = peoples.getJSONObject(i);

                String name = c.getString("exercise");
                String content = c.getString("content");
                int set = c.getInt("ECset");
                int number = c.getInt("ECnumber");
                cc1list.add(new MyCustomDTO2(false, name, content));
                cc1list.get(i).setexercise(set);
                cc1list.get(i).setnumber(number);
            }
            cc1adapter.notifyDataSetChanged();
        }
        catch (JSONException e) {
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
                nameValuePairs.add(new BasicNameValuePair("check", "1"));
                nameValuePairs.add(new BasicNameValuePair("_id", ids));

                Log.i("TAG", "2: ");
                try {
                    HttpClient httpclient = new DefaultHttpClient();
                    HttpPost httppost = new HttpPost(uri);
                    httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
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
                    Log.i("TAG", "3: ");
                    return sb.toString().trim();
                } catch (Exception e) {
                    Log.e("Fail 2", e.toString());
                    return null;
                }

            }

            @Override
            protected void onPostExecute(String result) {
                myJSON = result;
                showList();
            }
        }


        GetDataJSON g = new GetDataJSON();
        g.execute(url, id);
    }
}
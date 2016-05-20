package com.example.parkdusang.healthtrainer;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


public class Customercontent3 extends Fragment{

    TextView cc3Txt1,cc3Txt2,cc3Txt3,cc3Txt4,cc3Txt5,cc3Txt6;
    ProgressBar cc3Pbar1,cc3Pbar2,cc3Pbar3,cc3Pbar4,cc3Pbar5,cc3Pbar6;
    String id;
    String url = "http://pesang72.cafe24.com/Customercontent3.php";
    String myJSON;
    JSONArray peoples;
    double height,muscle,bodyfat,weight;
    int barValue[]={1,2,3,4,5,6};
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ViewGroup v = (ViewGroup)inflater.inflate(R.layout.customercontent3, container, false);
        id = this.getArguments().getString("_id","None");
        cc3Txt1=(TextView)v.findViewById(R.id.cc3Text1); //디비 연동해서 얻어온 값을 setText로 열어주게한다
        cc3Txt2=(TextView)v.findViewById(R.id.cc3Text2);
        cc3Txt3=(TextView)v.findViewById(R.id.cc3Text3);
        cc3Txt4=(TextView)v.findViewById(R.id.cc3Text4);
        cc3Txt5=(TextView)v.findViewById(R.id.cc3Text5);
        cc3Txt6=(TextView)v.findViewById(R.id.cc3Text6);

        cc3Pbar1=(ProgressBar)v.findViewById(R.id.cc3Bar1);
        cc3Pbar2=(ProgressBar)v.findViewById(R.id.cc3Bar2);
        cc3Pbar3=(ProgressBar)v.findViewById(R.id.cc3Bar3);
        cc3Pbar4=(ProgressBar)v.findViewById(R.id.cc3Bar4);
        cc3Pbar5=(ProgressBar)v.findViewById(R.id.cc3Bar5);
        cc3Pbar6=(ProgressBar)v.findViewById(R.id.cc3Bar6);

        cc3Pbar1.setProgress(1);
        cc3Pbar2.setProgress(2);
        cc3Pbar3.setProgress(3);
        cc3Pbar4.setProgress(4);
        cc3Pbar5.setProgress(5);
        cc3Pbar6.setProgress(6);
        getData(url,id);
        return v;

    }

    protected void saveList() {
        try {
            JSONObject jsonObj = new JSONObject(myJSON);
            peoples = jsonObj.getJSONArray("data");

            for (int i = 0; i < peoples.length(); i++) {
                JSONObject c = peoples.getJSONObject(i);
                String name = c.getString("name"); // 이름
                height = c.getDouble("height"); // 키
                muscle = c.getDouble("muscle"); // 근육
                bodyfat = c.getDouble("bodyfat"); // 지방
                weight = c.getDouble("weight"); // 몸무게

            }

        } catch (JSONException e) {
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

            }
            @Override
            protected void onPostExecute(String result) {
                myJSON = result;
                saveList();
            }
        }


        GetDataJSON g = new GetDataJSON();
        g.execute(url,id);
    }

}
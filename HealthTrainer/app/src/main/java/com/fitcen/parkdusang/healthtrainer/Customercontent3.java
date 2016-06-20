package com.fitcen.parkdusang.healthtrainer;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
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
import java.util.concurrent.ExecutionException;


public class Customercontent3 extends Fragment {

    int bar1rate, bar2rate, bar3rate, bar4rate, bar5rate;

    TextView cc3Txt1, cc3Txt2, cc3Txt3, cc3Txt4, cc3Txt5;
    ProgressBar cc3Pbar1, cc3Pbar2, cc3Pbar3, cc3Pbar4, cc3Pbar5;

    String id;
    String url = "http://pesang72.cafe24.com/Customercontent3.php";
    String myJSON;
    String result = null;
    String line = null;

    InputStream is = null;
    JSONArray peoples;
    double height, muscle, bodyfat, weight, BMI, persentfat;


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ViewGroup v = (ViewGroup) inflater.inflate(R.layout.customercontent3, container, false);
        id = this.getArguments().getString("_id", "None");
        cc3Txt1 = (TextView) v.findViewById(R.id.cc3Text1); //디비 연동해서 얻어온 값을 setText로 열어주게한다
        cc3Txt2 = (TextView) v.findViewById(R.id.cc3Text2);
        cc3Txt3 = (TextView) v.findViewById(R.id.cc3Text3);
        cc3Txt4 = (TextView) v.findViewById(R.id.cc3Text4);
        cc3Txt5 = (TextView) v.findViewById(R.id.cc3Text5);


        cc3Pbar1 = (ProgressBar) v.findViewById(R.id.cc3Bar1);
        cc3Pbar2 = (ProgressBar) v.findViewById(R.id.cc3Bar2);
        cc3Pbar3 = (ProgressBar) v.findViewById(R.id.cc3Bar3);
        cc3Pbar4 = (ProgressBar) v.findViewById(R.id.cc3Bar4);
        cc3Pbar5 = (ProgressBar) v.findViewById(R.id.cc3Bar5);

        getData(url, id);

        return v;

    }

    public void showProgress() {

    }

    int sex = 0;

    protected void saveList() {
        try {
            JSONObject jsonObj = new JSONObject(myJSON);
            peoples = jsonObj.getJSONArray("data");

            for (int i = 0; i < peoples.length(); i++) {
                JSONObject c = peoples.getJSONObject(i);
                if (i == 0) {
                    String name = c.getString("name"); // 이름
                    height = c.getDouble("height"); // 키
                    muscle = c.getDouble("muscle"); // 근육
                    bodyfat = c.getDouble("bodyfat"); // 지방
                    weight = c.getDouble("weight"); // 몸무게
                    persentfat = c.getDouble("persentfat"); // 지방률
                    BMI = c.getDouble("BMI"); //M
                } else {
                    sex = c.getInt("sex");
                }

            }
            int fatRate = (int) ((bodyfat / weight) * 100);
//남자 1 여자 2
            cc3Txt1.setText("" + weight);
            cc3Txt2.setText("" + muscle);
            cc3Txt3.setText("" + bodyfat);
            cc3Txt4.setText("" + fatRate);
            cc3Txt5.setText("" + (int) ((weight / (height * height)) * 10000));

            if (sex == 1) {//남성일 경우
                cc3Pbar1.setProgress((int) (weight - height) + 124);//
                int muscleAmount=(int)((muscle/weight)*100);
                int fatAmount=(int)((bodyfat/weight)*100);

                if(muscleAmount>=40||muscleAmount<=50)
                    cc3Pbar2.setProgress((int)(muscleAmount*1.2));
                else if(muscleAmount<40)
                    cc3Pbar2.setProgress((int)(muscleAmount*0.75));
                else
                    cc3Pbar2.setProgress((int)(muscleAmount*1.2));

                if(fatAmount>=10||fatAmount<=20) {
                    cc3Pbar3.setProgress(fatAmount*3);
                    cc3Pbar4.setProgress(fatAmount*3);
                }
                else if(fatAmount<10) {
                    cc3Pbar3.setProgress(fatAmount*3);
                    cc3Pbar4.setProgress(fatAmount*3);
                }
                else {
                    cc3Pbar3.setProgress(fatAmount*3);
                    cc3Pbar4.setProgress(fatAmount*3);
                }

                cc3Pbar5.setProgress((int) ((weight / (height * height)) * 10000) - 13);//
            }
            else
            {
                cc3Pbar1.setProgress((int) (weight - height) + 124);//
                int muscleAmount=(int)((muscle/weight)*100);
                int fatAmount=(int)((bodyfat/weight)*100);

                if(muscleAmount>=34||muscleAmount<=44)
                    cc3Pbar2.setProgress((int)(muscleAmount*1.36));
                else if(muscleAmount<34)
                    cc3Pbar2.setProgress((int)(muscleAmount*0.88));
                else
                    cc3Pbar2.setProgress((int)(muscleAmount*1.36));

                if(fatAmount>=18||fatAmount<=28) {
                    cc3Pbar3.setProgress((int)(fatAmount*2.1));
                    cc3Pbar4.setProgress((int)(fatAmount*2.1));
                }
                else if(fatAmount<18) {
                    cc3Pbar3.setProgress((int)(fatAmount*1.7));
                    cc3Pbar4.setProgress((int)(fatAmount*1.7));
                }
                else {
                    cc3Pbar3.setProgress((int)(fatAmount*2.06));
                    cc3Pbar4.setProgress((int)(fatAmount*2.06));
                }

                cc3Pbar5.setProgress((int) ((weight / (height * height)) * 10000) - 13);//
            }
            Log.d("97979219", height + " " + weight + " " + muscle + " " + bodyfat + " " + persentfat + " " + BMI + " ");
            int tmp = (int) (weight - height) + 124;
            int tmp1 = (int) (muscle / weight * 100) - 25;
            int tmp2 = (int) (bodyfat / weight * 100) - 9;
            int tmp3 = (int) (weight / (height * height) * 10000) - 13;
            Log.d("97979219", tmp + " " + tmp1 + " " + tmp2 + " " + tmp3);
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

                    return sb.toString().trim();
                } catch (Exception e) {
                    Log.e("Fail 2", e.toString());
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
        try {
            g.execute(url, id).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }


}
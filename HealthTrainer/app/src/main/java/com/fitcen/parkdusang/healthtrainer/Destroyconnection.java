package com.fitcen.parkdusang.healthtrainer;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;


public class Destroyconnection extends IntentService {

    String url = "http://pesang72.cafe24.com/destroyconnection.php";

    public Destroyconnection() {
        super("Destroyconnection");
    }

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */


    @Override
    protected void onHandleIntent(Intent intent) {
        Log.i("TAG", "deletedt: ");
        if (intent != null) {
            String check  =intent.getStringExtra("check");

            if(check.equals("1")){
                final String t_id = intent.getStringExtra("id");
                final String c_name = intent.getStringExtra("name");
                final String c_phone = intent.getStringExtra("phone");
                Log.i("TAG", "deletedt: "+ t_id + " "+ c_name+" " + c_phone);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        deletedt(t_id,c_name,c_phone);
                    }
                }).start();

            }
            else if(check.equals("2")){
                Log.i("TAG", "deleted2222222t: ");
                final String t_id = intent.getStringExtra("id");
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        deletedc(t_id);
                    }
                }).start();
            }

        }
    }
    // 트레이너용 연결해제
    public void deletedt(String id, String name, String phone){
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        Log.i("TAG", "deletedt: "+ id + " "+ name+" " + phone);
        nameValuePairs.add(new BasicNameValuePair("check","1"));
        nameValuePairs.add(new BasicNameValuePair("id",id));
        nameValuePairs.add(new BasicNameValuePair("name",name));
        nameValuePairs.add(new BasicNameValuePair("phone",phone));

        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(url);
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs,"UTF-8"));
            httpclient.execute(httppost);
            Log.e("pass1", "connection success ");
        } catch (Exception e) {
            Log.e("Fail1", e.toString());

        }
    }

    //고객용 연결해제
    public void deletedc(String id){
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

        nameValuePairs.add(new BasicNameValuePair("check","2"));
        nameValuePairs.add(new BasicNameValuePair("id",id));
        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(url);
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs,"UTF-8"));
            httpclient.execute(httppost);
            Log.e("pass1", "connection success ");
        } catch (Exception e) {
            Log.e("Fail1", e.toString());

        }
    }
}

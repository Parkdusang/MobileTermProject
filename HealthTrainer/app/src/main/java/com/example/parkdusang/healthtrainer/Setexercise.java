package com.example.parkdusang.healthtrainer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class Setexercise extends AppCompatActivity {
    ListView listView;
    ArrayList<MyCustomDTO2> list;
    Button btnok,btnnewec;
    MyCustomAdapter2 adapter2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setexercise);
        btnok = (Button)findViewById(R.id.setexercisebtn);
        btnok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent returnintent = new Intent();
                String str="";
                for (int i = 0; i < adapter2.getCount(); i++) {
                    if (list.get(i).getCheckboxt())
                        str += list.get(i).getTitle() + " ";

                }
                returnintent.putExtra("name",str);
                setResult(Activity.RESULT_OK, returnintent);
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
        list.add(new MyCustomDTO2(false,"달리기", "겁나 달려!", R.drawable.exersice));
        list.add(new MyCustomDTO2(false,"달리기", "겁나 달려!", R.drawable.exersice));
        list.add(new MyCustomDTO2(false,"달리기", "겁나 달려!", R.drawable.exersice));

        listView = (ListView)findViewById(R.id.listView2);

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
                    Log.i("TAG", "onItemClick: "+list.get(position).getCheckboxt());
                }

                else {
                    list.get(position).setCheckboxt(true);
                    Log.i("TAG", "onItemClick: "+list.get(position).getCheckboxt());
                }
                adapter2.notifyDataSetChanged();


            }
        });
    }
}

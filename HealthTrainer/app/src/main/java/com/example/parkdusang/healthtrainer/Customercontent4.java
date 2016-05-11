package com.example.parkdusang.healthtrainer;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;


public class Customercontent4 extends Fragment{

    ListView cc4listView;
    Button cc4btn;
    ArrayList<MyCustomDTO2> cc4list;
    Cc4Adapter cc4adapter;
    String id;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        ViewGroup v = (ViewGroup)inflater.inflate(R.layout.customercontent4, container, false);

        id = this.getArguments().getString("_id","None");
        cc4listView = (ListView)v.findViewById(R.id.cc4listView);
        cc4list=new ArrayList<MyCustomDTO2>();
        //디비 연동할 부분

        cc4list.add(new MyCustomDTO2(false,"달리기", "겁나 달려!", R.drawable.exersice));
        cc4list.add(new MyCustomDTO2(false,"달리기", "겁나 달려!", R.drawable.exersice));
        cc4list.add(new MyCustomDTO2(false,"달리기", "겁나 달려!", R.drawable.exersice));
        cc4list.add(new MyCustomDTO2(false,"달리기", "겁나 달려!", R.drawable.exersice));
        cc4list.add(new MyCustomDTO2(false,"달리기", "겁나 달려!", R.drawable.exersice));
        cc4adapter=new Cc4Adapter(getActivity(),R.layout.cc4_list_row,cc4list);

        cc4listView.setAdapter(cc4adapter);
        return v;
    }
}
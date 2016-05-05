package com.example.parkdusang.healthtrainer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by yooyj on 2016-05-04.
 */
public class Cc1Adapter extends BaseAdapter {
    Context ctx;
    int layout;
    ArrayList<MyCustomDTO> list;
    LayoutInflater inf;

    public Cc1Adapter(Context ctx, int layout, ArrayList<MyCustomDTO> list){
        this.ctx = ctx;
        this.layout = layout;
        this.list = list;

        inf = (LayoutInflater)ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if( convertView == null){
            convertView = inf.inflate(layout, null);
        }

        TextView txtTitle = (TextView)convertView.findViewById(R.id.cc1listtxt1);
        TextView txtContent = (TextView)convertView.findViewById(R.id.cc1listtxt2);
        ImageView imgIcon =  (ImageView)convertView.findViewById(R.id.cc1listimg);

        MyCustomDTO dto = list.get(position);
        txtTitle.setText(dto.getTitle());
        txtContent.setText(dto.getContent());
        imgIcon.setImageResource(dto.getImgIcon());


        return convertView;
    }
}

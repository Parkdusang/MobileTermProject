package com.example.parkdusang.healthtrainer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by yooyj on 2016-05-02.
 */
public class Cc4Adapter extends BaseAdapter{
    Context ctx;
    int layout;
    ArrayList<MyCustomDTO2> list;
    LayoutInflater inf;

    public Cc4Adapter(Context ctx, int layout, ArrayList<MyCustomDTO2> list){
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

        TextView txtTitle = (TextView)convertView.findViewById(R.id.cc4listtext1);
        TextView txtContent = (TextView)convertView.findViewById(R.id.cc4listtext2);
        ImageView imgIcon =  (ImageView)convertView.findViewById(R.id.cc4listimg);
        CheckBox box = (CheckBox)convertView.findViewById(R.id.cc4listcheck);

        MyCustomDTO2 dto = list.get(position);
        txtTitle.setText(dto.getTitle());
        txtContent.setText(dto.getContent());
        imgIcon.setImageResource(dto.getImgIcon());

        if(dto.getCheckboxt()) {
            box.setChecked(true);
        }
        else{
            box.setChecked(false);

        }
        return convertView;
    }
}

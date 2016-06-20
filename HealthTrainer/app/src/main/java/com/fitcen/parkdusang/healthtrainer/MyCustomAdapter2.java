package com.fitcen.parkdusang.healthtrainer;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;

public class MyCustomAdapter2 extends BaseAdapter {

    Context ctx;
    int layout;
    ArrayList<MyCustomDTO2> list;
    LayoutInflater inf;

    public MyCustomAdapter2(Context ctx, int layout, ArrayList<MyCustomDTO2> list){
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
        TextView txtset = (TextView)convertView.findViewById(R.id.exersettx);
        TextView txtnumber = (TextView)convertView.findViewById(R.id.exernumbertx);

        TextView set = (TextView)convertView.findViewById(R.id.exerset);
        TextView number = (TextView)convertView.findViewById(R.id.exernumber);

        TextView txtTitle = (TextView)convertView.findViewById(R.id.exertextview1);
        TextView txtContent = (TextView)convertView.findViewById(R.id.exertextview2);
        CheckBox box = (CheckBox)convertView.findViewById(R.id.checkexercise);

        MyCustomDTO2 dto = list.get(position);
        txtTitle.setText(dto.getTitle());
        txtContent.setText(dto.getContent());



        if(dto.getCheckboxt()) {
            box.setChecked(true);
            txtset.setVisibility(View.VISIBLE);
            txtnumber.setVisibility(View.VISIBLE);
            set.setVisibility(View.VISIBLE);
            set.setText(dto.getexercise());
            number.setVisibility(View.VISIBLE);
            number.setText(dto.getnumber());
        }
        else{
            box.setChecked(false);
            txtset.setVisibility(View.INVISIBLE);
            txtnumber.setVisibility(View.INVISIBLE);

            set.setVisibility(View.INVISIBLE);
            number.setVisibility(View.INVISIBLE);

        }
        return convertView;
    }

}

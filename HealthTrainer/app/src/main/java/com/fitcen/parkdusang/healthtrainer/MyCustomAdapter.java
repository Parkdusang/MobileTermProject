package com.fitcen.parkdusang.healthtrainer;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class MyCustomAdapter extends BaseAdapter {

    Context ctx;
    int layout;
    ArrayList<MyCustomDTO> list;
    LayoutInflater inf;
    boolean input = true;

    public MyCustomAdapter(Context ctx, int layout, ArrayList<MyCustomDTO> list) {
        this.ctx = ctx;
        this.layout = layout;
        this.list = list;

        inf = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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

    public void setinput(boolean input) {
        this.input = input;
    }
    public boolean getinput() {
        return input;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = inf.inflate(layout, null);
        }
        TextView txtTitle = (TextView) convertView.findViewById(R.id.txtTitle);
        TextView txtContent = (TextView) convertView.findViewById(R.id.txtContent);
        ImageView imgIcon = (ImageView) convertView.findViewById(R.id.imgIcon);

        MyCustomDTO dto = list.get(position);

        txtTitle.setText(dto.getTitle());
        if (input) {
            int len = dto.getContent().length();
            String result1 = dto.getContent().substring(0, len - 5);
            String encript = result1 + "*****";
            txtContent.setText(encript);
            imgIcon.setImageResource(dto.getImgIcon());
        }
        return convertView;
    }

}

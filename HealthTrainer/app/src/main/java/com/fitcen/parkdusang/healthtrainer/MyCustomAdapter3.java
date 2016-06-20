package com.fitcen.parkdusang.healthtrainer;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;

public class MyCustomAdapter3 extends BaseAdapter {

    Context ctx;
    int layout;
    ArrayList<MyCustomDTOAddCustim> templist;
    ArrayList<MyCustomDTOAddCustim> list =null;
    LayoutInflater inf;

    public MyCustomAdapter3(Context ctx, int layout, ArrayList<MyCustomDTOAddCustim> list){
        this.ctx = ctx;
        this.layout = layout;
        this.list = list;
        this.templist = new ArrayList<MyCustomDTOAddCustim>();
        this.templist.addAll(list);
        Log.i("d", "MyCustomAdapter3: "+templist.size() + " " + this.list.size() + " "+ list.size());
        inf = (LayoutInflater)ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        //Log.i("TAG1", "getCount: "+list.size());
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
        View v = convertView;
        if( v == null){
            v = inf.inflate(layout, null);
        }

        TextView txtTitle = (TextView)v.findViewById(R.id.txtTitle3);
        TextView txtContent = (TextView) v.findViewById(R.id.txtContent3);
        ImageView imgIcon =  (ImageView)v.findViewById(R.id.imgIcon3);
        CheckBox box = (CheckBox)v.findViewById(R.id.checkbox12);

        MyCustomDTOAddCustim dto = list.get(position);

        if(!dto.getnullinput()) {

            int len = dto.getContent().length();
            String result1 = dto.getContent().substring(0, len - 5);
            String encript =result1+ "*****";

            txtTitle.setText(dto.getTitle());
            txtContent.setText(encript);
            imgIcon.setImageResource(dto.getImgIcon());
            if (dto.getCheckboxt())
                box.setChecked(true);
            else {
                box.setChecked(false);
            }
        }

        return v;
    }


    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        Log.i("TAG", "text:"+charText);
        list.clear();
        Log.i("TAG", "charTextlength:" + charText.length());
        if (charText.length() == 0) {
            list.addAll(templist);
        }
        else {
            Log.i("TAG", "come!:"+templist.size());
            for (MyCustomDTOAddCustim wpt : templist) {
                Log.i("TAG", "get:"+ wpt.getTitle());
                if (wpt.getTitle().toLowerCase(Locale.getDefault()).contains(charText)) {
                    list.add(wpt);
                }
            }
        }
        notifyDataSetChanged();
    }


}

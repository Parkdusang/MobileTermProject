package com.example.parkdusang.healthtrainer;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;


public class Customercontent3 extends Fragment{

    TextView cc3Txt1,cc3Txt2,cc3Txt3,cc3Txt4,cc3Txt5,cc3Txt6;
    ProgressBar cc3Pbar1,cc3Pbar2,cc3Pbar3,cc3Pbar4,cc3Pbar5,cc3Pbar6;


    int barValue[]={1,2,3,4,5,6};
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ViewGroup v = (ViewGroup)inflater.inflate(R.layout.customercontent3, container, false);

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

        return v;

    }


}
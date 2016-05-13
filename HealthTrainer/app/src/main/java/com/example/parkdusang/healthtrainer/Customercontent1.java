package com.example.parkdusang.healthtrainer;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;


public class Customercontent1 extends Fragment {
    ListView cc1listView;
    Button cc1btn;
    ArrayList<MyCustomDTO> cc1list;
    ProgressBar cc1pBar;
    TextView cc1txt1, cc1txt2;
    Cc1Adapter cc1adapter;
    Cc1Diaglog cc1Dialog;
    String crtWeight, stWeight, goalWeight;
    String id;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup v = (ViewGroup) inflater.inflate(R.layout.customercontent1, container, false);

        cc1listView = (ListView) v.findViewById(R.id.cc1listView);

        cc1txt1 = (TextView) v.findViewById(R.id.cc1txt1);
        cc1txt2 = (TextView) v.findViewById(R.id.cc1txt2);


        id = this.getArguments().getString("_id", "None");


        cc1pBar = (ProgressBar) v.findViewById(R.id.cc1pBar);

        cc1btn = (Button) v.findViewById(R.id.cc1btn);
        cc1Dialog = new Cc1Diaglog(getActivity());
        cc1Dialog.setTitle("몸무게를 입력해주세요!");
        //다이어로그를 열어서 입력한 값을 얻어오는곳
        //이것도 디비에 저장해둬야 할듯
        //아무튼 앱을 껏다가 다시켜도 다시 이 몸무게 정보가 보일 수 있도록
        //현재 몸무게는 계속 수정할 수 있도록

        cc1Dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {

                stWeight = cc1Dialog.getStartWeight();
                goalWeight = cc1Dialog.getGoalWeight();
                crtWeight = cc1Dialog.getCurrentWeight();


                if (crtWeight != null && stWeight != null && goalWeight != null) {
                    if (goalRate(crtWeight, stWeight, goalWeight) == 1) {
                        cc1txt1.setText(cc1Dialog.getStartWeight() + "kg");
                        cc1txt2.setText(cc1Dialog.getGoalWeight() + "kg");
                        cc1btn.setText("범위를 넘어섰어요 ㅠㅠ");
                        cc1btn.setText("현재 몸무게" + crtWeight);
                        cc1pBar.setProgress(0);
                    } else if (goalRate(crtWeight, stWeight, goalWeight) == 2) {
                        cc1Dialog.startweight.setText("");
                        cc1Dialog.goalweight.setText("");
                        cc1Dialog.currentweight.setText("");
                    } else {
                        cc1txt1.setText(cc1Dialog.getStartWeight() + "kg");
                        cc1txt2.setText(cc1Dialog.getGoalWeight() + "kg");
                        cc1btn.setText("현재 몸무게" + crtWeight);
                        cc1pBar.setProgress((int) goalRate(crtWeight, stWeight, goalWeight));
                    }
                }
            }
        });

        cc1Dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                cc1btn.setText("취소하셨어요 다시 입력하시겠어요?");
            }
        });

        cc1btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cc1Dialog.show();
            }
        });

        cc1list = new ArrayList<MyCustomDTO>();
        //디비 연동할부분

        cc1list.add(new MyCustomDTO("달리기", "겁나 달려!", 0));
        cc1list.add(new MyCustomDTO("달리기", "겁나 달려!", 0));
        cc1list.add(new MyCustomDTO("달리기", "겁나 달려!", 0));
        cc1list.add(new MyCustomDTO("달리기", "겁나 달려!", 0));
        cc1list.add(new MyCustomDTO("달리기", "겁나 달려!", 0));
        cc1adapter = new Cc1Adapter(getActivity(), R.layout.cc1_list_row, cc1list);

        cc1listView.setAdapter(cc1adapter);
        return v;
    }

    private float goalRate(String c, String s, String g) {
        float cw, sw, gw;
        float result = 0;
        if (c.equals(null) || s.equals(null) || g.equals(null))
            result = 2;
        else {
            cw = Float.parseFloat(c);
            sw = Float.parseFloat(s);
            gw = Float.parseFloat(g);

            if (sw < gw) {
                if (cw <= gw && cw >= sw) {
                    result = ((cw - sw) / (gw - sw)) * 100;
                } else
                    result = 1; //범위 밖에 있을 때
            } else if (sw > gw) {
                if (cw >= gw && cw <= sw) {
                    result = ((cw - gw) / (sw - gw)) * 100;
                } else
                    result = 1;
            }
        }
        return result;
    }

    public void testid() {

    }
}
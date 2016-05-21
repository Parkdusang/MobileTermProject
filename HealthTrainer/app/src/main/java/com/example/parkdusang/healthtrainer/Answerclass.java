package com.example.parkdusang.healthtrainer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ScrollView;

public class Answerclass extends AppCompatActivity {

    ScrollView repo_scroll,edit_scroll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answerclass);
        edit_scroll=(ScrollView)findViewById(R.id.edit_scroll);
        repo_scroll=(ScrollView)findViewById(R.id.repo_scroll);


        edit_scroll.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_UP)
                    repo_scroll.requestDisallowInterceptTouchEvent(false);
                else
                    repo_scroll.requestDisallowInterceptTouchEvent(true);

                return false;
            }
        });

    }
}

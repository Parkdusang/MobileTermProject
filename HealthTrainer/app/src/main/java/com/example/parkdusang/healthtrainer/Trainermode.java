package com.example.parkdusang.healthtrainer;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class Trainermode extends AppCompatActivity {
    // 처음 트레이너 모드로 로그인하면 들어오는 모드 list view 로 보여준다.
    ListView listView;
    FloatingActionButton btn;
    ArrayList<MyCustomDTO> list;
    MyCustomAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trainermode);
        btn = (FloatingActionButton)findViewById(R.id.fab);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myAct1 = new Intent(getApplicationContext(), AddCustomer.class);
                startActivityForResult(myAct1,1);

            }
        });
        // 이부분을 db 불러와서 저장해야됌 ㅇㅇ 겁나 빡실거임
         list = new ArrayList<MyCustomDTO>();
        list.add(new MyCustomDTO("홍길동", "홍길동 테스트!", R.drawable.user1));
        list.add(new MyCustomDTO("영희", "영희 테스트!", R.drawable.user2));
        list.add(new MyCustomDTO("철수", "철수 테스트!", R.drawable.user1));
        list.add(new MyCustomDTO("마동석", "마동석 테스트!", R.drawable.user1));
        list.add(new MyCustomDTO("루피", "루피 테스트", R.drawable.user1));
        list.add(new MyCustomDTO("원펀맨", "원펀맨 테스트", R.drawable.user1));
        list.add(new MyCustomDTO("아몰랑", "아몰랑 테스트", R.drawable.user2));

        listView = (ListView)findViewById(R.id.listView1);

        adapter = new MyCustomAdapter(
                        getApplicationContext(),
                        R.layout.list_row,
                        list);

        listView.setAdapter(adapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent myAct = new Intent (getApplicationContext(), CustomerInformation.class);
                String title  =list.get(position).getTitle().toString();
                String content  =list.get(position).getContent().toString();
                int imgid  =list.get(position).getImgIcon();
                myAct.putExtra("imgid",imgid);
                myAct.putExtra("title1",title);
                myAct.putExtra("content1",content);
                startActivity(myAct);
            }
        });

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) // 액티비티가 정상적으로 종료되었을 경우
        {
            if (requestCode == 1) //  CreateActivity에서 호출한 경우에만 처리
            {

                String name = data.getStringExtra("name");
                String content = data.getStringExtra("content");
                int imgid = data.getIntExtra("imgid",10);

                list.add(new MyCustomDTO(name, content, imgid));
                adapter.notifyDataSetChanged();
            }
        }
    }
}

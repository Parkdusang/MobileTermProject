package com.fitcen.parkdusang.healthtrainer;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

/**
 * Created by parkdusang on 16. 4. 9..
 */
public class MyViewPagerAdapter extends FragmentStatePagerAdapter {
    /*
	 * 이 클래스의 부모생성자 호출시 인수로 반드시 FragmentManager객체를 넘겨야한다.
	 * 이 객체는 Activity에서만 만들수 있고, 여기서사용중인 Fragment가 v4이기 때문에
	 * Activity중에서도 ActionBarActivity에서 얻어와야한다.
	 */
    Fragment[] fragments = new Fragment[4];
    String id;


    public MyViewPagerAdapter(FragmentManager fm,String id) {
        super(fm);
        Bundle b = new Bundle();
        b.putString("_id", id);
        fragments[0] = new Customercontent1();
        fragments[1] = new Customercontent2();
        fragments[2] = new Customercontent3();
        fragments[3] = new Customercontent4();
        fragments[0].setArguments(b);
        fragments[1].setArguments(b);
        fragments[2].setArguments(b);
        fragments[3].setArguments(b);
        Log.i("TAG", id);
        Log.i("TAG", "MyViewPagerAdapter! ");

    }

    //아래의 메서드들의 호출 주체는 ViewPager이다.
    //ListView와 원리가 같다.

    /*
     * 여러 프레그먼트 중 어떤 프레그먼트를 보여줄지 결정
     */
    public Fragment getItem(int arg0) {
//        Bundle b = new Bundle();
//        b.putString("_id",id);
//        Log.i("result id", id);
//        fragments[0].setArguments(b);
//        fragments[1].setArguments(b);
//        fragments[2].setArguments(b);
//        fragments[3].setArguments(b);

        return fragments[arg0];
    }

    /*
     * 보여질 프레그먼트가 몇개인지 결정
     */
    public int getCount() {
        return fragments.length;
    }

}

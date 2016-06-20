package com.fitcen.parkdusang.healthtrainer;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

public class MainTab extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);
        Intent intent = getIntent();
        id = intent.getStringExtra("_id");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(),id);

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, 0, Menu.NONE, "만든사람들");
        menu.add(0, 1, Menu.NONE, "연결 해제");
        menu.add(0, 2, Menu.NONE, "로그아웃");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case 0:
                Intent intent = new Intent(getApplicationContext(), Developer.class);
                startActivity(intent);
                break;
            case 1:

                AlertDialog.Builder alt_bld = new AlertDialog.Builder(this);
                alt_bld.setMessage("트레이너와의 연결을 끊으시겠습니까 ?").setCancelable(
                        false).setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent intent2 = new Intent(getApplicationContext(), Destroyconnection.class);
                                intent2.putExtra("check", "2");
                                intent2.putExtra("id", id);
                                startService(intent2);

                                Intent intent3 = new Intent(getApplicationContext(), Connectionwaiting.class);
                                intent3.putExtra("_id",id);
                                intent3.putExtra("type",2);
                                intent3.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent3);
                            }
                        }).setNegativeButton("No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // Action for 'NO' Button
                                dialog.cancel();
                            }
                        });
                AlertDialog alert = alt_bld.create();
                // Title for AlertDialog
                alert.setTitle("트레이너 연결관리");
                // Icon for AlertDialog
                alert.setIcon(R.drawable.icon);
                alert.show();



                break;
            case 2:
                Intent intent1 = new Intent(getApplicationContext(), MainActivity.class);
                intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent1);
                break;
        }
        return super.onOptionsItemSelected(item);
    }



    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        Fragment[] fragments = new Fragment[4];
        String id;
        public SectionsPagerAdapter(FragmentManager fm,String id) {
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

        }

        @Override
        public Fragment getItem(int position) {
            return fragments[position];
        }

        @Override
        public int getCount() {

            return fragments.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "HOME";
                case 1:
                    return "RECORD";
                case 2:
                    return "INBODY";
                case 3:
                    return "REPORT";
            }
            return null;
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */

}

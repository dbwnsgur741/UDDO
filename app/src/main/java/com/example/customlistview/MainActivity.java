package com.example.customlistview;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public SharedPreferences sf;
    public String sf_txt;
    public int mp;
    public int START_TIME_5_MIN = 3000;
    private int sec;
    private int tmin;
    private int min;

    private TextView timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //툴바 초기화
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        View include01 = findViewById(R.id.include1);
        View include02 = include01.findViewById(R.id.include2);
        TextView point = (TextView) findViewById(R.id.my_point_text);

        // <!--SharedPreferences --!>

        sf = getSharedPreferences("NamSan",MODE_PRIVATE);
        sf_txt = sf.getString("NickName","");
        mp = sf.getInt("myPoint",0);

        Log.d("@@", String.valueOf(mp));

        TextView name_mission = (TextView)findViewById(R.id.name_mission);
        name_mission.setText(sf_txt + "님의 지령지");
        point.setText(String.valueOf(mp));

        //  <!--리스트 뷰 및 아이템 초기화 --!>
        ListView listView;
        final ListViewAdapter adapter;

        adapter = new ListViewAdapter();

        listView = (ListView) findViewById(R.id.listview1);
        listView.setAdapter(adapter);

        adapter.addItem("상시","지피지기면 백전백승\n퀴즈1이 진행중입니다.");
        adapter.addItem("퀴즈","사라진 고종황제의 비밀금고를 열어라!");
        adapter.addItem("보물찾기","일본군영(4)에서 아이템을 획득하라!");
        adapter.addItem("사진미션","애국동지들과 추억쌓기");
        adapter.addItem("NPC","대결,경쟁을 통해 참다운 의병이 되어라!");
        adapter.addItem("Test","test123");
        adapter.addItem("Test2","test123");

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(),Detail_Menu.class);
                intent.putExtra("category", ((ListViewItem)adapter.getItem(position)).getTitle());
                startActivity(intent);
            }
        });

        this.timer = adapter.getTimerView();
        timer.setText("!!:!!");  // ---------> null

        // <!--리스트 뷰 및 아이템 초기화 끝 --!>

        //<!-- 상단 네비게이션 바 초기화 --!>

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        //<!-- 네비게이션 바 초기화 끝 --!>
    }

    /*
    public void setTimer(TextView timerTextView){
        this.timer = timerTextView;
        getTimer();
    }

    public void getTimer(){
        // 타이머 선언
        Timer ntimer = new Timer();
        TimerTask TT = new TimerTask() {
            @Override
            public void run() {
                timer.setText("11:11");
            }
        };
        ntimer.schedule(TT,0,1000);
    }
    */
    @Override
    protected void onResume() {
        super.onResume();
        sf = getSharedPreferences("NamSan",MODE_PRIVATE);
        mp = sf.getInt("myPoint",0);
        TextView point = (TextView) findViewById(R.id.my_point_text);
        point.setText(String.valueOf(mp));
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_sort_black_30dp);
        actionBar.setDisplayShowTitleEnabled(false);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_tools) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}

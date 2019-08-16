package com.example.customlistview;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
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
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private SharedPreferences sf;
    private String sf_txt;
    private int mp;
    private TextView timer;
    private ListView listView;
    private ListViewAdapter adapter;
    public SharedPreferences sharedPreferences;
    public SharedPreferences.Editor editor;
    private int START_TIME_5_MIN ;
    private Timer ntimer ;
    private TimerTask TT ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences("NamSan",MODE_PRIVATE);
        editor = sharedPreferences.edit();

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

        TextView name_mission = (TextView)findViewById(R.id.name_mission);
        name_mission.setText(sf_txt + "님의 지령지");
        point.setText(String.valueOf(mp));

        //  <!--리스트 뷰 및 아이템 초기화 --!>

        this.adapter = new ListViewAdapter();

        listView = (ListView) findViewById(R.id.listview1);
        listView.setAdapter(adapter);

        adapter.addItem("상시","지피지기면 백전백승\n퀴즈1이 진행중입니다.");
        adapter.addItem("퀴즈","사라진 고종황제의 비밀금고를 열어라!");
        adapter.addItem("보물찾기","일본군영(4)에서 아이템을 획득하라!");
        adapter.addItem("사진미션","애국동지들과 추억쌓기");
        adapter.addItem("NPC","대결,경쟁을 통해 참다운 의병이 되어라!");
        adapter.addItem("QRTEST","QRTEST");

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = null;
                switch (position) {
                  case 0:
                    intent = new Intent(getApplicationContext(), A_Quiz_Default_Activity.class);
                    intent.putExtra("category", ((ListViewItem) adapter.getItem(position)).getTitle());
                    startActivityForResult(intent, 2000);
                    break;
                    case 3:
                        intent = new Intent( getApplicationContext(), Picture_default_Activity.class );
                        startActivityForResult( intent,4000 );
                        break;
                  case 5:
                    intent = new Intent(getApplicationContext(), QR_Code_Activity.class);
                    intent.putExtra("category",((ListViewItem)adapter.getItem(position)).getTitle());
                    startActivityForResult(intent,3000);
                    break;
                }
            }
        });

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_CANCELED) {
            if (requestCode == 2000 ) {
                if(TT !=null){
                    stopTimer();
                }
                setTimer();
            }
        }
    }

    protected void setTimer(){

        final long EVENT_TIME = sharedPreferences.getLong( "Timer",0 );

        // 타이머 레이아웃 초기화 및 타이머 초기화
        ntimer = new Timer();
        timer = adapter.getTimerView();

        TT = new TimerTask() {
            @Override
            public void run() {
                long TIME_NOW = System.currentTimeMillis();
                int TIMER = 300;
                int temp = ((int)(TIME_NOW - EVENT_TIME))/1000;
                int temp2 = TIMER - temp;

                int min = temp2 / 60;
                int sec = temp2 % 60;

                if(String.valueOf(sec).length() == 1){
                    timer.setText(String.format("0%d:0%d",min,sec));
                }
                else{
                    timer.setText(String.format("0%d:%d",min,sec));
                }
                if(temp2 < 0){
                    TT.cancel();
                    timer.setText("퀴즈풀기!");
                }
            }
        };
        ntimer.schedule(TT,0,1000);
    }

    private void stopTimer(){
        TT.cancel();
    }

    @Override
    protected void onResume() {
        super.onResume();

        // <!-- 다시돌아왔을때 포인트 변화 부분 --!> //

        sf = getSharedPreferences("NamSan",MODE_PRIVATE);
        mp = sf.getInt("myPoint",0);
        TextView point = (TextView) findViewById(R.id.my_point_text);
        point.setText(String.valueOf(mp));

        if(TT != null){
            TT.cancel();
            setTimer();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
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
        stopTimer();
        setTimer();
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

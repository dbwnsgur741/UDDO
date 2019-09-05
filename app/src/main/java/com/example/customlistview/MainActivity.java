package com.example.customlistview;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
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
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private String sf_txt;
    private int mp;
    private ListView listView;
    private ListViewAdapter adapter;
    private TextView timer;
    public SharedPreferences sharedPreferences;
    public SharedPreferences.Editor editor;
    public TextView point;
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private String admin_status;
    private TextView admin_textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /******* Layout Setting*******/

        //// SharedPreferences

        sharedPreferences = getSharedPreferences("NamSan",MODE_PRIVATE);
        editor = sharedPreferences.edit();
        sf_txt = sharedPreferences.getString("NickName","");
        mp = sharedPreferences.getInt("myPoint",0);
        admin_status = sharedPreferences.getString( "Admin","" );
        //// End Of SharedPreferences

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        View include01 = findViewById(R.id.include1);
        View include02 = include01.findViewById(R.id.include2);
        point = (TextView) findViewById(R.id.my_point_text);
        TextView name_mission = (TextView)findViewById(R.id.name_mission);
        name_mission.setText(sf_txt + "님의 지령지");
        point.setText(String.valueOf(mp));
        listView = (ListView) findViewById(R.id.listview1);
        admin_textView = (TextView)findViewById( R.id.main_admin_textview );

        //// Top Navigation Bar

        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(this);

        //// End Of Top Navigation Bar

        /******* End Of Layout Setting*******/


        /******* ListView Layout & Event Setting *******/
        //check admin status
        checkAdminLogin();

        this.adapter = new ListViewAdapter();

        adapter.addItem("지피지기면 백전백승","5분마다 제공되는 퀴즈를 풀자.");
        adapter.addItem("사라진 고종황제의 비밀금고를 열어라!","퀴즈 풀고 금고의 비밀번호를 받자!");
        adapter.addItem("일본군영( D )에서 아이템을 획득하라!","지도의 위치에서 보물을 찾아보자");
        adapter.addItem("애국동지들과 추억쌓기","사진미션을 통해 추억을 쌓자");
        adapter.addItem("대결 경쟁을 통해 참다운 의병이 되어라!","NPC에게 미션을 받아보자");
        adapter.addItem("상대와의 대결을 통해 군자금을 확보하라!","다른 팀과의 대결을 진행해보자");
        //adapter.addItem("미션2","미션2");
        listView.setAdapter(adapter);
        timer = adapter.getTimerView();

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
                    case 1:
                        intent = new Intent( getApplicationContext(), MissingSafeQuiz_Default_Activity.class );
                        startActivity( intent );
                        break;
                    case 2:
                        intent = new Intent(getApplicationContext(), QR_Code_Default_Activity.class);
                        intent.putExtra("category",((ListViewItem)adapter.getItem(position)).getTitle());
                        startActivityForResult(intent,3000);
                        break;
                    case 3:
                        intent = new Intent( getApplicationContext(), Picture_default_Activity.class );
                        startActivityForResult( intent,4000 );
                        break;
                    case 4:
                        intent = new Intent( getApplicationContext(), TrainMissionActivity.class );
                        startActivityForResult( intent,5000 );
                        break;
                    case 5:
                        intent = new Intent( getApplicationContext(), VsActivity.class );
                        startActivityForResult( intent,6000 );
                        break;
                }
            }
        });

        /******* End Of ListView Layout & Event Setting *******/
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 2000 ) {
    //        setTimer();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // check admin status
        checkAdminLogin();

        // <!-- 다시돌아왔을때 포인트 변화 부분 --!> //
        sharedPreferences = getSharedPreferences("NamSan",MODE_PRIVATE);
        mp = sharedPreferences.getInt("myPoint",0);
        TextView point = (TextView) findViewById(R.id.my_point_text);
        point.setText(String.valueOf(mp));
        adapter.setTimer_check();
    }

    @Override
    public void onPause() {
        adapter.getHandler().removeCallbacksAndMessages(null);
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
        int id = item.getItemId();
        Intent intent = null;

        if (id == R.id.nav_home) {
            intent = new Intent( getApplicationContext(),Admin_Login_Activity.class );
            startActivity(intent);
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_tools) {

        }

        /**** if use fragment
         *
        if(fragment !=null){

            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.main_content,fragment);
            fragmentTransaction.commit();
        }
         ******/

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d("afdfasfa","dsafsafds");
        return super.onTouchEvent( event );
    }

    private void checkAdminLogin(){
        this.admin_status = sharedPreferences.getString( "Admin","" );

        if(admin_status == ""){
            admin_textView.setVisibility( View.GONE );
        }else{
            admin_textView.setVisibility( View.VISIBLE );
        }
    }
}

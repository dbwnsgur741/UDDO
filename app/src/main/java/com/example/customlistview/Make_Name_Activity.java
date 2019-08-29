package com.example.customlistview;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class Make_Name_Activity extends Activity {

    private TextView textView;
    private TextView textView1;
    private final String txt = "당신의 닉네임을\n 입력해주세요.";
    private Button start_btn;
    private EditText nick_name;
    private SharedPreferences save_nick_sf;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.make_nickname);

        /*** 로딩액티비티 받아오기 ****/
        Intent intent = new Intent(this, LoadingActivity.class);
        startActivity(intent);

        /******* Layout setting *******/

        // TODO : "닉네임" => 글꼴변경!!
        textView = (TextView)findViewById( R.id.make_nick_textview );
        textView1 = (TextView)findViewById( R.id.make_nick_NoName );
        textView.setText(txt);
        start_btn = (Button) findViewById(R.id.start_button);
        nick_name = (EditText)findViewById(R.id.nick_name);

        /******* End Of Layout setting *******/

        /******* Event Setting *******/
        // TODO : SharePreferences 초기화 --> editor.clear() --> 테스트 위하여 초기화 시켜놓음

        save_nick_sf = getSharedPreferences("NamSan",MODE_PRIVATE);
        editor = save_nick_sf.edit();
        /*
        editor.clear();
        editor.commit();
        */
        ///// 저장된 닉네임이 있는지 확인
        if(save_nick_sf.getString("NickName","") != ""){
            Intent intent1 = new Intent(this, MainActivity.class);
            startActivity(intent1);
        }

        start_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(check_nick(nick_name)){
                    saveNickName(nick_name);
                    Intent intent = new Intent(Make_Name_Activity.this, MainActivity.class);
                    intent.putExtra("nick_name", nick_name.getText().toString());
                    startActivity(intent);
                    finish();
                }
            }
        });
        textView1.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putInt("myPoint",200);
                editor.putString("NickName", "NoName");
                editor.commit();
                Intent intent = new Intent(Make_Name_Activity.this, MainActivity.class);
                intent.putExtra("nick_name", "NoName");
                startActivity(intent);
                finish();
            }
        } );

        /******* End Of Event Setting *******/
    }

    private void saveNickName(EditText et){
        SharedPreferences preferences = getSharedPreferences("NamSan",Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("myPoint",200);
        editor.putString("NickName", et.getText().toString());
        editor.commit();
    }

    public boolean check_nick(EditText et){
        if(3<= et.length() || et.length() >=8){
            return true;
        }
        else{
            Toast.makeText(getApplicationContext(),"올바른 닉네임을 입력해주세요.(3~8자리)",Toast.LENGTH_LONG).show();
            return false;
        }
    }
    @Override
    public void onResume(){
        super.onResume();
        SharedPreferences save_nick_sf = getSharedPreferences("NamSan",MODE_PRIVATE);

        if(save_nick_sf.getString("NickName","") != ""){
            finish();
        }
    }
}

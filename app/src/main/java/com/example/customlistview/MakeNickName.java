package com.example.customlistview;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class MakeNickName extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.make_nickname);

        // 저장된 닉네임이 있는지 확인
        SharedPreferences save_nick_sf = getSharedPreferences("NamSan",MODE_PRIVATE);
        SharedPreferences.Editor editor = save_nick_sf.edit();
        editor.clear();
        editor.commit();

        if(save_nick_sf.getString("NickName","") != ""){
            Intent intent1 = new Intent(this, MainActivity.class);
            startActivity(intent1);
        }

        // 로딩액티비티 받아오기
        Intent intent = new Intent(this, LoadingActivity.class);
        startActivity(intent);

        Button start_btn = (Button) findViewById(R.id.start_button);
        final EditText nick_name = (EditText)findViewById(R.id.nick_name);

        start_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(check_nick(nick_name)){
                    saveNickName(nick_name);
                    Intent intent = new Intent(MakeNickName.this, MainActivity.class);
                    intent.putExtra("nick_name", nick_name.getText().toString());
                    startActivity(intent);
                }
            }
        });
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

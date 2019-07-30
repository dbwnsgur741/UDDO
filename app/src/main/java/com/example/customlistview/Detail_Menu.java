package com.example.customlistview;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

public class Detail_Menu extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final String CATEGORY1 = "상시";
        final String CATEGORY2 = "퀴즈";
        final String CATEGORY3 = "보물찾기";
        final String CATEGORY4 = "사진미션";
        final String CATEGORY5 = "NPC";
        final String CATEGORY6 = "Test";

        Intent intent = getIntent();
        String _category = intent.getStringExtra("category");

        TextView txt = (TextView) findViewById(R.id.test);

        if(_category.equals(CATEGORY1)){
            Intent intent1 = new Intent(getApplicationContext(),Category_Always_Quiz.class);
            startActivity(intent1);
            finish();
        }
        else if(_category.equals(CATEGORY2)){
            txt.setText("@@@");
        }
        else if(_category.equals(CATEGORY3)){
            txt.setText("###");
        }
        else if(_category.equals(CATEGORY4)){
            Intent intent4 = new Intent(getApplicationContext(),Take_Picture_Mission.class);
            startActivity(intent4);
            finish();
        }
        else if(_category.equals(CATEGORY5)){
            txt.setText("%%%%");
        }
    }

}

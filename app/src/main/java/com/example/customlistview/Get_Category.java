package com.example.customlistview;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

public class Get_Category extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final String CATEGORY1 = "상시";
        final String CATEGORY2 = "퀴즈";
        final String CATEGORY3 = "보물찾기";
        final String CATEGORY4 = "사진미션";
        final String CATEGORY5 = "NPC";

        Intent intent = getIntent();
        String _category = intent.getStringExtra("category");

        TextView txt = (TextView) findViewById(R.id.test);

        if (_category.equals(CATEGORY1)) {
            Intent intent1 = new Intent(getApplicationContext(), A_Quiz_Default_Activity.class);
            startActivityForResult(intent1, 2000);
            finish();
        } else if (_category.equals(CATEGORY2)) {
            txt.setText("@@@");
        } else if (_category.equals(CATEGORY3)) {
            txt.setText("###");
        } else if (_category.equals(CATEGORY4)) {
            finish();
        } else if (_category.equals(CATEGORY5)) {
            txt.setText("%%%%");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (resultCode == 2000) {
                setResult(2000);
            }
        }
    }
}

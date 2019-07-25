package com.example.customlistview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

public class Always_Quiz_Detail extends AppCompatActivity {

    public String quiz_name = "Question";
    public int num = 1 ;
    public String what_quiz = quiz_name + " "+String.valueOf(num)+".";
    public boolean true_or_false;
    private PopupWindow popupWindow;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.always_quiz_detail);
        set_quiz_num_text();

        final ImageButton corbx = (ImageButton)findViewById(R.id.correct_box);
        final ImageButton falbx = (ImageButton)findViewById(R.id.false_box);

        corbx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                true_or_false = true;
                Log.d("num", String.valueOf(num));
                Log.d("bool", String.valueOf(true_or_false));
                correctevent();
            }
        });

        falbx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                true_or_false = false;
                correctevent();
            }
        });
    }

    public void correctevent() {
        ViewGroup vg = (ViewGroup)findViewById(android.R.id.content);
        View popUpView = LayoutInflater.from(this).inflate(R.layout.point_popup,vg, false);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(popUpView);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public void set_quiz_num_text(){
        TextView quiz_num = (TextView)findViewById(R.id.quiz_num);
        quiz_num.setText(what_quiz);
    }
}

package com.example.customlistview;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class Always_Quiz_Detail extends AppCompatActivity implements View.OnClickListener {


    public int quiz_number ;
    public String quiz_description ;
    public String what_quiz ;
    public boolean flag;
    public MySingleton mySingleton;
    ImageButton corbx;
    ImageButton falbx;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.always_quiz_detail);

        IntiailzeView();

        mySingleton = MySingleton.getInstance(this);
        SharedPreferences sharedPreferences = getSharedPreferences("NamSan",MODE_PRIVATE);

        if(sharedPreferences.getInt("Quiz1",0) == 0 ){
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("Quiz1",1);
            editor.commit();
        }

        mySingleton.setQuiz_num(sharedPreferences.getInt("Quiz1", 0));
        mySingleton.setQuiz_desc(sharedPreferences.getInt("Quiz1",0));

        this.quiz_description = mySingleton.getQuiz_desc();
        this.what_quiz = mySingleton.getWhat_quiz();

        TextView quiz_num = (TextView)findViewById(R.id.quiz_num);
        TextView quiz_desc = (TextView)findViewById(R.id.quiz_desc);

        quiz_num.setText(what_quiz);
        quiz_desc.setText(quiz_description);

    }

    public void IntiailzeView(){
        corbx = (ImageButton)findViewById(R.id.correct_box);
        falbx = (ImageButton)findViewById(R.id.false_box);
        corbx.setOnClickListener(this);
        falbx.setOnClickListener(this);
    }


    public void correctevent(int num) {

        SharedPreferences sharedPreferences = getSharedPreferences("NamSan",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        mySingleton.setQuiz_answer(sharedPreferences.getInt("Quiz1",0));
        int answer = mySingleton.getQuiz_answer();

        if(num == answer){
            editor.putInt("Quiz1", sharedPreferences.getInt("Quiz1",0)+1);
            editor.putInt("myPoint",sharedPreferences.getInt("myPoint",0)+50);
            editor.commit();
            ViewGroup vg = (ViewGroup)findViewById(android.R.id.content);
            View popUpView = LayoutInflater.from(this).inflate(R.layout.point_popup, vg , false);
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setView(popUpView);
            final AlertDialog alertDialog = builder.create();
            alertDialog.show();
            Button btn_popup = (Button)popUpView.findViewById(R.id.btn_popup);

            btn_popup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog.dismiss();
                    setResult(1000);
                    finish();
                }
            });
            alertDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    alertDialog.dismiss();
                    setResult(1000);
                    finish();
                }
            });
        }
        else{
            ViewGroup vg = (ViewGroup)findViewById(android.R.id.content);
            View popUpView = LayoutInflater.from(this).inflate(R.layout.point_popup_false, vg , false);
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setView(popUpView);
            final AlertDialog alertDialog = builder.create();
            alertDialog.show();
            Button btn_popup = (Button)popUpView.findViewById(R.id.btn_popup);

            btn_popup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog.dismiss();
                    setResult(1000);
                    finish();
                }
            });
            alertDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    alertDialog.dismiss();
                    setResult(1000);
                    finish();
                }
            });
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.correct_box:
                correctevent(0);
                break;
            case R.id.false_box:
                correctevent(1);
                break;
        }
    }
}

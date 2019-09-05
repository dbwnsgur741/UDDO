package com.example.customlistview;

import android.content.Context;

public class MySingleton {

    private final String q = "Question";
    private static Context context; // 받아온 Context

    private static MySingleton instance;

    private static String[] quiz_desc_array; // 퀴즈 리스트 배열
    private static int[] quiz_answer_array; // 리스트에 대한 정답 배열
    private String quiz_desc; // 퀴즈 번호에 따른 문제 설명
    private int quiz_answer; // 정답
    private int quiz_num ; // 퀴즈 번호
    private String what_quiz ; // 레이아웃 왼쪽 상단
    private static String[] quiz_answer_desc_array; // 퀴즈 해설 배열
    private String quiz_answer_desc; // 퀴즈 해설

    public static MySingleton getInstance(Context context) {
        if(instance == null){
            instance = new MySingleton(context);
            MySingleton.quiz_desc_array = context.getResources().getStringArray(R.array.ox_quiz_list);
            MySingleton.quiz_answer_array= context.getResources().getIntArray(R.array.ox_quiz_answer);
            MySingleton.quiz_answer_desc_array = context.getResources().getStringArray( R.array.quiz_answer_desc_list );
        }
        MySingleton.context = context;
        return instance;
    }

    public MySingleton(Context context){
        this.context = context;
    }

    public String getWhat_quiz() {
        what_quiz = q + " " + (quiz_num) + ".";
        return what_quiz;
    }

    public String getQuiz_answer_desc(){
        return quiz_answer_desc;
    }
    public void setQuiz_answer_desc(int quiz_num){
        this.quiz_answer_desc = quiz_answer_desc_array[quiz_num];
    }
    public int getQuiz_num() {
        return quiz_num;
    }

    public void setQuiz_num(int quiz_num) {
        this.quiz_num = quiz_num;
    }

    public String getQuiz_desc() {
        return quiz_desc;
    }

    public void setQuiz_desc(int quiz_num) {
        this.quiz_desc = quiz_desc_array[quiz_num];
    }

    public int getQuiz_answer() {
        return quiz_answer;
    }

    public void setQuiz_answer(int quiz_num) {
        this.quiz_answer = quiz_answer_array[quiz_num];
    }

}

package com.example.customlistview;

import android.graphics.drawable.Drawable;

public class ListViewItem {

    private String titleStr;
    private String descStr;


    public void setTitle(String title){
        titleStr = title;
    }
    public void setDesc(String desc){
        descStr = desc ;
    }

    public String getTitle(){
        return  this.titleStr;
    }
    public String getDescStr(){
        return this.descStr;
    }
}

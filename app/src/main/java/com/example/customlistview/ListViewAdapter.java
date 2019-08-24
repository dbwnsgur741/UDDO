package com.example.customlistview;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class ListViewAdapter extends BaseAdapter {

    private ArrayList<ListViewItem> listViewItemList = new ArrayList<ListViewItem>();
    public TextView timerTextView;
    public LinearLayout linearLayout;

    public SharedPreferences sharedPreferences;
    private Long savedTime;
    private Handler handler;
    private final long currentTime = System.currentTimeMillis();
    // long currentTime : should be set when class started or it can be changed at every call
    private long maxTime;
    public ListViewAdapter(){
        super();
        handler = new Handler();
    }
    @Override // Adapter에 사용되는 데이터의 개수를 리턴. : 필수 구현
    public int getCount() {
        return listViewItemList.size();
    }
    public Handler getHandler(){
        return handler;
    }
    @Override
    public Object getItem(int position) {
        return listViewItemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override // position에 위치한 데이터를 화면에 출력하는데 사용될 View를 리턴 : 필수 구현
    public View getView(int position, View convertView, ViewGroup parent) {
        final Context context = parent.getContext();

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if(convertView == null){
            convertView = inflater.inflate(R.layout.listview_item,parent,false);
        }

        if (position == 0){
            // 타이머 세팅
            sharedPreferences = context.getSharedPreferences("NamSan",MODE_PRIVATE);
            String timeValue = context.getResources().getString(R.string.timerValue);
            if(timeValue!=null) {
                maxTime = Long.parseLong(timeValue);
            }
            timerTextView = (TextView) convertView.findViewById(R.id.timer);
            timerTextView.setVisibility(View.VISIBLE);
            setTimer_check();
        }

        TextView titleTextView = (TextView) convertView.findViewById(R.id.textView1);
        TextView descTextView = (TextView) convertView.findViewById(R.id.textView2);

        ListViewItem listViewItem = listViewItemList.get(position);


        titleTextView.setText(listViewItem.getTitle());
        descTextView.setText(listViewItem.getDescStr());

        return convertView;
    }

    public TextView getTimerView(){
        return this.timerTextView;
    }

    public void addItem(String title, String desc) {
        ListViewItem item = new ListViewItem();

        item.setTitle(title);
        item.setDesc(desc);

        listViewItemList.add(item);
    }

    public void setTimer_check() {
        final TextView timer = getTimerView();
        if(timer == null){
            return;
        }
        savedTime = sharedPreferences.getLong("Timer", currentTime);

        if (currentTime - savedTime  > maxTime){ // over 5 mins
            timer.setText("퀴즈풀기!");
            handler.removeCallbacksAndMessages(null);
            return;
        }

        if (currentTime == savedTime){
            timer.setText("퀴즈풀기!");
            handler.removeCallbacksAndMessages(null);
            return;
        }
        final Runnable runnableCode = new Runnable() {
            @Override
            public void run() {
                long TIME_NOW = System.currentTimeMillis();
                int timeGab = ((int) (TIME_NOW - savedTime)) / 1000;
                int remainTimeSec = (int)( maxTime/1000) - timeGab;
                int min = remainTimeSec / 60;
                int sec = remainTimeSec % 60;
                Log.e("!!!!!!!!!!",(TIME_NOW-savedTime) + " "+ maxTime );
                if (TIME_NOW - savedTime  > maxTime){ // over 5 mins
                    timer.setText("퀴즈풀기!");
                    handler.removeCallbacksAndMessages(null);
                } else {
                    if (String.valueOf( sec ).length() == 1) {
                        timer.setText( String.format( "0%d:0%d", min, sec ) );
                    } else {
                        timer.setText( String.format( "0%d:%d", min, sec ) );
                    }
                }
                handler.postDelayed( this, 1000 );
            }
        };
        handler.post( runnableCode );
    }
}

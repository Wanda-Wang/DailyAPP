package com.example.df_daily;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.df_daily.Helper.DailyDbContorller;
import com.example.df_daily.bean.DailyInfo;

public class ReadDailyActivity extends AppCompatActivity {

    DailyInfo dailyInfo;
    DailyDbContorller dailyDbContorller;
    private TextView title,weather,emotion,content;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_daily);
        title=findViewById(R.id.read_title);
        weather=findViewById(R.id.read_weather);
        emotion=findViewById(R.id.read_emotion);
        content=findViewById(R.id.read_des);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        dailyDbContorller=DailyDbContorller.getInstance(this);
        Intent intent=getIntent();
        String title_=intent.getStringExtra("title");
        dailyInfo=dailyDbContorller.searchByWhere(title_);

        title.setText(title_);
        weather.setText(dailyInfo.getDailyWeather());
        emotion.setText(dailyInfo.getDailyEmotion());
        content.setText(dailyInfo.getDailyDescription());

    }
    public void inits(){

    }

}
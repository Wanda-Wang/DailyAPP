package com.example.df_daily;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.df_daily.Helper.DailyDbContorller;
import com.example.df_daily.Helper.DbController;
import com.example.df_daily.bean.DailyInfo;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DailyActivity extends AppCompatActivity {

    private static final String TAG ="wanda DailyActivity" ;
    EditText title,description;
    Button daily_add;
    Intent intent,intent_jump;
    DailyInfo dailyInfo;
    DailyDbContorller dailyDbContorller;
    String weather,emotion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily);
        intent=getIntent();
        weather=intent.getStringExtra("weather");
        emotion=intent.getStringExtra("emotion");
        title=findViewById(R.id.title);
        description=findViewById(R.id.description);
        daily_add=findViewById(R.id.daily_add);
        dailyDbContorller= DailyDbContorller.getInstance(DailyActivity.this);
        daily_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent_jump=new Intent(DailyActivity.this,MainActivity.class);
                intent_jump.putExtra("navigate",1);
                dailyInfo=new DailyInfo(null,title.getText().toString(),description.getText().toString(),emotion,weather,getStringDateShort(new Date(System.currentTimeMillis())));
                dailyDbContorller.insertOrReplace(dailyInfo);
                Log.i(TAG,dailyInfo.getDailyTitle());
                startActivity(intent_jump);
            }
        });
//        dailyInfo=new DailyInfo(null,title.getText().toString(),description.getText().toString(),emotion,weather,getStringDateShort(new Date(System.currentTimeMillis())));
    }

    /**
     * 获取现在时间
     *
     * @return 返回短时间字符串格式yyyy-MM-dd
     */
    public static String getStringDateShort(Date currentTime) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = formatter.format(currentTime);
        return dateString;
    }
    @Override
    protected void onResume() {

        super.onResume();
    }

    @Override
    protected void onPause() {

        super.onPause();
    }
}
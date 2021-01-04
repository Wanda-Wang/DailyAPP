package com.example.df_daily;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;

public class HappyActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener {

    private static final String TAG = "wanda HappyActivity";
    private RadioGroup rg;
    private Button ok;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_happy);
        Intent intent_weather=getIntent();
        intent=new Intent(HappyActivity.this,DailyActivity.class);
        intent.putExtra("weather",intent_weather.getStringExtra("weather"));
        Log.i(TAG,"intent_weather.getStringExtra(\"weather\")"+intent_weather.getStringExtra("weather"));
        rg = (RadioGroup) findViewById(R.id.emotions);
        ok=findViewById(R.id.btn_emotion);
        rg.setOnCheckedChangeListener(this);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);
            }
        });

    }
    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch(checkedId){
            case R.id.happy:
                intent.putExtra("emotion","开心");
//                Toast.makeText(WeatherActivity.this, "男", 1).show();
                break;
            case R.id.sad:
                intent.putExtra("emotion","悲伤");
//                Toast.makeText(WeatherActivity.this, "女", 1).show();
                break;
            case R.id.angry:
                intent.putExtra("emotion","愤怒");
//                Toast.makeText(WeatherActivity.this, "男", 1).show();
                break;
            case R.id.do_not_know:
                intent.putExtra("emotion","不知道");
//                Toast.makeText(WeatherActivity.this, "女", 1).show();
                break;
            case R.id.clam:
                intent.putExtra("emotion","平静");
//                Toast.makeText(WeatherActivity.this, "男", 1).show();
                break;
            default:
                intent.putExtra("emotion","不知道");
                break;
        }

    }
}
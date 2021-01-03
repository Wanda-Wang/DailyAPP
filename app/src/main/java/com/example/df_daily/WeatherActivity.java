package com.example.df_daily;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;

public class WeatherActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener {

    private RadioGroup rg;
    private Button ok;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        intent=new Intent(WeatherActivity.this,HappyActivity.class);
        rg = (RadioGroup) findViewById(R.id.weathers);
        ok=findViewById(R.id.btn_weather);
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
            case R.id.rain:
                intent.putExtra("weather","rain");
//                Toast.makeText(WeatherActivity.this, "男", 1).show();
                break;
            case R.id.sunny:
                intent.putExtra("weather","sunny");
//                Toast.makeText(WeatherActivity.this, "女", 1).show();
                break;
            case R.id.cloudy:
                intent.putExtra("weather","cloudy");
//                Toast.makeText(WeatherActivity.this, "男", 1).show();
                break;
            case R.id.snow:
                intent.putExtra("weather","snow");
//                Toast.makeText(WeatherActivity.this, "女", 1).show();
                break;
            case R.id.lei:
                intent.putExtra("weather","lei");
//                Toast.makeText(WeatherActivity.this, "男", 1).show();
                break;
            default:
                intent.putExtra("weather","sunny");
                break;
        }

    }
}
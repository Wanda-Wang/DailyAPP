package com.example.gallerydemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.github.chrisbanes.photoview.PhotoView;

public class SimpleSampleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_sample);
        PhotoView photoView = (PhotoView) findViewById(R.id.photoview);
        photoView.setImageResource(R.drawable.sanxia);
    }
}
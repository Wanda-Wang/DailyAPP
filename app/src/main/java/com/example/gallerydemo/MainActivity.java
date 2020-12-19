package com.example.gallerydemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private List<PersonBean> list = new ArrayList<>();
    private RecyclerView mRecyclerView = null;
    RecyclerView.LayoutManager mLayoutManager = null;
    RecyclerView.Adapter mAdapter = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initWidgets();
        initData();

        mAdapter = new MyRecyclerViewAdapter(this, new MyRecyclerViewAdapter.OnRecyclerItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(MainActivity.this, "点击了"+position+"项", Toast.LENGTH_SHORT).show();
            }
        });
        mRecyclerView.setAdapter(mAdapter);
    }

    private void initWidgets(){
        //设置recyclerview
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        //创建线性布局
        mLayoutManager = new LinearLayoutManager(this);
        //设置布局管理器
        mRecyclerView.setLayoutManager(mLayoutManager);
    }

    private void initData(){
        for (int i = 0; i < 15; i++){
            PersonBean bean = new PersonBean();
            bean.setIdImg(R.mipmap.ic_launcher);
            bean.setName("hello"+i);
            list.add(bean);
        }
    }
}
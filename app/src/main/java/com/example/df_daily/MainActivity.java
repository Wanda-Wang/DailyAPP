package com.example.df_daily;

import android.content.Intent;
import android.os.Bundle;

import com.example.df_daily.Helper.SharedHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.util.Log;
import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.Button;
import android.widget.TextView;

import java.util.Iterator;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    TextView text_user_name;
    TextView btn_exit_login;
    SharedHelper sh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("日迹");
//        FloatingActionButton fab = findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        View headerLayout = navigationView.inflateHeaderView(R.layout.nav_header_main);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,
                R.id.nav_tools, R.id.nav_share, R.id.nav_send)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        text_user_name= headerLayout.findViewById(R.id.name);
        btn_exit_login=headerLayout.findViewById(R.id.btn_exit);
        Intent intent=getIntent();
        if(intent.getIntExtra("navigate",0)==1){
            navController.navigate(R.id.nav_gallery);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    protected void onResume() {
        Log.i("TAG", "onActivityCreated: ");
        sh=new SharedHelper(this);
        if(!("".equals(sh.read_name().get("_username")))){
            text_user_name.setText(sh.read_name().get("_username"));
            Log.i("cgx",sh.read_name().get("_username"));
//            Intent name_intent=new Intent(getActivity(), EssayDetailActivity.class);
//            name_intent.putExtra("user_name_fav",sh.read_name().get("_username"));


        }
        else{
            text_user_name.setText("登录");
            text_user_name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
            });
        }

        btn_exit_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteKeyOfMap(sh.read_name());
                text_user_name.setText("登录");
                if(text_user_name.getText().toString()=="登录"){
                    text_user_name.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent=new Intent(MainActivity.this, LoginActivity.class);
                            startActivity(intent);
                        }
                    });
                }
            }
        });
        super.onResume();
    }

    public void inits(){
        text_user_name=findViewById(R.id.name);
        btn_exit_login=findViewById(R.id.btn_exit);
    }
    private static void deleteKeyOfMap(Map<String,String> paramsMap){
        Iterator<String> iter = paramsMap.keySet().iterator();
        while(iter.hasNext()){
            String key = iter.next();
            if(key.startsWith("_user")){
                iter.remove();
            }
        }
    }
    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}

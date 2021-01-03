package com.example.df_daily;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.df_daily.Helper.SharedHelper;

import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    private EditText user_name,password;
    private Button login;
    private TextView register;
    SharedHelper sh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        sh=new SharedHelper(getApplicationContext());

        inits();
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });

        user_name.setText(getIntent().getStringExtra("username"));

        //password.setText(getIntent().getStringExtra("passwd"));
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, String> data=sh.read(user_name.getText().toString());
                if(!password.getText().toString().equals("")&&password.getText().toString().equals(data.get("passwd"+user_name.getText().toString()))){
                    Toast.makeText(LoginActivity.this,"登录成功",Toast.LENGTH_LONG).show();
                    sh.save_name(user_name.getText().toString());
                    Log.d("cgx",user_name.getText().toString());
//                    Intent name_intent=new Intent(LoginActivity.this,EssayDetailActivity.class);
//                    name_intent.putExtra("user_name_fav",getIntent().getStringExtra("username"));
                    Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                    // intent.putExtra("fg_id",R.id.txt_user);


                    //TODO:存入数据库


                    startActivity(intent);
                }
                else{
                    Toast.makeText(LoginActivity.this,"用户名或密码错误",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    public void inits(){
        user_name=findViewById(R.id.login_name);
        password=findViewById(R.id.login_psw);
        login=findViewById(R.id.btn_login);
        register=findViewById(R.id.no_id_register);
    }
//    @Override
//    protected void onStart() {
//        super.onStart();
//
//    }



}
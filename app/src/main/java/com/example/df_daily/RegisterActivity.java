package com.example.df_daily;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.df_daily.Helper.SharedHelper;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {
    private EditText r_user_name,r_psw_f,r_psw_s;
    private Button btn_register;
    String strname,strpsw,strsecpsw;
    Context mContext;
    SharedHelper sh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mContext = getApplicationContext();
        sh = new SharedHelper(mContext);
        bindViews();
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strname=r_user_name.getText().toString();
                strpsw=r_psw_f.getText().toString();
                strsecpsw=r_psw_s.getText().toString();
                if(sh.read(strname).containsValue(strname)){
                    Toast.makeText(RegisterActivity.this,"用户名已经存在",Toast.LENGTH_SHORT).show();
                }
                else if(!isPasswordNO(strpsw)){
                    Toast.makeText(RegisterActivity.this,"请输入6到18位的字母大小写及数字下划线",Toast.LENGTH_SHORT).show();
                }
                else if(strpsw.equals(strsecpsw)){
                    sh.save(strname,strpsw);
                    Intent intent=new Intent(RegisterActivity.this,LoginActivity.class);
                    intent.putExtra("username",strname);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(RegisterActivity.this,"两次密码不同",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void bindViews() {
        r_user_name=findViewById(R.id.register_name);
        r_psw_f=findViewById(R.id.register_psw);
        r_psw_s=findViewById(R.id.register_psw_sec);
        btn_register=findViewById(R.id.btn_register);
    }

    //判断是否为6到18位的字母大小写及数字下划线。
    public static boolean isPasswordNO(String password) {
        Pattern p = Pattern.compile("[0-9a-zA-Z_]{6,18}");
        Matcher m = p.matcher(password);
        return m.matches();
    }


}
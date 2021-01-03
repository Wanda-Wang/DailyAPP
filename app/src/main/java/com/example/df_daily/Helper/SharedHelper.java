package com.example.df_daily.Helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.example.df_daily.bean.MyAlbum;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nostra13.universalimageloader.utils.L;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SharedHelper {


    private static final String TAG ="wanda SharedHelper" ;
    private Context mContext;

    public SharedHelper() {
    }

    public SharedHelper(Context mContext) {
        this.mContext = mContext;
    }


    //保存album信息
    public void saveAlbum(List<MyAlbum> albums) {
        SharedPreferences sp = mContext.getSharedPreferences("myAlbum", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        Gson gson = new Gson();
        //list转换为json（string）
        String jsonStr = gson.toJson(albums);
        editor.putString("albumsList", jsonStr);
        editor.commit();
        Log.i(TAG,"信息已写入SharedPreference中");
//        Toast.makeText(mContext, "信息已写入SharedPreference中", Toast.LENGTH_SHORT).show();
    }

    //读取album信息
        public List<MyAlbum> readAlbum() {
        List<MyAlbum> albums=new ArrayList<MyAlbum>();
        Map<String, String> data = new HashMap<String,String>();
        SharedPreferences sp = mContext.getSharedPreferences("myAlbum", Context.MODE_PRIVATE);
        Log.i(TAG,sp.toString());
        String jsonStr=sp.getString("albumsList", "");
        Gson gson = new Gson();
        albums= gson.fromJson(jsonStr, new TypeToken<List<MyAlbum>>() {}.getType());
        return albums;
    }
    //定义一个保存数据的方法
    public void save(String username, String passwd) {
        SharedPreferences sp = mContext.getSharedPreferences("myuser", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("username", username);
        editor.putString("username"+username, username);
        editor.putString("passwd"+username, passwd);
//        editor.putString("autograph"+username, autograph);
        editor.commit();
        Toast.makeText(mContext, "信息已写入SharedPreference中", Toast.LENGTH_SHORT).show();
    }

    //定义一个读取SP文件的方法
    public Map<String, String> read(String username) {
        Map<String, String> data = new HashMap<String, String>();
        SharedPreferences sp = mContext.getSharedPreferences("myuser", Context.MODE_PRIVATE);
       data.put("username"+username, sp.getString("username"+username, ""));
        data.put("passwd"+username, sp.getString("passwd"+username, ""));
//        data.put("autograph"+username, sp.getString("autograph"+username, ""));
        return data;
    }
    //保存和读取用户名
    public void save_name(String username) {
        SharedPreferences sp = mContext.getSharedPreferences("myuser", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("_username", username);
        editor.commit();
       // Toast.makeText(mContext, "信息已写入SharedPreference中", Toast.LENGTH_SHORT).show();
    }
    public Map<String, String> read_name() {
        Map<String, String> data = new HashMap<String, String>();
        SharedPreferences sp = mContext.getSharedPreferences("myuser", Context.MODE_PRIVATE);
        data.put("_username", sp.getString("_username", ""));
        return data;
    }
    //保存和读取用户名
    public void save_album(String albumname) {
        SharedPreferences sp = mContext.getSharedPreferences("myuser", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("_albumName", albumname);
        editor.commit();
        // Toast.makeText(mContext, "信息已写入SharedPreference中", Toast.LENGTH_SHORT).show();
    }
    public Map<String, String> read_album() {
        Map<String, String> data = new HashMap<String, String>();
        SharedPreferences sp = mContext.getSharedPreferences("myuser", Context.MODE_PRIVATE);
        data.put("_albumName", sp.getString("_albumName", ""));
        return data;
    }
}
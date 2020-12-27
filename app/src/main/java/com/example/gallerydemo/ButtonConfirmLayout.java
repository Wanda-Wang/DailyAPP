package com.example.gallerydemo;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.util.AttributeSet;
import android.view.LayoutInflater;

import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.getbase.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import static androidx.core.app.ActivityCompat.startActivityForResult;
import static androidx.core.content.ContextCompat.startActivity;

public class ButtonConfirmLayout extends RelativeLayout{
    Bundle bundle = new Bundle();
    SaveService.SaveBinder mBinder=null;
    private static final int PHOTO_FROM_GALLERY = 1;
    private static final int PHOTO_FROM_CAMERA = 2;
    private FloatingActionButton buttonConfirm = null;
    private ArrayList<String> itemsSelectAlbum = new ArrayList<String>();
    public SDFileHelper sdFileHelper=new SDFileHelper(getContext());
    public static List<MyImage> mySelectedImageList=MyEditImageAdapter.mySelectedImageList;

    public ButtonConfirmLayout(final Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.button_confirm, this);
        bundle.putString("Data", "data from TestBundle");
        if(!itemsSelectAlbum.contains("新建相册")){
            itemsSelectAlbum.add("新建故事");
        }
        //监听浮动按键
        buttonConfirm = (FloatingActionButton) findViewById(R.id.button_to_gallery);
        buttonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                Toast.makeText(v.getContext(), "Click select button", Toast.LENGTH_SHORT).show();
                //注意将ArrayList转为String[]
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext())
                        .setIcon(R.mipmap.ic_launcher)
                        .setTitle("选择故事")
                        .setItems((String[]) itemsSelectAlbum.toArray(new String[itemsSelectAlbum.size()]), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (itemsSelectAlbum.get(which).equals(itemsSelectAlbum.get(0))) {
                                    final EditText editText = new EditText(v.getContext());
                                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext())
                                            .setIcon(R.mipmap.ic_launcher)
                                            .setTitle("相册名字")
                                            .setView(editText)
                                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    Toast.makeText(v.getContext(), "点击确定", Toast.LENGTH_SHORT).show();
                                                    itemsSelectAlbum.add(editText.getText().toString());
                                                    Intent intent =new Intent(getContext(),SaveService.class);
                                                    intent.putExtra("albumName",editText.getText().toString());
                                                    context.startService(intent);
                                                }
                                            })
                                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    Toast.makeText(v.getContext(), "点击取消", Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                    builder.create().show();
                                    Toast.makeText(v.getContext(), "点击" + itemsSelectAlbum.get(which),
                                            Toast.LENGTH_SHORT).show();
                                }

                            }
                        });
                builder.create().show();
            }
        });
    }
    public class SaveServiceCon implements ServiceConnection{

        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            mBinder=(SaveService.SaveBinder)iBinder;
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    }
}

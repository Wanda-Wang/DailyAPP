package com.example.df_daily.Layout;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.df_daily.AlbumActivity;
import com.example.df_daily.EditImageActivity;
import com.example.df_daily.Helper.SharedHelper;
import com.example.df_daily.MapActivity;
import com.example.df_daily.MapDrawActivity;
import com.example.df_daily.R;
import com.getbase.floatingactionbutton.FloatingActionButton;

import static androidx.core.content.ContextCompat.startActivity;

public class ButtonChangeLayout extends RelativeLayout {

    private final String TAG="wanda ButtonChangeLayout";
    Bundle bundle = new Bundle();
    private static final int PHOTO_FROM_GALLERY = 1;
    private static final int PHOTO_FROM_CAMERA = 2;
    private FloatingActionButton buttonSwitchView = null;
    SharedHelper sharedHelper;
    String albumName;

//    String albumName=AlbumActivity.as.get(0);
    AlbumBroadCastReciver albumBroadCastReciver;
    @SuppressLint("LongLogTag")
    public ButtonChangeLayout(Context context, AttributeSet attrs) {

        super(context, attrs);

        albumBroadCastReciver=new AlbumBroadCastReciver();
        LayoutInflater.from(context).inflate(R.layout.button_change, this);
        bundle.putString("Data", "data from TestBundle");
        buttonSwitchView = (FloatingActionButton) findViewById(R.id.button_switch_view);
        buttonSwitchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedHelper=new SharedHelper((Activity) v.getContext());
                albumName=sharedHelper.read_album().get("_albumName");
                Log.i(TAG,"NAME"+albumName);
                IntentFilter intentFilter=new IntentFilter();
//                intentFilter.addAction("android.albumName.cast.name");
//                (Activity) v.getContext().registerReceiver(albumBroadCastReciver,intentFilter);
                Intent intent = new Intent((Activity)v.getContext(), MapActivity.class);
                intent.putExtra("albumName",albumName);
                startActivity((Activity) v.getContext(), intent, bundle);
            }
        });
    }
    public class AlbumBroadCastReciver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
//            albumName=intent.getStringExtra("albumName");
        }
    }
}

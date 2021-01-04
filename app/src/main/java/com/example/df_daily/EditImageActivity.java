package com.example.df_daily;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.df_daily.Adapter.MyEditImageAdapter;
import com.example.df_daily.bean.MyImage;
import com.example.df_daily.ui.home.HomeFragment;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;

public class EditImageActivity extends AppCompatActivity {

    private static final int PHOTO_FROM_GALLERY = 1;
    private static final int PHOTO_FROM_CAMERA = 2;
    private ImageView imageView = null;
    private Button button = null;
    private List<MyImage> myImageList = HomeFragment.myImageList;
    private RecyclerView recyclerView = null;
    private StaggeredGridLayoutManager layoutManager = null;
    private MyEditImageAdapter adapter = null;
    private AlertDialog.Builder builder = null;
    private ArrayList<String> itemsSelectAlbum = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_image);
        initRecyclerView();
//        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.relativelayout_edit_image_activity);
//        relativeLayout.addView(View.inflate(this, R.layout.button_confirm, null));
    }

    @Override
    protected void onResume() {

//        initButton();
        super.onResume();
    }

    //从相册取图片
    public void gallery(View view) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PHOTO_FROM_GALLERY);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //第一层switch
        switch (requestCode) {
            case PHOTO_FROM_GALLERY:
                //第二层switch
                switch (resultCode) {
                    case RESULT_OK:
                        if (data != null) {
                            Uri uri = data.getData();
                            imageView.setImageURI(uri);
                        }
                        break;
                    case RESULT_CANCELED:
                        break;
                }
                break;
            case PHOTO_FROM_CAMERA:
                break;
            default:
                break;
        }
    }


    /**
     * 初始化控件
     */
    public void initRecyclerView(){
        //设置recyclerview
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview_edit_image);
        recyclerView.setHasFixedSize(true);
        //创建布局
        layoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        //设置布局管理器
        recyclerView.setLayoutManager(layoutManager);
        //初始化适配器
        adapter = new MyEditImageAdapter(this, myImageList,
                R.layout.item_recyclerview_edit_image, R.id.image_item_recyclerview_edit_image);

        adapter.setOnRecyclerItemClickListener(new MyEditImageAdapter.OnRecyclerItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
            }
        });
        adapter.setOnRecyclerItemLongClickListener(new MyEditImageAdapter.OnRecyclerItemLongClickListener() {
            @Override
            public void onLongItemClick(View view, int position) {
            }
        });
        //                {
//                    @Override
//                    public void onItemClick(View view, int position) {
//                        Toast.makeText(EditImageActivity.this, "点击了"+position+"项",
//                                Toast.LENGTH_SHORT).show();
//                        //跳转到ViewPager
//                        Intent intent = new Intent(EditImageActivity.this, ViewPagerActivity.class);
//                        intent.putExtra("position", position);
//                        startActivity(intent);
//                    }
//                }
        //设计动画并绑定适配器
        ScaleInAnimationAdapter scaleInAnimationAdapter = new ScaleInAnimationAdapter(adapter);
        scaleInAnimationAdapter.setFirstOnly(false);
        recyclerView.setAdapter(scaleInAnimationAdapter);

    }
//    //多余
//    public void initButton(){
//        if(!itemsSelectAlbum.contains("新建相册")){
//            itemsSelectAlbum.add("新建故事");
//        }
//        button = (Button) findViewById(R.id.button_select);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(v.getContext(), "Click select button", Toast.LENGTH_SHORT).show();
//                //注意将ArrayList转为String[]
//                builder = new AlertDialog.Builder(v.getContext())
//                        .setIcon(R.mipmap.ic_launcher)
//                        .setTitle("选择故事")
//                        .setItems((String[]) itemsSelectAlbum.toArray(new String[itemsSelectAlbum.size()]), new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                if (itemsSelectAlbum.get(which).equals(itemsSelectAlbum.get(0))) {
//                                    final EditText editText = new EditText(EditImageActivity.this);
//                                    AlertDialog.Builder builder = new AlertDialog.Builder(EditImageActivity.this)
//                                            .setIcon(R.mipmap.ic_launcher)
//                                            .setTitle("相册名字")
//                                            .setView(editText)
//                                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
//                                                @Override
//                                                public void onClick(DialogInterface dialog, int which) {
//                                                    Toast.makeText(EditImageActivity.this, "点击确定", Toast.LENGTH_SHORT).show();
//                                                    itemsSelectAlbum.add(editText.getText().toString());
//                                                }
//                                            })
//                                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
//                                                @Override
//                                                public void onClick(DialogInterface dialog, int which) {
//                                                    Toast.makeText(EditImageActivity.this, "点击取消", Toast.LENGTH_SHORT).show();
//                                                }
//                                            });
//                                    builder.create().show();
//                                    Toast.makeText(EditImageActivity.this, "点击" + itemsSelectAlbum.get(which),
//                                            Toast.LENGTH_SHORT).show();
//                                }
//
//                            }
//                        });
//                builder.create().show();
//            }
//        });
//    }


}
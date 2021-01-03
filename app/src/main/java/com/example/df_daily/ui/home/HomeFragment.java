package com.example.df_daily.ui.home;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.exifinterface.media.ExifInterface;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.baidu.mapapi.model.LatLng;
import com.example.df_daily.Adapter.MyMainImageAdapter;
import com.example.df_daily.Adapter.TimerAdapter;
import com.example.df_daily.Adapter.TraceListAdapter;
import com.example.df_daily.AlbumActivity;
import com.example.df_daily.Helper.SharedHelper;
import com.example.df_daily.ParallaxImageView;
import com.example.df_daily.R;
import com.example.df_daily.bean.MyAlbum;
import com.example.df_daily.bean.MyImage;
import com.example.df_daily.bean.Trace;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;


public class HomeFragment extends Fragment {

    private static final String TAG ="wanda HomeFragment";
    public static List<MyImage> myImageList = new ArrayList<>();
    final HashMap<String,List<MyImage>> allPhotosTemp = new HashMap<>();//所有照片
    public ArrayList<String> ExistedAlbum = new ArrayList<String>();
    private HomeViewModel homeViewModel;
    private ListView lvTrace;//时间轴ListView
    private List<Trace> traceList = new ArrayList<>(10);//模拟数据的list数据
    private TimerAdapter timerAdapter;//显示主页时间轴适配器
    SharedHelper sp;
    private List<MyAlbum> albums;//相册数据list
    private RecyclerView recyclerView = null;
    RecyclerView.LayoutManager layoutManager = null;
    RecyclerView.Adapter adapter = null;
    private TextView textView;//没有相册数据显示添加故事吧！


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
//        findView(root);
        sp=new SharedHelper(getActivity());
        albums = new ArrayList<MyAlbum>();
        textView=root.findViewById(R.id.tv);
        //设置recyclerview
        recyclerView = (RecyclerView) root.findViewById(R.id.recyclerview_main);
//        initData();
        initWidgets();
        requestWritePermission();
        getAllPhotoInfo();

        return root;
    }

    @Override
    public void onResume() {
        initData();
        adapter.notifyDataSetChanged();
//        timerAdapter.notifyDataSetChanged();
        super.onResume();
    }
    @Override
    public void onStop() {
        albums.clear();
        super.onStop();
    }


    //    private void findView(View view) {
//        lvTrace = view.findViewById(R.id.lvTrace);
//    }
    /**
     * 读取手机中所有图片信息
     */
    public void getAllPhotoInfo() {
        Uri imageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        String[] projImage = { MediaStore.Images.Media._ID,
                MediaStore.Images.Media.DATA,
                MediaStore.Images.Media.DISPLAY_NAME,
                MediaStore.Images.Media.TITLE,
                MediaStore.Images.Media.DATE_ADDED,
                MediaStore.Images.Media.DATE_MODIFIED,
                MediaStore.Images.Media.LATITUDE,
                MediaStore.Images.Media.LONGITUDE,
                MediaStore.Images.Media.SIZE};
        Cursor mCursor = getActivity().getContentResolver().query(imageUri,
                projImage,
                MediaStore.Images.Media.MIME_TYPE + "=? or " + MediaStore.Images.Media.MIME_TYPE + "=?",
                new String[]{"image/jpeg", "image/png"},
                MediaStore.Images.Media.DATE_MODIFIED+" desc");

        if(mCursor!=null){
            while (mCursor.moveToNext()) {
                String path = mCursor.getString(mCursor.getColumnIndex(MediaStore.Images.Media.DATA));
                String displayName = mCursor.getString(mCursor.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME));
                float size = mCursor.getInt(mCursor.getColumnIndex(MediaStore.Images.Media.SIZE))/1024;
                float latitude = mCursor.getFloat(mCursor.getColumnIndex(MediaStore.Images.Media.LATITUDE));
                float longitude = mCursor.getFloat(mCursor.getColumnIndex(MediaStore.Images.Media.LONGITUDE));
                Log.i(TAG,"经纬度："+latitude+","+longitude);
                //用于展示相册初始化界面
                myImageList.add(new MyImage(size,path,displayName,latitude,longitude));
                // 获取该图片的父路径名
                String dirPath = new File(path).getParentFile().getAbsolutePath();
                //存储对应关系
                if (allPhotosTemp.containsKey(dirPath)) {
                    List<MyImage> data = allPhotosTemp.get(dirPath);
                    data.add(new MyImage(size, path, displayName,latitude,longitude));
                    continue;
                } else {
                    List<MyImage> data = new ArrayList<>();
                    data.add(new MyImage(size, path, displayName,latitude,longitude));
                    allPhotosTemp.put(dirPath,data);
                }
            }
            mCursor.close();
        }
    }
    /**
     * 初始化控件
     */
    private void initWidgets(){

        recyclerView.setHasFixedSize(true);
        //创建线性布局
        layoutManager = new LinearLayoutManager(getActivity());
        //设置布局管理器
        recyclerView.setLayoutManager(layoutManager);
        //初始化适配器
        adapter = new MyMainImageAdapter(getActivity(), albums,
                R.layout.item_recyclerview_main, R.id.image_item_recyclerview_main,R.id.dateItem,R.id.AlbumNameItem,
                new MyMainImageAdapter.OnRecyclerItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Toast.makeText(getContext(), "点击了"+position+"项", Toast.LENGTH_SHORT).show();
                        //跳转到相簿
//                        Intent intent = new Intent(getActivity(), AlbumActivity.class);
//                        startActivity(intent);
                        Intent intent=new Intent(getActivity(), AlbumActivity.class);
                        intent.putExtra("first",albums.get(position).getFirstPhotoPath());
                        intent.putExtra("albumName",albums.get(position).getAlbumName());
                        intent.putExtra("buildDate",getStringDateShort(albums.get(position).getBuildDate()));
                        Log.i(TAG,"albumName:"+albums.get(position).getAlbumName());
                        startActivity(intent);
                    }
                });
        //添加动画并绑定适配器
        ScaleInAnimationAdapter scaleInAnimationAdapter = new ScaleInAnimationAdapter(adapter);
        scaleInAnimationAdapter.setFirstOnly(false);
        recyclerView.setAdapter(scaleInAnimationAdapter);
//        recyclerView.setAdapter(adapter);
        recyclerView.setTag(ParallaxImageView.RECYCLER_VIEW_TAG);
//        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//                if(recyclerviewHeight == -1){
//                    recyclerviewHeight = recyclerView.getHeight();
//                    recyclerView.getLocationOnScreen(recyclerviewLocation);
//
//                }
//
//            }
//        });
    }
    /**
     * 获取现在时间
     *
     * @return 返回短时间字符串格式yyyy-MM-dd
     */
    public static String getStringDateShort(Date currentTime) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = formatter.format(currentTime);
        return dateString;
    }
    private void initData() {
        // 模拟一些假的数据
        traceList.add(new Trace("2016-05-25 17:48:00", "[沈阳市] [沈阳和平五部]的派件已签收 感谢使用中通快递,期待再次为您服务!"));
        traceList.add(new Trace("2016-05-25 14:13:00", "[沈阳市] [沈阳和平五部]的东北大学代理点正在派件 电话:18040xxxxxx 请保持电话畅通、耐心等待"));
        traceList.add(new Trace("2016-05-25 13:01:04", "[沈阳市] 快件到达 [沈阳和平五部]"));
        traceList.add(new Trace("2016-05-25 12:19:47", "[沈阳市] 快件离开 [沈阳中转]已发往[沈阳和平五部]"));
        traceList.add(new Trace("2016-05-25 11:12:44", "[沈阳市] 快件到达 [沈阳中转]"));
        traceList.add(new Trace("2016-05-24 03:12:12", "[嘉兴市] 快件离开 [杭州中转部]已发往[沈阳中转]"));
        traceList.add(new Trace("2016-05-23 21:06:46", "[杭州市] 快件到达 [杭州汽运部]"));
        traceList.add(new Trace("2016-05-23 18:59:41", "[杭州市] 快件离开 [杭州乔司区]已发往[沈阳]"));
        traceList.add(new Trace("2016-05-23 18:35:32", "[杭州市] [杭州乔司区]的市场部已收件 电话:18358xxxxxx"));

            String pathStr=Environment.getExternalStorageDirectory() + File.separator+ ".photo"+ File.separator;
            File path=new File(pathStr);
            File[] files = path.listFiles();// 读取文件夹下文件
        //如果sp里存有album数据，就读取
        if(sp.readAlbum()!=null&&files!=null){
            albums.addAll(sp.readAlbum());
        }
        //将album倒序排列
        Collections.reverse(albums);
//        timerAdapter=new TimerAdapter(getActivity(),albums);
//        lvTrace.setAdapter(timerAdapter);
        if(albums.size()>0){
            textView.setText("");
        }
//        //设置listview点击事件
//        lvTrace.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
////                Book book = bookList.get(i);
////                Toast.makeText(ListViewActivity.this,book.toString(),Toast.LENGTH_LONG).show();
//                Intent intent=new Intent(getActivity(), AlbumActivity.class);
//                intent.putExtra("albumName",albums.get(i).getAlbumName());
//                Log.i(TAG,"albumName:"+albums.get(i).getAlbumName());
//                startActivity(intent);
//            }
//        });

    }
    private void requestWritePermission(){
        //动态获取GPS定位权限
        List<String> permissionList = new ArrayList<>();
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.READ_PHONE_STATE);
        }
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (!permissionList.isEmpty()) {
            String[] permissions = permissionList.toArray(new String[permissionList.size()]);
            ActivityCompat.requestPermissions(getActivity(), permissions, 1);
        }
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
        }
    }
    public LatLng getPhotoLocation(String imagePath){
        float output1=0.0f;
        float output2=0.0f;
        LatLng latLng=null;
        try {
            ExifInterface exifInterface= new ExifInterface(imagePath);
            String datetime = exifInterface.getAttribute(ExifInterface.TAG_DATETIME);// 拍摄时间
            String deviceName = exifInterface.getAttribute(ExifInterface.TAG_MAKE);// 设备品牌
            String deviceModel = exifInterface.getAttribute(ExifInterface.TAG_MODEL); // 设备型号
            String latValue = exifInterface.getAttribute(ExifInterface.TAG_GPS_LATITUDE);
            String lngValue = exifInterface.getAttribute(ExifInterface.TAG_GPS_LONGITUDE);
            String latRef = exifInterface.getAttribute(ExifInterface.TAG_GPS_LATITUDE_REF);
            String lngRef = exifInterface.getAttribute
                    (ExifInterface.TAG_GPS_LONGITUDE_REF);
            if (latValue != null && latRef != null && lngValue != null && lngRef != null) {
                try {
                    output1 = convertRationalLatLonToFloat(latValue, latRef);
                    Log.i(TAG, String.valueOf(output1));
                    output2 = convertRationalLatLonToFloat(lngValue, lngRef);
                    Log.i(TAG, String.valueOf(output2));
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                }
            }
            Log.i(TAG, String.valueOf(output1));
            Log.i(TAG, String.valueOf(output2));
//            Toast.makeText(AlbumActivity.this, deviceName + ":" + deviceModel, Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
        }

//        Toast.makeText(getBaseContext(), output1 + ";" + output2 , Toast.LENGTH_LONG).show();

        latLng = new LatLng(output1 , output1 );
        return latLng;
    }
    private static float convertRationalLatLonToFloat(
            String rationalString, String ref) {

        String[] parts = rationalString.split(",");

        String[] pair;
        pair = parts[0].split("/");
        double degrees = Double.parseDouble(pair[0].trim())
                / Double.parseDouble(pair[1].trim());

        pair = parts[1].split("/");
        double minutes = Double.parseDouble(pair[0].trim())
                / Double.parseDouble(pair[1].trim());

        pair = parts[2].split("/");
        double seconds = Double.parseDouble(pair[0].trim())
                / Double.parseDouble(pair[1].trim());

        double result = degrees + (minutes / 60.0) + (seconds / 3600.0);
        if ((ref.equals("S") || ref.equals("W"))) {
            return (float) -result;
        }
        return (float) result;
    }
}
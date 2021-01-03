package com.example.df_daily;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.Overlay;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.Polyline;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.map.UiSettings;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.LatLngBounds;
import com.example.df_daily.Helper.DbController;
import com.example.df_daily.bean.PhotoInfo;
import com.nostra13.universalimageloader.utils.L;

import java.util.ArrayList;
import java.util.List;

public class MapActivity extends AppCompatActivity {

    private String TAG="wanda MapActivity";
    private MapView mMapView = null;
    private BaiduMap mBaiduMap = null;
    private LocationClient mLocationClient = null;
    private UiSettings mUiSettings = null;
    private double mCurrentLatitude;
    private double mCurrentLongitude;
    boolean isFisrtLocation = true;
    private BitmapDescriptor bitmapA = null;
    private Marker mMarker = null;
    private Polyline mPolyline2;
    private Handler mHandler;
    // 通过设置间隔时间和距离可以控制速度和图标移动的距离
    private static final int TIME_INTERVAL = 80;
    private static final double DISTANCE = 0.00250;
    private ArrayList<LatLng> latLngs2 = new ArrayList<>();
    List<LatLng> points;
    private DbController mDbController;
//    private PhotoInfo photoInfo;
    private List<PhotoInfo> photoInfos;
    double latitude;    //获取纬度信息
    double longitude;

    double latitude1;    //获取纬度信息
    double longitude1;

    private String cityName,weather1;
    private MyLocationListener myListener = new MyLocationListener();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        mDbController = DbController.getInstance(MapActivity.this);
        mHandler = new Handler(Looper.getMainLooper());

        mMapView = (MapView) findViewById(R.id.bmapView);
        mLocationClient = new LocationClient(getApplicationContext());
        mLocationClient.registerLocationListener(myListener); //注册监听函数
        initLocation();
        mLocationClient.start();
        //地图控制器对象
        mBaiduMap = mMapView.getMap();
        mBaiduMap.setMyLocationEnabled(true);
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);

        //实例uiSettings
        mUiSettings = mBaiduMap.getUiSettings();

        //指南针
        mUiSettings.setCompassEnabled(false);

        //定位使能
        mBaiduMap.setMyLocationEnabled(true);

        //定位初始化
        mLocationClient = new LocationClient(this);

        //LocationClient参数
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true);
        option.setCoorType("bd09ll");
        option.setScanSpan(1000);
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        mLocationClient.setLocOption(option);

        //动态获取GPS定位权限
        List<String> permissionList = new ArrayList<>();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.READ_PHONE_STATE);
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (!permissionList.isEmpty()) {
            String[] permissions = permissionList.toArray(new String[permissionList.size()]);
            ActivityCompat.requestPermissions(this, permissions, 1);
        }

//        //注册LocationListener监听器
//        MyLocationListener myLocationListener = new MyLocationListener();
//        mLocationClient.registerLocationListener(myLocationListener);

        //LocationConfiguration参数

        //开启定位
        mLocationClient.start();
        //注册MarkerClick监听器
        mBaiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Log.d("click", "click");
                Toast.makeText(MapActivity.this, "Click photo marker", Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        drawLine();

    }



    public void drawLine(){
        points= new ArrayList<LatLng>();
        Intent intent=getIntent();
        LatLngBounds.Builder tempBounds = new LatLngBounds.Builder();
        final String albumName=intent.getStringExtra("albumName");
//                photoInfos=mDbController.searchAll();
        photoInfos=mDbController.searchByAlbumName(albumName);
        for(PhotoInfo photoInfo:photoInfos){
//            if(photoInfo.getAlbumName()==albumName){
            mDbController.insertOrReplace(photoInfo);
            if(photoInfo.getLatitude()!=0&&photoInfo.getLongitude()!=0){
                points.add(new LatLng(photoInfo.getLatitude(),photoInfo.getLongitude()));
                tempBounds.include(new LatLng(photoInfo.getLatitude(),photoInfo.getLongitude()));
            }
            else{
                continue;
            }

//            }
        }


//        //构建折线点坐标
//        LatLng p1 = new LatLng(39.97923, 116.357428);
//        tempBounds.include(p1);
//        LatLng p2 = new LatLng(39.94923, 116.397428);
//        tempBounds.include(p2);
//        LatLng p3 = new LatLng(39.97923, 116.437428);
//        tempBounds.include(p3);
//
//        points.add(p1);
//        points.add(p2);
//        points.add(p3);

        BitmapDescriptor blueArrow = BitmapDescriptorFactory
                .fromResource(R.mipmap.icon_road_blue_arrow);
        BitmapDescriptor grayArrow = BitmapDescriptorFactory
                .fromResource(R.mipmap.icon_road_gray_arrow);
        BitmapDescriptor che = BitmapDescriptorFactory
                .fromResource(R.mipmap.che);
        BitmapDescriptor qi = BitmapDescriptorFactory
                .fromResource(R.mipmap.qi);
        BitmapDescriptor zhong = BitmapDescriptorFactory
                .fromResource(R.mipmap.zhong);

        if(points.size()<2){

            //构建Marker图标
            BitmapDescriptor bitmap = BitmapDescriptorFactory
                    .fromResource(R.drawable.icon_gcoding);
            OverlayOptions option = new MarkerOptions();
//构建MarkerOption，用于在地图上添加Marker
            if(points.size()==0){
                ((MarkerOptions) option)
                        .position(new LatLng(29.599655,106.29403))
                        .icon(bitmap);
            }else{
                ((MarkerOptions) option)
                        .position(points.get(0))
                        .icon(bitmap);
            }

//在地图上添加Marker，并显示
            mBaiduMap.addOverlay(option);
            LatLngBounds mBaiduLatLngBounds = tempBounds.build();
            mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newLatLngBounds(mBaiduLatLngBounds));
        }
        else{
            //控制线条的宽度样式等(画线)
            PolylineOptions ooPolyline = new PolylineOptions().customTexture(blueArrow).width(20).dottedLine(true).points(points);
            PolylineOptions ooPolyline2 = new PolylineOptions().customTexture(grayArrow).width(20).dottedLine(true).points(points);
            //在地图上批量添加(把灰线绘制入百度地图中)
            mBaiduMap.addOverlay(ooPolyline);
            //在地图上批量添加(把蓝线绘制入百度地图中)
            mPolyline2 = (Polyline) mBaiduMap.addOverlay(ooPolyline2);
//        //设置折线的属性
//        OverlayOptions mOverlayOptions = new PolylineOptions()
//                .width(10)
//                .color(0xAAFF0000)
//                .points(points);
            //在地图上绘制折线
            //mPloyline 折线对象
//        Overlay mPolyline = mBaiduMap.addOverlay(mOverlayOptions);

            //拿到集合的第一个位置
            LatLng pointQi =new LatLng(photoInfos.get(0).getLatitude(),photoInfos.get(0).getLongitude());
            //画起点的图标(也就是集合的第一个位置就是起点)
            OverlayOptions optionQi = new MarkerOptions().position(pointQi).icon(qi);
            //拿到集合最后一个位置
            LatLng pointZhong =new LatLng(photoInfos.get(photoInfos.size()-1).getLatitude(),photoInfos.get(photoInfos.size()-1).getLongitude());;
            //画终点的图标(也就是集合的最后一个位置就是终点)
            OverlayOptions optionZhong = new MarkerOptions().position(pointZhong).icon(zhong);
            //拿到集合的第一个位置
            LatLng pointChe =new LatLng(photoInfos.get(0).getLatitude(),photoInfos.get(0).getLongitude());;
            //画车辆的图标(因为车辆是从第一个位置开始行驶的,所以车辆的初始位置是第一个)
            MarkerOptions markerOptions = new MarkerOptions().position(pointChe).icon(che);

            //创建OverlayOptions的集合(把起点终点和车辆绘制在地图上)
            List<OverlayOptions> options = new ArrayList<>();
            options.add(optionQi);
            options.add(optionZhong);
            mMarker = (Marker) mBaiduMap.addOverlay(markerOptions);
            //(绘制在地图上)
            mBaiduMap.addOverlays(options);
            LatLngBounds mBaiduLatLngBounds = tempBounds.build();
            mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newLatLngBounds(mBaiduLatLngBounds));
            //循环移动车辆位置
            moveLooper();
        }

    }

    private void initLocation() {

        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        /**可选，设置定位模式，默认高精度LocationMode.Hight_Accuracy：高精度；
         * LocationMode. Battery_Saving：低功耗；LocationMode. Device_Sensors：仅使用设备；*/

        option.setCoorType("gcj02gcj02");
        /**可选，设置返回经纬度坐标类型，默认gcj02gcj02：国测局坐标；bd09ll：百度经纬度坐标；bd09：百度墨卡托坐标；
         海外地区定位，无需设置坐标类型，统一返回wgs84类型坐标*/

        option.setScanSpan(3000);
        /**可选，设置发起定位请求的间隔，int类型，单位ms如果设置为0，则代表单次定位，即仅定位一次，默认为0如果设置非0，需设置1000ms以上才有效*/

        option.setOpenGps(true);
        /**可选，设置是否使用gps，默认false使用高精度和仅用设备两种定位模式的，参数必须设置为true*/

        option.setLocationNotify(true);
/**可选，设置是否当GPS有效时按照1S/1次频率输出GPS结果，默认false*/

        option.setIgnoreKillProcess(false);
        /**定位SDK内部是一个service，并放到了独立进程。设置是否在stop的时候杀死这个进程，默认（建议）不杀死，即setIgnoreKillProcess(true)*/

        option.SetIgnoreCacheException(false);
        /**可选，设置是否收集Crash信息，默认收集，即参数为false*/
        option.setIsNeedAltitude(true);/**设置海拔高度*/

        option.setWifiCacheTimeOut(5 * 60 * 1000);
        /**可选，7.2版本新增能力如果设置了该接口，首次启动定位时，会先判断当前WiFi是否超出有效期，若超出有效期，会先重新扫描WiFi，然后定位*/

        option.setEnableSimulateGps(false);
        /**可选，设置是否需要过滤GPS仿真结果，默认需要，即参数为false*/

        option.setIsNeedAddress(true);
        /**可选，设置是否需要地址信息，默认不需要*/

        mLocationClient.setLocOption(option);
        /**mLocationClient为第二步初始化过的LocationClient对象需将配置好的LocationClientOption对象，通过setLocOption方法传递给LocationClient对象使用*/
    }

    public class MyLocationListener extends BDAbstractLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            //此处的BDLocation为定位结果信息类，通过它的各种get方法可获取定位相关的全部结果
            //以下只列举部分获取经纬度相关（常用）的结果信息
            //更多结果信息获取说明，请参照类参考中BDLocation类中的说明

            latitude = location.getLatitude();    //获取纬度信息
            longitude = location.getLongitude();    //获取经度信息
            Log.i("wanda TAG",latitude+" "+longitude);
            float radius = location.getRadius();    //获取定位精度，默认值为0.0f
            cityName = location.getCity();
            //获取经纬度坐标类型，以LocationClientOption中设置过的坐标类型为准
            String coorType = location.getCoorType();
            int errorCode = location.getLocType();
            //获取定位类型、定位错误返回码，具体信息可参照类参考中BDLocation类中的说明

        }

    }
        /**
     * 计算x方向每次移动的距离
     */
    private double getXMoveDistance(double slope) {
        if (slope == Double.MAX_VALUE) {
            return DISTANCE;
        }
        return Math.abs((DISTANCE * slope) / Math.sqrt(1 + slope * slope));
    }

    /**
     * 循环进行移动逻辑
     */
    public void moveLooper() {
        //为了不阻塞ui线程所以开启子线程
        new Thread(new Runnable() {
            @Override
            public void run() {
                //死循环
                while (true) {
                    for (int i = 0; i < points.size() - 1; i++) {
                        final LatLng startPoint = points.get(i);
                        final LatLng endPoint = points.get(i + 1);
                        mMarker
                                .setPosition(startPoint);

                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                // refresh marker's rotate
                                if (mMapView == null) {
                                    return;
                                }
                                mMarker.setRotate((float) getAngle(startPoint,
                                        endPoint));
                            }
                        });
                        double slope = getSlope(startPoint, endPoint);
                        // 是不是正向的标示
                        boolean isReverse = (startPoint.latitude > endPoint.latitude);

                        double intercept = getInterception(slope, startPoint);

                        double xMoveDistance = isReverse ? getXMoveDistance(slope) :
                                -1 * getXMoveDistance(slope);


                        for (double j = startPoint.latitude; !((j > endPoint.latitude) ^ isReverse);
                             j = j - xMoveDistance) {
                            LatLng latLng;
                            if (slope == Double.MAX_VALUE) {
                                latLng = new LatLng(j, startPoint.longitude);
                            } else {
                                latLng = new LatLng(j, (j - intercept) / slope);
                            }
                            final LatLng finalLatLng = latLng;
                            mHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    if (mMapView == null) {
                                        return;
                                    }
                                    mMarker.setPosition(finalLatLng);
                                    latLngs2.add(startPoint);
                                    latLngs2.add(finalLatLng);
                                    mPolyline2.setPoints(latLngs2);
                                }
                            });
                            try {
                                Thread.sleep(TIME_INTERVAL);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }

                    }
                }
            }
        }).start();
    }

    /**
     * 根据两点算取图标转的角度
     */
    private double getAngle(LatLng fromPoint, LatLng toPoint) {
        double slope = getSlope(fromPoint, toPoint);
        if (slope == Double.MAX_VALUE) {
            if (toPoint.latitude > fromPoint.latitude) {
                return 0;
            } else {
                return 180;
            }
        }
        float deltAngle = 0;
        if ((toPoint.latitude - fromPoint.latitude) * slope < 0) {
            deltAngle = 180;
        }
        double radio = Math.atan(slope);
        return 180 * (radio / Math.PI) + deltAngle - 90;
    }

    /**
     * 根据点和斜率算取截距
     */
    private double getInterception(double slope, LatLng point) {

        return point.latitude - slope * point.longitude;
    }

    /**
     * 算斜率
     */
    private double getSlope(LatLng fromPoint, LatLng toPoint) {
        if (toPoint.longitude == fromPoint.longitude) {
            return Double.MAX_VALUE;
        }
        return ((toPoint.latitude - fromPoint.latitude) / (toPoint.longitude - fromPoint.longitude));

    }
    /***********************************************************************
     @Description 添加标记点
     @param latLng 经纬度
     ***********************************************************************/
    public void addMarker(LatLng latLng){
        if (0.0 == latLng.latitude || 0.0 == latLng.longitude) return;


        //获得自定义控件转换的Bitmap
        bitmapA = BitmapDescriptorFactory.fromResource(R.drawable.icon_gcoding);

//        //MakerOptions参数
//        MarkerOptions makerOptions = new MarkerOptions()
//                .position(latLng)
//                .yOffset(300)
//                .icon(bitmapA)
//                .draggable(true)
//                .scaleX((float) 1)
//                .scaleY((float) 1);
//        mMarker = (Marker) mBaiduMap.addOverlay(makerOptions);
    }

    /***********************************************************************
     @Description 将view转换成Bitmap
     @param addViewContent 图片View
     @return bitmap 图片Bitmap
     ***********************************************************************/
    private Bitmap getViewBitmap(View addViewContent){
        addViewContent.setDrawingCacheEnabled(true);
        addViewContent.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
                , View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        addViewContent.layout(0, 0, addViewContent.getMeasuredWidth()
                , addViewContent.getMeasuredHeight());

        addViewContent.buildDrawingCache();
        Bitmap cacheBitmap = addViewContent.getDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(cacheBitmap);

        return bitmap;
    }

//    /***********************************************************************
//     @Description 定位监听
//     ***********************************************************************/
//    public class MyLocationListener extends BDAbstractLocationListener {
//        @Override
//        public void onReceiveLocation(BDLocation bdLocation) {
//            if(bdLocation == null || mMapView == null){
//                return;
//            }
//            MyLocationData locData = new MyLocationData.Builder()
//                    .accuracy(bdLocation.getRadius())
//                    .direction(bdLocation.getDirection())
//                    .latitude(bdLocation.getLatitude())
//                    .longitude(bdLocation.getLongitude())
//                    .build();
//            mBaiduMap.setMyLocationData(locData);
//            //Log.d("Location", "latitude:"+Double.toString(bdLocation.getLatitude()));
//            //Log.d("Location", "longitude"+Double.toString(bdLocation.getLongitude()));
//
//            //初始化聚焦到定位点
//            if (isFisrtLocation){
//                isFisrtLocation = false;
//                LatLng latLng = new LatLng(bdLocation.getLatitude(), bdLocation.getLongitude());
//                MapStatus.Builder builder = new MapStatus.Builder();
//                builder.target(latLng).zoom(20.0f);
//                mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
//                addMarker(latLng);
//            }
//        }
//    }

    @Override
    protected void onResume() {
        mMapView.onResume();
        super.onResume();
    }

    @Override
    protected void onPause() {
        mMapView.onPause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        mLocationClient.stop();
        mBaiduMap.setMyLocationEnabled(false);
        mMapView.onDestroy();
        mMapView = null;
        super.onDestroy();
    }
}
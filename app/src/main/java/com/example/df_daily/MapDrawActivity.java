package com.example.df_daily;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.Polyline;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.LatLngBounds;

import java.util.ArrayList;
import java.util.List;

public class MapDrawActivity extends AppCompatActivity {
    private MapView mMapView;
    private Polyline mPolyline2;
    private Handler mHandler;
    private ArrayList<LatLng> latLngs;
    private Marker mMoveMarker;
    // 通过设置间隔时间和距离可以控制速度和图标移动的距离
    private static final int TIME_INTERVAL = 80;
    private static final double DISTANCE = 0.00250;
    private ArrayList<LatLng> latLngs2 = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_draw);
        //初始化控件
        mMapView = findViewById(R.id.bmapView_draw);
        BaiduMap mBaiduMap = mMapView.getMap();


//        //处理数据源,把assets中的文件数据转化成一个装有位置的集合
//        List<Data> location = JsonReadUtil.fromJsonArray2(this, "test.json");
//        latLngs = new ArrayList<>();
//        LatLngBounds.Builder tempBounds = new LatLngBounds.Builder();
//        for (int i = 0; i < location.size(); i++) {
//            LatLng latLng = new LatLng(location.get(i).baidulat, location.get(i).baidulng);
//            latLngs.add(latLng);
//            tempBounds.include(latLng);
//        }
        latLngs=new ArrayList<LatLng>();
        latLngs.add(new LatLng(116.51172,39.92123));
        latLngs.add(new LatLng(116.51135,39.93883));
        latLngs.add(new LatLng(116.51627,39.91034));
        latLngs.add(new LatLng(116.47191,39.90577));
        latLngs.add(new LatLng(116.49625,39.9146));

        LatLngBounds.Builder tempBounds = new LatLngBounds.Builder();
        for (int i = 0; i < latLngs.size(); i++) {
            tempBounds.include(latLngs.get(i));
        }

        mHandler = new Handler(Looper.getMainLooper());

        //五个需要绘制在地图上的图标
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

        //控制线条的宽度样式等(画线)
        PolylineOptions ooPolyline = new PolylineOptions().customTexture(blueArrow).width(20).dottedLine(true).points(latLngs);
        PolylineOptions ooPolyline2 = new PolylineOptions().customTexture(grayArrow).width(20).dottedLine(true).points(latLngs);
        //在地图上批量添加(把灰线绘制入百度地图中)
        mBaiduMap.addOverlay(ooPolyline);
        //在地图上批量添加(把蓝线绘制入百度地图中)
        mPolyline2 = (Polyline) mBaiduMap.addOverlay(ooPolyline2);

        //拿到集合的第一个位置
        LatLng pointQi = latLngs.get(0);
        //画起点的图标(也就是集合的第一个位置就是起点)
        OverlayOptions optionQi = new MarkerOptions().position(pointQi).icon(qi);
        //拿到集合最后一个位置
        LatLng pointZhong = latLngs.get(latLngs.size()-1);
        //画终点的图标(也就是集合的最后一个位置就是终点)
        OverlayOptions optionZhong = new MarkerOptions().position(pointZhong).icon(zhong);
        //拿到集合的第一个位置
        LatLng pointChe = latLngs.get(0);
        //画车辆的图标(因为车辆是从第一个位置开始行驶的,所以车辆的初始位置是第一个)
        MarkerOptions markerOptions = new MarkerOptions().position(pointChe).icon(che);

        //创建OverlayOptions的集合(把起点终点和车辆绘制在地图上)
        List<OverlayOptions> options = new ArrayList<>();
        options.add(optionQi);
        options.add(optionZhong);
        mMoveMarker = (Marker) mBaiduMap.addOverlay(markerOptions);
        //(绘制在地图上)
        mBaiduMap.addOverlays(options);

        LatLngBounds mBaiduLatLngBounds = tempBounds.build();
        mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newLatLngBounds(mBaiduLatLngBounds));
        //循环移动车辆位置
        moveLooper();
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
                    for (int i = 0; i < latLngs.size() - 1; i++) {
                        final LatLng startPoint = latLngs.get(i);
                        final LatLng endPoint = latLngs.get(i + 1);
                        mMoveMarker
                                .setPosition(startPoint);

                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                // refresh marker's rotate
                                if (mMapView == null) {
                                    return;
                                }
                                mMoveMarker.setRotate((float) getAngle(startPoint,
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
                                    mMoveMarker.setPosition(finalLatLng);
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

}
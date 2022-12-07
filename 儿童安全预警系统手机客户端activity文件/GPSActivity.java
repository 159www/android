package com.example.ok;

import static com.amap.api.maps.AMapUtils.calculateLineDistance;
import static com.amap.api.maps.MapsInitializer.updatePrivacyAgree;
import static com.amap.api.maps.MapsInitializer.updatePrivacyShow;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.CoordinateConverter;
import com.amap.api.location.DPoint;
import com.amap.api.maps.AMapException;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Poi;
import com.amap.api.navi.AMapNavi;
import com.amap.api.navi.AmapNaviPage;
import com.amap.api.navi.AmapNaviParams;
import com.amap.api.navi.AmapNaviType;
import com.amap.api.navi.AmapPageType;
import com.amap.api.navi.enums.TravelStrategy;
import com.amap.api.navi.model.AMapNaviPath;
import com.amap.api.navi.model.NaviLatLng;
import com.amap.api.navi.model.NaviPoi;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GPSActivity extends AppCompatActivity {
    private int distance_limitation=100;//距离阈值
    private String source_longitude;//本机经度
    private String source_latitude;//本机纬度
    private String destination_longitude=null;//目标经度
    private String destination_latitude=null;//目标纬度
    private double destination_longitude_convert;//转换后的目标经度
    private double destination_latitude_convert;//转换后的目标纬度
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gpsactivity);
        TextView source_longitude_view=findViewById(R.id.gps_activity_source_longitude_text_view);//本机经度展示
        TextView source_latitude_view=findViewById(R.id.gps_activity_source_latitude_text_view);//本机纬度展示
        TextView destination_longitude_view=findViewById(R.id.gps_activity_destination_longitude_text_view);//目的经度展示
        TextView destination_latitude_view=findViewById(R.id.gas_activity_destination_latitude_text_view);//目的纬度展示
        TextView current_distance_text_view=findViewById(R.id.gps_activity_distance_text_view);//当前直线距离展示
        EditText distance_limitation_edit=findViewById(R.id.gps_activity_distance_limitation_edit);//距离阈值设置
        Button  distance_limitation_set_button=findViewById(R.id.gps_activity_distance_limitation_set_button);//距离阈值设置按钮
        Button  start_navigation_button=findViewById(R.id.gps_activity_start_navigation_button);//启动导航组件按钮
        //声明AMapLocationClient类对象
        AMapLocationClient mLocationClient = null;
        //声明定位回调监听器
        AMapLocationListener mLocationListener = new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation aMapLocation) {
                if (aMapLocation != null) {
                    if (aMapLocation.getErrorCode() == 0)
                    {
                        //可在其中解析amapLocation获取相应内容。
                        source_longitude  = Double.toString(aMapLocation.getLongitude());
                        source_longitude_view.setText("经度："+source_longitude);
                        source_latitude=Double.toString(aMapLocation.getLatitude());
                        source_latitude_view.setText("纬度："+source_latitude);
                        //目标点经纬度已成功获取
                        if(destination_latitude.isEmpty()==false&&destination_longitude.isEmpty()==false)
                        {
                            //起点
                            LatLng latLng_start=new LatLng(Double.parseDouble(source_latitude),Double.parseDouble(source_longitude));
                            //终点
                            LatLng latLng_end=new LatLng(Double.parseDouble(destination_latitude),Double.parseDouble(destination_longitude));
                            int length=(int)calculateLineDistance(latLng_start, latLng_end);
                            current_distance_text_view.setText(Integer.toString(length));
                        }
                    }else {
                        //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                        Log.e("AmapError","location Error, ErrCode:"
                                + aMapLocation.getErrorCode() + ", errInfo:"
                                + aMapLocation.getErrorInfo());
                    }
                }

            }
        };
        try {
            mLocationClient = new AMapLocationClient(getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }
        //设置定位回调监听
        mLocationClient.setLocationListener(mLocationListener);
        AMapLocationClientOption option = new AMapLocationClientOption();
        /**
         * 设置定位场景，目前支持三种场景（签到、出行、运动，默认无场景）
         */
        option.setLocationPurpose(AMapLocationClientOption.AMapLocationPurpose.Sport);
        if(null != mLocationClient){
            mLocationClient.setLocationOption(option);
            //设置场景模式后最好调用一次stop，再调用start以保证场景模式生效
            mLocationClient.stopLocation();
            mLocationClient.startLocation();
        }
        //设置定位模式为AMapLocationMode.Hight_Accuracy，高精度模式。
        option.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置定位间隔,单位毫秒,默认为2000ms，最低1000ms。
        option.setInterval(1000);
        //设置是否返回地址信息（默认返回地址信息）
        option.setNeedAddress(true);
        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(option);
        //启动定位
        mLocationClient.startLocation();
        // 距离阈值设置按钮监听
        distance_limitation_set_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                //获取距离阈值编辑框中的字符串
                distance_limitation=Integer.parseInt(distance_limitation_edit.getText().toString());
                Toast.makeText(GPSActivity.this, "距离阈值设置成功", Toast.LENGTH_SHORT).show();
            }
        });
        //开始导航按钮监听
        start_navigation_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                //
                updatePrivacyShow(getApplicationContext(),true,true);
                //隐私权政策是否取得用户同意  true是用户同意
                updatePrivacyAgree(getApplicationContext(),true);
                //起点
                Poi start = new Poi("本机位置", new LatLng(Double.parseDouble(source_latitude),Double.parseDouble(source_longitude)),"");
             //   Poi end = new Poi("目标位置", new LatLng(Double.parseDouble(destination_latitude),Double.parseDouble(destination_longitude)),"");
                //坐标转换
                CoordinateConverter converter  = new CoordinateConverter(getApplicationContext());
                // CoordType.GPS 待转换坐标类型
                converter.from(CoordinateConverter.CoordType.GPS);
                DPoint dPoint=new DPoint(Double.parseDouble(destination_latitude),Double.parseDouble(destination_longitude));
                // sourceLatLng待转换坐标点 DPoint类型
                try {
                    converter.coord(dPoint);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                // 执行转换操作
                try {
                    DPoint dPoint_end = converter.convert();
                    //转换经纬度
                  destination_latitude_convert= dPoint_end.getLatitude();
                  destination_longitude_convert=dPoint_end.getLongitude();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Poi end = new Poi("目标位置", new LatLng(destination_latitude_convert,destination_longitude_convert),"");
                AmapNaviParams params = new AmapNaviParams(start, null,end, AmapNaviType.DRIVER, AmapPageType.ROUTE);
              //启动导航组件
                AmapNaviPage.getInstance().showRouteActivity(getApplicationContext(), params, null);
            }
        });
        //获取目标经纬度
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true) {
                    //获取温度数据
                    String json = null;
                    One_net_Request one_net_request = new One_net_Request();
                    try {
                        json = one_net_request.get_data();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    One_net_json one_net_json = new One_net_json();
                    String data_string = one_net_json.get_data_stream(json);
                    Data data = Data.get_data(data_string);
                    destination_longitude=data.getLongitude();
                    destination_longitude_view.setText("经度："+destination_longitude);
                    destination_latitude=data.getAltitude();
                    destination_latitude_view.setText("纬度："+destination_latitude);
                }
            }
        }).start();


    }
}
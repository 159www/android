package com.example.ok;

import static android.content.ContentValues.TAG;
import static android.content.Intent.getIntent;
import static android.content.Intent.getIntentOld;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import java.io.IOException;

public class MyService extends Service {
    private  int battery_low_limitation=20;//低电量预警值默认20
    private  int temperture_low_limitation=10;//温度下限
    private  int temperture_high_limitation=40;//温度上限
    private  int humidity_high_limitation=90;//湿度上限
    private  int humidity_low_limitation=10;//湿度下限
    private  int heart_beats_high=180;//心率上限
    private  int heart_beats_low=30;//心率下限
    private  int length_high=100;// 最远距离
    private  int C0_high_limitation=4000;//一氧化碳浓度
    private  NotificationManager notificationManager=null;
    private  NotificationChannel notificationChannel=null;
    private Notification notification=null;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //创建notificationmanger对象
         notificationManager=(NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        //若系统为8.0以上需创建通道对象
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.FROYO)
        {
            //通知id 通知名 重要性
             notificationChannel=new NotificationChannel("normal","普通通知",NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(notificationChannel);
        }
        Log.d("MyService","创建服务");
        //创建子线程轮询获取最新数据
        new Thread(new Runnable() {
            @Override
            public void run()
            {
                while(true)
                {
                    //获取温度数据
                    String json=null;
                    One_net_Request one_net_request=new One_net_Request();
                    try {
                        json=one_net_request.get_data();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    One_net_json one_net_json=new One_net_json();
                    String data_string=one_net_json.get_data_stream(json);
                    Data data=Data.get_data(data_string);
                    //温度过高
                    if(Integer.parseInt(data.getTemperture())>temperture_high_limitation)
                    {
                      Log.d("MyService","温度过高");
                        //创建通知对象
                        notification=new  NotificationCompat.Builder(getApplicationContext(),"normal")
                                .setContentTitle("通知")
                                .setContentText("温度过高")
                                .setWhen(System.currentTimeMillis())
                                .setSmallIcon(R.mipmap.ic_launcher)
                                .setPriority(NotificationCompat.VISIBILITY_PRIVATE)
                                .build();
                        //显示通知
                        notificationManager.notify(1,notification);
                    }
                    //温度过低
                    if(Integer.parseInt(data.getTemperture())<temperture_low_limitation)
                    {
                        Log.d("MyService","温度过低");
                        //创建通知对象
                        notification=new  NotificationCompat.Builder(getApplicationContext(),"normal")
                                .setContentTitle("通知")
                                .setContentText("温度过低")
                                .setWhen(System.currentTimeMillis())
                                .setSmallIcon(R.mipmap.ic_launcher)
                                .setPriority(NotificationCompat.VISIBILITY_PRIVATE)
                                .build();
                        //显示通知
                        notificationManager.notify(2,notification);

                    }
                    //湿度过高
                    if(Integer.parseInt(data.getHumidity())>humidity_high_limitation)
                    {
                        Log.d("MyService","湿度过高");
                        //创建通知对象
                        notification=new  NotificationCompat.Builder(getApplicationContext(),"normal")
                                .setContentTitle("通知")
                                .setContentText("湿度过高")
                                .setWhen(System.currentTimeMillis())
                                .setSmallIcon(R.mipmap.ic_launcher)
                                .setPriority(NotificationCompat.VISIBILITY_PRIVATE)
                                .build();
                        //显示通知
                        notificationManager.notify(3,notification);
                    }
                    //湿度过低
                    if(Integer.parseInt(data.getHumidity())<humidity_low_limitation)
                    {

                        Log.d("MyService","湿度过低");
                        //创建通知对象
                        notification=new  NotificationCompat.Builder(getApplicationContext(),"normal")
                                .setContentTitle("通知")
                                .setContentText("湿度过低")
                                .setWhen(System.currentTimeMillis())
                                .setSmallIcon(R.mipmap.ic_launcher)
                                .setPriority(NotificationCompat.VISIBILITY_PRIVATE)
                                .build();
                        //显示通知
                        notificationManager.notify(4,notification);
                    }
                    //电量过低
                    //心率过高
                    if(Integer.parseInt(data.getHeart_beats())>heart_beats_high)
                    {
                        Log.d("MyService","心率过高");
                        //创建通知对象
                        notification=new  NotificationCompat.Builder(getApplicationContext(),"normal")
                                .setContentTitle("通知")
                                .setContentText("心率过高")
                                .setWhen(System.currentTimeMillis())
                                .setSmallIcon(R.mipmap.ic_launcher)
                                .setPriority(NotificationCompat.VISIBILITY_PRIVATE)
                                .build();
                        //显示通知
                        notificationManager.notify(5,notification);
                    }
                    //心率过低
                    if(Integer.parseInt(data.getHeart_beats())<heart_beats_low)
                    {
                        Log.d("MyService","心率过低");
                        //创建通知对象
                        notification=new  NotificationCompat.Builder(getApplicationContext(),"normal")
                                .setContentTitle("通知")
                                .setContentText("心率过低")
                                .setWhen(System.currentTimeMillis())
                                .setSmallIcon(R.mipmap.ic_launcher)
                                .setPriority(NotificationCompat.VISIBILITY_PRIVATE)
                                .build();
                        //显示通知
                        notificationManager.notify(6,notification);
                    }
                    //一氧化碳浓度过高
                    Gas_Convert gas_convert=new Gas_Convert();
                    int CO_value=(int)gas_convert.CH4_Convert((Integer.parseInt(data.getGas_voltage())/100.0));//ppm
                    if(CO_value>C0_high_limitation)
                    {
                        Log.d("MyService","CO浓度过高");
                        //创建通知对象
                        notification=new  NotificationCompat.Builder(getApplicationContext(),"normal")
                                .setContentTitle("通知")
                                .setContentText("CO浓度过高")
                                .setWhen(System.currentTimeMillis())
                                .setSmallIcon(R.mipmap.ic_launcher)
                                .setPriority(NotificationCompat.VISIBILITY_PRIVATE)
                                .build();
                        //显示通知
                        notificationManager.notify(7,notification);
                    }

                }

            }
        }).start();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        Log.d("MyService","onStartCommand");
        //更新温度上限阈值
        int tem_high=intent.getIntExtra("tem_high",0);
        if(tem_high>0) {
            temperture_high_limitation = tem_high;
            Log.d("MyService","onStartCommand"+String.valueOf(tem_high));
        }
        //更新温度下限阈值
        int tem_low=intent.getIntExtra("tem_low",-1);
        if(tem_low>=0)
        {
            temperture_low_limitation = tem_low;
            Log.d("MyService","onStartCommand"+String.valueOf(tem_low));
        }
        //更新湿度上限阈值
        int humidity_high=intent.getIntExtra("humidity_high",-1);
        if(humidity_high>=0)
        {
            humidity_high_limitation=humidity_high;
            Log.d("MyService","湿度"+String.valueOf(humidity_high));
        }
        //更新温度下限阈值
        int humidity_low=intent.getIntExtra("humidity_low",-1);
        if(humidity_low>=0)
        {
            humidity_low_limitation=humidity_low;
            Log.d("MyService","湿度"+String.valueOf(humidity_low));

        }
        //更新电池电量下限阈值
        int battery_low=intent.getIntExtra("battery",-1);
        if(battery_low>0)
        {
            battery_low_limitation=battery_low;

        }
        //更新心率下限
        int heart_beats_low=intent.getIntExtra("heart_beats_low",-1);
        if(heart_beats_low>0)
        {
            this.heart_beats_low=heart_beats_low;
        }
        //更新心率上限
        int heart_beats_high=intent.getIntExtra("heart_beats_high",-1);
        if(heart_beats_high>0)
        {
            this.heart_beats_high=heart_beats_high;
        }
        //更新一氧化碳浓度
        int CO_high_limitation=intent.getIntExtra("CO",-1);
        if(CO_high_limitation>=0)
        {
            this.C0_high_limitation=CO_high_limitation;
        }
        return super.onStartCommand(intent, flags, startId);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("MyService","销毁服务");
    }
}

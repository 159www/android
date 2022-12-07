package com.example.ok;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.IOException;

public class HistoryDataShowActivity extends AppCompatActivity {
    private  String []history_data_array;
    private String select;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_data_show);
        ListView listView=findViewById(R.id.history_data_show_list_view);
        new Thread(new Runnable() {
            @Override
            public void run() {
                Intent intent=getIntent();
                String start_date_time=intent.getStringExtra("start_date_time");
                String end_date_time=intent.getStringExtra("end_date_time");
                select=intent.getStringExtra("select");
                One_net_Request one_net_request=new One_net_Request();
                String history_data=null;
                try {
                    history_data=one_net_request.get_history_data("T",start_date_time,end_date_time);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                One_net_json one_net_json=new One_net_json();
                history_data_array=one_net_json.get_history_data(history_data);
                String []list_view_data=new String[(history_data_array.length)];
                int index=0;
                String time_temp=null;
                String data_temp=null;
                Data data=null;

                if(select.equals("温湿度数据")==true) {
                    for(int i=0;i<history_data_array.length;i+=2)
                    {

                          time_temp=history_data_array[i];
                          data_temp=history_data_array[i+1];
                          if(data_temp!=null) {
                              data = Data.get_data(data_temp);
                              list_view_data[i / 2] = "温度：" + data.getTemperture() + "℃" + "湿度：" + data.getHumidity() + "％\r\n" + "日期：" + time_temp;
                          }
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ArrayAdapter<String> adapter = new ArrayAdapter<>(HistoryDataShowActivity.this, android.R.layout.simple_list_item_1, list_view_data);
                            //将适配器加载到控件
                            listView.setAdapter(adapter);
                        }
                    });

                }
                else if(select.equals("心率数据")==true)
                {
                    for(int i=0;i<history_data_array.length;i+=2)
                    {

                        time_temp=history_data_array[i];
                        data_temp=history_data_array[i+1];
                        if(data_temp!=null) {
                            data = Data.get_data(data_temp);
                            list_view_data[i / 2] = "心率：" + data.getTemperture() + "bps"+"\r\n" + "日期：" + time_temp;
                        }
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ArrayAdapter<String> adapter = new ArrayAdapter<>(HistoryDataShowActivity.this, android.R.layout.simple_list_item_1, list_view_data);
                            //将适配器加载到控件
                            listView.setAdapter(adapter);
                        }
                    });

                }
                else if(select.equals("可燃气体浓度"))
                {
                    for(int i=0;i<history_data_array.length;i+=2)
                    {

                        time_temp=history_data_array[i];
                        data_temp=history_data_array[i+1];
                        if(data_temp!=null) {
                            data = Data.get_data(data_temp);
                            //需将电压转换成对应的co浓度
                            list_view_data[i / 2] = "一氧化碳浓度：" + data.getGas_voltage() + "ppm"+"\r\n" + "日期：" + time_temp;
                        }
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ArrayAdapter<String> adapter = new ArrayAdapter<>(HistoryDataShowActivity.this, android.R.layout.simple_list_item_1, list_view_data);
                            //将适配器加载到控件
                            listView.setAdapter(adapter);
                        }
                    });

                }
                else if(select.equals("电量数据"))
                {

                }
                else if(select.equals("摔倒监测"))
                {

                }

            }
        }).start();


    }
}
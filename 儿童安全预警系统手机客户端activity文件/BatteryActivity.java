package com.example.ok;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.IOException;

public class BatteryActivity extends AppCompatActivity {
    private Dashboard dashboard;//自定义仪表盘控件对象
    private TextView battery_low_text_view;
    private SeekBar  battery_seekbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battery);
        dashboard=findViewById(R.id.battery_activity_dashboard);
        battery_low_text_view=findViewById(R.id.battery_activity_low_limitation);
        battery_seekbar=findViewById(R.id.battery_activity_low_seekbar);
        battery_seekbar.setMax(100);//设置最大值为100
        battery_seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                battery_low_text_view.setText(String.valueOf(progress)+"%");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                //松手后设置阈值
                Intent service_intent=new Intent(getApplicationContext(),com.example.ok.MyService.class);
                service_intent.putExtra("battery_low",seekBar.getProgress());
                startService(service_intent);
            }
        });

        new Thread(new Runnable() {
            @Override
            public void run()
            {
                while(true)
                {
                    //获取数据对象data
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
                    //得到扩大100倍的电压值对应字符串
                    String battery_voltage=data.getBattery_voltage();
                    int battery_voltage_of_Integer=(Integer.parseInt(battery_voltage)/100)*5;
                    //显示电量
                   // dashboard.setNumAnimator((float) (battery_voltage_of_Integer/100));

                }
            }
        }).start();
    }
}
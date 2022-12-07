package com.example.ok;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class DashboardActivity extends AppCompatActivity {
    //传感器实例集合
    private List<Senor> senors=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        RadioGroup dashboard_navigation=findViewById(R.id.dashboard_navigation);//底部导航栏（单选栏）
        RadioButton dashboard_activity_dashboard=findViewById(R.id.dashboard_activity_dashboard);//导航栏监控按钮
        RadioButton dashboard_activity_history=findViewById(R.id.dashboard_activity_history);//导航栏历史按钮
        RadioButton dashboard_activity_home=findViewById(R.id.dashboard_activity_home);//导航栏主页按钮
        Senor_Listview_Init();
        //导航栏按钮监听
        dashboard_navigation.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                Intent intent=null;
                switch (checkedId)
                {
                    case R.id.dashboard_activity_dashboard:
                       dashboard_activity_dashboard.setChecked(false);
                       Toast.makeText(getApplicationContext(),"已处于当前页面",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.dashboard_activity_history:
                        dashboard_activity_history.setChecked(false);
                        intent=new Intent(getApplicationContext(),HistoryActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.dashboard_activity_home:
                      dashboard_activity_home.setChecked(false);
                      intent=new Intent(getApplicationContext(),HomeActivity.class);
                      startActivity(intent);
                        break;
                }
            }
        });

    }
    //初始化senorlistview
    public void Senor_Listview_Init()
    {
        Senor dht11=new Senor("温湿度监测",R.drawable.ic_baseline_invert_colors_off_24);
        Senor max30102=new Senor("心率监测",R.drawable.ic_baseline_monitor_heart_24);
        Senor mq_9=new Senor("可燃气体监测",R.drawable.ic_baseline_smoke_free_24);
        Senor gps=new Senor("GPS",R.drawable.ic_baseline_gps_fixed_24);
        Senor battery=new Senor("电量监测",R.drawable.ic_baseline_battery_unknown_24);
        Senor mpu6050=new Senor("摔倒监测",R.drawable.ic_baseline_scuba_diving_24);
        senors.add(dht11);
        senors.add(max30102);
        senors.add(mq_9);
        senors.add(gps);
        senors.add(battery);
        senors.add(mpu6050);
        SenorAdapter senorAdapter=new SenorAdapter(getApplicationContext(),R.layout.senor_item,senors);
        ListView senor_listview=findViewById(R.id.dashboard_list_view);
        senor_listview.setAdapter(senorAdapter);
        //listview点击监听
        senor_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=null;
                switch (position)
                {
                    case 0:
                        intent=new Intent(getApplicationContext(),Dht11Activity.class);
                        startActivity(intent);
                        break;
                    case 1:
                        intent=new Intent(getApplicationContext(),Max30102Activity.class);
                        startActivity(intent);
                        break;
                    case 2:
                        intent=new Intent(getApplicationContext(),Mq_9Activity.class);
                        startActivity(intent);
                        break;
                    case 3:
                        intent=new Intent(getApplicationContext(),GPSActivity.class);
                        startActivity(intent);
                        break;
                    case 4:
                        intent=new Intent(getApplicationContext(),BatteryActivity.class);
                        startActivity(intent);
                        break;
                    case 5:
                        intent=new Intent(getApplicationContext(),Mpu6050Activity.class);
                        startActivity(intent);
                        break;

                }

            }
        });

    }
}

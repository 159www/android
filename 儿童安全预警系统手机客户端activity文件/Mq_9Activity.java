package com.example.ok;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

public class Mq_9Activity extends AppCompatActivity {
    private GasDashboard   gas_dashboard;//自定义仪表盘控件对象
    private TextView   gas_textview;
    private  Button   back_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mq9);
        gas_dashboard=findViewById(R.id.mq9_activity_gas_dashboard);//绑定仪表盘控件
        gas_textview=findViewById(R.id.mq9_activity_co_value);
        back_button=findViewById(R.id.mq9_activity_back_button);
        //co浓度阈值显示及更新
        SeekBar CO_high_seekbar=findViewById(R.id.mq9_activity_co_high_seekbar);
        //co浓度阈值显示
        TextView CO_high_textview=findViewById(R.id.mq9_activity_co_high_limitation_textview);
        CO_high_seekbar.setMax(10000);
        CO_high_seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                CO_high_textview.setText("上限："+String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Intent service_intent=new Intent(getApplicationContext(),com.example.ok.MyService.class);
                service_intent.putExtra("CO",seekBar.getProgress());
                startService(service_intent);

            }
        });
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),DashboardActivity.class);
                startActivity(intent);
            }
        });
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true)
                {
                    //获取气体传感器电压
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
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //将电压转换成浓度
                            double gas_voltage=(Integer.parseInt(data.getGas_voltage())/100.0);
                            Gas_Convert gas_convert=new Gas_Convert();
                            double gas_value=gas_convert.CH4_Convert(gas_voltage);
                            int temp=(int)gas_value;//忽略小数
                            gas_dashboard.setNumAnimator(temp );
                            gas_textview.setText(String.valueOf(temp));
                        }
                    });


                }

            }
        }).start();
    }
}
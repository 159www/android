package com.example.ok;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class HistoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
       RadioGroup history_navigation=findViewById(R.id.history_navigation);//底部导航栏（单选栏）
        RadioButton history_activity_dashboard=findViewById(R.id.history_activity_dashboard);//导航栏监控按钮
        RadioButton history_activity_history=findViewById(R.id.history_activity_history);//导航栏历史按钮
        RadioButton history_activity_home=findViewById(R.id.history_activity_home);//导航栏主页按钮
        Button start_date_button=findViewById(R.id.start_date_button);//开始日期设置按钮
        Button start_time_button=findViewById(R.id.start_time_button);//开始时间设置按钮
        Button end_date_button=findViewById(R.id.end_date_button);//结束日期设置按钮
        Button end_time_button=findViewById(R.id.end_time_button);//结束日期设置按钮
        Button history_button=findViewById(R.id.history_data_show);//历史数据跳转按钮
        TextView start_date_text_view=findViewById(R.id.start_date_text_view);//开始日期显示
        TextView start_time_text_view=findViewById(R.id.start_time_text_view);//开始时间显示
        TextView end_time_text_view=findViewById(R.id.end_time_text_view);//结束时间显示
        TextView end_date_text_view=findViewById(R.id.end_date_text_view);//结束日期显示
        Spinner spinner_rank=findViewById(R.id.spinner_rank);//下拉框
        //历史数据跳转按钮监听
        history_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String select= spinner_rank.getSelectedItem().toString();
                //起止时间精确到分秒默认为00
                String start_date_time=start_date_text_view.getText().toString()+"T"+"00:00:00";
                String end_date_time=end_date_text_view.getText().toString()+"T"+"00:00:00";
                Intent intent=new Intent(getApplicationContext(),HistoryDataShowActivity.class);
                //将额外的数据放入intent对象
                intent.putExtra("start_date_time",start_date_time);
                intent.putExtra("end_date_time",end_date_time);
                intent.putExtra("select",select);
                startActivity(intent);
                //finish();//结束当前activity





            }
        });
        //开始日期设置按钮监听
        start_date_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar=Calendar.getInstance();
                new DatePickerDialog(HistoryActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        // TODO Auto-generated method stub
                        String start_month=String.valueOf(month+1);
                        String start_day=String.valueOf(day);
                        if(start_month.length()<2)
                        {
                            start_month="0"+start_month;
                        }
                        if(start_day.length()<2)
                        {
                            start_day="0"+start_day;
                        }
                        start_date_text_view.setText(String.valueOf(year)+"-"+start_month+"-"+start_day);

                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH) ).show();
            }
        });
        //开始时间按钮监听
        start_time_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar=Calendar.getInstance();
                new TimePickerDialog(HistoryActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        String start_hour=String.valueOf(hourOfDay);
                        String start_minute=String.valueOf(minute);
                        if(start_hour.length()<2)
                        {
                            start_hour="0"+start_hour;
                        }
                        if(start_minute.length()<2)
                        {
                            start_minute="0"+start_minute;
                        }
                      start_time_text_view.setText(start_hour+":"+start_minute);
                    }
                }, calendar.get(Calendar.HOUR_OF_DAY),
                        calendar.get(Calendar.MINUTE), true).show();
            }
        });
        //结束日期按钮监听
        end_date_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar=Calendar.getInstance();
                new DatePickerDialog(HistoryActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        // TODO Auto-generated method stub
                        String end_month=String.valueOf(month+1);
                        String end_day=String.valueOf(day);
                        if(end_month.length()<2)
                        {
                            end_month="0"+end_month;
                        }
                        if(end_day.length()<2)
                        {
                            end_day="0"+end_day;
                        }
                        end_date_text_view.setText(String.valueOf(year)+"-"+end_month+"-"+end_day);

                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH) ).show();

            }
        });
        //结束时间按钮监听
        end_time_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar=Calendar.getInstance();
                new TimePickerDialog(HistoryActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        String end_hour=String.valueOf(hourOfDay);
                        String end_minute=String.valueOf(minute);
                        if(end_hour.length()<2)
                        {
                            end_hour="0"+end_hour;
                        }
                        if(end_minute.length()<2)
                        {
                            end_minute="0"+end_minute;
                        }
                        end_time_text_view.setText(end_hour+":"+end_minute);
                    }
                }, calendar.get(Calendar.HOUR_OF_DAY),
                        calendar.get(Calendar.MINUTE), true).show();

            }
        });
        //导航栏按钮监听
        history_navigation.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                Intent intent=null;
                switch (checkedId)
                {
                    case R.id.history_activity_dashboard:
                        history_activity_dashboard.setChecked(false);
                        intent=new Intent(getApplicationContext(),DashboardActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.history_activity_history:
                       history_activity_history.setChecked(false);
                       Toast.makeText(getApplicationContext(),"已处于当前页面",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.history_activity_home:
                        history_activity_home.setChecked(false);
                        intent=new Intent(getApplicationContext(),HomeActivity.class);
                        startActivity(intent);
                        break;
                }
            }
        });
    }
}
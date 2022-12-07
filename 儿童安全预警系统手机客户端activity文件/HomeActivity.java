package com.example.ok;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class HomeActivity extends AppCompatActivity {
   static private String account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home);
        RadioGroup home_navigation=findViewById(R.id.home_navigation);//底部导航栏（单选栏）
        RadioButton home_activity_dashboard=findViewById(R.id.home_activity_dashboard);//导航栏监控按钮
        RadioButton home_activity_history=findViewById(R.id.home_activity_history);//导航栏历史按钮
        RadioButton home_activity_home=findViewById(R.id.home_activity_home);//导航栏主页按钮
        TextView account_textview=findViewById(R.id.home_activity_id_textview);//账号显示
        Button change_password_button=findViewById(R.id.home_activity_change_password_button);//修改密码按钮
        Button exit_button=findViewById(R.id.home_activity_logon_out);//退出登录按钮
        Intent intent=getIntent();
       //account=intent.getStringExtra("account");
       //account_textview.setText("账号："+account);
        SharedPreferences sharedPreferences=getSharedPreferences("user",Context.MODE_PRIVATE);
        String account=sharedPreferences.getString("account",null);
        if(account!=null)
        {
            account_textview.setText("账号："+account);
        }
        //退出按钮监听
        exit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent return_main_activity=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(return_main_activity);
                finish();
            }
        });
        //修改密码按钮
        change_password_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent return_change_password_activity=new Intent(getApplicationContext(),ChangePasswordActivity.class);
                startActivity(return_change_password_activity);



            }
        });


        //导航栏按钮监听
        home_navigation.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                Intent intent=null;
                switch (checkedId)
                {
                    case R.id.home_activity_dashboard:
                        home_activity_dashboard.setChecked(false);
                        intent=new Intent(getApplicationContext(),DashboardActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.home_activity_history:
                        home_activity_history.setChecked(false);
                        intent=new Intent(getApplicationContext(),HistoryActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.home_activity_home:
                        home_activity_home.setBackgroundColor(Color.blue(1));
                        Toast.makeText(getApplicationContext(),"已处于当前页",Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
    }
}
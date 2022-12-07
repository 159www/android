package com.example.ok;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
{


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //创建notificationmanger对象
        NotificationManager notificationManager=(NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        //若系统为8.0以上需创建通道对象
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.FROYO)
        {
            //通知id 通知名 重要性
            NotificationChannel notificationChannel=new NotificationChannel("normal","普通通知",NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(notificationChannel);
        }
        //设置顶部状态栏为透明
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
        Button  login_button= findViewById(R.id.login_button);//登录按钮
        Button register_button= findViewById(R.id.go_register_button);//注册按钮
        EditText account_edit=findViewById(R.id.account_edit);//账户编辑框
        EditText pass_edit=findViewById(R.id.pass_edit);//密码编辑框

        //设置账号编辑框图标大小
        Drawable drawable = getResources().getDrawable(R.drawable.register3);
        drawable .setBounds(0, 0, 40, 40);//第一个 0 是距左边距离，第二个 0 是距上边距离，40 分别是长宽
        account_edit .setCompoundDrawables(drawable , null, null, null);//只放左边
        //设置密码编辑框图标大小
        drawable = getResources().getDrawable(R.drawable.register4);
        drawable .setBounds(0, 0, 40, 40);//第一个 0 是距左边距离，第二个 0 是距上边距离，40 分别是长宽
        pass_edit.setCompoundDrawables(drawable , null, null, null);//只放左边
        //创建数据库对象
        DataBase dataBase=new DataBase(MainActivity.this,"data",null,1);
        login_button.setOnClickListener(new View.OnClickListener() //登录按钮监听
        {
            @Override
            public void onClick(View v)
            {
                Cursor cursor=null;
                //输入账号
                String input_account=account_edit.getText().toString();
                //输入密码
                String input_pass=pass_edit.getText().toString();
                if( input_account.equals("")||input_pass.equals(""))
                {
                    Toast.makeText(MainActivity.this,"账号或密码均不能为空",Toast.LENGTH_SHORT).show();
                }
                else
                {

                    SQLiteDatabase sqLiteDatabase = dataBase.getWritableDatabase();
                    //要查询的账号
                    String target = account_edit.getText().toString();
                    //创建游标对象
                    cursor = sqLiteDatabase.query("user", new String[]{"account_id", "pass"}, "account_id=?", new String[]{target}, null, null, null);
                    //查询返回的数据条数
                    int data_number = cursor.getCount();
                    //判断是否有查询到用户的数据
                    if (data_number == 0) {
                        Toast.makeText(MainActivity.this, "不存在用户信息", Toast.LENGTH_SHORT).show();
                        cursor.close();
                    }
                    if (data_number > 0) {
                        //account_id 为主键因此只有一条数据
                        cursor.moveToNext();
                        String account = cursor.getString(cursor.getColumnIndexOrThrow("account_id"));
                        String pass = cursor.getString(cursor.getColumnIndexOrThrow("pass"));
                        //验证密码
                        if (pass.equals(input_pass))
                        {
                            //启动后台服务
                            Intent service_intent=new Intent(getApplicationContext(),com.example.ok.MyService.class);
                            startService(service_intent);
                            //创建通知对象
                            Notification notification=new  NotificationCompat.Builder(getApplicationContext(),"normal")
                                    .setContentTitle("通知")
                                    .setContentText("已启动后台服务")
                                    .setSmallIcon(R.mipmap.ic_launcher)
                                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                                    .build();
                            //显示通知
                            notificationManager.notify(1,notification);
                            //跳转到导航页面
                            //Toast.makeText(MainActivity.this,"ok",Toast.LENGTH_SHORT).show();
                          Intent intent=new Intent(getApplicationContext(),HomeActivity.class);
                          intent.putExtra("account",account);
                          //创建xml文件储存登录的账号和密码
                           SharedPreferences sharedPreferences=getSharedPreferences("user",Context.MODE_PRIVATE);
                           //创建编辑对象
                           SharedPreferences.Editor editor=sharedPreferences.edit();
                           editor.putString("account",account);
                           editor.putString("password",pass);
                           editor.commit();//提交保存
                          startActivity(intent);
                        } else {
                            Toast.makeText(MainActivity.this, "密码错误", Toast.LENGTH_LONG).show();

                        }
                        cursor.close();


                    }
                }
            }

        });
        register_button.setOnClickListener(new View.OnClickListener() //注册按钮监听
        {
            @Override
            public void onClick(View v)
            {
                Intent intent=new Intent(getApplicationContext(),RegisterActivity.class);
                startActivity(intent);
            }
        });
    }




}
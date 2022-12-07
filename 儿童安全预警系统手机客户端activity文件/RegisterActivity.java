package com.example.ok;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //设置顶部状态栏为透明
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
        EditText register_account_edit=findViewById(R.id.register_account_edit);//注册账号编辑框
        EditText register_pass_edit=findViewById(R.id.register_pass_edit);//注册密码编辑框
        EditText register_pass_verify_edit=findViewById(R.id.register_pass_verify_edit);//注册确认密码编辑框
        Button register_button=findViewById(R.id.register_button);//注册按钮
        Button go_main_activity_button=findViewById(R.id.go_mainactivity);//返回开始页面按钮
        TextView notification_textview=findViewById(R.id.register_notification_textview);//通知栏
        //设置注册账号编辑框中的图标大小
        Drawable drawable = getResources().getDrawable(R.drawable.register3);
        drawable .setBounds(0, 0, 40, 40);//第一个 0 是距左边距离，第二个 0 是距上边距离，40 分别是长宽
        register_account_edit .setCompoundDrawables(drawable , null, null, null);//只放左边
        //设置注册密码编辑框中的图标大小
        drawable = getResources().getDrawable(R.drawable.register4);
        drawable .setBounds(0, 0, 40, 40);//第一个 0 是距左边距离，第二个 0 是距上边距离，40 分别是长宽
        register_pass_edit .setCompoundDrawables(drawable , null, null, null);//只放左边
        //设置注册确认密码编辑框中的图标大小
        drawable = getResources().getDrawable(R.drawable.register4);
        drawable .setBounds(0, 0, 40, 40);//第一个 0 是距左边距离，第二个 0 是距上边距离，40 分别是长宽
        register_pass_verify_edit .setCompoundDrawables(drawable , null, null, null);//只放左边
        //注册按钮监听
        register_button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //输入注册账号
                String input_account=register_account_edit.getText().toString();
                //输入账号密码
                String input_pass=register_pass_edit.getText().toString();
                //输入确认密码
                String input_verify_pass=register_pass_verify_edit.getText().toString();
                if(input_account.isEmpty()||input_pass.isEmpty()||input_verify_pass.isEmpty())
                {
                    Toast.makeText(RegisterActivity.this,"请完善信息",Toast.LENGTH_SHORT).show();
                }
                else
                {
                   if(input_pass.equals(input_verify_pass))
                   {
                       //获得数据库操作对象
                       DataBase dataBase=new DataBase(RegisterActivity.this,"data",null,1);
                       SQLiteDatabase sqLiteDatabase = dataBase.getWritableDatabase();
                       //创建存放数据的ContentValues对象
                       ContentValues values = new ContentValues();
                       values.put("account_id",input_account);
                       values.put("pass",input_pass);
                       //数据库执行插入命令
                       long result =sqLiteDatabase.insert("user", null, values);
                       if(result!=-1)
                       {
                           Toast.makeText(RegisterActivity.this,"注册成功",Toast.LENGTH_LONG).show();
                       }

                   }
                   else if(input_pass!=input_verify_pass)
                   {
                       notification_textview.setText("两次输入密码不一致");
                   }
                }

            }
        });
        //返回主页面按钮监听
        go_main_activity_button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //Intent 显式跳转
                Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });

    }
}
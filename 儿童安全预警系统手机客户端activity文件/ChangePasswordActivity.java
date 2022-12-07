package com.example.ok;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ChangePasswordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        //账号编辑框
        EditText account_edit=findViewById(R.id.change_password_activity_account_edit);
        //旧密码输入框
        EditText old_password=findViewById(R.id.change_password_activity_old_password_edit);
        //新密码输入框
        EditText new_password=findViewById(R.id.change_password_activity_new_password_edit);
        //确认新密码框
        EditText new_password_ok=findViewById(R.id.change_password_activity_new_password_ok_edit);
        //修改密码按钮
        Button change_password_button=findViewById(R.id.change_password_activity_change_button);
        SharedPreferences sharedPreferences=getSharedPreferences("user", Context.MODE_PRIVATE);
        String account=sharedPreferences.getString("account",null);
        String password=sharedPreferences.getString("password",null);
        if(account.isEmpty()==false)
        {
            account_edit.setText(account);
        }
        if(password.isEmpty()==false)
        {
            old_password.setText(password);
        }
        //修改密码按钮监听
        change_password_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if(new_password.getText().toString().isEmpty()==true||new_password_ok.getText().toString().isEmpty()==true)
                {
                    Toast.makeText(ChangePasswordActivity.this, "信息填写不全", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    if(new_password.getText().toString().equals(new_password_ok.getText().toString())==false)
                    {
                        Toast.makeText(ChangePasswordActivity.this, "输入两次新密码不一致", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        //创建数据库对象
                        DataBase dataBase=new DataBase(getApplicationContext(),"data",null,1);
                        SQLiteDatabase sqLiteDatabase = dataBase.getWritableDatabase();
                        ContentValues values = new ContentValues();
                        values.put("pass",new_password.getText().toString());
                        String []array=new String[1];
                        array[0]=account;
                        int result=sqLiteDatabase.update("user",values,"account_id=?",array);
                        if(result>0)
                        {
                            Toast.makeText(getApplicationContext(),"修改密码成功",Toast.LENGTH_SHORT).show();
                            Intent return_home_activity=new Intent(getApplicationContext(),MainActivity.class);
                            startActivity(return_home_activity);
                        }
                        Log.d("result",String.valueOf(result));
                    }
                }

            }
        });
    }
}
package com.example.ok;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;

public class SenorAdapter extends ArrayAdapter<Senor> {
    //resource_id指定ListView的布局方式
   private int resource_id;
    //重写SenorAdapter的构造器
   public SenorAdapter(Context context,int textViewResourceID , List<Senor> objects)
   {

       super(context,textViewResourceID,objects);
       resource_id=textViewResourceID;
   }
    //自定义item资源的解析方式
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //获取当前Senor实例
        Senor senor = getItem(position);
        //使用LayoutInfater为子项加载传入的布局
        View view = LayoutInflater.from(getContext()).inflate(resource_id,null);
        ImageView senor_image = (ImageView)view.findViewById(R.id.senor_image);
        TextView senor_name = (TextView)view.findViewById(R.id.senor_name);
        //引入Senor对象的属性值
        senor_image.setImageResource(senor.getSenor_image_id());
        senor_name.setText(senor.getSenor_name());
        return view;
    }
}

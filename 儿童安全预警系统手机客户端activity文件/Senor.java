package com.example.ok;
//传感器类
public class Senor
{
    String senor_name; //传感器名
    int    senor_image_id;//传感器图片id
    public Senor(String name,int image_id)
    {
        this.senor_name=name;
        this.senor_image_id=image_id;
    }

    public int getSenor_image_id()
    {
        return senor_image_id;
    }



    public String getSenor_name()
    {
        return senor_name;
    }





}

package com.example.ok;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class One_net_Request
{
    private String api_key="3FyYtAy6fvgou5d0MKTQKQI7eE0=";
    private String device_id="1005551888";//设备id
    private  String  get_data_url="http://api.heclouds.com/devices/datapoints?devIds="+device_id;
    private String  get_history_data_url="http://api.heclouds.com/devices/"+device_id+"/datapoints?datastream_id=";
    //查询最新数据
    public String get_data() throws IOException {
        OkHttpClient okHttpClient=new OkHttpClient();
        Request request=new Request.Builder()
                .url(get_data_url)
                .addHeader("api-key",api_key)
                .addHeader("Content_Type","application/json").build();
        Response response=okHttpClient.newCall(request).execute();
        String response_string=response.body().string();
        return  response_string;

    }
    //查询历史数据
    //数据流id T    开始时间 2022-01-01T00:00:00    结束时间  2022-01-01T00:00:00
    public String  get_history_data(String stream_id,String start_time,String end_time) throws IOException {
      // String temp_url=get_history_data_url+stream_id+"&start="+start_time+"&end="+end_time;
        String temp_url=get_history_data_url+stream_id+"&start="+start_time+"&end"+end_time;
        OkHttpClient okHttpClient=new OkHttpClient();
        Request request=new Request.Builder()
                .url(temp_url)
                .addHeader("api-key",api_key)
                .addHeader("Content_Type","application/json").build();
        Response response=okHttpClient.newCall(request).execute();
        String response_string=response.body().string();
        return  response_string;
    }

}

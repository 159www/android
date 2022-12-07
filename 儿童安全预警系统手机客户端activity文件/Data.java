package com.example.ok;

public class Data
{
    //T27H55HT0AC0AN0GV323BV176BPS94P123.456789  ‘P’  123.456789  +‘E’ 加大写字母E表示结束
    private  String temperture;//环境温度
    private  String humidity;//环境湿度
    private  String human_temperture;//人体温度

    public String getTemperture() {
        return temperture;
    }

    public String getHumidity() {
        return humidity;
    }

    public String getHuman_temperture() {
        return human_temperture;
    }

    public String getTotal_acceleration() {
        return total_acceleration;
    }

    public String getTotal_angular() {
        return total_angular;
    }

    public String getGas_voltage() {
        return gas_voltage;
    }

    public String getBattery_voltage() {
        return battery_voltage;
    }

    public String getHeart_beats() {
        return heart_beats;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getAltitude() {
        return altitude;
    }

    private  String total_acceleration;//合加速度
    private  String total_angular;//合角速度
    private  String gas_voltage;//气体浓度电压
    private  String battery_voltage;//电池电压
    private  String heart_beats;//心率
    private  String longitude; //经度
    private  String altitude;  //纬度
  static   boolean Is_digital(char c)
    {
        if(c>='0'&&c<='9'||c=='-'||c=='+')
        {
          return  true;
        }
        else
        {
            return  false;
        }
    }
 static    boolean Is_Upper(char c)
    {
        if(c>='A'&&c<='Z')
        {
            return  true;
        }
        else
        {
            return  false;
        }
    }
  static   boolean Is_dot(char c)
    {
        if(c=='.')
        {
            return  true;
        }
        else
        {
            return  false;
        }
    }

  static   public  Data get_data(String json)
    {
        String temp="";
        String []result=new String[10];
        int index=0;
        for(int i=0;i<json.length();i++) {
            if(Is_digital(json.charAt(i))||Is_dot(json.charAt(i)))
            {
                temp+=json.charAt(i);
            }
            if(Is_digital(json.charAt(i))&&Is_Upper(json.charAt(i+1)))
            {
                result[index++]=temp;
                temp="";

            }

        }
        Data data=new Data();
        data.temperture=result[0];
        data.humidity=result[1];
        data.human_temperture=result[2];
        data.total_acceleration=result[3];
        data.total_angular=result[4];
        data.gas_voltage=result[5];
        data.battery_voltage=result[6];
        data.heart_beats=result[7];
        data.longitude=result[8];
        data.altitude=result[9];
        return  data;
    }


}

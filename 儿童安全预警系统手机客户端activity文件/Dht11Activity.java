package com.example.ok;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.gesture.ContainerScrollType;
import lecho.lib.hellocharts.gesture.ZoomType;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.ValueShape;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.view.LineChartView;

public class Dht11Activity extends AppCompatActivity {
    private LineChartView lineChart;
    private List<PointValue> temperturePointValues = new ArrayList<PointValue>();
    private List<PointValue> humidityPointValues=new ArrayList<PointValue>();
    private List<AxisValue> mAxisXValues = new ArrayList<AxisValue>();
    private  int index=0;
    private Intent service_intent=null;//服务意图对象
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dht11);
        //绑定折线图控件
        lineChart = (LineChartView)findViewById(R.id.line_chart);
        TextView temperture_text_view=findViewById(R.id.dht11_activity_temperture_value_text_view);//温度显示textview
        TextView humidity_text_view=findViewById(R.id.dht11_activity_humidity_value_text_view);//湿度显示textview
        TextView temperture_high_text_view=findViewById(R.id.dht11_activity_temperture_high_text_view);//温度上限显示
        TextView temperture_low_text_view=findViewById(R.id.dht11_activity_temperture_low_text_view);//温度下限显示
        TextView humidity_high_text_view=findViewById(R.id.dht11_activity_humidity_high_text_view);//湿度上限显示
        TextView humidity_low_text_view=findViewById(R.id.dht11_activity_humidity_low_text_view);//湿度下限显示
        SeekBar  temperture_high_seekbar=findViewById(R.id.dht11_activity_temperture_high_seekbar);//温度上限调节
        SeekBar  temperture_low_seekbar=findViewById(R.id.dht11_activity_temperture_low_seekbar);//温度下限调节
        SeekBar  humidity_high_seekbar=findViewById(R.id.dht11_activity_humidity_high_seekbar);//湿度上限调节
        SeekBar  humidity_low_seekbar=findViewById(R.id.dht11_activity_humidity_low_seekbar);//湿度下限调节
        //温度上限调节显示
        temperture_high_seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                temperture_high_text_view.setText("上限："+String.valueOf(progress)+"℃");

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                //松手时设置阈值
                service_intent=new Intent(getApplicationContext(),com.example.ok.MyService.class);
                service_intent.putExtra("tem_high",seekBar.getProgress());
                startService(service_intent);


            }
        });
        //温度下限调节显示
        temperture_low_seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                temperture_low_text_view.setText("下限："+String.valueOf(progress)+"℃");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                //松手时设置阈值
                service_intent=new Intent(getApplicationContext(),com.example.ok.MyService.class);
                service_intent.putExtra("tem_low",seekBar.getProgress());
                startService(service_intent);
            }
        });
        //湿度上限调节显示
        humidity_high_seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                humidity_high_text_view.setText("上限："+String.valueOf(progress)+"％");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                //松手时设置阈值
                service_intent=new Intent(getApplicationContext(),com.example.ok.MyService.class);
                service_intent.putExtra("humidity_high",seekBar.getProgress());
                startService(service_intent);
            }
        });
        //湿度下限调节
        humidity_low_seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                humidity_low_text_view.setText("下限："+String.valueOf(progress)+"％");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                //松手时设置阈值
                service_intent=new Intent(getApplicationContext(),com.example.ok.MyService.class);
                service_intent.putExtra("humidity_low",seekBar.getProgress());
                startService(service_intent);
            }
        });

        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true)
                {
                    //获取温度数据
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
                    int tempertre=Integer.parseInt(data.getTemperture());
                    int humidity=Integer.parseInt(data.getHumidity());
                    temperture_text_view.setText("温度："+data.getTemperture()+"℃");
                    humidity_text_view.setText("湿度："+data.getHumidity()+"%");
                   // int temp=(int)Math.random()*1000;
                    //显示实时温度
                    temperturePointValues.add(new PointValue(index, tempertre));
                    //显示实时湿度
                    humidityPointValues.add(new PointValue(index,humidity));
                    mAxisXValues.add(new AxisValue(index).setLabel(Integer.toString(index)));
                    index++;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Line tempertureline = new Line(temperturePointValues).setColor(Color.parseColor("#FF990D06"));  //折线的颜色（红色）
                            Line humidityline=new Line(humidityPointValues).setColor(Color.parseColor("#FF022EB9"));//蓝色
                            List<Line> lines = new ArrayList<Line>();
                           // tempertureline.setShape(ValueShape.CIRCLE);//折线图上每个数据点的形状  这里是圆形 （有三种 ：ValueShape.SQUARE  ValueShape.CIRCLE  ValueShape.DIAMOND）
                            tempertureline.setCubic(false);//曲线是否平滑，即是曲线还是折线
                            tempertureline.setFilled(false);//是否填充曲线的面积
                           // tempertureline.setHasLabels(true);//曲线的数据坐标是否加上备注
//      line.setHasLabelsOnlyForSelected(true);//点击数据坐标提示数据（设置了这个line.setHasLabels(true);就无效）
                            tempertureline.setHasLines(true);//是否用线显示。如果为false 则没有曲线只有点显示
                            tempertureline.setHasPoints(true);//是否显示圆点 如果为false 则没有原点只有点显示（每个数据点都是个大的圆点）
                          //  humidityline.setShape(ValueShape.SQUARE);
                            humidityline.setCubic(false);
                            humidityline.setFilled(false);
                            lines.add(humidityline);
                            lines.add(tempertureline);
                            LineChartData lineChartData = new LineChartData();
                            lineChartData.setLines(lines);
                            //坐标轴
                            Axis axisX = new Axis(); //X轴
                            axisX.setHasTiltedLabels(true);  //X坐标轴字体是斜的显示还是直的，true是斜的显示
                            axisX.setTextColor(Color.GRAY);  //设置字体颜色
                            axisX.setName("实时环境温湿度曲线图");  //表格名称
                            axisX.setTextColor(Color.parseColor("#FF23AAF2"));
                            axisX.setTextSize(10);//设置字体大小
                            axisX.setMaxLabelChars(8); //最多几个X轴坐标，意思就是你的缩放让X轴上数据的个数7<=x<=mAxisXValues.length
                            axisX.setValues(mAxisXValues);  //填充X轴的坐标名称
                            lineChartData.setAxisXBottom(axisX); //x 轴在底部
                            //data.setAxisXTop(axisX);  //x 轴在顶部
                            axisX.setHasLines(true); //x 轴分割线

                            // Y轴是根据数据的大小自动设置Y轴上限(在下面我会给出固定Y轴数据个数的解决方案)
                            Axis axisY = new Axis();  //Y轴
                            axisY.setName("温度：℃ 湿度：％");//y轴标注
                            axisY.setTextColor(Color.parseColor("#FF23AAF2"));
                            axisY.setTextSize(10);//设置字体大小
                            lineChartData.setAxisYLeft(axisY);  //Y轴设置在左边
                            //data.setAxisYRight(axisY);  //y轴设置在右边


                            //设置行为属性，支持缩放、滑动以及平移
                            lineChart.setInteractive(true);
                            lineChart.setZoomType(ZoomType.HORIZONTAL);
                            lineChart.setMaxZoom((float) 2);//最大方法比例
                            lineChart.setContainerScrollEnabled(true, ContainerScrollType.HORIZONTAL);
                            lineChart.setLineChartData(lineChartData);
                            lineChart.setVisibility(View.VISIBLE);

                            //设置X轴数据的显示个数（x轴0-7个数据）
                            Viewport v = new Viewport(lineChart.getMaximumViewport());
                            if (v.top == v.bottom && v.top != 0) {   //解决最大值最小值相等时，图不能展示问题
                                v.top = v.top * 2;
                                v.bottom = 0;
                            } else if (v.bottom == 0.0) {    //解决最大值最小值相等时全部为0时，图不能展示问题
                                v.top = 1;
                                v.bottom = 0;
                            }
                            lineChart.setMaximumViewport(v);
                            v.left = 0;
                            v.right = 7;
                            lineChart.setCurrentViewport(v);

                        }
                    });


                }

            }
        }).start();




    }

}
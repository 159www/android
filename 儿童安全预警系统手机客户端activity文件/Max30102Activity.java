package com.example.ok;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import lecho.lib.hellocharts.gesture.ContainerScrollType;
import lecho.lib.hellocharts.gesture.ZoomType;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.view.LineChartView;

public class Max30102Activity extends AppCompatActivity {
    private LineChartView lineChart;
    private List<PointValue> heart_PointValues = new ArrayList<PointValue>();
    private List<AxisValue> mAxisXValues = new ArrayList<AxisValue>();
    private List<Integer> heart_value=new ArrayList<Integer>();
    private  int index=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_max30102);
        //绑定折线图控件
        lineChart = (LineChartView)findViewById(R.id.heart_line_chart);
        //最高心率显示
        TextView  max_heart_beats_text_view=findViewById(R.id.max30102_activity_max_heart_beats_text_view);
        //最低心率显示
        TextView  min_heart_beats_text_view=findViewById(R.id.max30102_activity_min_heart_beats_text_view);
        TextView  average_haert_beats_text_view=findViewById(R.id.max30102_activity_average_heart_beats_text_view);
        //心率上限控制
        SeekBar heart_high_seekbar=findViewById(R.id.max30102_activity_high_seekbar);
        //心率上限显示
        TextView heart_high_textview=findViewById(R.id.max30102_activity_high_text_view);
        //心率下限控制
        SeekBar heart_low_seekbar=findViewById(R.id.max30102_activity_low_seekbar);
        //心率下限显示
        TextView heart_low_textview=findViewById(R.id.max30102_activity_low_text_view);
        //心率上限显示更新及阈值更新
        heart_high_seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
            {
                //显示更新
                heart_high_textview.setText("上限："+String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                //阈值传递到服务端
                Intent service_intent=new Intent(getApplicationContext(),com.example.ok.MyService.class);
                service_intent.putExtra("heart_beats_high",seekBar.getProgress());
                startService(service_intent);

            }
        });
        //心率下限显示及阈值更新
        heart_low_seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                heart_low_textview.setText("下限："+String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                //阈值传递到服务端
                Intent service_intent=new Intent(getApplicationContext(),com.example.ok.MyService.class);
                service_intent.putExtra("heart_beats_low",seekBar.getProgress());
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
                    int heart_beats=Integer.parseInt(data.getHeart_beats());
                     //显示实时心率
                    heart_value.add(new Integer(data.getHeart_beats()));
                    heart_PointValues.add(new PointValue(index, heart_beats));
                    mAxisXValues.add(new AxisValue(index).setLabel(Integer.toString(index)));
                    index++;
                    if(index>=20)
                    {
                        index=0;
                        heart_PointValues.clear();
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            String max_heart=Collections.max(heart_value).toString();
                            String min_heart=Collections.min(heart_value).toString();
                            long total=0;
                            for(int i=0;i<heart_value.size();i++)
                            {
                                total+=heart_value.get(i);
                            }
                            long average=total/heart_value.size();
                            max_heart_beats_text_view.setText("最高心率："+max_heart+"pbs");
                            min_heart_beats_text_view.setText("最低心率："+min_heart+"pbs");
                            average_haert_beats_text_view.setText("平均心率："+String.valueOf(average)+"pbs");
                            Line tempertureline = new Line(heart_PointValues).setColor(Color.parseColor("#FF990D06"));  //折线的颜色（红色）
                            List<Line> lines = new ArrayList<Line>();
                            // tempertureline.setShape(ValueShape.CIRCLE);//折线图上每个数据点的形状  这里是圆形 （有三种 ：ValueShape.SQUARE  ValueShape.CIRCLE  ValueShape.DIAMOND）
                            tempertureline.setCubic(false);//曲线是否平滑，即是曲线还是折线
                            tempertureline.setFilled(true);//是否填充曲线的面积
                            // tempertureline.setHasLabels(true);//曲线的数据坐标是否加上备注
//      line.setHasLabelsOnlyForSelected(true);//点击数据坐标提示数据（设置了这个line.setHasLabels(true);就无效）
                            tempertureline.setHasLines(true);//是否用线显示。如果为false 则没有曲线只有点显示
                            tempertureline.setHasPoints(true);//是否显示圆点 如果为false 则没有原点只有点显示（每个数据点都是个大的圆点）
                            lines.add(tempertureline);
                            LineChartData lineChartData = new LineChartData();
                            lineChartData.setLines(lines);
                            //坐标轴
                            Axis axisX = new Axis(); //X轴
                            axisX.setHasTiltedLabels(true);  //X坐标轴字体是斜的显示还是直的，true是斜的显示
                            axisX.setTextColor(Color.GRAY);  //设置字体颜色
                            axisX.setName("实时心率曲线图");  //表格名称
                            axisX.setTextColor(Color.parseColor("#FF23AAF2"));
                            axisX.setTextSize(10);//设置字体大小
                            axisX.setMaxLabelChars(8); //最多几个X轴坐标，意思就是你的缩放让X轴上数据的个数7<=x<=mAxisXValues.length
                            axisX.setValues(mAxisXValues);  //填充X轴的坐标名称
                            lineChartData.setAxisXBottom(axisX); //x 轴在底部
                            //data.setAxisXTop(axisX);  //x 轴在顶部
                            axisX.setHasLines(true); //x 轴分割线

                            // Y轴是根据数据的大小自动设置Y轴上限(在下面我会给出固定Y轴数据个数的解决方案)
                            Axis axisY = new Axis();  //Y轴
                            axisY.setName("心率：bps");//y轴标注
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
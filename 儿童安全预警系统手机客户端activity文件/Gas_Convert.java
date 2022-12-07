package com.example.ok;

public class Gas_Convert
{
    //mq-9输出电压转浓度
   double CO_Convert(double output_voltage)
    {
        double m,n;//x轴起点和终点
        double i,sum;
        double eps=1e-6;//eps是精度控制，此处为10^-6，表示无限小
        double a,b,c,d;//依次为3、2、1、0、次方的系数
        double f1,f2;//y轴对应的值
        m=0;
        n=500;
        a=Math.pow(8.45837*2.7,-13);
        b=Math.pow(2.26444*2.7,-8);
        c=0.00027;
        d=-1.81535;
        //f(x)=0
        f1=-1.81535+0.00027*m-(Math.pow(2.26444*2.7,-8))*Math.pow(m,2)-(Math.pow(8.45837*2.7,-13))*Math.pow(m,3);
        f2=-1.81535+0.00027*n-(Math.pow(2.26444*2.7,-8))*Math.pow(n,2)-(Math.pow(8.45837*2.7,-13))*Math.pow(n,3);
        //判断f1*f2<0是主要代码
        if(f1*f2<0)
        {
            while(Math.abs(m-n)>eps)
            {
                i=(m+n)/2;
                sum=a*Math.pow(i,3)+b*Math.pow(i,2)+c*i+d;
                if(Math.abs(sum)<eps)//如果函数f(i)的绝对值|sum|小于无限小
                {

                   return i;
                }
                else if(f1*sum<0)
                {
                    n=i;
                }
                else if(f2*sum<0)
                {
                    m=i;
                }
            }
        }
        //如果刚好区间取值为方程解
        else if(f1*f2==0)
        {
            if(f1==0)
            {
                return  f1;
            }
            if(f2==0)
            {
                return  f2;
            }
        }

        else
        {
            return -1;//无解
        }
        return -1;
    }
    double CH4_Convert(double output_voltage)
    {
        //[0,2.06319)
        if(output_voltage>=0&&output_voltage<2.06319)
        {
            return output_voltage/0.002060;
        }
        //[2.06319,2.2836)
        if(output_voltage>=2.06319&&output_voltage<2.2836)
        {
            return (output_voltage-1.84277)/0.000220;
        }
        //[2.2836,2.44813)
        if(output_voltage>=2.2836&&output_voltage<2.44813)
        {
            return (output_voltage-1.95457)/0.000165;
        }
        //[2.44813,2.56319)
        if(output_voltage>=2.44813&&output_voltage<2.56319)
        {
            return (output_voltage-2.07775)/0.000115;
        }
        //[2.56319,2.69521)
        if(output_voltage>=2.56319&&output_voltage<2.69521)
        {
            return  (output_voltage-2.03511)/0.00013;
        }
        //[2.69521,2.76558)
        if(output_voltage>=2.69521&&output_voltage<2.76588)
        {
            return (output_voltage-2.34336)/0.000070;
        }
        //[2.76588,2.82138)
        if(output_voltage>=2.76588&&output_voltage<2.82138)
        {
            return  (output_voltage-2.43078)/0.000056;
        }
        //[2.82138,2.89191)
        if(output_voltage>=2.82138&&output_voltage<2.89191)
        {
            return (output_voltage-2.32767)/0.000070;
        }
        //[2.89191,2.91299)
        if(output_voltage>=2.89191&&output_voltage<2.91299)
        {
            return (output_voltage-2.72327)/0.000021;
        }
        //[2.91299,2.95718]
        if(output_voltage>=2.91299&&output_voltage<2.95718)
        {
            return  (output_voltage-2.51528)/0.000044;
        }

        return -1;
    }

}

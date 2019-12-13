package cn.aorise.petition.staff.common;

import android.content.Context;
import android.graphics.Color;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;

import java.util.ArrayList;
import java.util.List;

import cn.aorise.petition.staff.R;


/**
 * 作者：李世林 Administrator on 2018/7/24 14:52
 * 邮箱：1871907207@qq.com
 */
public class MpChartAndroidUtil {

    // TODO: 2017/9/1 饼状图
    public static void setPieData(PieChart pieChart, String[] x, int count, int[] y, List<Integer> colorList) {

        pieChart.animateXY(1400, 1400);
        pieChart.setDescription("");
        // 准备x"轴"数据：在i的位置，显示x[i]字符串
        ArrayList<String> xVals = new ArrayList<String>();

        // 真实的饼状图百分比分区。
        // Entry包含两个重要数据内容：position和该position的数值。
        ArrayList<Entry> yVals = new ArrayList<Entry>();

        for (int xi = 0; xi < count; xi++) {
            xVals.add(xi, x[xi]);

            // y[i]代表在x轴的i位置真实的百分比占
            yVals.add(new Entry(y[xi], xi));
        }

        PieDataSet yDataSet = new PieDataSet(yVals, "");

        // 每个百分比占区块绘制的不同颜色
        ArrayList<Integer> colors = new ArrayList<Integer>();
        for (int i = 0; i < colorList.size(); i++) {
            colors.add(colorList.get(i));
        }
        yDataSet.setColors(colors);

        // 将x轴和y轴设置给PieData作为数据源
        PieData data = new PieData(xVals, yDataSet);

        // 设置成PercentFormatter将追加%号
        data.setValueFormatter(new PercentFormatter());

        // 文字的颜色
        data.setValueTextColor(Color.BLACK);

        // 最终将全部完整的数据喂给PieChart
        pieChart.setData(data);
        //这个方法为true就是环形图，为false就是饼图
        pieChart.setDrawHoleEnabled(false);

        pieChart.setUsePercentValues(true);

        pieChart.setExtraOffsets(5, 10, 5, 5);

        pieChart.setDragDecelerationFrictionCoef(0.95f);


        //mBinding.piechart.setDrawHoleEnabled(true);
        pieChart.setHoleColor(Color.WHITE);

        pieChart.setTransparentCircleColor(Color.WHITE);
        pieChart.setTransparentCircleAlpha(110);

        pieChart.setHoleRadius(58f);
        pieChart.setTransparentCircleRadius(61f);

        pieChart.setDrawCenterText(true);

        pieChart.setRotationAngle(0);
        // enable rotation of the chart by touch
        pieChart.setRotationEnabled(true);
        pieChart.setHighlightPerTapEnabled(true);
        pieChart.setDrawSliceText(false);//是否显示饼形图上的文字描述
        // TODO: 2017/9/13 x轴标签一行显示不限分行显示
        Legend l=pieChart.getLegend();
        //l.setForm(Legend.LegendForm.LINE);
        l.setWordWrapEnabled(true);

        pieChart.setExtraBottomOffset(10);



        pieChart.invalidate();
    }

    // TODO: 2018/7/24 横向柱状图
    public static void showBarChart(Context context, HorizontalBarChart barChart, BarData barData) {
        barChart.setDrawBorders(false);  ////是否在折线图上添加边框

        barChart.setDescription("");// 数据描述

        // 如果没有数据的时候，会显示这个，类似ListView的EmptyView
        barChart.setNoDataTextDescription("宝宝没搜寻到数据~ ~ ~");


        barChart.setMaxVisibleValueCount(60);
        barChart.setPinchZoom(false);
        barChart.setDrawBarShadow(false);//是否显示柱状图背景颜色

        XAxis xAxis = barChart.getXAxis();// x轴
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);// 设置x轴坐标在底部，默认在顶部
        xAxis.setTextColor(context.getResources().getColor(R.color.bb_565656));
        barChart.getXAxis().setGridColor(context.getResources().getColor(R.color.bb_ffffff)); //将横向网格线设置为白色  与底色同步  达到隐藏的目的
        barChart.setDrawGridBackground(false); // 是否显示表格颜色
        barChart.setGridBackgroundColor(Color.WHITE & 0x70FFFFFF); // 表格的的颜色，在这里是是给颜色设置一个透明度

        barChart.setTouchEnabled(true); // 设置是否可以触摸
        barChart.setDragEnabled(true);// 是否可以拖拽
        barChart.setScaleEnabled(true);// 是否可以缩放
        barChart.setPinchZoom(false);//


        YAxis yl = barChart.getAxisLeft();
        yl.setDrawAxisLine(true);
        yl.setDrawGridLines(true);

        barChart.setData(barData); // 设置数据

        Legend mLegend = barChart.getLegend(); // 设置比例图标示  标签
        mLegend.setForm(Legend.LegendForm.SQUARE);// 标签样式
        mLegend.setFormSize(6f);// 字体
        mLegend.setTextColor(context.getResources().getColor(R.color.bb_565656));// 颜色
        mLegend.setEnabled(true);


//      X轴设定
//      XAxis xAxis = barChart.getXAxis();
//      xAxis.setPosition(XAxisPosition.BOTTOM);

        barChart.animateXY(1000, 1000); // 立即执行的动画,xy轴  动画时间
    }


    // TODO: 2017/9/1 柱状图
    public static void showBarChart1(Context context, BarChart barChart, BarData barData) {
        barChart.setDrawBorders(false);  ////是否在折线图上添加边框

        barChart.setDescription("");// 数据描述

        // 如果没有数据的时候，会显示这个，类似ListView的EmptyView
        barChart.setNoDataTextDescription("");


        barChart.setMaxVisibleValueCount(60);
        barChart.setPinchZoom(false);
        barChart.setDrawBarShadow(false);//是否显示柱状图背景颜色

        XAxis xAxis = barChart.getXAxis();// x轴
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);// 设置x轴坐标在底部，默认在顶部
        xAxis.setTextColor(context.getResources().getColor(R.color.bb_565656));
        barChart.getXAxis().setGridColor(context.getResources().getColor(R.color.bb_ffffff)); //将横向网格线设置为白色  与底色同步  达到隐藏的目的
        barChart.getAxisLeft().setEnabled(true);
        barChart.setDrawGridBackground(false); // 是否显示表格颜色
        barChart.setGridBackgroundColor(Color.WHITE & 0x70FFFFFF); // 表格的的颜色，在这里是是给颜色设置一个透明度
        barChart.getAxisRight().setEnabled(false);   //隐藏右边 的坐标轴
        barChart.setTouchEnabled(true); // 设置是否可以触摸
        barChart.setDragEnabled(true);// 是否可以拖拽
        barChart.setScaleEnabled(true);// 是否可以缩放
        barChart.setPinchZoom(false);//


        barChart.setData(barData); // 设置数据

        Legend mLegend = barChart.getLegend(); // 设置比例图标示  标签
        mLegend.setForm(Legend.LegendForm.SQUARE);// 标签样式
        // TODO: 2017/9/13 x轴标签一行显示不限分行显示
        mLegend.setWordWrapEnabled(true);
        mLegend.setFormSize(6f);// 字体
        mLegend.setEnabled(false);
        mLegend.setTextColor(context.getResources().getColor(R.color.bb_565656));// 颜色
        mLegend.setMaxSizePercent(2.0f);//设置最大图例的百分比（相对整个图表大小）。 默认值：0.95f（95％）
        barChart.animateXY(1000, 1000); // 立即执行的动画,xy轴  动画时间
    }


    // TODO: 2018/7/24 折线图
    public static void showChart2(Context context, LineChart lineChart, LineData lineData) {
        lineChart.setDrawBorders(false);  //是否在折线图上添加边框

        // no description text
        lineChart.setDescription("");// 数据描述
        // 如果没有数据的时候，会显示这个，类似listview的emtpyview
        lineChart.setNoDataTextDescription("You need to provide data for the chart.");

        // enable / disable grid background
        lineChart.setDrawGridBackground(false); // 是否显示表格颜色
        lineChart.setGridBackgroundColor(Color.WHITE & 0x70FFFFFF); // 表格的的颜色，在这里是是给颜色设置一个透明度

        // enable touch gestures
        lineChart.setTouchEnabled(true); // 设置是否可以触摸

        // enable scaling and dragging
        lineChart.setDragEnabled(true);// 是否可以拖拽
        lineChart.setScaleEnabled(true);// 是否可以缩放
        lineChart.getAxisRight().setEnabled(false);   //隐藏右边 的坐标轴
        lineChart.setDrawGridBackground(false); // 是否显示表格颜色
        lineChart.getXAxis().setGridColor(context.getResources().getColor(R.color.bb_ffffff)); //将横向网格线设置为白色  与底色同步  达到隐藏的目的
        XAxis xAxis = lineChart.getXAxis();// x轴
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);// 设置x轴坐标在底部，默认在顶部
        lineChart.setBackgroundColor(context.getResources().getColor(R.color.bb_ffffff));// 设置背景

        // if disabled, scaling can be done on x- and y-axis separately
        lineChart.setPinchZoom(false);//


        // add data
        lineChart.setData(lineData); // 设置数据

        // get the legend (only possible after setting data)
        Legend mLegend = lineChart.getLegend(); // 设置比例图标示，就是那个一组y的value的

        // modify the legend ...
        // mLegend.setPosition(LegendPosition.LEFT_OF_CHART);
        mLegend.setForm(Legend.LegendForm.CIRCLE);// 样式
        mLegend.setFormSize(6f);// 字体
        mLegend.setTextColor(Color.BLACK);// 颜色
//      mLegend.setTypeface(mTf);// 字体
        lineChart.animateXY(1000, 1000); // 立即执行的动画,x轴
    }
}

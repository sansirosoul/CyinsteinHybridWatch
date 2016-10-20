package com.xyy.Gazella.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.ysp.smartwatch.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/10/11.
 */

public class StepDayFragment extends Fragment {
    @BindView(R.id.chart1)
    BarChart mChart;
    private View view;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        view = inflater.inflate(R.layout.fragment_step_day, container, false);

        ButterKnife.bind(this, view);
        initChart();
        return view;
    }

    private void initChart() {


        mChart.setDescription("");
        mChart.setPinchZoom(false);
        mChart.setDrawBarShadow(false);
        mChart.setDrawGridBackground(false);
        XAxis xAxis = mChart.getXAxis();
     //   xAxis.setStartAtZero(true);
      //  xAxis.setSpaceBetweenLabels();

        xAxis.setAvoidFirstLastClipping(true);
        xAxis.setTextColor(Color.rgb(255, 255, 255));
        xAxis.setAxisLineColor(Color.rgb(255, 255, 255));
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setLabelCount(12);
        xAxis.setGranularity(5f);

        xAxis.setDrawGridLines(false);

        mChart.getAxisLeft().setTextColor(Color.rgb(255, 255, 255));
        mChart.getAxisLeft().setAxisLineColor(Color.rgb(255, 255, 255));
        mChart.getAxisLeft().setDrawGridLines(false);
        mChart.getAxisRight().setEnabled(false);

        // setting data
        mChart.animateY(2500);   //动画

        mChart.getLegend().setEnabled(false);

















//        mChart.setTouchEnabled(false); // 设置是否可以触摸
//        mChart.setDragEnabled(false);// 是否可以拖拽
//
//        // if more than 60 entries are displayed in the chart, no values will be
//        // drawn
//        xAxis = mChart.getXAxis();
//        yAxis = mChart.getAxisLeft();
//        mChart.setMaxVisibleValueCount(40);
//
//        // scaling can now only be done on x- and y-axis separately
//        mChart.setPinchZoom(false);
//        mChart.setDrawBarShadow(false);
//
//        mChart.setDrawValueAboveBar(false);
//        mChart.setHighlightFullBarEnabled(false);
//
////     leftAxis.setValueFormatter(new MyAxisValueFormatter());
////     leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)
//        mChart.getAxisRight().setEnabled(false);
//        mChart.getAxisLeft().setEnabled(false);
//
//        mChart.setDescription("");
//        mChart.setDrawGridBackground(true);   //设置图表内格子背景是否显示，默认是false
//        mChart.setGridBackgroundColor(Color.rgb(55, 55, 55));   //设置背景色
//        mChart.setDrawBorders(false);     //设置图表内格子外的边框是否显示
//        mChart.setBorderColor(Color.rgb(236, 228, 126));   //上面的边框颜色
//        mChart.setBorderWidth(20);       //上面边框的宽度，float类型，dp单位
//
//        xAxis.setEnabled(false);    // 不画背景空格
//        xAxis.setDrawAxisLine(true); //是否绘制坐标轴的线，即含有坐标的那条线，默认是true
//
//        yAxis.setSpaceBottom(0);     //  设置 Y距离底部位置
//        yAxis.setStartAtZero(true);    //设置Y轴坐标是否从0开始
//        //yAxis.setAxisMaxValue(50);    //设置Y轴坐标最大为多少
//
//        xAxis.setTextColor(Color.rgb(255, 255, 255)); //X轴上的刻度的颜色
//        xAxis.setPosition(XAxis.XAxisPosition.TOP);//把坐标轴放在上下 参数有：TOP, BOTTOM, BOTH_SIDED, TOP_INSIDE or BOTTOM_INSIDE.
//        xAxis.setTextSize(15); //X轴上的刻度的字的大小 单位dp
//        xAxis.setGridColor(Color.rgb(255, 255, 255)); //X轴上的刻度竖线的颜色
//        xAxis.setGridLineWidth(1); //X轴上的刻度竖线的宽 float类型
//        xAxis.enableGridDashedLine(40, 3, 0); //虚线表示X轴上的刻度竖线(float lineLength, float spaceLength, float phase)三个参数，1.线长，2.虚线间距，3.虚线开始坐标

        // setting data
        setChartData();

    }

    private void setChartData() {

        ArrayList<BarEntry> yVals1 = new ArrayList<>();
        for (int i = 0; i < 24; i++) {
            float mult = (1000);
            float val = (float) (Math.random() * mult) + mult / 1;
            yVals1.add(new BarEntry(i, val));
        }
        BarDataSet set1;

        if (mChart.getData() != null &&mChart.getData().getDataSetCount() > 0) {
            set1 = (BarDataSet)mChart.getData().getDataSetByIndex(0);
            set1.setValues(yVals1);
            mChart.getData().notifyDataChanged();
            mChart.notifyDataSetChanged();


        } else {
            set1 = new BarDataSet(yVals1, "");
            set1.setColor(Color.rgb(255,255,255));
            set1.setDrawValues(false);
            ArrayList<IBarDataSet> dataSets = new ArrayList<>();
            dataSets.add(set1);
            BarData data = new BarData(dataSets);
            mChart.setData(data);
            mChart.setFitBars(true);
        }
        mChart.invalidate();
    }
}

package com.xyy.Gazella.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.ysp.smartwatch.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/10/11.
 */

public class SleepDayFragment extends Fragment {
    @BindView(R.id.chart1)
    BarChart mChart;
    @BindView(R.id.ll_date)
    LinearLayout llDate;
    private View view;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        view = inflater.inflate(R.layout.fragment_sleep_day, container, false);

        ButterKnife.bind(this, view);
        initChart();
        return view;
    }

    private void initChart() {
        mChart.setDescription("");
        mChart.setPinchZoom(false);
        mChart.setDrawBarShadow(false);
        mChart.setDrawGridBackground(false);
        mChart.setBackground(getResources().getDrawable(R.drawable.page20_tubiao_bg));
        mChart.setBorderColor(Color.rgb(255, 255, 255));
//        mChart.setDrawBorders(true);

        XAxis xAxis = mChart.getXAxis();

        xAxis.setAvoidFirstLastClipping(true);
        xAxis.setTextColor(Color.rgb(255, 255, 255));
        xAxis.setAxisLineColor(Color.rgb(255, 255, 255));
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setLabelCount(10);
        xAxis.setDrawGridLines(false);

        mChart.getAxisLeft().setTextColor(Color.rgb(255, 255, 255));
        mChart.getAxisLeft().setAxisLineColor(Color.rgb(255, 255, 255));
        mChart.getAxisLeft().setDrawGridLines(false);
        mChart.getAxisRight().setEnabled(false);
        mChart.getAxisLeft().setEnabled(false);
        mChart.getAxisLeft().setSpaceBottom(0);
        // setting data
        mChart.animateY(2500);   //动画
        mChart.getLegend().setEnabled(false);
        setChartData();

    }

    private void setChartData() {

        float start = 0f;

        ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();

        for (int i = (int) start; i < start + 7 + 1; i++) {
            float mult = (7 + 1);
            float val = (float) (Math.random() * mult);
            yVals1.add(new BarEntry(i + 1f, val));
        }

        BarDataSet set1;

        if (mChart.getData() != null &&
                mChart.getData().getDataSetCount() > 0) {
            set1 = (BarDataSet) mChart.getData().getDataSetByIndex(0);
            set1.setValues(yVals1);
            mChart.getData().notifyDataChanged();
            mChart.notifyDataSetChanged();
        } else {
            set1 = new BarDataSet(yVals1, "The year 2017");
            set1.setColors(ColorTemplate.MATERIAL_COLORS);

            ArrayList<IBarDataSet> dataSets = new ArrayList<>();
            dataSets.add(set1);

            BarData data = new BarData(dataSets);
            data.setValueTextSize(10f);
            data.setBarWidth(0.9f);

            mChart.setData(data);
        }
    }

    /***
     *     设置日期栏是否显示
     * @param visible
     */

    public  void setLlDateVisible(int visible){
        llDate.setVisibility(visible);
    }
    public  boolean getLlDateVisible(){
        if(llDate.getVisibility()==View.VISIBLE)
            return true;
        else
        return  false;
    }
}

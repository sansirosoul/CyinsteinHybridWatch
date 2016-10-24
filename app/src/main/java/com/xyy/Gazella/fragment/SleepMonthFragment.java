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
import com.xyy.Gazella.view.CreateColor;
import com.ysp.smartwatch.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/10/11.
 */

public class SleepMonthFragment extends Fragment {
    @BindView(R.id.ll_date)
    LinearLayout llDate;
    @BindView(R.id.chart1)
    BarChart mChart;
    private View view;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        view = inflater.inflate(R.layout.fragment_sleep_month, container, false);
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
        XAxis xAxis = mChart.getXAxis();

        xAxis.setAvoidFirstLastClipping(true);
        xAxis.setTextColor(Color.rgb(255, 255, 255));
        xAxis.setAxisLineColor(Color.rgb(255, 255, 255));
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setLabelCount(12);

        xAxis.setAxisLineWidth(1f);
        xAxis.setDrawGridLines(false);
        mChart.getAxisRight().setStartAtZero(true);
        mChart.getAxisLeft().setSpaceBottom(0);
        mChart.getAxisLeft().setTextColor(Color.rgb(255, 255, 255));
        mChart.getAxisLeft().setAxisLineColor(Color.rgb(255, 255, 255));
        mChart.getAxisLeft().setDrawGridLines(false);
        mChart.getAxisRight().setEnabled(false);

        // setting data
        mChart.animateY(2500);   //动画
        mChart.getLegend().setEnabled(false);

        setChartData();

    }

    private void setChartData() {
        ArrayList<BarEntry> yVals1 = new ArrayList<>();
        for (int i = 0; i < 31; i++) {
            float mult = (30);
            float val1 = (float) (Math.random() * mult) + mult / 3;
            float val2 = (float) (Math.random() * mult) + mult / 3;
            float val3 = (float) (Math.random() * mult) + mult / 3;

            yVals1.add(new BarEntry(i, new float[]{val1, val2, val3}));
        }

        BarDataSet set1;

        if (mChart.getData() != null &&
                mChart.getData().getDataSetCount() > 0) {
            set1 = (BarDataSet) mChart.getData().getDataSetByIndex(0);
            set1.setValues(yVals1);
            mChart.getData().notifyDataChanged();
            mChart.notifyDataSetChanged();
        } else {
            set1 = new BarDataSet(yVals1, "");
            set1.setBarBorderColor(Color.rgb(55,55,55));
            set1.setColors(getColors());
            set1.setDrawValues(false);

            ArrayList<IBarDataSet> dataSets = new ArrayList<>();
            dataSets.add(set1);
            BarData data = new BarData(dataSets);
            data.setBarWidth(0.5f);
            data.setValueTextColor(Color.WHITE);

            mChart.setData(data);
        }
        mChart.setFitBars(true);
        mChart.invalidate();
    }

    private int[] getColors() {

        int stacksize = 3;

        // have as many colors as stack-values per entry
        int[] colors = new int[stacksize];

        for (int i = 0; i < colors.length; i++) {
            colors[i] = CreateColor.MATERIAL_COLORS[i];
        }

        return colors;
    }

    /***
     * 设置日期栏是否显示
     *
     * @param visible
     */

    public void setLlDateVisible(int visible) {
        llDate.setVisibility(visible);
    }

    public boolean getLlDateVisible() {
        if (llDate.getVisibility() == View.VISIBLE&&llDate!=null)
            return true;
        else
            return false;
    }
}

package com.xyy.Gazella.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.AxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.xyy.Gazella.utils.SomeUtills;
import com.ysp.smartwatch.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/10/11.
 */

public class StepMonthFragment extends Fragment {
    @BindView(R.id.ll_date)
    LinearLayout llDate;
    @BindView(R.id.chart1)
    BarChart mChart;
    @BindView(R.id.tv_date)
    TextView tvDate;
    private View view;

    private String[] XString = new String[]{"1", "3", "5", "7", "9", "11", "13", "15", "17", "19", "21", "23",};

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        view = inflater.inflate(R.layout.fragment_step_month, container, false);
        ButterKnife.bind(this, view);
        initChart();
        tvDate.setText(new SomeUtills().getDate(1));
        return view;
    }

    private void initChart() {
        mChart.setDescription("");
        mChart.setPinchZoom(false);
        mChart.setDrawBarShadow(false);
        mChart.setDrawGridBackground(false);

        XAxis xAxis = mChart.getXAxis();
        xAxis.setAvoidFirstLastClipping(true);
        xAxis.setTextColor(Color.rgb(255, 255, 255));
        xAxis.setAxisLineColor(Color.rgb(255, 255, 255));

        xAxis.setLabelCount(12);
        xAxis.setAxisLineWidth(1f);
        xAxis.setDrawGridLines(false);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        mChart.getAxisLeft().setTextColor(Color.rgb(255, 255, 255));
        mChart.getAxisLeft().setAxisLineColor(Color.rgb(255, 255, 255));
        mChart.getAxisLeft().setDrawGridLines(false);
        mChart.getAxisRight().setEnabled(false);

        mChart.animateY(2500);   //动画

        mChart.getLegend().setEnabled(false);
        setChartData();

    }

    private void setChartData() {

        ArrayList<BarEntry> yVals1 = new ArrayList<>();
        for (int i = 0; i < 31; i++) {
            float mult = (1000);
            float val = (float) (Math.random() * mult) + mult / 1;
            yVals1.add(new BarEntry(i, val));
        }
        BarDataSet set1;

        if (mChart.getData() != null && mChart.getData().getDataSetCount() > 0) {
            set1 = (BarDataSet) mChart.getData().getDataSetByIndex(0);
            set1.setValues(yVals1);
            mChart.getData().notifyDataChanged();
            mChart.notifyDataSetChanged();
        } else {
            set1 = new BarDataSet(yVals1, "");
            set1.setColor(Color.rgb(255, 255, 255));
            set1.setDrawValues(false);
            ArrayList<IBarDataSet> dataSets = new ArrayList<>();
            dataSets.add(set1);

            BarData data = new BarData(dataSets);
            data.setBarWidth(0.5f);
            mChart.setData(data);
            mChart.setFitBars(true);
        }
        mChart.invalidate();
    }

    class axisValueformatter implements AxisValueFormatter {

        @Override
        public String getFormattedValue(float value, AxisBase axis) {
            //Log.e(Tag, " " + value);
            return XString[(int) value % XString.length];
        }

        @Override
        public int getDecimalDigits() {
            return 0;
        }
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
        if (llDate.getVisibility() == View.VISIBLE && llDate != null)
            return true;
        else
            return false;
    }

    public void setTvDateValue(String date) {
        tvDate.setText(date);
    }
}

package com.xyy.Gazella.activity;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.Utils;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;
import com.ysp.newband.BaseActivity;
import com.ysp.smartwatch.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepActivity extends BaseActivity implements OnDateSelectedListener, OnMonthChangedListener {

    @BindView(R.id.calendarView)
    MaterialCalendarView widget;
    private LineChart mChart;
    private  boolean WidgetType=false;
    private static final DateFormat FORMATTER = SimpleDateFormat.getDateInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_step);

        ButterKnife.bind(this);
        initCalendar();
        initChart();
    }

    private void initCalendar() {

        widget.setTopbarVisible(!widget.getTopbarVisible());
        widget.state().edit() .setCalendarDisplayMode(CalendarMode.WEEKS)
                .commit();
        widget.setOnDateChangedListener(this);
        widget.setOnMonthChangedListener(this);
    }


    private void initChart() {
        mChart = (LineChart) findViewById(R.id.chart1);
        mChart.setDrawGridBackground(false);
        mChart.setTouchEnabled(true);
        mChart.setHighlightPerDragEnabled(false);//能否拖拽高亮线(数据点与坐标的提示线)，默认是true
        mChart.setHighlightPerTapEnabled(false);
        // enable scaling and dragging
        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(true);
        mChart.setPinchZoom(true);

        // x-axis limit line
        LimitLine llXAxis = new LimitLine(10f, "Index 10");
        llXAxis.setLineWidth(4f);
        llXAxis.enableDashedLine(10f, 10f, 0f);
        llXAxis.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
        llXAxis.setTextSize(10f);

        XAxis xAxis = mChart.getXAxis();
        xAxis.enableGridDashedLine(10f, 10f, 0f);
        mChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);

        LimitLine ll1 = new LimitLine(150f, "Upper Limit");
        ll1.setLineWidth(4f);
        ll1.enableDashedLine(10f, 10f, 0f);
        ll1.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
        ll1.setTextSize(10f);

        LimitLine ll2 = new LimitLine(-30f, "Lower Limit");
        ll2.setLineWidth(4f);
        ll2.enableDashedLine(10f, 10f, 0f);
        ll2.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
        ll2.setTextSize(10f);

        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.removeAllLimitLines(); // reset all limit lines to avoid overlapping lines
        leftAxis.enableGridDashedLine(10f, 10f, 0f);
        leftAxis.setDrawZeroLine(false);
        leftAxis.setDrawLimitLinesBehindData(true);

        mChart.getAxisRight().setEnabled(false);
        mChart.getLegend().setEnabled(false);
        mChart.setDescription("");  //显示右下角描述

        setData(7, 100);
        mChart.animateX(2500);
        mChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM); // 让x轴在下面

        List<ILineDataSet> sets = mChart.getData().getDataSets();
        for (ILineDataSet iSet : sets) {
            LineDataSet set = (LineDataSet) iSet;
            set.setMode(set.getMode() == LineDataSet.Mode.CUBIC_BEZIER ? LineDataSet.Mode.LINEAR : LineDataSet.Mode.CUBIC_BEZIER);
        }
    }

    private void setData(int count, float range) {
        ArrayList<Entry> values = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            float val = (float) (Math.random() * range) + 3;
            values.add(new Entry(i, val));
        }

        LineDataSet set1;
        if (mChart.getData() != null && mChart.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet) mChart.getData().getDataSetByIndex(0);
            set1.setValues(values);
            mChart.getData().notifyDataChanged();
            mChart.notifyDataSetChanged();
        } else {
            // create a dataset and give it a type
            set1 = new LineDataSet(values, "DataSet 1");
            set1.setLineWidth(1f);
            set1.setDrawCircleHole(false);
            set1.setValueTextSize(9f);
            set1.setCubicIntensity(0.3f);

            if (Utils.getSDKInt() >= 18) {
                // fill drawable only supported on api level 18 and above
                Drawable drawable = ContextCompat.getDrawable(this, R.drawable.fade_red);
                set1.setFillDrawable(drawable);
            } else {
                set1.setFillColor(Color.BLACK);
            }
            ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
            dataSets.add(set1); // add the datasets
            LineData data = new LineData(dataSets);
            mChart.setData(data);
        }




    }

//    @OnClick({R.id.btnExit, R.id.btnOpt, R.id.TVTitle})
//    public void onClick(View view) {
//        switch (view.getId()) {
//            case R.id.btnExit:
//                StepActivity.this.finish();
//                overridePendingTransitionExit(StepActivity.this);
//                break;
//            case R.id.btnOpt:
//                if (!WidgetType){
//                    widget.state().edit().setCalendarDisplayMode(CalendarMode.MONTHS)
//                            .commit();
//                    WidgetType=true;
//                }else {
//                    widget.state().edit() .setCalendarDisplayMode(CalendarMode.WEEKS)
//                            .commit();
//                    WidgetType=false;
//                }
//
//                break;
//            case R.id.TVTitle:
//                break;
//        }
//    }

    @Override
    public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
       // TVTitle.setText(getSelectedDatesString());

        if (WidgetType){
            widget.state().edit() .setCalendarDisplayMode(CalendarMode.WEEKS)
                    .commit();
            WidgetType=false;
        }
    }
    @Override
    public void onMonthChanged(MaterialCalendarView widget, CalendarDay date) {
       // TVTitle.setText(FORMATTER.format(date.getDate()));
    }

    private String getSelectedDatesString() {
        CalendarDay date = widget.getSelectedDate();
        if (date == null) {
            return "";
        }
        return FORMATTER.format(date.getDate());
    }
}

package com.xyy.Gazella.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
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
import com.ysp.newband.BaseFragment;
import com.ysp.smartwatch.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/10/11.
 */

public class StepWeekFragment extends BaseFragment {
    @BindView(R.id.chart1)
    BarChart mChart;
    @BindView(R.id.ll_date)
    LinearLayout llDate;
    @BindView(R.id.tv_date)
    TextView tvDate;
    @BindView(R.id.iv_left)
    ImageView ivLeft;
    @BindView(R.id.iv_right)
    ImageView ivRight;
    @BindView(R.id.ll_step_week)
    LinearLayout llStepWeek;
    @BindView(R.id.ll_setp_bata)
    LinearLayout llSetpBata;
    @BindView(R.id.scrollView)
    ScrollView scrollView;
    private View view;
    private String[] XString = new String[]{"周一", "周二", "周三", "周四", "周五", "周六", "周七",};

    private HashMap<String, String> weekMap;
    private Calendar CalendarInstance = Calendar.getInstance();

    private int widthChart = 0;
    private int heightChatr = 0;
    private ViewGroup.LayoutParams params;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        view = inflater.inflate(R.layout.fragment_step_week, container, false);

        ButterKnife.bind(this, view);
        initChart();
        initLldate();
        initView();
        return view;
    }

    private void initLldate() {
        weekMap = new SomeUtills().getWeekdate(CalendarInstance.getTime());
        if (weekMap != null)
            tvDate.setText(weekMap.get("1") + " - " + weekMap.get("7"));
    }

    private void initView() {
        params = mChart.getLayoutParams();
        ViewTreeObserver vto = mChart.getViewTreeObserver();
        vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                heightChatr = mChart.getHeight();
                widthChart = mChart.getWidth();

                return true;
            }
        });

    }

    private void initChart() {

        mChart.setDescription("");
        mChart.setPinchZoom(false);
        mChart.setDrawBarShadow(false);
        mChart.setDrawGridBackground(false);
        mChart.setDoubleTapToZoomEnabled(false);//双击缩放

        XAxis xAxis = mChart.getXAxis();

        xAxis.setAvoidFirstLastClipping(true);
        xAxis.setTextColor(Color.rgb(255, 255, 255));
        xAxis.setAxisLineColor(Color.rgb(255, 255, 255));
        xAxis.setLabelCount(7);
        xAxis.setGridLineWidth(10);

        xAxis.setValueFormatter(new axisValueformatter());
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setAxisLineWidth(1f);
        xAxis.setDrawGridLines(false);

        mChart.getAxisLeft().setTextColor(Color.rgb(255, 255, 255));
        mChart.getAxisLeft().setAxisLineColor(Color.rgb(255, 255, 255));
        mChart.getAxisLeft().setDrawGridLines(false);
        mChart.getAxisLeft().setMaxWidth(35f);
        mChart.getAxisLeft().setMinWidth(35f);
        mChart.getAxisLeft().setLabelCount(6, true);
        mChart.getAxisRight().setEnabled(false);

        // setting data
        mChart.animateY(2500);   //动画
        mChart.getLegend().setEnabled(false);

        setChartData();

        mChart.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                new SomeUtills().setCalendarViewGone(1);

                return false;
            }
        });

    }

    private void setChartData() {

        ArrayList<BarEntry> yVals1 = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
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
        if (visible == View.VISIBLE) {
            scrollView.setFillViewport(true);
        } else {
            scrollView.setFillViewport(false);
            params.height = heightChatr;
            params.width = widthChart;
            mChart.setLayoutParams(params);
        }
        llDate.setVisibility(visible);
        llSetpBata.setVisibility(visible);
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

    @OnClick({R.id.iv_left, R.id.iv_right, R.id.ll_step_week})
    public void onClick(View view) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
        String strDatre = tvDate.getText().toString();
        String strings[] = strDatre.split(" ");
        strDatre = strings[0];
        Date date = null;
        try {
            date = sdf.parse(strDatre);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        switch (view.getId()) {
            case R.id.iv_left:
                weekMap = new SomeUtills().getAmountWeekdate(date, 0);
                if (weekMap != null)
                    tvDate.setText(weekMap.get("1") + " - " + weekMap.get("7"));
                break;
            case R.id.iv_right:
                weekMap = new SomeUtills().getAmountWeekdate(date, 1);
                if (weekMap != null)
                    tvDate.setText(weekMap.get("1") + " - " + weekMap.get("7"));
                break;
            case R.id.ll_step_week:
                new SomeUtills().setCalendarViewGone(1);
                break;
        }
    }
}

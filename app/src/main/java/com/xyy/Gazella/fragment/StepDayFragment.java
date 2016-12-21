package com.xyy.Gazella.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.xyy.Gazella.utils.SomeUtills;
import com.ysp.hybridtwatch.R;
import com.ysp.newband.BaseFragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/10/11.
 */

public class StepDayFragment extends BaseFragment {
    @BindView(R.id.chart1)
    BarChart mChart;
    @BindView(R.id.ll_date)
    LinearLayout llDate;
    @BindView(R.id.tv_date)
    TextView tvDate;
    @BindView(R.id.iv_left)
    LinearLayout ivLeft;
    @BindView(R.id.iv_right)
    LinearLayout ivRight;
    @BindView(R.id.ll_step_day)
    LinearLayout llStepDay;
    @BindView(R.id.ll_setp_bata)
    LinearLayout llSetpBata;
    @BindView(R.id.scrollView)
    ScrollView scrollView;
    private View view;
    private String[] xValue = new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23",};

    private int widthChart = 0;
    private int heightChatr = 0;
    private ViewGroup.LayoutParams params;
    private  int count=145631;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_step_day, container, false);

        ButterKnife.bind(this, view);
        initChart();
        initView();
        tvDate.setText(new SomeUtills().getDate(Calendar.getInstance().getTime(), 0));
        super.onCreateView(inflater, container, savedInstanceState);
        return view;
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

//
//        SpannableString styledText = new SpannableString("你今天走了" + count + "步，比昨天多走了"+count+"步！");
//        styledText.setSpan(new TextAppearanceSpan(mContext,
//                R.style.StepText), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//        styledText.setSpan(new TextAppearanceSpan(mContext,
//                        R.style.countTextViewZero),
//                getTimeLabel(timeType).length() + 2, String.valueOf(count)
//                        .length() + getTimeLabel(timeType).length() + 2,
//                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//        mTitle.setText(styledText, TextView.BufferType.SPANNABLE);


    }


    private void initChart() {
        mChart.setDescription("");
        mChart.setPinchZoom(false);
        mChart.setDrawBarShadow(false);
        mChart.setDrawGridBackground(false);
        mChart.setDoubleTapToZoomEnabled(false);//双击缩放
        mChart.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                new SomeUtills().setCalendarViewGone(1);
                return false;
            }
        });
        XAxis xAxis = mChart.getXAxis();

        mChart.refreshDrawableState();
        xAxis.setAvoidFirstLastClipping(true);
        xAxis.setTextColor(Color.rgb(255, 255, 255));
        xAxis.setAxisLineColor(Color.rgb(255, 255, 255));
        xAxis.setGridLineWidth(1);
        xAxis.setGranularity(1f);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setLabelCount(12);

        xAxis.setAxisLineWidth(1f);
        xAxis.setGridLineWidth(10);
        xAxis.setDrawGridLines(false);

        mChart.getAxisLeft().setTextColor(Color.rgb(255, 255, 255));
        mChart.getAxisLeft().setAxisLineColor(Color.rgb(255, 255, 255));
        mChart.getAxisLeft().setDrawGridLines(false);
        mChart.getAxisRight().setEnabled(false);

        mChart.getAxisLeft().setSpaceBottom(0);
        mChart.getAxisLeft().setMaxWidth(35f);
        mChart.getAxisLeft().setMinWidth(35f);
        mChart.getAxisLeft().setStartAtZero(false);

        mChart.getAxisLeft().setLabelCount(6, true);
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

//        ArrayList<BarEntry> yVals1 = new ArrayList<>();
//        for (int i = 0; i < xValue.length; i++) {
//            yVals1.add(new BarEntry(i,  Integer.valueOf(xValue[i])));
//        }

        ArrayList<BarEntry> yVals1 = new ArrayList<>();
        for (int i = 0; i < 24; i++) {
            float mult = (10000);
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
            mChart.animateY(2500);
        }
        mChart.invalidate();
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

    @OnClick({R.id.iv_left, R.id.iv_right, R.id.ll_step_day})
    public void onClick(View view) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
        Date date = null;
        try {
            date = sdf.parse(tvDate.getText().toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        switch (view.getId()) {
            case R.id.iv_left:
                tvDate.setText(new SomeUtills().getAmountDate(date, 0, 0));
                String[] xValue = new String[]{"0", "10", "20", "30", "40", "50", "60", "70", "80", "90", "100", "110", "120", "130", "140", "150", "160",
                        "170", "180", "190", "200", "210", "220", "230",};
                updateUI(xValue);
                break;
            case R.id.iv_right:
                tvDate.setText(new SomeUtills().getAmountDate(date, 0, 1));
                String[] xValues = new String[]{"0", "100", "200", "300", "400", "500", "600", "700", "800", "900", "1000", "1100", "1200", "1300", "1400",
                        "1500", "1600", "1700", "1800", "1900", "2000", "2100", "2200", "2300",};
                updateUI(xValues);
                break;
            case R.id.ll_step_day:
                new SomeUtills().setCalendarViewGone(1);
                break;
        }
    }

    public void updateUI(String[] xValue) {
        this.xValue = xValue;
        setChartData();
    }
}

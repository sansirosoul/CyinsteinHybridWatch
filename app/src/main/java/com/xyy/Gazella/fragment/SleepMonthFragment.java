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
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.AxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.partner.entity.Partner;
import com.xyy.Gazella.activity.SleepActivity;
import com.xyy.Gazella.utils.SomeUtills;
import com.xyy.Gazella.view.CreateColor;
import com.xyy.model.SleepWeekTime;
import com.ysp.hybridtwatch.R;
import com.ysp.newband.BaseFragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/10/11.
 */

public class SleepMonthFragment extends BaseFragment {
    @BindView(R.id.ll_date)
    LinearLayout llDate;
    @BindView(R.id.chart1)
    BarChart mChart;
    @BindView(R.id.tv_date)
    TextView tvDate;
    @BindView(R.id.iv_left)
    LinearLayout ivLeft;
    @BindView(R.id.iv_right)
    LinearLayout ivRight;
    @BindView(R.id.ll_sleep_month)
    LinearLayout llSleepMonth;
    @BindView(R.id.ll_sleep_bata)
    LinearLayout llSleepBata;
    @BindView(R.id.scrollView)
    ScrollView scrollView;
    @BindView(R.id.tv_sleeptime)
    TextView tvSleeptime;
    @BindView(R.id.tv_lightsleepTime)
    TextView tvLightsleepTime;
    @BindView(R.id.tv_sleepingtime)
    TextView tvSleepingtime;
    @BindView(R.id.tv_awakeTime)
    TextView tvAwakeTime;
    @BindView(R.id.tv_awakeCount)
    TextView tvAwakeCount;
    private View view;
    private Calendar CalendarInstance = Calendar.getInstance();
    private int widthChart = 0;
    private int heightChatr = 0;
    private ViewGroup.LayoutParams params;
    private HashMap<String, String> monthMap;
    private String[] xValue;
    private String today, yeday;
    private String[] XString = new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"};

    private List<Partner> todayPartners = new ArrayList<>();
    private List<Partner> yesterdayPartners = new ArrayList<>();
    private int awakeTime, lightsleepTime, sleepingTime, sleepTime, awakeCount;
    private List<SleepWeekTime> sleepWeekTimes = new ArrayList<>();
    boolean booleanAwake = true;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        view = inflater.inflate(R.layout.fragment_sleep_month, container, false);
        ButterKnife.bind(this, view);

        initView();
        initData(monthMap);
        initChart();

        tvDate.setText(new SomeUtills().getDate(Calendar.getInstance().getTime(), 1));
        return view;

    }

    public void initData(HashMap<String, String> monthMap) {
        int sleepTimes = 0;
        int lightsleepTimes = 0;
        int sleepingTimes = 0;
        int awakeTimes = 0;
        int awakeCounts=0;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
        Date date = null;
        if (sleepWeekTimes != null && sleepWeekTimes.size() > 0) sleepWeekTimes.clear();

        for (int i = 0; i < monthMap.size(); i++) {
            today = monthMap.get(String.valueOf(i + 1));

            try {
                date = sdf.parse(today);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if (monthMap.get(String.valueOf(i + 1)).equals(String.valueOf(monthMap.size())))
                yeday = new SomeUtills().getAmountDate(date, 0, 1);
            else
                yeday = new SomeUtills().getAmountDate(date, 0, 0);

            initData(today, yeday);
        }
        for (int m = 0; m < sleepWeekTimes.size(); m++) {
            sleepingTimes += sleepWeekTimes.get(m).getSleepingTime();
            awakeTimes += sleepWeekTimes.get(m).getAwakeTime();
            sleepTimes += sleepWeekTimes.get(m).getSleeptime();
            lightsleepTimes += sleepWeekTimes.get(m).getLightsleepTime();
            awakeCounts += sleepWeekTimes.get(m).getAwakeCount();
        }
        tvSleeptime.setText(String.valueOf(sleepTimes));
        tvLightsleepTime.setText(String.valueOf(lightsleepTimes));
        tvSleepingtime.setText(String.valueOf(sleepingTimes));
        tvAwakeTime.setText(String.valueOf(awakeTimes));
        tvAwakeCount.setText(String.valueOf(awakeCounts));
        updateUI(xValue);
    }

    private void initData(String Today, String yesterday) {

        awakeTime = 0;
        lightsleepTime = 0;
        sleepingTime = 0;
        sleepTime = 0;
        xValue = new String[monthMap.size()];

        if (todayPartners != null || todayPartners.size() > 0) {
            todayPartners.clear();
            yesterdayPartners.clear();
        }

        todayPartners = SleepActivity.sleepActivity.mCommonUtils.queryByBuilder("sleep", Today);
        yesterdayPartners = SleepActivity.sleepActivity.mCommonUtils.queryByBuilder("sleep", yesterday);

        if (todayPartners.size() == 24 && yesterdayPartners.size() == 24) {
            yesterdayPartners = yesterdayPartners.subList(12, 24);
            todayPartners = todayPartners.subList(0, 12);
            for (int i = 0; i < yesterdayPartners.size(); i++) {
                xValue[i] = yesterdayPartners.get(i).getSleep();
            }
            for (int i = 0; i < todayPartners.size(); i++) {
                xValue[i + 12] = todayPartners.get(i).getSleep();
            }
        } else {
            for (int k = 0; k < xValue.length; k++) {
                xValue[k] = "4";
            }
        }

        for (int i = 0; i < xValue.length; i++) {
            String state = xValue[i];
            if (state != null) {
                switch (state) {
                    case "0":
                        awakeTime += 1;
                        if (booleanAwake){
                            awakeCount++;
                            booleanAwake=false;
                        }
                        break;
                    case "1":
                        sleepTime += 1;
                        lightsleepTime += 1;
                        booleanAwake=true;
                        break;
                    case "2":
                        sleepTime += 1;
                        sleepingTime += 1;
                        booleanAwake=true;
                        break;
                    case "3":
                        awakeTime += 1;
                        if (booleanAwake){
                            awakeCount++;
                            booleanAwake=false;
                        }
                        break;
                    case "4":
                        awakeTime += 0;
                        sleepTime += 0;
                        lightsleepTime += 0;
                        sleepingTime += 0;
                        break;
                }
            }
        }

        SleepWeekTime sleepWeekTime = new SleepWeekTime();
        sleepWeekTime.setAwakeTime(awakeTime);
        sleepWeekTime.setLightsleepTime(lightsleepTime);
        sleepWeekTime.setSleepingTime(sleepingTime);
        sleepWeekTime.setSleeptime(sleepTime);
        sleepWeekTime.setAwakeCount(awakeCount);
        sleepWeekTimes.add(sleepWeekTime);

    }

    private void initView() {
        monthMap = new SomeUtills().getMonthdate(CalendarInstance.getTime());
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
        mChart.setBackground(getResources().getDrawable(R.drawable.page20_tubiao_bg));
        mChart.setDoubleTapToZoomEnabled(false);//双击缩放

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
        xAxis.setValueFormatter(new axisValueformatter());
        mChart.getAxisLeft().setTextColor(Color.rgb(255, 255, 255));
        mChart.getAxisLeft().setAxisLineColor(Color.rgb(255, 255, 255));
        mChart.getAxisLeft().setDrawGridLines(false);
        mChart.getAxisRight().setEnabled(false);

        // setting data
        mChart.animateY(2500);   //动画
        mChart.getLegend().setEnabled(false);

        setChartData();
        mChart.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                new SomeUtills().setCalendarViewGone(0);
                return false;
            }
        });
    }

    private void setChartData() {
        ArrayList<BarEntry> yVals1 = new ArrayList<>();
        for (int i = 0; i < sleepWeekTimes.size(); i++) {
            float val1 = (float) sleepWeekTimes.get(i).getAwakeTime();
            float val2 = (float) sleepWeekTimes.get(i).getLightsleepTime();
            float val3 = (float) sleepWeekTimes.get(i).getSleepingTime();

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
            set1.setBarBorderColor(Color.rgb(55, 55, 55));
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
        if (visible == View.VISIBLE) {
            scrollView.setFillViewport(true);
        } else {
            scrollView.setFillViewport(false);
            params.height = heightChatr;
            params.width = widthChart;
            mChart.setLayoutParams(params);
        }
        llDate.setVisibility(visible);
        llSleepBata.setVisibility(visible);
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
    public String getTvDateValue() {
        return tvDate.getText().toString();
    }

    @OnClick({R.id.iv_left, R.id.iv_right, R.id.ll_sleep_month})
    public void onClick(View view) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM");
        Date date = null;
        try {
            date = sdf.parse(tvDate.getText().toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        switch (view.getId()) {
            case R.id.iv_left:
                tvDate.setText(new SomeUtills().getAmountDate(date, 1, 0));

                try {
                    date = sdf.parse(tvDate.getText().toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                monthMap = new SomeUtills().getMonthdate(date);
                if (monthMap != null) {
                    initData(monthMap);
                }

                break;
            case R.id.iv_right:
                tvDate.setText(new SomeUtills().getAmountDate(date, 1, 1));
                try {
                    date = sdf.parse(tvDate.getText().toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                monthMap = new SomeUtills().getMonthdate(date);
                if (monthMap != null) {
                    initData(monthMap);
                }
                break;
            case R.id.ll_sleep_month:
                new SomeUtills().setCalendarViewGone(0);
                break;
        }
    }

    public void updateUI(String[] xValue) {
        this.xValue = xValue;
        setChartData();
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
}

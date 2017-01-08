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
import com.xyy.Gazella.activity.StepActivity;
import com.xyy.Gazella.utils.SomeUtills;
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

public class StepWeekFragment extends BaseFragment {
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
    @BindView(R.id.ll_step_week)
    LinearLayout llStepWeek;
    @BindView(R.id.ll_setp_bata)
    LinearLayout llSetpBata;
    @BindView(R.id.scrollView)
    ScrollView scrollView;
    @BindView(R.id.tv_num_hour)
    TextView tvNumHour;
    @BindView(R.id.tv_hour)
    TextView tvHour;
    @BindView(R.id.tv_num_minute)
    TextView tvNumMinute;
    @BindView(R.id.tv_minute)
    TextView tvMinute;
    @BindView(R.id.tv_num_mi)
    TextView tvNumMi;
    @BindView(R.id.tv_mi)
    TextView tvMi;
    @BindView(R.id.tv_num_card)
    TextView tvNumCard;
    @BindView(R.id.tv_card)
    TextView tvCard;
    @BindView(R.id.tv_sumsnum)
    TextView tvSumsnum;
    private View view;
    private String[] XString = new String[]{"周日", "周一", "周二", "周三", "周四", "周五", "周六",};
    private String[] xValue = new String[]{"0", "0", "0", "0", "0", "0", "0"};
    private int[] second = new int[7];
    private double[] km = new double[7];
    private double[] calcalNum = new double[7];


    private HashMap<String, String> weekMap;
    private Calendar CalendarInstance = Calendar.getInstance();

    private int widthChart = 0;
    private int heightChatr = 0;
    private ViewGroup.LayoutParams params;
    private List<Partner> partners = new ArrayList<>();
    private int sumsStep, sumsSecond;
    private double sumsKm, sumsCalcalNum;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        view = inflater.inflate(R.layout.fragment_step_week, container, false);

        ButterKnife.bind(this, view);
        initChart();
        initLldate();
        initData(weekMap);
        initView();
        return view;
    }

    private void initLldate() {
        weekMap = new SomeUtills().getWeekdate(CalendarInstance.getTime());
        if (weekMap != null)
            tvDate.setText(weekMap.get("1") + " - " + weekMap.get("7"));
    }

    public void initData(HashMap<String, String> weekMap) {
        for (int i = 0; i < weekMap.size(); i++) {
            String weekDate = weekMap.get(String.valueOf(i + 1));
            initData(weekDate,i);
        }
        updateUI(xValue);
        for (int n = 0; n < xValue.length; n++) {
            sumsStep += Integer.valueOf(xValue[n]);
            sumsSecond += Integer.valueOf(second[n]);
            sumsKm += (km[n]);
            sumsCalcalNum += calcalNum[n];
        }

        if (sumsSecond > 60) {
            tvNumMinute.setText(String.valueOf(sumsSecond / 60));
            if (tvNumHour.getVisibility() == View.VISIBLE || tvHour.getVisibility() == View.VISIBLE) {
                tvNumHour.setVisibility(View.INVISIBLE);
                tvHour.setVisibility(View.INVISIBLE);
            }
        } else if (sumsSecond > 60 * 60) {
            if (tvNumHour.getVisibility() == View.INVISIBLE || tvHour.getVisibility() == View.INVISIBLE) {
                tvNumHour.setText(String.valueOf(sumsSecond / 360));
                tvNumMinute.setText(String.valueOf((sumsSecond % 3600) / 60));
            }
        } else if (sumsSecond == 0) {
            if (tvNumHour.getVisibility() == View.INVISIBLE || tvHour.getVisibility() == View.INVISIBLE) {
                tvNumHour.setVisibility(View.VISIBLE);
                tvHour.setVisibility(View.VISIBLE);
                tvNumHour.setText("0");
                tvNumMinute.setText("0");
            }
        }
        // 计算活动距离
        if (sumsKm < 1000) {
            tvNumMi.setText(String.valueOf((int) sumsKm));
            tvMi.setText(getResources().getString(R.string.mi));
        } else {
            tvNumMi.setText(String.valueOf(new SomeUtills().changeDouble(sumsKm)));
            tvMi.setText(getResources().getString(R.string.km));
        }
        if (sumsCalcalNum < 1000) {
            tvNumCard.setText(String.valueOf(sumsCalcalNum));
            tvCard.setText(getResources().getString(R.string.card));
        } else {
            tvNumCard.setText(String.valueOf(new SomeUtills().changeDouble(sumsCalcalNum)));
            tvCard.setText(getResources().getString(R.string.Kcard));
        }
        tvSumsnum.setText(String.valueOf(sumsStep));
        sumsStep=0;
        sumsSecond=0;
        sumsCalcalNum=0;
        sumsKm=0;
    }

    public void initData(String date,int n) {
        if (partners != null || partners.size() > 0) partners.clear();
        partners = StepActivity.stepActivity.mCommonUtils.queryByBuilder("step", date);
        if (partners.size() == 24) {
            for (int i = 0; i < partners.size(); i++) {
                if (Integer.valueOf(partners.get(i).getTime()) == 23) {
                        xValue[n] = partners.get(i).getStepsumsnum();
                        second[n] = Integer.valueOf(partners.get(i).getExercisetime());
                        km[n] = Double.valueOf(partners.get(i).getExercisedistance());
                        calcalNum[n] = Double.valueOf(partners.get(i).getCalcalNum());
                        break;
                }
            }
        }else {
                xValue[n] = "0";
                second[n] = 0;
                km[n] = 0;
                calcalNum[n] =0.0;
        }
    }


    public void updateUI(String[] xValue) {
        this.xValue = xValue;
        setChartData();
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

        xAxis.setValueFormatter(new axisValueformatter());

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
        // setting data
        setChartData();
    }

    private void setChartData() {

        ArrayList<BarEntry> yVals1 = new ArrayList<>();
        for (int i = 0; i < xValue.length; i++) {
            yVals1.add(new BarEntry(i, Float.parseFloat(xValue[i])));
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
                    initData(weekMap);
                tvDate.setText(weekMap.get("1") + " - " + weekMap.get("7"));
                break;
            case R.id.iv_right:
                weekMap = new SomeUtills().getAmountWeekdate(date, 1);
                if (weekMap != null)
                    initData(weekMap);
                tvDate.setText(weekMap.get("1") + " - " + weekMap.get("7"));
                break;
            case R.id.ll_step_week:
                new SomeUtills().setCalendarViewGone(1);
                break;
        }
    }
}

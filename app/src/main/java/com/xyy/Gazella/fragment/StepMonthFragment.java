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

public class StepMonthFragment extends BaseFragment {
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
    @BindView(R.id.ll_step_month)
    LinearLayout llStepMonth;
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
//    @BindView(R.id.tv_step_target)
//    TextView tvStepTarget;
    @BindView(R.id.tv_sumsnum)
    TextView tvSumsnum;
    @BindView(R.id.tv_manystep)
    TextView tvManystep;
    @BindView(R.id.tv_netweekstep)
    TextView tvNetweekstep;
    private View view;

    private int widthChart = 0;
    private int heightChatr = 0;
    private ViewGroup.LayoutParams params;
    private List<Partner> partners = new ArrayList<>();
    private HashMap<String, String> monthMap;
    private Calendar CalendarInstance = Calendar.getInstance();
    private String[] XString = new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"};
    private String[] netMonthStep = new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"};
    private String[] xValue;
    private int[] second;
    private double[] km;
    private double[] calcalNum;
    private int sumsStep, sumsSecond, netSumsStep;
    private double sumsKm, sumsCalcalNum;
    private String weekDate;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        view = inflater.inflate(R.layout.fragment_step_month, container, false);
        ButterKnife.bind(this, view);
        initView();
        initData(monthMap);
        initChart();
        return view;
    }

    private void initView() {
        tvDate.setText(new SomeUtills().getDate(Calendar.getInstance().getTime(), 1));
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

    public void initData(HashMap<String, String> monthMap) {
        xValue = new String[monthMap.size()];
        second = new int[monthMap.size()];
        km = new double[monthMap.size()];
        calcalNum = new double[monthMap.size()];

        for (int i = 0; i < monthMap.size(); i++) {
            weekDate = monthMap.get(String.valueOf(i + 1));
            initData(weekDate, i);
        }

        updateUI(xValue);

        for (int n = 0; n < xValue.length; n++) {
            sumsStep += Integer.valueOf(xValue[n]);
            sumsSecond += Integer.valueOf(second[n]);
            sumsKm += (km[n]);
            sumsCalcalNum += calcalNum[n];
        }
        if (sumsSecond >= 60 && sumsSecond<3600) {
            tvNumMinute.setText(String.valueOf(sumsSecond / 60));
            if (tvNumHour.getVisibility() == View.VISIBLE || tvHour.getVisibility() == View.VISIBLE) {
                tvNumHour.setVisibility(View.INVISIBLE);
                tvHour.setVisibility(View.INVISIBLE);
            }
            tvNumMinute.setText(String.valueOf(sumsSecond));
        } else if (sumsSecond >= 3600) {
            if (tvNumHour.getVisibility() == View.INVISIBLE || tvHour.getVisibility() == View.INVISIBLE) {
                tvNumHour.setVisibility(View.VISIBLE);
                tvHour.setVisibility(View.VISIBLE);
            }
            tvNumHour.setText(String.valueOf(sumsSecond /3600));
            tvNumMinute.setText(String.valueOf((sumsSecond % 3600) / 60));
        } else {
            if (tvNumHour.getVisibility() == View.INVISIBLE || tvHour.getVisibility() == View.INVISIBLE) {
                tvNumHour.setVisibility(View.VISIBLE);
                tvHour.setVisibility(View.VISIBLE);
            }
            tvNumHour.setText("0");
            tvNumMinute.setText("0");
        }
        // 计算活动距离
        if (sumsKm < 1000) {
            tvNumMi.setText(String.valueOf((int) sumsKm));
            tvMi.setText(getResources().getString(R.string.mi));
        } else {
            if(new SomeUtills().changeDouble(sumsKm)>9999){
                tvNumMi.setText(">9999");
            }else{
                tvNumMi.setText(String.valueOf(new SomeUtills().changeDouble(sumsKm)));
            }
            tvMi.setText(getResources().getString(R.string.km));
        }
        if (sumsCalcalNum < 1000) {
            tvNumCard.setText(String.valueOf(sumsCalcalNum));
            tvCard.setText(getResources().getString(R.string.card));
        } else {
            if(new SomeUtills().changeDouble(sumsCalcalNum)>9999){
                tvNumCard.setText(">9999");
            }else{
                tvNumCard.setText(String.valueOf(new SomeUtills().changeDouble(sumsCalcalNum)));
            }
            tvCard.setText(getResources().getString(R.string.Kcard));
        }
        tvSumsnum.setText(String.valueOf(sumsStep));


        if (monthMap.size() != 0) monthMap.clear();
        Date netWeekDate = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM");
        try {
            netWeekDate = sdf.parse(weekDate);
            netWeekDate=  sdf.parse(new SomeUtills().getAmountDate(netWeekDate, 1, 0));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        int num = 0;
        for (int m = 0; m < netMonthStep.length; m++) {
            netMonthStep[m] = "0";
        }
        monthMap =new SomeUtills().getMonthdate(netWeekDate);
        for (int m = 0; m < monthMap.size(); m++) {
            String strNetWeekDate = monthMap.get(String.valueOf(m + 1));
            if (partners != null || partners.size() > 0) partners.clear();
            partners = StepActivity.stepActivity.mCommonUtils.queryByBuilder("step", strNetWeekDate);
            for (int i = 0; i < partners.size(); i++) {
                if (Integer.valueOf(partners.get(i).getTime()) == 23) {
                    netMonthStep[m] = partners.get(i).getStepsumsnum();
                    break;
                }
            }
        }
        for (int l = 0; l < netMonthStep.length; l++) {
            netSumsStep += Integer.valueOf(netMonthStep[l]);
        }
        num = sumsStep - netSumsStep;
        if (num < 0) {
            tvManystep.setText(getResources().getString(R.string.ye_step_datamonth));
            num = Math.abs(num);
        } else
            tvManystep.setText(getResources().getString(R.string.ye_step_manydatamonth));

        tvNetweekstep.setText(String.valueOf(num));


        sumsStep = 0;
        sumsSecond = 0;
        sumsCalcalNum = 0;
        sumsKm = 0;
        netSumsStep=0;

    }

    public void initData(String date, int n) {
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
        } else {
            xValue[n] = "0";
            second[n] = 0;
            km[n] = 0;
            calcalNum[n] = 0.0;
        }
    }

    public void updateUI(String[] xValue) {
        this.xValue = xValue;
        setChartData();
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
        mChart.getAxisLeft().setMaxWidth(40f);
        mChart.getAxisLeft().setMinWidth(40f);
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

    public String getTvDateValue() {
        return tvDate.getText().toString();
    }

    @OnClick({R.id.iv_left, R.id.iv_right, R.id.ll_step_month})
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
            case R.id.ll_step_month:
                new SomeUtills().setCalendarViewGone(1);
                break;
        }
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

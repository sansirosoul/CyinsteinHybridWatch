package com.xyy.Gazella.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.text.format.Time;
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
import java.util.List;

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
    @BindView(R.id.tv_step_target)
    TextView tvStepTarget;
    @BindView(R.id.tv_sumsnum)
    TextView tvSumsnum;
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
    @BindView(R.id.tv_netsumsstep)
    TextView tvNetsumsstep;
    @BindView(R.id.tv_manystep)
    TextView tvManystep;
    private View view;
    private String[] xValue = new String[]{"0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"};

    private int widthChart = 0;
    private int heightChatr = 0;
    private ViewGroup.LayoutParams params;
    private Time mCalendar;
    private int myear, month, day, sumsNum;
    private StringBuffer sb = new StringBuffer();
    private List<Partner> partners = new ArrayList<>();
    private String strMonth, strDay, exerciseTime;
    private Calendar CalendarInstance = Calendar.getInstance();

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_step_day, container, false);

        ButterKnife.bind(this, view);
        initTime();
        if (month < 10)
            strMonth = sb.append("0").append(String.valueOf(month)).toString();
        else
            strMonth = String.valueOf(month);
        sb.setLength(0);
        if (day < 10)
            strDay = sb.append("0").append(String.valueOf(day)).toString();
        else
            strDay = String.valueOf(day);
        sb.setLength(0);
        String strday = sb.append(String.valueOf(myear)).append(".").append(strMonth).append(".").append(strDay).toString();
        initData(strday);
        initChart();
        initView();
        super.onCreateView(inflater, container, savedInstanceState);
        return view;
    }

    public void initData(String date) {

        if (partners != null || partners.size() > 0) partners.clear();
        partners = StepActivity.stepActivity.mCommonUtils.queryByBuilder("step", date);
        if (partners.size() == 24) {
            for (int i = 0; i < partners.size(); i++) {
                xValue[i] = partners.get(i).getSleep();
                if (Integer.valueOf(partners.get(i).getTime()) == 23) {
                    if (partners.get(i).getStepsumsnum() != null)
                     sumsNum = Integer.valueOf(partners.get(i).getStepsumsnum());
                    int second = Integer.valueOf(partners.get(i).getExercisetime());
                    double km = Double.valueOf(partners.get(i).getExercisedistance());
                    double calcalNum = Double.valueOf(partners.get(i).getCalcalNum());
                    if (second > 60) {
                        tvNumMinute.setText(String.valueOf(second / 60));
                        if (tvNumHour.getVisibility() == View.VISIBLE || tvHour.getVisibility() == View.VISIBLE) {
                            tvNumHour.setVisibility(View.INVISIBLE);
                            tvHour.setVisibility(View.INVISIBLE);
                        }
                    } else if (second > 60 * 60) {
                        if (tvNumHour.getVisibility() == View.INVISIBLE || tvHour.getVisibility() == View.INVISIBLE) {
                            tvNumHour.setText(String.valueOf(second / 360));
                            tvNumMinute.setText(String.valueOf((second % 3600) / 60));
                        }
                    } else if (second == 0) {
                        if (tvNumHour.getVisibility() == View.INVISIBLE || tvHour.getVisibility() == View.INVISIBLE) {
                            tvNumHour.setVisibility(View.VISIBLE);
                            tvHour.setVisibility(View.VISIBLE);
                            tvNumHour.setText("0");
                            tvNumMinute.setText("0");
                        }
                    }
                    // 计算活动距离
                    if (km < 1000) {
                        tvNumMi.setText(String.valueOf((int) km));
                        tvMi.setText(getResources().getString(R.string.mi));
                    } else {
                        tvNumMi.setText(String.valueOf(new SomeUtills().changeDouble(km)));
                        tvMi.setText(getResources().getString(R.string.km));
                    }
                    if (calcalNum < 1000) {
                        tvNumCard.setText(String.valueOf(calcalNum));
                        tvCard.setText(getResources().getString(R.string.card));
                    } else {
                        tvNumCard.setText(String.valueOf(new SomeUtills().changeDouble(calcalNum)));
                        tvCard.setText(getResources().getString(R.string.Kcard));
                    }
                    tvSumsnum.setText(String.valueOf(sumsNum));
                }
            }
            tvStepTarget.setText(getResources().getString(R.string.step_target_ok));
        } else {
            for (int i = 0; i < xValue.length; i++)
                xValue[i] = "0";
            if (tvNumHour.getVisibility() == View.INVISIBLE || tvHour.getVisibility() == View.INVISIBLE) {
                tvNumHour.setVisibility(View.VISIBLE);
                tvHour.setVisibility(View.VISIBLE);
            }
            tvNumHour.setText("0");
            tvNumMinute.setText("0");
            tvNumMi.setText("0");
            tvNumCard.setText("0.0");
            tvSumsnum.setText("0");
            tvStepTarget.setText(getResources().getString(R.string.no_step_data));
        }
        Date netDate = null;
        int netSumsNum = 0;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
        try {
            netDate = sdf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String strNetDay = new SomeUtills().getAmountDate(netDate, 0, 0);
        if (partners != null || partners.size() > 0) partners.clear();
        partners = StepActivity.stepActivity.mCommonUtils.queryByBuilder("step", strNetDay);
        if (partners.size() == 24) {
            for (int i = 0; i < partners.size(); i++) {
                if (Integer.valueOf(partners.get(i).getTime()) == 23) {
                    netSumsNum = Integer.valueOf(partners.get(i).getStepsumsnum());
                    int i1 = sumsNum - netSumsNum;
                    if (i1 < 0) {
                        tvManystep.setText(getResources().getString(R.string.ye_step_data));
                        Math.abs(i1);
                    } else
                        tvManystep.setText(getResources().getString(R.string.ye_step_manydata));
                    tvNetsumsstep.setText(String.valueOf(i1));
                }
            }
        }
        updateUI(xValue);
    }

    private void initView() {
        tvDate.setText(new SomeUtills().getDate(Calendar.getInstance().getTime(), 0));
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
                initData(new SomeUtills().getAmountDate(date, 0, 0));

                break;
            case R.id.iv_right:
                tvDate.setText(new SomeUtills().getAmountDate(date, 0, 1));
                initData(new SomeUtills().getAmountDate(date, 0, 1));
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

    private void initTime() {
        mCalendar = new Time();
        mCalendar.setToNow();
        myear = mCalendar.year;
        month = mCalendar.month + 1;
        day = mCalendar.monthDay;
    }
}

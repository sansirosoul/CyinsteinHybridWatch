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
import com.xyy.Gazella.dbmanager.CommonUtils;
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
    private View view;
    private String[] xValue = new String[]{"0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"};

    private int widthChart = 0;
    private int heightChatr = 0;
    private ViewGroup.LayoutParams params;
    private int count = 145631;
    private CommonUtils mCommonUtils;
    private Time mCalendar;
    private int myear, month, day;
    private StringBuffer sb = new StringBuffer();
    private String date;
    private List<Partner> partners = new ArrayList<>();


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_step_day, container, false);

        ButterKnife.bind(this, view);
        initTime();
        String date = sb.append(String.valueOf(myear)).append(".").append(String.valueOf(month)).append(".").append(String.valueOf(day)).toString();
        initData(date);
        initChart();
        initView();
        tvDate.setText(new SomeUtills().getDate(Calendar.getInstance().getTime(), 0));
        super.onCreateView(inflater, container, savedInstanceState);
        return view;
    }


    public void initData(String date) {
        if (partners != null || partners.size() > 0) partners.clear();
        partners = StepActivity.stepActivity.mCommonUtils.queryByBuilder("step", date);
        if (partners.size() == 24) {
            for (int i = 0; i < partners.size(); i++)
                xValue[i] = partners.get(i).getSleep();
            tvStepTarget.setText(getResources().getString(R.string.step_target_ok));
        } else {
            for (int i = 0; i < xValue.length; i++)
                xValue[i] = "0";
            tvStepTarget.setText(getResources().getString(R.string.no_step_data));
        }
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
        // setting data
        setChartData();


    }

    private void setChartData() {
        ArrayList<BarEntry> yVals1 = new ArrayList<>();
        for (int i = 0; i < xValue.length; i++) {
//            float mult = (10000);
//            float val = (float) (Math.random() * mult) + mult / 1;
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
                updateUI(xValue);
                break;
            case R.id.iv_right:
                tvDate.setText(new SomeUtills().getAmountDate(date, 0, 1));
                initData(new SomeUtills().getAmountDate(date, 0, 1));
                updateUI(xValue);
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

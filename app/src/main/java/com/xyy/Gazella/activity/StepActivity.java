package com.xyy.Gazella.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.github.mikephil.charting.charts.LineChart;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;
import com.ysp.newband.BaseActivity;
import com.ysp.smartwatch.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

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
    }

    private void initCalendar() {

        widget.setTopbarVisible(widget.getTopbarVisible());
        widget.setBackgroundColor(getResources().getColor(R.color.black));
        widget.setHeaderTextAppearance(R.layout.calend_handr);
        widget.setBackgroundColor(getResources().getColor(R.color.red));
        widget.setArrowColor(getResources().getColor(R.color.green));
        widget.setSelectionColor(getResources().getColor(R.color.main_bg));
        widget.
        widget.state().edit() .setCalendarDisplayMode(CalendarMode.MONTHS).commit();
        widget.setOnDateChangedListener(this);
        widget.setOnMonthChangedListener(this);
    }

    @Override
    public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
       // TVTitle.setText(getSelectedDatesString());

        if (WidgetType){
            widget.state().edit() .setCalendarDisplayMode(CalendarMode.WEEKS).commit();
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

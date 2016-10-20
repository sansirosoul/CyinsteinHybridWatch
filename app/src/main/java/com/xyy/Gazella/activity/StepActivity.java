package com.xyy.Gazella.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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
import butterknife.OnClick;

public class StepActivity extends BaseActivity implements OnDateSelectedListener, OnMonthChangedListener {

    @BindView(R.id.calendarView)
    MaterialCalendarView widget;
    @BindView(R.id.btnExit)
    Button btnExit;
    @BindView(R.id.btnOpt)
    Button btnOpt;
    @BindView(R.id.btnDate)
    Button btnDate;
    @BindView(R.id.TVTitle)
    TextView TVTitle;
    private boolean WidgetType = false;
    private static final DateFormat FORMATTER = SimpleDateFormat.getDateInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step);
        ButterKnife.bind(this);
        initCalendar();
        initView();
    }


    private void initView() {

        btnDate.setBackground(this.getResources().getDrawable(R.drawable.page17_rili));
        btnOpt.setBackground(this.getResources().getDrawable(R.drawable.page17_share));
        TVTitle.setText("计步详情");
    }

    private void initCalendar() {
        widget.setVisibility(View.GONE);
        widget.setOnDateChangedListener(this);
        widget.setOnMonthChangedListener(this);
    }

    @OnClick({R.id.btnExit, R.id.btnOpt, R.id.btnDate})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnExit:

                StepActivity.this.finish();
                overridePendingTransitionExit(StepActivity.this);
                break;
            case R.id.btnOpt:
                break;
            case R.id.btnDate:

                if (widget.getVisibility() == View.VISIBLE) {
                    widget.setVisibility(View.GONE);
                    btnDate.setBackground(this.getResources().getDrawable(R.drawable.page17_rili));
                } else {

                    ///初始化日历
                    widget.setVisibility(View.VISIBLE);
                    widget.setBackgroundColor(this.getResources().getColor(R.color.black));
                    widget.setArrowColor(this.getResources().getColor(R.color.white));

                    widget.setHeaderLinearColor(this.getResources().getColor(R.color.title_gray));
                    widget.setSelectionMode(MaterialCalendarView.SELECTION_MODE_SINGLE);
                    widget.setSelectionColor(this.getResources().getColor(R.color.personalize2));

                    widget.state().edit().setCalendarDisplayMode(CalendarMode.MONTHS).commit();
                    btnDate.setBackground(this.getResources().getDrawable(R.drawable.page23_selected_rili));
                }
                break;
        }




    }

    @Override
    public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
        // TVTitle.setText(getSelectedDatesString());

        if (WidgetType) {
            widget.state().edit().setCalendarDisplayMode(CalendarMode.WEEKS)
                    .commit();
            WidgetType = false;
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

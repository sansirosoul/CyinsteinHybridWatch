package com.xyy.Gazella.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;
import com.xyy.Gazella.fragment.StepDayFragment;
import com.xyy.Gazella.fragment.StepMonthFragment;
import com.xyy.Gazella.fragment.StepWeekFragment;
import com.ysp.smartwatch.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.ysp.newband.BaseActivity.overridePendingTransitionExit;

public class StepActivity extends FragmentActivity implements OnDateSelectedListener, OnMonthChangedListener {

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
    @BindView(R.id.button_day)
    Button butDay;
    @BindView(R.id.button_week)
    Button butWeek;
    @BindView(R.id.button_month)
    Button butMonth;
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    @BindView(R.id.ll_check_date)
    LinearLayout llCheckDate;

    private ArrayList<Fragment> fragmentsList;
    private StepDayFragment stepDayFragment;
    private StepWeekFragment stepWeekFragment;
    private StepMonthFragment stepMonthFragment;
    private FragmentAdapter mFragmentAdapter;


    private boolean WidgetType = false;
    private static final DateFormat FORMATTER = SimpleDateFormat.getDateInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step);
        ButterKnife.bind(this);
        initView();
        initCalendar();
        InitViewPager();
    }

    private void initView() {

        TVTitle.setText("计步详情");
        btnDate.setBackground(this.getResources().getDrawable(R.drawable.page17_rili));
        btnOpt.setBackground(this.getResources().getDrawable(R.drawable.page17_share));
    }

    private void initCalendar() {
        widget.setVisibility(View.GONE);
        widget.setBackgroundColor(this.getResources().getColor(R.color.black));
        widget.setArrowColor(this.getResources().getColor(R.color.white));
        widget.setHeaderLinearColor(this.getResources().getColor(R.color.title_gray));
        widget.setSelectionMode(MaterialCalendarView.SELECTION_MODE_SINGLE);
        widget.setSelectionColor(this.getResources().getColor(R.color.personalize2));
        widget.setTileHeight(90);
        widget.setOnDateChangedListener(this);
        widget.setOnMonthChangedListener(this);
    }

    private void InitViewPager() {
        fragmentsList = new ArrayList<>();
        stepDayFragment = new StepDayFragment();
        stepWeekFragment = new StepWeekFragment();
        stepMonthFragment = new StepMonthFragment();
        fragmentsList.add(stepDayFragment);
        fragmentsList.add(stepWeekFragment);
        fragmentsList.add(stepMonthFragment);

        mFragmentAdapter = new FragmentAdapter(this.getSupportFragmentManager(), fragmentsList);
        viewpager.setAdapter(mFragmentAdapter);
        viewpager.setCurrentItem(0);


        viewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        setBtnBackgroundType(0);
                        break;
                    case 1:
                        setBtnBackgroundType(1);
                        break;
                    case 2:
                        setBtnBackgroundType(2);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    @OnClick({R.id.button_day, R.id.button_week, R.id.button_month, R.id.btnExit, R.id.btnOpt, R.id.btnDate})
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
                    llCheckDate.setVisibility(View.VISIBLE);
                    btnDate.setBackground(this.getResources().getDrawable(R.drawable.page17_rili));

                    if(!stepDayFragment.getLlDateVisible())
                        stepDayFragment.setLlDateVisible(View.VISIBLE);
                    if(!stepWeekFragment.getLlDateVisible())
                        stepWeekFragment.setLlDateVisible(View.VISIBLE);
                    if(!stepMonthFragment.getLlDateVisible())
                        stepMonthFragment.setLlDateVisible(View.VISIBLE);

                } else {

                    ///初始化日历
                    widget.setVisibility(View.VISIBLE);
                    widget.state().edit().setCalendarDisplayMode(CalendarMode.MONTHS).commit();
                    btnDate.setBackground(this.getResources().getDrawable(R.drawable.page23_selected_rili));
                    llCheckDate.setVisibility(View.GONE);

                    if(stepDayFragment.getLlDateVisible())
                        stepDayFragment.setLlDateVisible(View.GONE);
                    if(stepWeekFragment.getLlDateVisible())
                        stepWeekFragment.setLlDateVisible(View.GONE);
                    if(stepMonthFragment.getLlDateVisible())
                        stepMonthFragment.setLlDateVisible(View.GONE);
                }
                break;

            case R.id.button_day:
                setBtnBackgroundType(0);
                viewpager.setCurrentItem(0);
                break;
            case R.id.button_week:
                setBtnBackgroundType(1);
                viewpager.setCurrentItem(1);
                break;
            case R.id.button_month:
                setBtnBackgroundType(2);
                viewpager.setCurrentItem(2);
                break;
        }
    }

    /***
     * 设置Butnon 背景
     *
     * @param type 0day   1 week  2month
     */

    private void setBtnBackgroundType(int type) {
        switch (type) {
            case 0:
                butDay.setBackground(this.getResources().getDrawable(R.drawable.step_leftbtn_pressed));
                butWeek.setBackground(this.getResources().getDrawable(R.drawable.step_inbtn_normal));
                butMonth.setBackground(this.getResources().getDrawable(R.drawable.step_rightbtn_normal));
                break;
            case 1:
                butDay.setBackground(this.getResources().getDrawable(R.drawable.step_leftbtn_normal));
                butWeek.setBackground(this.getResources().getDrawable(R.drawable.step_inbtn_pressed));
                butMonth.setBackground(this.getResources().getDrawable(R.drawable.step_rightbtn_normal));
                break;
            case 2:
                butDay.setBackground(this.getResources().getDrawable(R.drawable.step_leftbtn_normal));
                butWeek.setBackground(this.getResources().getDrawable(R.drawable.step_inbtn_normal));
                butMonth.setBackground(this.getResources().getDrawable(R.drawable.step_rightbtn_pressed));
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

    public class FragmentAdapter extends FragmentPagerAdapter {

        List<Fragment> fragmentList = new ArrayList<>();

        public FragmentAdapter(FragmentManager fm, List<Fragment> fragmentList) {
            super(fm);
            this.fragmentList = fragmentList;
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }
    }
}

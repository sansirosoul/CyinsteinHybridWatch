package com.xyy.Gazella.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
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
import com.xyy.Gazella.fragment.SleepDayFragment;
import com.xyy.Gazella.fragment.SleepMonthFragment;
import com.xyy.Gazella.fragment.SleepWeekFragment;
import com.xyy.Gazella.utils.SomeUtills;
import com.ysp.newband.BaseActivity;
import com.ysp.smartwatch.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SleepActivity extends BaseActivity implements OnDateSelectedListener, OnMonthChangedListener {

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
    private SleepDayFragment sleepDayFragment;
    private SleepWeekFragment sleepWeekFragment;
    private SleepMonthFragment sleepMonthFragment;
    private FragmentAdapter mFragmentAdapter;

    private static final DateFormat FORMATTER = SimpleDateFormat.getDateInstance();
    private Calendar CalendarInstance = Calendar.getInstance();
    private HashMap<String, String> weekMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sleep);
        ButterKnife.bind(this);
        initView();
        initCalendar();
        InitViewPager();

    }

    private void initView() {

        TVTitle.setText("睡眠详情");
        btnDate.setBackground(this.getResources().getDrawable(R.drawable.page17_rili));
        btnOpt.setBackground(this.getResources().getDrawable(R.drawable.page17_share));
    }

    private void initCalendar() {
        ///初始化日历

        widget.setBackgroundColor(this.getResources().getColor(R.color.dataBackgroundColor));
        widget.setArrowColor(this.getResources().getColor(R.color.white));
        widget.setHeaderLinearColor(this.getResources().getColor(R.color.title_gray));
        widget.setSelectionMode(MaterialCalendarView.SELECTION_MODE_SINGLE);
        widget.setSelectionColor(this.getResources().getColor(R.color.personalize2));
        widget.setTileHeight(90);

        widget.setSelectedDate(CalendarInstance.getTime());
        widget.state().edit().setCalendarDisplayMode(CalendarMode.MONTHS).commit();
        widget.setVisibility(View.GONE);
        widget.setOnDateChangedListener(this);
        widget.setOnMonthChangedListener(this);
    }

    private void InitViewPager() {
        fragmentsList = new ArrayList<>();
        sleepDayFragment = new SleepDayFragment();
        sleepWeekFragment = new SleepWeekFragment();
        sleepMonthFragment = new SleepMonthFragment();
        fragmentsList.add(sleepDayFragment);
        fragmentsList.add(sleepWeekFragment);
        fragmentsList.add(sleepMonthFragment);

        mFragmentAdapter = new FragmentAdapter(this.getSupportFragmentManager(), fragmentsList);
        viewpager.setAdapter(mFragmentAdapter);
        viewpager.setOffscreenPageLimit(3);
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

                SleepActivity.this.finish();
                overridePendingTransitionExit(SleepActivity.this);
                break;
            case R.id.btnOpt:
                break;
            case R.id.btnDate:

                if (widget.getVisibility() == View.VISIBLE) {
                    setLlDateVisible(1);
                } else {
                    setLlDateVisible(2);
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
     * 是否显示 选择日期条  , 日历
     *
     * @param type 1 是显示  2 是隐藏
     */

    private void setLlDateVisible(int type) {
        if (type == 1) {

            widget.setVisibility(View.GONE);
            llCheckDate.setVisibility(View.VISIBLE);
            btnDate.setBackground(this.getResources().getDrawable(R.drawable.page17_rili));

            if (!sleepDayFragment.getLlDateVisible())
                sleepDayFragment.setLlDateVisible(View.VISIBLE);
            if (!sleepWeekFragment.getLlDateVisible())
                sleepWeekFragment.setLlDateVisible(View.VISIBLE);
            if (!sleepMonthFragment.getLlDateVisible())
                sleepMonthFragment.setLlDateVisible(View.VISIBLE);
        } else {
            widget.setVisibility(View.VISIBLE);
            btnDate.setBackground(this.getResources().getDrawable(R.drawable.page23_selected_rili));
            llCheckDate.setVisibility(View.GONE);

            if (sleepDayFragment.getLlDateVisible())
                sleepDayFragment.setLlDateVisible(View.GONE);
            if (sleepWeekFragment.getLlDateVisible())
                sleepWeekFragment.setLlDateVisible(View.GONE);
            if (sleepMonthFragment.getLlDateVisible())
                sleepMonthFragment.setLlDateVisible(View.GONE);
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

        setLlDateVisible(1);
        switch (viewpager.getCurrentItem()) {
            case 0:
                sleepDayFragment.setTvDateValue(new SomeUtills().getDate(date.getDate(), 0));
                break;
            case 1:
               weekMap= new SomeUtills().getWeekdate(date.getDate());
                if(weekMap!=null)
                sleepWeekFragment.setTvDateValue(weekMap.get("1") + " - " + weekMap.get("7"));
                break;
            case 2:
                sleepMonthFragment.setTvDateValue(new SomeUtills().getDate(date.getDate(), 1));
                break;
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

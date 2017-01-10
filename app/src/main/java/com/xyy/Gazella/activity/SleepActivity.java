package com.xyy.Gazella.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.format.Time;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.partner.entity.Partner;
import com.polidea.rxandroidble.RxBleConnection;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;
import com.xyy.Gazella.dbmanager.CommonUtils;
import com.xyy.Gazella.fragment.SleepDayFragment;
import com.xyy.Gazella.fragment.SleepMonthFragment;
import com.xyy.Gazella.fragment.SleepWeekFragment;
import com.xyy.Gazella.utils.BleUtils;
import com.xyy.Gazella.utils.SomeUtills;
import com.xyy.model.SleepData;
import com.ysp.hybridtwatch.R;
import com.ysp.newband.BaseActivity;
import com.ysp.newband.PreferenceData;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;

public class SleepActivity extends BaseActivity implements OnDateSelectedListener, OnMonthChangedListener {

    @BindView(R.id.calendarView)
    public MaterialCalendarView widget;
    @BindView(R.id.btnExit)
    LinearLayout btnExit;
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
    @BindView(R.id.sleepActivity)
    ScrollView SlsleepActivity;


    private static String TAG = SleepActivity.class.getName();

    private ArrayList<Fragment> fragmentsList;
    private SleepDayFragment sleepDayFragment;
    private SleepWeekFragment sleepWeekFragment;
    private SleepMonthFragment sleepMonthFragment;
    private FragmentAdapter mFragmentAdapter;

    private static final DateFormat FORMATTER = SimpleDateFormat.getDateInstance();
    private Calendar CalendarInstance = Calendar.getInstance();
    private HashMap<String, String> weekMap;
    public static SleepActivity sleepActivity = null;

    private SomeUtills utills;
    private Time mCalendar;
    private HashMap<String, String> monthMap;
    public Observable<RxBleConnection> connectionObservable;
    private BleUtils bleUtils;
    private int myear, month, day, Queryday, SumsStep, Weight;
    private StringBuffer sb = new StringBuffer();
    public CommonUtils mCommonUtils;
    private List<SleepData> data;
    private List<Partner> partners = new ArrayList<>();
    private String strMonth, strDay, userWeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sleep);
        ButterKnife.bind(this);
        initView();
        initCalendar();
        InitViewPager();
        initTime();
        String address = PreferenceData.getAddressValue(this);
        if (address != null && !address.equals("")) {
            connectionObservable = getRxObservable(this);
            bleUtils = new BleUtils();
        //    Write(bleUtils.getSleepData(6), connectionObservable);
        }

        Notify(connectionObservable);
        mCommonUtils = new CommonUtils(this);
        sleepActivity = this;
    }

    @Override
    protected void onNotifyReturn(int type, String str) {
        super.onNotifyReturn(type, str);
        switch (type) {
            case 0:
                break;
            case 1:
                break;
            case 2:
                break;
        }
    }

    @Override
    protected void onReadReturn(byte[] bytes) {
        super.onReadReturn(bytes);
        data = bleUtils.returnSleepData(bytes);
        SaveStepData();
    }


    private void SaveStepData() {
        if (data.size() != 0 && data != null) {
            for (int i = 0; i < data.size(); i++) {
                int count = data.get(i).getCount();
                int time = data.get(i).getTime();
                if (count <= 5 && count >= 0)
                    setPartnerData(i);
                if (count <= 11 && count >= 6)
                    setPartnerData(i);
                if (count <= 17 && count >= 12)
                    setPartnerData(i);
                if (count <= 23 && count >= 18)
                    setPartnerData(i);
                if (count <= 29 && count >= 24)
                    setPartnerData(i);
                if (count <= 35 && count >= 30)
                    setPartnerData(i);
                if (count <= 41 && count >= 36)
                    setPartnerData(i);
                if (count == 41 && time == 23) {
                    if (weekMap.size() != 0) weekMap.clear();
                }
            }
        }
    }

    private int[] lightsleepTime = new int[24];
    private int[] sleepingTime = new int[24];
    private int[] awakeTime = new int[24];
    private int intlightsleepTime;
    private int intsleepingTime;
    private int intawakeTime;

    private void setPartnerData(int i) {
        Partner partner;
        String strday = setStrDay(i);
        String time = String.valueOf(data.get(i).getTime());

        if (data.get(i).getTime() != 23) {
            partner = new Partner();

            partner.setType("sleep");                                                          // 保存计步或 睡眠
            int status = data.get(i).getStatus();
            switch (status) {
                case 0:
                    awakeTime[i] = 1;
                    break;
                case 1:
                    lightsleepTime[i] = 1;
                    break;
                case 2:
                    sleepingTime[i] = 1;
                    break;
            }

            partner.setTime(String.valueOf(data.get(i).getTime()));         // 保存各时间段
            partner.setSleep(String.valueOf(data.get(i).getStatus()));       //  保存睡眠状态 0 清醒     1 潜睡     2深睡
            partner.setDate(strday);                                                         //  保存日期
        } else {
            for (int n = 0; n < awakeTime.length; n++) {
                intawakeTime += awakeTime[n];
                intsleepingTime += sleepingTime[n];
                intlightsleepTime += lightsleepTime[n];
            }
            partner = new Partner();
            partner.setType("sleep");                                                      // 保存计步或 睡眠
            partner.setTime(String.valueOf(data.get(i).getTime()));        // 保存各时间段
            partner.setSleep(String.valueOf(data.get(i).getStatus()));   //  保存记步数
            partner.setAwake(String.valueOf(intawakeTime));              //   保存清醒时长
            partner.setLightsleep(String.valueOf(intlightsleepTime));  //   保存 浅睡时长
            partner.setSleeping(String.valueOf(intsleepingTime));        //   保存深睡时长
            partner.setDate(strday);                                                     //  保存日期

        }
        intawakeTime=0;
        intlightsleepTime=0;
        intsleepingTime=0;
        sb.setLength(0);
        if (partners.size() != 0) partners.clear();
        partners = mCommonUtils.PartnerqueryByBuilder("sleep", strday, time);
        if (partners.size() != 0) {
            partner.setId(partners.get(0).getId());
            mCommonUtils.uoDatePartner(partner);  //更新数据
        } else
            mCommonUtils.insertPartner(partner);   //插入数据

    }

    private String setStrDay(int i) {
        String strday = null;
        if (month < 10)
            strMonth = sb.append("0").append(String.valueOf(month)).toString();
        else
            strMonth = String.valueOf(month);
        sb.setLength(0);
        if (i != 999999) {
            if (data.get(i).getDate() < 10)
                strDay = sb.append("0").append(String.valueOf(data.get(i).getDate())).toString();
            else
                strDay = String.valueOf(data.get(i).getDate());
        } else {
            if (Queryday < 10)
                strDay = sb.append("0").append(String.valueOf(Queryday)).toString();
            else
                strDay = String.valueOf(Queryday);
        }
        sb.setLength(0);
        return strday = sb.append(String.valueOf(myear)).append(".").append(strMonth).append(".").append(strDay).toString();
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
                new SomeUtills().setShare(SleepActivity.this, R.id.sleepActivity);
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

    private Animation loadImageAnimation;

    public void setLlDateVisible(int type) {

        if (type == 1) {

            loadImageAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.btn_up);
            widget.startAnimation(loadImageAnimation);

            loadImageAnimation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    widget.setVisibility(View.GONE);
                    llCheckDate.setVisibility(View.VISIBLE);
                    btnDate.setBackground(getResources().getDrawable(R.drawable.page17_rili));

                    if (!sleepDayFragment.getLlDateVisible())
                        sleepDayFragment.setLlDateVisible(View.VISIBLE);

                    if (!sleepWeekFragment.getLlDateVisible())
                        sleepWeekFragment.setLlDateVisible(View.VISIBLE);
                    if (!sleepMonthFragment.getLlDateVisible())
                        sleepMonthFragment.setLlDateVisible(View.VISIBLE);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
        } else {

            loadImageAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.btn_down);
            loadImageAnimation.setFillAfter(!loadImageAnimation.getFillAfter());
            widget.startAnimation(loadImageAnimation);

            //初始化日历
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
                weekMap = new SomeUtills().getWeekdate(date.getDate());
                if (weekMap != null)
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

    private void initTime() {
        mCalendar = new Time();
        mCalendar.setToNow();
        myear = mCalendar.year;
        month = mCalendar.month + 1;
        day = mCalendar.monthDay;
        Queryday = mCalendar.monthDay;
    }
}

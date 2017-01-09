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
import android.widget.TextView;

import com.partner.entity.Partner;
import com.polidea.rxandroidble.RxBleConnection;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.xyy.Gazella.dbmanager.CommonUtils;
import com.xyy.Gazella.fragment.StepDayFragment;
import com.xyy.Gazella.fragment.StepMonthFragment;
import com.xyy.Gazella.fragment.StepWeekFragment;
import com.xyy.Gazella.utils.BleUtils;
import com.xyy.Gazella.utils.CommonDialog;
import com.xyy.Gazella.utils.SomeUtills;
import com.xyy.model.StepData;
import com.ysp.hybridtwatch.R;
import com.ysp.newband.BaseActivity;
import com.ysp.newband.PreferenceData;

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
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

public class StepActivity extends BaseActivity implements OnDateSelectedListener {

    private static String TAG = StepActivity.class.getName();

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

    private ArrayList<Fragment> fragmentsList;
    private StepDayFragment stepDayFragment;
    private StepWeekFragment stepWeekFragment;
    private StepMonthFragment stepMonthFragment;
    private FragmentAdapter mFragmentAdapter;

    private SomeUtills utills;
    private Time mCalendar;

    private Calendar CalendarInstance = Calendar.getInstance();
    private HashMap<String, String> weekMap;
    private HashMap<String, String> monthMap;
    public Observable<RxBleConnection> connectionObservable;
    private BleUtils bleUtils;
    public static StepActivity stepActivity = null;
    private int myear, month, day, Queryday,SumsStep,Weight;
    private StringBuffer sb = new StringBuffer();
    public CommonUtils mCommonUtils;
    private List<StepData> data;
    private List<Partner> partners = new ArrayList<>();
    private String strMonth, strDay,userWeight;
    private CommonDialog commonDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step);
        ButterKnife.bind(this);
        initView();
        initCalendar();
        InitViewPager();
        initTime();
        String address = PreferenceData.getAddressValue(this);
        if (address != null && !address.equals("")) {
            connectionObservable = getRxObservable(this);
            bleUtils = new BleUtils();
            Write(bleUtils.getStepData(6), connectionObservable);
//            commonDialog= new CommonDialog(StepActivity.this);
//            commonDialog.show();
//            commonDialog.setTvContext("请稍等,正在同步数据");
        }
        Notify(connectionObservable);
        stepActivity = this;
        mCommonUtils = new CommonUtils(this);

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
        data = bleUtils.returnStepData(bytes);
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
                    String strday = setStrDay(999999);
                    stepDayFragment.initData(strday);
                    if(commonDialog.isShowing())commonDialog.dismiss();
                }
            }
        }
    }

    private void setPartnerData(int i) {
        Partner partner;
        String strday = setStrDay(i);
        String time = String.valueOf(data.get(i).getTime());
        SumsStep += data.get(i).getStep();

        if (data.get(i).getTime() != 23) {
            partner = new Partner();
            partner.setType("step");                                                          // 保存计步或 睡眠
            partner.setTime(String.valueOf(data.get(i).getTime()));         // 保存各时间段
            partner.setSleep(String.valueOf(data.get(i).getStep()));       //  保存记步数
            partner.setDate(strday);                                                         //  保存日期
        } else {
            // 计算活动时间
            int second =(int) (SumsStep*1.08);
            double km = SumsStep * 0.5;
            //计算卡路里
                Weight = Integer.valueOf(userWeight);
                double card = ((Weight * 0.0005 + (SumsStep - 1) * 0.005) * SumsStep);

            partner = new Partner();
            partner.setType("step");                                                      // 保存计步或 睡眠
            partner.setTime(String.valueOf(data.get(i).getTime()));     // 保存各时间段
            partner.setSleep(String.valueOf(data.get(i).getStep()));   //  保存记步数
            partner.setDate(strday);                                                     //  保存日期
            partner.setStepsumsnum(String.valueOf(SumsStep));       //保存总记步
            partner.setExercisetime(String.valueOf(second));              //  保存运动时间
            partner.setExercisedistance(String.valueOf(km));               //  保存运动距离
            partner.setCalcalNum(String.valueOf((int)card));                 //  保存卡路里
            SumsStep = 0;
        }
        sb.setLength(0);
        if (partners.size() != 0) partners.clear();
        partners = mCommonUtils.PartnerqueryByBuilder("step", strday, time);
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
            if (data.get(i).getDay() < 10)
                strDay = sb.append("0").append(String.valueOf(data.get(i).getDay())).toString();
            else
                strDay = String.valueOf(data.get(i).getDay());
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

        TVTitle.setText("计步详情");
        btnDate.setBackground(this.getResources().getDrawable(R.drawable.page17_rili));
        btnOpt.setBackground(this.getResources().getDrawable(R.drawable.page17_share));
        utills = new SomeUtills();


        userWeight = PreferenceData.getUserInfo(StepActivity.this).getWeight();
        if(userWeight!=null&&!userWeight.equals("")) {
            userWeight = userWeight.replaceAll("[a-z]", ",");
            String s2[] = userWeight.split(",");
            userWeight = s2[0];
        }else
            userWeight="0";
    }

    private void initCalendar() {
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
            case R.id.btnExit:  //退出
                StepActivity.this.finish();
                overridePendingTransitionExit(StepActivity.this);

                break;
            case R.id.btnOpt:  //分享
//                utills.setShare(stepActivity, R.id.activity_step);
                utills.onSharesdk(stepActivity, R.id.activity_step)
                        .observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean aBoolean) {
                        utills.showShare(StepActivity.this);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                    }
                });
                break;

            case R.id.btnDate:  // 显示 隐藏 日历

                if (widget.getVisibility() == View.VISIBLE) {
                    setLlDateVisible(1);
                } else {
                    setLlDateVisible(2);
                }
                break;

            case R.id.button_day:   //  日
                setBtnBackgroundType(0);
                viewpager.setCurrentItem(0);
                break;
            case R.id.button_week:  //  周
                setBtnBackgroundType(1);
                viewpager.setCurrentItem(1);
                break;
            case R.id.button_month:   // 月
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

                    if (!stepDayFragment.getLlDateVisible())
                        stepDayFragment.setLlDateVisible(View.VISIBLE);
                    if (!stepWeekFragment.getLlDateVisible())
                        stepWeekFragment.setLlDateVisible(View.VISIBLE);
                    if (!stepMonthFragment.getLlDateVisible())
                        stepMonthFragment.setLlDateVisible(View.VISIBLE);
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

            if (stepDayFragment.getLlDateVisible())
                stepDayFragment.setLlDateVisible(View.GONE);

            if (stepWeekFragment.getLlDateVisible())
                stepWeekFragment.setLlDateVisible(View.GONE);
            if (stepMonthFragment.getLlDateVisible())
                stepMonthFragment.setLlDateVisible(View.GONE);

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
                stepDayFragment.setTvDateValue(utills.getDate(date.getDate(), 0));
                stepDayFragment.initData(utills.getDate(date.getDate(), 0));
                break;
            case 1:
                weekMap = utills.getWeekdate(date.getDate());
                if (weekMap != null)
                    stepWeekFragment.setTvDateValue(weekMap.get("1") + " - " + weekMap.get("7"));
                stepWeekFragment.initData(weekMap);
                break;
            case 2:
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM");
                Date monthdate = null;
                stepMonthFragment.setTvDateValue(utills.getDate(date.getDate(), 1));
                try {
                    monthdate = sdf.parse(stepMonthFragment.getTvDateValue());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                monthMap = new SomeUtills().getMonthdate(monthdate);
                if (monthMap != null) {
                    stepMonthFragment.initData(monthMap);
                }
                break;
        }
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

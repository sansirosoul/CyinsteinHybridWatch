package com.xyy.Gazella.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.EdgeEffectCompat;
import android.text.format.Time;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.partner.entity.Partner;
import com.polidea.rxandroidble.RxBleConnection;
import com.xyy.Gazella.dbmanager.CommonUtils;
import com.xyy.Gazella.fragment.SleepFragment;
import com.xyy.Gazella.fragment.StepFragment;
import com.xyy.Gazella.utils.BleUtils;
import com.xyy.Gazella.utils.SomeUtills;
import com.xyy.model.SleepData;
import com.xyy.model.StepData;
import com.ysp.hybridtwatch.R;
import com.ysp.newband.BaseActivity;
import com.ysp.newband.GazelleApplication;
import com.ysp.newband.PreferenceData;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;

import static com.ysp.hybridtwatch.R.id.viewpager;


public class HealthyActivity extends BaseActivity {

    private static String TAG = HealthyActivity.class.getName();

    @BindView(R.id.step)
    TextView step;
    @BindView(R.id.sleep)
    TextView sleep;
    @BindView(R.id.btnExit)
    LinearLayout btnExit;
    @BindView(R.id.btnOpt)
    Button btnOpt;
    @BindView(R.id.TVTitle)
    TextView TVTitle;
    private ViewPager viewPager;
    private ArrayList<Fragment> fragmentsList;
    private SleepFragment sleepFragment;
    private StepFragment stepFragment;
    private FragmentAdapter mFragmentAdapter;
    private EdgeEffectCompat leftEdge;
    private EdgeEffectCompat rightEdge;
    public static HealthyActivity install;

    public static Observable<RxBleConnection> connectionObservable;
    private BleUtils bleUtils;
    public boolean isNotify;
    private int TargetStep;
    private boolean isTrue;
    public static int dayStep;
    private List<StepData> data;
    private List<SleepData> sleepData;
    private StepData todayStepData;
    private Time mCalendar;
    private int myear, month, day, Queryday, SumsStep, Weight;
    private StringBuffer sb = new StringBuffer();
    public CommonUtils mCommonUtils;
    private List<Partner> partners = new ArrayList<>();
    private String strMonth, strDay, userWeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_healthy);
        ButterKnife.bind(this);
        String address = PreferenceData.getAddressValue(this);
        bleUtils = new BleUtils();
        InitViewPager();
        btnOpt.setBackground(getResources().getDrawable(R.drawable.page15_tongbu));
        if (address != null && !address.equals("")) {
            if (GazelleApplication.isBleConnected) {
                setNotifyCharacteristic();
            } else {
                connectBLEbyMac(address);
            }
        }

        initData();
        initTime();
        install = this;
        mCommonUtils = new CommonUtils(this);
    }

    @Override
    public void onConnectionState(int state) {
        if (state == 1) {

        }
    }

    private void initData() {
        TargetStep = PreferenceData.getTargetRunValue(HealthyActivity.this);
        userWeight = PreferenceData.getUserInfo(HealthyActivity.this).getWeight();
        if (userWeight != null && !userWeight.equals("")) {
            userWeight = userWeight.replaceAll("[a-z]", ",");
            String s2[] = userWeight.split(",");
            userWeight = s2[0];
        } else
            userWeight = "0";
        isTrue = true;
    }

    private void handerSleepData(List<SleepData> list) {
        Date date = new Date();
        for (int i = 0; i < list.size(); i++) {
            System.out.println("日期："+list.get(i).getDate()+"时间："+list.get(i).getHour()+":"+list.get(i).getMin()+"状态："+list.get(i).getStatus());
            if (list.get(i).getDate() == date.getDate()) {
                saveSleepData(date, list.get(i));
            } else if (list.get(i).getDate() == getBeforeDay(date, 1).getDate()) {
                saveSleepData(getBeforeDay(date, 1), list.get(i));
            } else if (list.get(i).getDate() == getBeforeDay(date, 2).getDate()) {
                saveSleepData(getBeforeDay(date, 2), list.get(i));
            } else if (list.get(i).getDate() == getBeforeDay(date, 3).getDate()) {
                saveSleepData(getBeforeDay(date, 3), list.get(i));
            } else if (list.get(i).getDate() == getBeforeDay(date, 4).getDate()) {
                saveSleepData(getBeforeDay(date, 4), list.get(i));
            } else if (list.get(i).getDate() == getBeforeDay(date, 5).getDate()) {
                saveSleepData(getBeforeDay(date, 5), list.get(i));
            } else if (list.get(i).getDate() == getBeforeDay(date, 6).getDate()) {
                saveSleepData(getBeforeDay(date, 6), list.get(i));
            }
            if (list.get(i).isLast()) {
                sleepFragment.setUploadFinsh();
            }
        }
        sleepFragment.setTvSynchronizationtime();
        sleepFragment.setToDayTime();
    }

    private void saveSleepData(Date date, SleepData sleepData) {
        String strday;
        if (date.getMonth() + 1 < 10) {
            if (date.getDate() < 10) {
                strday = (date.getYear() + 1900) + ".0" + (date.getMonth() + 1) + ".0" + date.getDate();
            } else {
                strday = (date.getYear() + 1900) + ".0" + (date.getMonth() + 1) + "." + date.getDate();
            }
        } else {
            if (date.getDate() < 10) {
                strday = (date.getYear() + 1900) + "." + (date.getMonth() + 1) + ".0" + date.getDate();
            } else {
                strday = (date.getYear() + 1900) + "." + (date.getMonth() + 1) + "." + date.getDate();
            }
        }

        Partner partner = new Partner();
        partner.setType("sleep");
        partner.setDate(strday);
        partner.setTime(sleepData.getHour() + "." + sleepData.getMin());
        partner.setSleep(sleepData.getStatus() + "");

        if (partners.size() != 0) partners.clear();
        partners = mCommonUtils.PartnerqueryByBuilder("sleep", strday, sleepData.getHour() + "." + sleepData.getMin());
        if (partners.size() != 0) {
            partner.setId(partners.get(0).getId());
            mCommonUtils.uoDatePartner(partner);  //更新数据
        } else
            mCommonUtils.insertPartner(partner);   //插入数据
    }

    public static Date getBeforeDay(Date date, int num) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, -num);
        date = calendar.getTime();
        return date;
    }

    List<SleepData> list2 = new ArrayList<>();
    public static int type = 0;

    @Override
    protected void onReadReturn(byte[] bytes) {
        if (type == 1) {
            if ((data = bleUtils.returnStepData(bytes)) != null) {   //  同步计步数据
                SaveStepData();
            }
        } else if (type == 2) {
            if ((sleepData = bleUtils.returnSleepData(bytes)) != null) { //  同步睡眠数据
                list2.addAll(sleepData);
                Date date = new Date();
                for (int i = 0; i < sleepData.size(); i++) {
                    if (sleepData.get(i).getDate() == date.getDate()) {
                        sleepFragment.setBerbarNum(1, sleepData.get(i).getDate());
                    } else if (sleepData.get(i).getDate() == getBeforeDay(date, 1).getDate()) {
                        sleepFragment.setBerbarNum(2, sleepData.get(i).getDate());
                    } else if (sleepData.get(i).getDate() == getBeforeDay(date, 2).getDate()) {
                        sleepFragment.setBerbarNum(3, sleepData.get(i).getDate());
                    } else if (sleepData.get(i).getDate() == getBeforeDay(date, 3).getDate()) {
                        sleepFragment.setBerbarNum(4, sleepData.get(i).getDate());
                    } else if (sleepData.get(i).getDate() == getBeforeDay(date, 4).getDate()) {
                        sleepFragment.setBerbarNum(5, sleepData.get(i).getDate());
                    } else if (sleepData.get(i).getDate() == getBeforeDay(date, 5).getDate()) {
                        sleepFragment.setBerbarNum(6, sleepData.get(i).getDate());
                    } else if (sleepData.get(i).getDate() == getBeforeDay(date, 6).getDate()) {
                        sleepFragment.setBerbarNum(7, sleepData.get(i).getDate());
                    }
                    if (sleepData.get(i).isLast) {
                        handerSleepData(SomeUtills.sort(list2));
                    }
                }
            }
        } else if (type == 0) {
            if ((todayStepData = bleUtils.returnTodayStep(bytes)) != null) {  // 今日步数
                StepData stepData = todayStepData;
                if (stepData != null) {
                    dayStep = stepData.getStep();
                    if (isTrue) {
                        stepFragment.setStepNum(stepData.getStep(), stepFragment.stepNum);
                        stepFragment.setStepNumTextOnEndListener(new StepFragment.setStepNumTextOnEndListener() {
                            @Override
                            public void setStepNumTextOnEndListener() {
//                                stepFragment.getTodayStepPost();
                                isTrue = false;
                            }
                        });
                    } else
                        stepFragment.setStepNumFalse(stepData.getStep());

                    double km = dayStep * 0.5;
                    // 计算活动距离
                    if (km < 1000)
                        stepFragment.setDistanceNum(String.valueOf((int) km) + getResources().getString(R.string.mi));
                    else
                        stepFragment.setDistanceNum(String.valueOf(new SomeUtills().changeDouble(km)) + getResources().getString(R.string.km));
                    //计算卡路里
                    Weight = Integer.valueOf(userWeight);
                    double card = ((Weight * 0.0005 + (dayStep - 1) * 0.005) * dayStep);
                    if (card < 1000)
                        stepFragment.setCalcalNum(String.valueOf(Integer.valueOf((int) card)) + getResources().getString(R.string.card));
                    else
                        stepFragment.setCalcalNum(String.valueOf(new SomeUtills().changeDouble(card)) + getResources().getString(R.string.Kcard));

                    //计算活动时间
                    int second;
                    if (dayStep < 2000) {
                        second = (int) (dayStep * 0.8);
                    } else if (dayStep > 4000) {
                        second = (int) (dayStep * 0.6);
                    } else {
                        second = (int) (dayStep * 0.7);
                    }
                    if (second >= 60 && second < 3600) {
                        stepFragment.setTime(String.valueOf(second / 60) + getResources().getString(R.string.minute));
                    } else if (second >= 3600) {
                        stepFragment.setTime(String.valueOf(second / 3600) + getResources().getString(R.string.hour)
                                + String.valueOf((second % 3600
                        ) / 60) + getResources().getString(R.string.minute));
                    }
                    if (dayStep <= TargetStep)
                        stepFragment.setIvTip(this.getResources().getDrawable(R.drawable.page15_nanguo), this.getResources().getString(R.string.no_over_target));
                    else
                        stepFragment.setIvTip(this.getResources().getDrawable(R.drawable.page15_kaixin), this.getResources().getString(R.string.over_target));
                    super.onReadReturn(bytes);
                }
            }
        }
    }

    private void SaveStepData() {
        Date date = new Date();
        if (data.size() != 0 && data != null) {
            for (int i = 0; i < data.size(); i++) {
                int count = data.get(i).getCount();
                int time = data.get(i).getTime();
                if (count <= 5 && count >= 0)
                    setPartnerData(getBeforeDay(date,6),i);
                if (count == 5 && time == 23)
                    stepFragment.setBerbarNum(1, data.get(i).getDay());
                if (count <= 11 && count >= 6)
                    setPartnerData(getBeforeDay(date,5),i);
                if (count == 11 && time == 23)
                    stepFragment.setBerbarNum(2, data.get(i).getDay());
                if (count <= 17 && count >= 12)
                    setPartnerData(getBeforeDay(date,4),i);
                if (count == 17 && time == 23)
                    stepFragment.setBerbarNum(3, data.get(i).getDay());
                if (count <= 23 && count >= 18)
                    setPartnerData(getBeforeDay(date,3),i);
                if (count == 23 && time == 23)
                    stepFragment.setBerbarNum(4, data.get(i).getDay());
                if (count <= 29 && count >= 24)
                    setPartnerData(getBeforeDay(date,2),i);
                if (count == 29 && time == 23)
                    stepFragment.setBerbarNum(5, data.get(i).getDay());
                if (count <= 35 && count >= 30)
                    setPartnerData(getBeforeDay(date,1),i);
                if (count == 35 && time == 23)
                    stepFragment.setBerbarNum(6, data.get(i).getDay());
                if (count <= 41 && count >= 36)
                    setPartnerData(date,i);
                if (count == 41 && time == 23) {
                    stepFragment.setBerbarNum(7, data.get(i).getDay());
                    type=0;
                    stepFragment.getTodayStepPost();
                    stepFragment.setTvSynchronizationtime();
                    stepFragment.removePoint();
                }
            }
        }
    }

    private void setPartnerData(Date date,int i) {
        Partner partner;
        String strday;
        if (date.getMonth() + 1 < 10) {
            if (date.getDate() < 10) {
                strday = (date.getYear() + 1900) + ".0" + (date.getMonth() + 1) + ".0" + date.getDate();
            } else {
                strday = (date.getYear() + 1900) + ".0" + (date.getMonth() + 1) + "." + date.getDate();
            }
        } else {
            if (date.getDate() < 10) {
                strday = (date.getYear() + 1900) + "." + (date.getMonth() + 1) + ".0" + date.getDate();
            } else {
                strday = (date.getYear() + 1900) + "." + (date.getMonth() + 1) + "." + date.getDate();
            }
        }

        String time = String.valueOf(data.get(i).getTime());
        SumsStep += data.get(i).getStep();

        if (data.get(i).getTime() != 23) {
            partner = new Partner();
            partner.setType("step");                                                    // 保存计步或 睡眠
            partner.setTime(String.valueOf(data.get(i).getTime()));       // 保存各时间段
            partner.setSleep(String.valueOf(data.get(i).getStep()));      //  保存记步数
            partner.setDate(strday);                                                    //  保存日期
        } else {
            // 计算活动时间
            int second = 0;

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
            partner.setCalcalNum(String.valueOf((int) card));                 //  保存卡路里
            SumsStep = 0;
        }
        sb.setLength(0);
        if (partners.size() != 0) partners.clear();
        partners = mCommonUtils.PartnerqueryByBuilder("step", strday, time);
        System.out.println(partner.getDate()+">>>"+partner.getTime()+">>>"+partner.getSleep()+"-----"+partner.getStepsumsnum());
        if (partners.size() != 0) {
            partner.setId(partners.get(0).getId());
            mCommonUtils.uoDatePartner(partner);  //更新数据
        } else
            mCommonUtils.insertPartner(partner);   //插入数据
    }

    private boolean flag = false;

    private void InitViewPager() {
        TVTitle.setText(getResources().getString(R.string.health_manage));
        viewPager = (ViewPager) findViewById(viewpager);
        //禁用ViewPager左右两侧拉到边界的渐变颜色
        try {
            Field leftEdgeField = viewPager.getClass().getDeclaredField("mLeftEdge");
            Field rightEdgeField = viewPager.getClass().getDeclaredField("mRightEdge");
            if (leftEdgeField != null && rightEdgeField != null) {
                leftEdgeField.setAccessible(true);
                rightEdgeField.setAccessible(true);
                leftEdge = (EdgeEffectCompat) leftEdgeField.get(viewPager);
                rightEdge = (EdgeEffectCompat) rightEdgeField.get(viewPager);
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        fragmentsList = new ArrayList<>();
        sleepFragment = new SleepFragment();
        stepFragment = new StepFragment();
        fragmentsList.add(stepFragment);
        fragmentsList.add(sleepFragment);

        mFragmentAdapter = new FragmentAdapter(this.getSupportFragmentManager(), fragmentsList);
        viewPager.setAdapter(mFragmentAdapter);
        viewPager.setCurrentItem(0);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (leftEdge != null && rightEdge != null) {
                    leftEdge.finish();
                    rightEdge.finish();
                    leftEdge.setSize(0, 0);
                    rightEdge.setSize(0, 0);
                }
            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        step.setBackground(getResources().getDrawable(R.drawable.health_btn_pressed_left));
                        sleep.setBackground(null);
                        break;
                    case 1:
                        sleep.setBackground(getResources().getDrawable(R.drawable.health_btn_pressed_right));
                        step.setBackground(null);
                        if (GazelleApplication.isBleConnected) {
                            if (!flag) {
                                stepFragment.removeTodayStepPost();
                                type=2;
                                sleepFragment.setSynchronizationData();
                                flag = true;
                            }
                        }
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        setActivityHandler();
        if(GazelleApplication.isBleConnected){
            type=0;
            stepFragment.getTodayStepPost();
        }
    }

    @OnClick({R.id.step, R.id.sleep, R.id.btnExit, R.id.btnOpt})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.step:
                step.setBackground(getResources().getDrawable(R.drawable.health_btn_pressed_left));
                sleep.setBackground(null);
                viewPager.setCurrentItem(0);
                break;
            case R.id.sleep:
                sleep.setBackground(getResources().getDrawable(R.drawable.health_btn_pressed_right));
                step.setBackground(null);
                viewPager.setCurrentItem(1);
                break;
            case R.id.btnExit:
                HealthyActivity.this.finish();
                overridePendingTransition(R.anim.in_lefttoright, R.anim.out_to_left);
                break;
            case R.id.btnOpt:
                String address = PreferenceData.getAddressValue(this);
                if (address != null && !address.equals("")) {
                    if (!GazelleApplication.isBleConnected) {
                        showToatst(HealthyActivity.this, getResources().getString(R.string.not_connect_device));
                        break;
                    }
                    stepFragment.removeTodayStepPost();
                    if (viewPager.getCurrentItem() == 0) {
                        type=1;
                        stepFragment.setSynchronizationData();
                    } else{
                        type=2;
                        sleepFragment.setSynchronizationData();
                    }
                } else {
                    showToatst(this, getResources().getString(R.string.inspect_ble_state));
                }
                break;
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            overridePendingTransition(R.anim.in_lefttoright, R.anim.out_to_left);
        }
        return super.onKeyDown(keyCode, event);
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

    @Override
    protected void onPause() {
        super.onPause();
        stepFragment.removeTodayStepPost();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stepFragment.removeTodayStepPost();
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

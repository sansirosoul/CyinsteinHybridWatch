package com.xyy.Gazella.activity;

import android.os.Bundle;
import android.os.Handler;
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
import com.xyy.model.StepData;
import com.ysp.hybridtwatch.R;
import com.ysp.newband.BaseActivity;
import com.ysp.newband.PreferenceData;

import java.lang.reflect.Field;
import java.util.ArrayList;
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
    private boolean isTrue ;
    private int dayStep;
    private List<StepData> data;
    private Time mCalendar;
    private int myear, month, day, Queryday,SumsStep,Weight;
    private StringBuffer sb = new StringBuffer();
    public CommonUtils mCommonUtils;
    private List<Partner> partners = new ArrayList<>();
    private String strMonth, strDay,userWeight;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_healthy);
        ButterKnife.bind(this);
        String address = PreferenceData.getAddressValue(this);
        bleUtils = new BleUtils();
        InitViewPager();

        if (address != null && !address.equals("")) {
            connectionObservable = getRxObservable(this);
            Notify(connectionObservable);
            Write(bleUtils.setSystemType(),connectionObservable);
            btnOpt.setBackground(getResources().getDrawable(R.drawable.page15_tongbu));
        }
        initData();
        initTime();
        install = this;
        mCommonUtils = new CommonUtils(this);
    }

    private void initData() {
        TargetStep = PreferenceData.getTargetRunValue(HealthyActivity.this);
        userWeight = PreferenceData.getUserInfo(HealthyActivity.this).getWeight();
        if(userWeight!=null&&!userWeight.equals("")) {
            userWeight = userWeight.replaceAll("[a-z]", ",");
            String s2[] = userWeight.split(",");
            userWeight = s2[0];
        }else
        userWeight="0";
        isTrue=true;
    }

    @Override
    protected void onNotifyReturn(int type,String str) {
        switch (type) {
            case 0:
                isNotify = true;
                break;
            case 1:   // 断开状态
                isNotify = false;
                HandleThrowableException(str);
                break;
            case 2:   // 重新连接
                Notify(connectionObservable);
                isNotify = true;
                stepFragment.getTodayStepPost();
                break;
        }
        super.onNotifyReturn(type,str);
    }

    @Override
    protected void onReadReturn(byte[] bytes) {

//        if (bleUtils.returnStepData(bytes)!=null) {
//                data = bleUtils.returnStepData(bytes);
//                SaveStepData();
//        }
        if (bytes[0] == 0x07 && bytes[1] == 0x0C) {  // 今日步数
            StepData stepData = bleUtils.returnTodayStep(bytes);
            if (stepData != null) {
                dayStep = stepData.getStep();
               if (isTrue) {
                stepFragment.setStepNum(stepData.getStep(),stepFragment.stepNum);
                stepFragment.setStepNumTextOnEndListener(new StepFragment.setStepNumTextOnEndListener() {
                    @Override
                    public void setStepNumTextOnEndListener() {
                        stepFragment.getTodayStepPost();
                        isTrue=false;
                    }
                });
            }else
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
                int second =(int) (dayStep*1.08);
                if(second>60){
                    stepFragment.setTime(String.valueOf(second/60)+getResources().getString(R.string.minute));
                }else if(second>60*60){
                    stepFragment.setTime(String.valueOf(second/360)+getResources().getString(R.string.hour)
                    +String.valueOf((second%3600)/60)+getResources().getString(R.string.minute));
                }

                if (dayStep <= TargetStep)
                    stepFragment.setIvTip(this.getResources().getDrawable(R.drawable.page15_nanguo), this.getResources().getString(R.string.no_over_target));
                else
                    stepFragment.setIvTip(this.getResources().getDrawable(R.drawable.page15_kaixin), this.getResources().getString(R.string.over_target));
                super.onReadReturn(bytes);
            }
        }
    }


    private void SaveStepData() {
        if (data.size() != 0 && data != null) {
            for (int i = 0; i < data.size(); i++) {
                int count = data.get(i).getCount();
                int time = data.get(i).getTime();
                int  date[]= new int [7];
                if (count <= 5 && count >= 0)
                    setPartnerData(i);
                if(count==5&&time==23)
                    date[0]=data.get(i).getDay();
                if (count <= 11 && count >= 6)
                    setPartnerData(i);
                if(count==11&&time==23)
                    date[1]=data.get(i).getDay();
                if (count <= 17 && count >= 12)
                    setPartnerData(i);
                if(count==17&&time==23)
                    date[2]=data.get(i).getDay();
                if (count <= 23 && count >= 18)
                    setPartnerData(i);
                if(count==23&&time==23)
                    date[3]=data.get(i).getDay();
                if (count <= 29 && count >= 24)
                    setPartnerData(i);
                if(count==29&&time==23)
                    date[4]=data.get(i).getDay();
                if (count <= 35 && count >= 30)
                    setPartnerData(i);
                if(count==35&&time==23)
                    date[5]=data.get(i).getDay();
                if (count <= 41 && count >= 36)
                    setPartnerData(i);
                if (count == 41 && time == 23) {
                    date[6]=data.get(i).getDay();
                    stepFragment.setSynchronization(date);
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
            partner.setType("step");                                                    // 保存计步或 睡眠
            partner.setTime(String.valueOf(data.get(i).getTime()));       // 保存各时间段
            partner.setSleep(String.valueOf(data.get(i).getStep()));      //  保存记步数
            partner.setDate(strday);                                                    //  保存日期
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
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
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
                if (viewPager.getCurrentItem() == 0) {
                    //stepFragment.setSynchronization();
                    stepFragment.removeTodayStepPost();

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Write(bleUtils.getStepData(6), connectionObservable);
                        }
                    },10000);

                }else
                    sleepFragment.setSynchronization();
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

    private void initTime() {
        mCalendar = new Time();
        mCalendar.setToNow();
        myear = mCalendar.year;
        month = mCalendar.month + 1;
        day = mCalendar.monthDay;
        Queryday = mCalendar.monthDay;
    }
}

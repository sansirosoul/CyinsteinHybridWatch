package com.xyy.Gazella.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.EdgeEffectCompat;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.polidea.rxandroidble.RxBleConnection;
import com.xyy.Gazella.fragment.SleepFragment;
import com.xyy.Gazella.fragment.StepFragment;
import com.xyy.Gazella.utils.BleUtils;
import com.xyy.Gazella.utils.SomeUtills;
import com.xyy.model.StepData;
import com.ysp.newband.BaseActivity;
import com.ysp.newband.PreferenceData;
import com.ysp.smartwatch.R;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;

import static com.ysp.smartwatch.R.id.viewpager;


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
    private String userWeight;
    private int Weight;
    private int TargetStep;


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
            btnOpt.setBackground(getResources().getDrawable(R.drawable.page15_tongbu));
        }
        initData();
        install = this;
    }

    private void initData() {
        TargetStep = PreferenceData.getTargetRunValue(HealthyActivity.this);
        userWeight = PreferenceData.getUserInfo(HealthyActivity.this).getWeight();
        userWeight = userWeight.replaceAll("[a-z]", ",");
        String s2[] = userWeight.split(",");
        userWeight = s2[0];
    }

    @Override
    protected void onNotifyReturn(int type) {
        switch (type) {
            case 0:
                isNotify = true;
                break;
            case 1:   // 断开状态
                isNotify = false;
//                stepFragment.removeTodayStepPost();
                break;
            case 2:   // 重新连接
                Notify(connectionObservable);
                stepFragment.getTodayStepPost();
                break;
        }
        super.onNotifyReturn(type);
    }

    @Override
    protected void onReadReturn(byte[] bytes) {
        if (bytes[0] == 0x07 && bytes[1] == 0x0C) {  // 今日步数
            StepData stepData = bleUtils.returnTodayStep(bytes);
            if (stepData != null) {
                int step = stepData.getStep();
                double km = step * 0.5;
                stepFragment.setStepNum(String.valueOf(stepData.getStep()));
                // 计算活动距离
                if (km < 1000)
                    stepFragment.setDistanceNum(String.valueOf((int) km) + getResources().getString(R.string.mi));
                else
                    stepFragment.setDistanceNum(String.valueOf(new SomeUtills().changeDouble(km)) + getResources().getString(R.string.km));
                //计算卡路里
                if (userWeight != null && !userWeight.equals("")) {
                    Weight = Integer.valueOf(userWeight);
                    double card = ((Weight * 0.0005 + (step - 1) * 0.005) * step);
                    if (card < 1000)
                        stepFragment.setCalcalNum(String.valueOf(Integer.valueOf((int) card)) + getResources().getString(R.string.card));
                    else
                        stepFragment.setCalcalNum(String.valueOf(new SomeUtills().changeDouble(card)) + getResources().getString(R.string.Kcard));
                }

                if (step <= TargetStep)
                    stepFragment.setIvTip(this.getResources().getDrawable(R.drawable.page15_nanguo), this.getResources().getString(R.string.no_over_target));
                else
                    stepFragment.setIvTip(this.getResources().getDrawable(R.drawable.page15_kaixin), this.getResources().getString(R.string.over_target));
                super.onReadReturn(bytes);
            }
        }
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
                finish();
                overridePendingTransition(R.anim.in_lefttoright, R.anim.out_to_left);
                break;
            case R.id.btnOpt:
                if (viewPager.getCurrentItem() == 0)
                    stepFragment.setSynchronization();
                else
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
}

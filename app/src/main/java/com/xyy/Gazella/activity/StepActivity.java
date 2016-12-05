package com.xyy.Gazella.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.orhanobut.logger.Logger;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.xyy.Gazella.fragment.StepDayFragment;
import com.xyy.Gazella.fragment.StepMonthFragment;
import com.xyy.Gazella.fragment.StepWeekFragment;
import com.xyy.Gazella.utils.CommonDialog;
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

public class StepActivity extends BaseActivity implements OnDateSelectedListener {

    private static  String TAG=StepActivity.class.getName();

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

    public static final int UPDATEUI = 1001;
    private  SomeUtills utills;


    private static final DateFormat FORMATTER = SimpleDateFormat.getDateInstance();
    private Calendar CalendarInstance = Calendar.getInstance();
    private HashMap<String, String> weekMap;
    public static StepActivity stepActivity = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step);
        ButterKnife.bind(this);
        initView();
        initCalendar();
        InitViewPager();
        stepActivity = this;
    }

    private void initView() {

        TVTitle.setText("计步详情");
        btnDate.setBackground(this.getResources().getDrawable(R.drawable.page17_rili));
        btnOpt.setBackground(this.getResources().getDrawable(R.drawable.page17_share));
        utills=new SomeUtills();
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
                CommonDialog dialog=new CommonDialog(this);
                dialog.show();
//                utills().showShare(this);
                utills.setCompress(stepActivity, R.id.activity_step);
//                utills.setress(stepActivity, R.id.activity_step).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<byte[]>() {
//                    @Override
//                    public void onCompleted() {
//                        Logger.t(TAG).e("111111111111>>>>>>>>>");
//                        dialog.dismiss();
//                        utills.showShare(StepActivity.this);
////                        File file = new File(Environment.getExternalStorageDirectory() + "/" + "share.png");
////                        if (file.exists() && file.isFile()) {
////                            file.delete();
////                        }
//                    }
//                    @Override
//                    public void onError(Throwable e) {
//                        Log.i(TAG,e.getMessage());
//                    }
//
//                    @Override
//                    public void onNext(byte[] bytes) {
//                        Logger.t(TAG).e("00000000000000>>>>>>>>>");
//                    }
//                });
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

            loadImageAnimation= AnimationUtils.loadAnimation(getApplicationContext(), R.anim.btn_up);
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
                stepDayFragment.updateUI(new String[]{});
                break;
            case 1:
                weekMap = utills.getWeekdate(date.getDate());
                if (weekMap != null)
                    stepWeekFragment.setTvDateValue(weekMap.get("1") + " - " + weekMap.get("7"));
                break;
            case 2:
                stepMonthFragment.setTvDateValue(utills.getDate(date.getDate(), 1));
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

    @Override
    protected void onResume() {
        super.onResume();
        Logger.t(TAG).e("1111111111");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Logger.t(TAG).e("2222222222");
    }
}

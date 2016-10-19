package com.xyy.Gazella.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xyy.Gazella.fragment.MainDialFragment;
import com.xyy.Gazella.fragment.SmallFragment1;
import com.xyy.Gazella.fragment.SmallFragment2;
import com.xyy.Gazella.fragment.SmallFragment3;
import com.xyy.Gazella.utils.CheckAnalogClock;
import com.xyy.Gazella.view.MyViewPage;
import com.ysp.smartwatch.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.ysp.newband.BaseActivity.overridePendingTransitionExit;

public class TimeSynchronization extends FragmentActivity {

    //    @BindView(R.id.analogclock)
//    AnalogClock analogclock;
    @BindView(R.id.but_reduce)
    ImageButton butReduce;
    @BindView(R.id.but_add)
    ImageButton butAdd;
    @BindView(R.id.but_hour)
    Button butHour;
    @BindView(R.id.but_muinutes)
    Button butMuinutes;
    @BindView(R.id.but_second)
    Button butSecond;
    @BindView(R.id.but_reset)
    Button butReset;
    @BindView(R.id.but_synchronization)
    Button butSynchronization;
    @BindView(R.id.activity_time_synchronization)
    LinearLayout activityTimeSynchronization;
    @BindView(R.id.btnExit)
    Button btnExit;
    @BindView(R.id.btnOpt)
    Button btnOpt;
    @BindView(R.id.TVTitle)
    TextView TVTitle;
    @BindView(R.id.iv_left)
    ImageView ivLeft;
    @BindView(R.id.iv_right)
    ImageView ivRight;
    @BindView(R.id.viewpager)
    MyViewPage viewpager;
    private int getMinutesValue;
    private int getHourValue;
    private int setMinutesValue;
    private int setHourValue;
    private boolean isChangeTime = false;
    private CheckAnalogClock checkAnalogClock;

    private ArrayList<Fragment> fragmentsList;
    private SmallFragment1 smallFragment1;
    private SmallFragment2 smallFragment2;
    private SmallFragment3 smallFragment3;
    private MainDialFragment mainDialFragment;
    private TimeSynchronization.FragmentAdapter mFragmentAdapter;
    private int item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_synchronization);
        ButterKnife.bind(this);
        InitView();
        InitViewPager();
    }

    private void InitView() {
        TVTitle.setText("智能校时");
        btnOpt.setBackground(getResources().getDrawable(R.drawable.page12_lianjie));
        ivLeft.setVisibility(View.GONE);
        ivRight.setVisibility(View.GONE);
        checkAnalogClock = new CheckAnalogClock(TimeSynchronization.this);
        butHour.setBackground(getResources().getDrawable(R.drawable.time_circlebtn_normal));
        butMuinutes.setBackground(getResources().getDrawable(R.drawable.time_circlebtn_press));
        butSecond.setBackground(getResources().getDrawable(R.drawable.time_circlebtn_press));
        checkAnalogClock.setOnItemClickListener(new CheckAnalogClock.onItemClickListener() {

            @Override
            public void onSmall1Click() {
                setSmallItem(0);
            }

            @Override
            public void onSmall2Click() {
                setSmallItem(1);
            }

            @Override
            public void onSmall3Click() {
                setSmallItem(2);
            }

            @Override
            public void onCloseClick() {
                checkAnalogClock.dismiss();
                butReset.setVisibility(View.VISIBLE);
                butSynchronization.setVisibility(View.VISIBLE);
            }
        });
    }

    @OnClick({R.id.iv_left, R.id.iv_right, R.id.btnExit, R.id.btnOpt, R.id.TVTitle, R.id.but_reduce, R.id.but_add, R.id.but_hour, R.id.but_muinutes, R.id.but_second, R.id.but_reset, R.id.but_synchronization})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.but_reduce:  //减时间
                if(fragmentsList.size()>1){
                    switch (viewpager.getCurrentItem()){
                        case 0:
                            smallFragment1.ReduceTime();
                            break;
                        case 1:
                            smallFragment2.ReduceTime();
                            break;
                        case 2:
                            smallFragment3.ReduceTime();
                            break;
                    }
                }else {
                    mainDialFragment.ReduceTime();
                }
                break;

            case R.id.but_add://加时间
                if(fragmentsList.size()>1){
                    switch (viewpager.getCurrentItem()){
                        case 0:
                            smallFragment1.AddTime();
                            break;
                        case 1:
                            smallFragment2.AddTime();
                            break;
                        case 2:
                            smallFragment3.AddTime();
                            break;
                    }
                }else {
                    mainDialFragment.AddTime();
                }
                break;



            case R.id.but_hour:   // 调整时针
                setImageVisible(2);
                setFragmentsList(2);
                viewpager.setScroll(true);
                viewpager.setCurrentItem(0);
                mainDialFragment.setChangeTimeType(1);

                butHour.setBackground(getResources().getDrawable(R.drawable.time_circlebtn_normal));
                butMuinutes.setBackground(getResources().getDrawable(R.drawable.time_circlebtn_press));
                butSecond.setBackground(getResources().getDrawable(R.drawable.time_circlebtn_press));

                break;
            case R.id.but_muinutes://  调整分针
                setImageVisible(2);
                setFragmentsList(2);
                viewpager.setScroll(true);
                viewpager.setCurrentItem(0);

                mainDialFragment.setChangeTimeType(2);

                butHour.setBackground(getResources().getDrawable(R.drawable.time_circlebtn_press));
                butMuinutes.setBackground(getResources().getDrawable(R.drawable.time_circlebtn_normal));
                butSecond.setBackground(getResources().getDrawable(R.drawable.time_circlebtn_press));

                break;
            case R.id.but_second:   // 调整小时针
                checkAnalogClock.show();
                butReset.setVisibility(View.GONE);
                butSynchronization.setVisibility(View.GONE);
                butHour.setBackground(getResources().getDrawable(R.drawable.time_circlebtn_press));
                butMuinutes.setBackground(getResources().getDrawable(R.drawable.time_circlebtn_press));
                butSecond.setBackground(getResources().getDrawable(R.drawable.time_circlebtn_normal));

                break;
            case R.id.but_reset:   /// 重置
                break;
            case R.id.but_synchronization:    ///同步
                break;
            case R.id.btnExit:   // 退出
                TimeSynchronization.this.finish();
                overridePendingTransitionExit(TimeSynchronization.this);
                break;
            case R.id.btnOpt:
                break;
            case R.id.TVTitle:
                break;
            case R.id.iv_left:
                item = viewpager.getCurrentItem();
                item--;
                viewpager.setCurrentItem(item);

                break;
            case R.id.iv_right:
                item = viewpager.getCurrentItem();
                item++;
                viewpager.setCurrentItem(item);

                break;
        }
    }

    private void InitViewPager() {

        fragmentsList = new ArrayList<>();
        mainDialFragment = new MainDialFragment();
        smallFragment1 = new SmallFragment1();
        smallFragment2 = new SmallFragment2();
        smallFragment3 = new SmallFragment3();

        fragmentsList.add(mainDialFragment);

        mFragmentAdapter = new TimeSynchronization.FragmentAdapter(this.getSupportFragmentManager(), fragmentsList);
        viewpager.setAdapter(mFragmentAdapter);
        viewpager.setCurrentItem(0);
        viewpager.setScroll(true);

    }

    public class FragmentAdapter extends FragmentStatePagerAdapter {
        List<Fragment> fragmentlist = new ArrayList<>();

        public FragmentAdapter(FragmentManager fm, List<Fragment> fragmentList) {
            super(fm);
            this.fragmentlist = fragmentList;
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentlist.get(position);
        }

        @Override
        public int getItemPosition(Object object) {
            return PagerAdapter.POSITION_NONE;
        }

        @Override
        public int getCount() {
            return fragmentlist.size();
        }
    }

    /***
     * @param type 1 是显示  2 是隐藏
     */
    private void setImageVisible(int type) {
        if (type == 1) {
            ivRight.setVisibility(View.VISIBLE);
            ivLeft.setVisibility(View.VISIBLE);
        } else {
            ivRight.setVisibility(View.GONE);
            ivLeft.setVisibility(View.GONE);
        }
    }

    /***
     * 切换 时分针 小针 Fragment
     *
     * @param type 1 是时分针  2是小针
     */

    private void setFragmentsList(int type) {
        fragmentsList.clear();
        if (type == 1) {
            fragmentsList.add(smallFragment1);
            fragmentsList.add(smallFragment2);
            fragmentsList.add(smallFragment3);
        } else {
            fragmentsList.add(mainDialFragment);
        }
        mFragmentAdapter.notifyDataSetChanged();
    }

    /***
     *     设置小针Fragment 位置
     * @param item
     */
    private  void  setSmallItem(int item){
        setImageVisible(1);
        butReset.setVisibility(View.VISIBLE);
        butSynchronization.setVisibility(View.VISIBLE);
        checkAnalogClock.dismiss();

        setFragmentsList(1);
        viewpager.setScroll(false);
        viewpager.setCurrentItem(item);
    }
}

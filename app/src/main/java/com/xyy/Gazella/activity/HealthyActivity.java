package com.xyy.Gazella.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.xyy.Gazella.fragment.SleepFragment;
import com.xyy.Gazella.fragment.StepFragment;
import com.ysp.smartwatch.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

import static com.ysp.smartwatch.R.id.viewpager;


public class HealthyActivity extends FragmentActivity {


    private ViewPager viewPager;
    private ArrayList<Fragment> fragmentsList;
    private SleepFragment sleepFragment;
    private StepFragment stepFragment;
    private FragmentAdapter mFragmentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_healthy);
        ButterKnife.bind(this);
        InitViewPager();
    }

    private void InitViewPager() {
        viewPager= (ViewPager) findViewById(viewpager);
        fragmentsList = new ArrayList<>();
        sleepFragment = new SleepFragment();
        stepFragment = new StepFragment();
        fragmentsList.add(stepFragment);
        fragmentsList.add(sleepFragment);

        mFragmentAdapter = new FragmentAdapter( this.getSupportFragmentManager(), fragmentsList);
        viewPager.setAdapter(mFragmentAdapter);
        viewPager.setCurrentItem(0);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
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

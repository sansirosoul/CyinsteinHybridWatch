package com.xyy.Gazella.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.text.format.Time;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.polidea.rxandroidble.RxBleConnection;
import com.polidea.rxandroidble.RxBleDevice;
import com.polidea.rxandroidble.utils.ConnectionSharingAdapter;
import com.xyy.Gazella.fragment.MainDialFragment;
import com.xyy.Gazella.fragment.SmallFragment1;
import com.xyy.Gazella.fragment.SmallFragment2;
import com.xyy.Gazella.fragment.SmallFragment3;
import com.xyy.Gazella.utils.BleUtils;
import com.xyy.Gazella.utils.CheckAnalogClock;
import com.xyy.Gazella.utils.CommonDialog;
import com.xyy.Gazella.utils.GuideShowDialog;
import com.xyy.Gazella.utils.HexString;
import com.xyy.Gazella.view.MyViewPage;
import com.ysp.newband.BaseActivity;
import com.ysp.newband.GazelleApplication;
import com.ysp.newband.PreferenceData;
import com.ysp.smartwatch.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.subjects.PublishSubject;


public class TimeSynchronization extends BaseActivity {

    private static final String TAG = TimeSynchronization.class.getName();
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
    @BindView(R.id.btnExit)
    LinearLayout btnExit;
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
    @BindView(R.id.activity_time_synchronization)
    LinearLayout activityTimeSynchronization;
    @BindView(R.id.tv_hint)
    TextView tvHint;
    private boolean isChangeTime = false;
    private CheckAnalogClock checkAnalogClock;

    private ArrayList<Fragment> fragmentsList;
    private SmallFragment1 smallFragment1;
    private SmallFragment2 smallFragment2;
    private SmallFragment3 smallFragment3;
    private MainDialFragment mainDialFragment;
    private FragmentAdapter mFragmentAdapter;
    private int small1TimeValue;
    private int small2TimeValue;
    private int small3TimeValue;
    private float HourTimeValue;
    private float MuintesTimeValue;
    private int item;
    private GuideShowDialog guideShowDialog;
    private RxBleDevice bleDevice;
    public Observable<RxBleConnection> connectionObservable;
    private BleUtils bleUtils;
    public static TimeSynchronization install;
    private CommonDialog dialog;
    private Time mCalendar;
    public int hour;
    public int minute;
    private int second;
    private int myear;
    private int month;
    private int mday;
    private PublishSubject<Void> disconnectTriggerSubject = PublishSubject.create();
    private  boolean isClickSynchronization=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_synchronization);
        ButterKnife.bind(this);
        String address = PreferenceData.getAddressValue(this);
        if (address != null && !address.equals(""))
            bleDevice = GazelleApplication.getRxBleClient(this).getBleDevice(address);
        if (bleDevice != null) {
            connectionObservable = bleDevice
                    .establishConnection(this, false)
//                    .takeUntil(disconnectTriggerSubject)
//                    .doOnUnsubscribe(this::clearSubscription)
                    .compose(new ConnectionSharingAdapter());

            Notify(connectionObservable);
            bleUtils = new BleUtils();
        }
        InitView();
        InitViewPager();
        install = this;
        dialog = new CommonDialog(TimeSynchronization.this);
        dialog.show();

    }

    @Override
    protected void onNotifyReturn(int type) {
        if (type == 0) {              //可以接收通知
            if (dialog.isShowing())
                dialog.dismiss();
        } else {
            if (dialog.isShowing()) {
                dialog.setTvContext("没有搜索蓝牙");
            }
        }
        super.onNotifyReturn(type);
    }

    @Override
    protected void onReadReturn(byte[] bytes) {
        HexString.bytesToHex(bytes);
        if(HexString.bytesToHex(bytes).equals("0702010A1A")) {
            isClickSynchronization=false;
            tvHint.setText("智能校时成功");
        }
        super.onReadReturn(bytes);
    }

    private void clearSubscription() {
        connectionObservable = null;
    }

    private void triggerDisconnect() {
        disconnectTriggerSubject.onNext(null);
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
            }
        });
        checkAnalogClock.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                butReset.setVisibility(View.VISIBLE);
                butSynchronization.setVisibility(View.VISIBLE);
            }
        });
    }

    @OnClick({R.id.iv_left, R.id.iv_right, R.id.btnExit, R.id.btnOpt, R.id.TVTitle, R.id.but_reduce, R.id.but_add, R.id.but_hour, R.id.but_muinutes, R.id.but_second, R.id.but_reset, R.id.but_synchronization})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.but_reduce:  //减时间
                if (fragmentsList.size() > 1) {
                    switch (viewpager.getCurrentItem()) {
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
                } else
                    mainDialFragment.ReduceTime();
                break;

            case R.id.but_add://加时间
                if (fragmentsList.size() > 1) {
                    switch (viewpager.getCurrentItem()) {
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
                } else
                    mainDialFragment.AddTime();

                break;

            case R.id.but_hour:   // 调整时针
                setChangeTimeType(1);

                break;
            case R.id.but_muinutes://  调整分针

                setChangeTimeType(2);


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
                if (isClickSynchronization) {
                    showToatst(TimeSynchronization.this,"请先点击同步按键");
                    break;
                }
                if (isconnectionObservable())
                    Write(bleUtils.resetHand(), connectionObservable);
                mHandler.post(runnable);
                isRun = true;
                tvHint.setText("第二步: 调整表盘指针将手表时,分针拨至12点整 后点击同步按键");
                isClickSynchronization=true;
                break;
            case R.id.but_synchronization:    ///同步
                if (!isClickSynchronization) {
                    showToatst(TimeSynchronization.this,"请先点击重置按键");
                    break;
                }
                initTime();
                HourTimeValue = PreferenceData.getSelectedHourValue(TimeSynchronization.this);
                MuintesTimeValue = PreferenceData.getSelectedMuinutesValue(TimeSynchronization.this);
                small1TimeValue = PreferenceData.getSelectedSmall1Value(this);
                small2TimeValue = PreferenceData.getSelectedSmall2Value(this);
                small3TimeValue = PreferenceData.getSelectedSmall3Value(this);

                Write(bleUtils.setWatchDateAndTime(1, myear, month, mday, hour, minute, second), connectionObservable);
//                setSynchronizationTime();
                mHandler.post(SynchronizationTime);
                SynchronizationTimeRun = true;

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

        mFragmentAdapter = new FragmentAdapter(this.getSupportFragmentManager(), fragmentsList);
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
     * 设置小针Fragment 位置
     *
     * @param item
     */
    private void setSmallItem(int item) {
        setImageVisible(1);
        butReset.setVisibility(View.VISIBLE);
        butSynchronization.setVisibility(View.VISIBLE);
        checkAnalogClock.dismiss();

        setFragmentsList(1);
        viewpager.setScroll(false);
        viewpager.setCurrentItem(item);

    }

    /***
     * 1 是 时针  2 分针
     *
     * @param type
     */
    private void setChangeTimeType(int type) {
        if (type == 1) {
            setImageVisible(2);
            setFragmentsList(2);
            viewpager.setScroll(true);
            viewpager.setCurrentItem(0);
            mainDialFragment.setChangeTimeType(1);
            mainDialFragment.setHourDrawable(R.drawable.page12_hour_selected);
            mainDialFragment.setMuinutesDrawable(R.drawable.page12_minute_normal);
            butHour.setBackground(getResources().getDrawable(R.drawable.time_circlebtn_normal));
            butMuinutes.setBackground(getResources().getDrawable(R.drawable.time_circlebtn_press));
            butSecond.setBackground(getResources().getDrawable(R.drawable.time_circlebtn_press));
        } else {
            setImageVisible(2);
            setFragmentsList(2);
            viewpager.setScroll(true);
            viewpager.setCurrentItem(0);

            mainDialFragment.setChangeTimeType(2);
            mainDialFragment.setMuinutesDrawable(R.drawable.page12_minute_selected);
            mainDialFragment.setHourDrawable(R.drawable.page12_hour_normal);
            butHour.setBackground(getResources().getDrawable(R.drawable.time_circlebtn_press));
            butMuinutes.setBackground(getResources().getDrawable(R.drawable.time_circlebtn_normal));
            butSecond.setBackground(getResources().getDrawable(R.drawable.time_circlebtn_press));
        }
        mainDialFragment.setHourTimeValue(PreferenceData.getSelectedHourValue(TimeSynchronization.this));
        mainDialFragment.setMuinutesTimeValue(PreferenceData.getSelectedMuinutesValue(TimeSynchronization.this));
    }


    /***
     * 小针 归0
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        PreferenceData.setSelectedSmall1Value(TimeSynchronization.this, 0);
        PreferenceData.setSelectedSmall2Value(TimeSynchronization.this, 0);
        PreferenceData.setSelectedSmall3Value(TimeSynchronization.this, 0);
    }

    private boolean isconnectionObservable() {
        if (connectionObservable != null)
            return true;
        else
            return false;
    }

    private boolean isRun = true;
    private boolean SynchronizationTimeRun = true;
    private boolean HourCount = true;
    private boolean MuinutesCount = true;
    private int count;
    private int count2 = 60;
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (isRun) {
                count++;
                count2--;
                mHandler.sendEmptyMessage(1001);
                mHandler.postDelayed(this, 50);
            }
        }
    };
    Runnable SynchronizationTime = new Runnable() {
        @Override
        public void run() {
            if (SynchronizationTimeRun) {
                if (MuinutesCount)
                    count++;
                if (HourCount)
                    count2--;
                mHandler.sendEmptyMessage(1002);
                mHandler.postDelayed(this, 50);
            }
        }
    };

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1001:
                    if (count > 60 && count2 < 0) {
                        handler.post(runnable);
                        count = 0;
                        count2 = 60;
                        isRun = false;
                    } else {
                        mainDialFragment.setMuinutesTimeValue(count);
                        mainDialFragment.setHourTimeValue(count2);
                        isRun = true;
                    }
                    break;
                case 1002:
                    if (count2 < countHour)
                        HourCount = false;
                    if (count > minute)
                        MuinutesCount = false;
                    if (count2 < countHour && count > minute) {
                        handler.post(SynchronizationTime);
                        count = 0;
                        count2 = 60;
                        SynchronizationTimeRun = false;
                    } else {
                        if (HourCount)
                            mainDialFragment.setHourTimeValue(count2);
                        if (MuinutesCount)
                            mainDialFragment.setMuinutesTimeValue(count);
                        SynchronizationTimeRun = true;
                        MuinutesCount = true;
                        HourCount = true;
                    }
                    break;
            }
            super.handleMessage(msg);
        }
    };

    private int countHour;

    private void initTime() {
        mCalendar = new Time();
        mCalendar.setToNow();
        hour = mCalendar.hour;
        minute = mCalendar.minute;
        second = mCalendar.second;
        myear = mCalendar.year;
        month = mCalendar.month;
        mday = mCalendar.monthDay;
        Calendar now;
        SimpleDateFormat fmt;
        now = Calendar.getInstance();
        fmt = new SimpleDateFormat("hh:mm:ss");
        String ss = fmt.format(now.getTime());
        ss = ss.substring(0, 2);
        countHour = Integer.valueOf(ss);
        int count = countHour;
        countHour = 0;
        for (int i = 0; i < count; i++) {
            countHour += 5;
        }
    }
}

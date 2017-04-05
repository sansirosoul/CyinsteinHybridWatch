package com.xyy.Gazella.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.xyy.Gazella.utils.BleUtils;
import com.xyy.Gazella.view.RotatView;
import com.ysp.hybridtwatch.R;
import com.ysp.newband.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/10/11.
 */

public class MainDialFragment extends BaseFragment {

    private static final String TAG = MainDialFragment.class.getName();
    @BindView(R.id.rotatView)
    RotatView rotatView;
    @BindView(R.id.rotatLayout)
    RelativeLayout rotatLayout;
    @BindView(R.id.logo)
    ImageView logo;
    private View view;
    private BleUtils bleUtils;
    private int timeType = 1;  //1 是 时针  2 分针

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        view = inflater.inflate(R.layout.fragment_main_dial, container, false);
        ButterKnife.bind(this, view);
//        logo.setImageDrawable(getResources().getDrawable(R.drawable.adj_julius));
        rotatView.setRotatDrawableResource(R.drawable.pan);
        rotatView.setChangeTimeListener(changeTimeListener);
        bleUtils = new BleUtils();
        return view;
    }

    public void setChangeTimeType(int ChangeTimeType) {
        this.timeType = ChangeTimeType;
    }

    public void AddTime() {
        if (timeType == 1) {
            writeCharacteristic(bleUtils.adjHourHand(1, 1));
        } else if (timeType == 2) {
            writeCharacteristic(bleUtils.adjMinuteHand(1, 1));
        }
    }

    public void ReduceTime() {
        if (timeType == 1) {
            writeCharacteristic(bleUtils.adjHourHand(2, 1));
        } else if (timeType == 2) {
            writeCharacteristic(bleUtils.adjMinuteHand(2, 1));
        }
    }

    private RotatView.ChangeTimeListener changeTimeListener = new RotatView.ChangeTimeListener() {
        @Override
        public void ChangeTimeListener(boolean isClockwise) {
            if (isClockwise) {
                if (timeType == 1) {
                    writeCharacteristic(bleUtils.adjHourHand(1, 1));
                } else if (timeType == 2) {
                    writeCharacteristic(bleUtils.adjMinuteHand(1, 1));
                }
            } else {
                if (timeType == 1) {
                    writeCharacteristic(bleUtils.adjHourHand(2, 1));
                } else if (timeType == 2) {
                    writeCharacteristic(bleUtils.adjMinuteHand(2, 1));
                }
            }
        }
    };
}

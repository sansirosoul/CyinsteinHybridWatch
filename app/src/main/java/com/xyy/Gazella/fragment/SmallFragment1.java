package com.xyy.Gazella.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.xyy.Gazella.utils.BleUtils;
import com.xyy.Gazella.view.RotatView;
import com.ysp.hybridtwatch.R;
import com.ysp.newband.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/10/11.
 */

public class SmallFragment1 extends BaseFragment {

    private static final String TAG = SmallFragment1.class.getName();
    @BindView(R.id.rotatView)
    RotatView rotatView;
    @BindView(R.id.logo)
    ImageView logo;
    private View view;
    private BleUtils bleUtils;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        view = inflater.inflate(R.layout.fragment_small_1, container, false);
        ButterKnife.bind(this, view);
        bleUtils = new BleUtils();
//        logo.setImageDrawable(getResources().getDrawable(R.drawable.adj_julius));
        rotatView.setRotatDrawableResource(R.drawable.pan);
        rotatView.setChangeTimeListener(changeTimeListener);
        return view;
    }

    public void AddTime() {
        writeCharacteristic(bleUtils.adjStepHand(1, 1));
    }

    public void ReduceTime() {
        writeCharacteristic(bleUtils.adjStepHand(2, 1));
    }

    private RotatView.ChangeTimeListener changeTimeListener = new RotatView.ChangeTimeListener() {
        @Override
        public void ChangeTimeListener(boolean isClockwise) {
            if (isClockwise) {
                writeCharacteristic(bleUtils.adjStepHand(1, 1));
            } else {
                writeCharacteristic(bleUtils.adjStepHand(2, 1));
            }
        }
    };
}

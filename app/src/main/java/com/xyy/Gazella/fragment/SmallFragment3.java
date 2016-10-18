package com.xyy.Gazella.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xyy.Gazella.view.AnalogClock;
import com.ysp.smartwatch.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/10/11.
 */

public class SmallFragment3 extends Fragment {


    @BindView(R.id.analogclock)
    AnalogClock analogclock;
    private View view;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        view = inflater.inflate(R.layout.fragment_small_3, container, false);
        ButterKnife.bind(this, view);
        analogclock.setChangeTimeType(2);
        return view;
    }
}

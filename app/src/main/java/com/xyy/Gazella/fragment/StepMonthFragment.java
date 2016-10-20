package com.xyy.Gazella.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ysp.smartwatch.R;

import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/10/11.
 */

public class StepMonthFragment extends Fragment {
    private View view;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        view = inflater.inflate(R.layout.fragment_step, container, false);

        ButterKnife.bind(this, view);
        return view;
    }



}

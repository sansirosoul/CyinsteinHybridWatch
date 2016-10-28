package com.xyy.Gazella.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;

import com.xyy.Gazella.activity.SleepActivity;
import com.ysp.newband.BaseFragment;
import com.ysp.smartwatch.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/10/11.
 */

public class SleepFragment extends BaseFragment {

    @BindView(R.id.circle)
    ImageView circle;
    @BindView(R.id.tv_hour)
    TextView tvHour;
    @BindView(R.id.tv_min)
    TextView tvMin;
    @BindView(R.id.quality)
    TextView quality;
    @BindView(R.id.deep_time)
    TextView deepTime;
    @BindView(R.id.low_time)
    TextView lowTime;
    @BindView(R.id.wake_time)
    TextView wakeTime;
    @BindView(R.id.details)
    TextView details;
    private View view;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        view = inflater.inflate(R.layout.fragment_sleep, container, false);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }


    private void initView() {
        final ViewGroup.LayoutParams params = circle.getLayoutParams();
        ViewTreeObserver vto = circle.getViewTreeObserver();
        vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                params.width = circle.getHeight();
                circle.setLayoutParams(params);
                return true;
            }
        });

    }

    @OnClick(R.id.circle)
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.circle:
                Intent intent = new Intent(getActivity(), SleepActivity.class);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.in_from_right, R.anim.out_righttoleft);
                break;
        }
    }
}

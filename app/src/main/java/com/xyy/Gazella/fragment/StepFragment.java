package com.xyy.Gazella.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.xyy.Gazella.activity.StepActivity;
import com.ysp.smartwatch.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/10/11.
 */

public class StepFragment extends Fragment {


    @BindView(R.id.image_but)
    ImageButton imageBut;
    @BindView(R.id.text)
    TextView text;
    private View view;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        view = inflater.inflate(R.layout.fragment_step, container, false);

        ButterKnife.bind(this, view);
        return view;
    }


    @OnClick({R.id.image_but, R.id.text})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.image_but:
                Intent intent = new Intent(getActivity(), StepActivity.class);
                startActivity(intent);
                break;
            case R.id.text:
                break;
        }
    }
}

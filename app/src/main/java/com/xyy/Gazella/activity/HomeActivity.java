package com.xyy.Gazella.activity;

/**
 * 主页
 */

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.xyy.Gazella.view.GuideView;
import com.ysp.newband.BaseActivity;
import com.ysp.smartwatch.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeActivity extends BaseActivity {

    @BindView(R.id.ll_time)
    LinearLayout llTime;
    @BindView(R.id.ll_notice)
    LinearLayout llNotice;
    @BindView(R.id.ll_healthy)
    LinearLayout llHealthy;

    private  GuideView guideView;
    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_home);

        ButterKnife.bind(this);

        // 使用图片
        final ImageView iv = new ImageView(this);
        iv.setImageResource(R.drawable.img_new_task_guide);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        iv.setLayoutParams(params);


        guideView = GuideView.Builder
                .newInstance(this)
                .setTargetView(llTime)//设置目标
                .setCustomGuideView(iv)
                .setDirction(GuideView.Direction.RIGHT)
                //  .setShape(GuideView.MyShape.CIRCULAR)
                .setBgColor(getResources().getColor(R.color.shadow))
                .setOnclickListener(new GuideView.OnClickCallback() {
                    @Override
                    public void onClickedGuideView() {
                        guideView.hide();
                    }
                })
                .build();
        guideView.show();

    }

    @OnClick({R.id.ll_time, R.id.ll_notice,R.id.ll_healthy})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_time:
                Intent timeIntent = new Intent(HomeActivity.this, TimeSynchronization.class);
                startActivity(timeIntent);
                overridePendingTransitionEnter(HomeActivity.this);
                break;
            case R.id.ll_notice:

                break;
            case  R.id.ll_healthy:
                Intent healthIntent = new Intent(HomeActivity.this, HealthyActivity.class);
                startActivity(healthIntent);
                overridePendingTransitionEnter(HomeActivity.this);
                break;






        }
    }
}

//package com.xyy.Gazella.utils;
//
//import android.app.Activity;
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.ImageView;
//
//import com.ysp.smartwatch.R;
//
//import butterknife.BindView;
//import butterknife.ButterKnife;
//import butterknife.OnClick;
//
///**
// * Created by Administrator on 2016/10/19.
// */
//
//public class GuideShowDialog extends Activity {
//    @BindView(R.id.guide_image)
//    ImageView guideImage;
//    private  int type;
//    private Intent intent;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.dialog_guide_show);
//        ButterKnife.bind(this);
//        type= this.getIntent().getIntExtra("type",0);
//        switch (type) {
//            case 1:
//                guideImage.setBackground(this.getResources().getDrawable(R.drawable.guide));
//                break;
//            case 2:
//                guideImage.setBackground(this.getResources().getDrawable(R.drawable.page09_guide));
//                break;
//        }
//    }
//
//    @OnClick(R.id.guide_image)
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case  R.id.guide_image:
//                this.finish();
//                this.overridePendingTransition(R.anim.zoomin, R.anim.zoomout);
//                break;
//        }
//    }
//}
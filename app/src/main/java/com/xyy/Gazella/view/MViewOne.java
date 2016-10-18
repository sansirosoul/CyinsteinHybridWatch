package com.xyy.Gazella.view;

import android.animation.ArgbEvaluator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.ysp.smartwatch.R;

/**
 * Created by Administrator on 2016/10/15.
 */

public class MViewOne extends View {
    private Paint mArcPaint, mCirclePaint, mTextPaint, mPaint;

    private float length;

    private float mRadius;

    private float mCircleXY;

    private float mSweepValue = 0;

    private String mShowText = "0%";

    private RectF mRectF;

    public MViewOne(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    public MViewOne(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public MViewOne(Context context) {
        super(context);
        initView();
    }

    private void initView() {
        mArcPaint = new Paint();
        mArcPaint.setStrokeWidth(20);
        mArcPaint.setAntiAlias(true);
        mArcPaint.setStyle(Paint.Style.STROKE);

        mCirclePaint = new Paint();

        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setColor(Color.RED);
        mTextPaint.setStrokeWidth(0);

        mPaint = new Paint();
        mPaint.setStrokeWidth(15);
        mPaint.setAntiAlias(true);
        mPaint.setColor(getResources().getColor(R.color.personalize1));
        mPaint.setStyle(Paint.Style.STROKE);


    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        length = w;
        mCircleXY = length / 2;
        mRadius = (float) (length * 0.5 / 2);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Bitmap bitmap =BitmapFactory.decodeResource(getResources(), R.drawable.page6_clock);
        setMeasuredDimension(bitmap.getWidth(),bitmap.getHeight());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Bitmap bitmap =BitmapFactory.decodeResource(getResources(), R.drawable.page6_clock);
        canvas.drawBitmap(bitmap,0,0,new Paint());

        length=bitmap.getWidth();
        // 画圆
        mRectF = new RectF((float) (length * 0.1), (float) (length * 0.1),
                (float) (length * 0.9), (float) (length * 0.9));
//        canvas.drawCircle(mCircleXY, mCircleXY, mRadius, mCirclePaint);
        // 画弧线
//        canvas.drawArc(mRectF, 270, 360, false, mPaint);


        canvas.rotate(-90, length / 2, length / 2);
//        SweepGradient sg = new SweepGradient(length/2,length/2,new int[]{Color.GREEN,Color.YELLOW,Color.RED},null);
        SweepGradient sg = new SweepGradient(length/2,length/2,new int[]{getResources().getColor(R.color.personalize1),getResources().getColor(R.color.personalize2)},null);
        mArcPaint.setShader(sg);
//        mArcPaint.setColor(getResources().getColor(R.color.personalize1));
        canvas.drawArc(mRectF, 0, mSweepValue, false, mArcPaint);

//        canvas.drawCircle(length/2,length/2,(float)(length * 0.5 / 2),mCirclePaint);
        // 绘制文字
        float textWidth = mTextPaint.measureText(mShowText);   //测量字体宽度，我们需要根据字体的宽度设置在圆环中间

//        canvas.drawText(mShowText, (int)(length/2-textWidth/2), (int)(length/2+textWidth/2) , mTextPaint);


    }

    public void setProgress(float mSweepValue) {
        float a = (float) mSweepValue;
        if (a != 0) {
            this.mSweepValue = (float) (360.0 * (a / 100.0));
            mShowText = mSweepValue + "%";
            Log.e("this.mSweepValue:", this.mSweepValue + "");
        } else {
//            this.mSweepValue = 25;
//            mShowText = 25 + "%";
        }

        invalidate();
    }

}

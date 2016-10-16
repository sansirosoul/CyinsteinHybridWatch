package com.xyy.Gazella.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.os.SystemClock;
import android.text.format.DateUtils;
import android.text.format.Time;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.ysp.smartwatch.R;

public class CustomAnalogClock extends View {

    private static final String TAG = CustomAnalogClock.class.getName();
    private Time mCalendar;
    private Drawable mHourHand;
    private Drawable mMinuteHand;
    private Drawable mSecondHand;
    private Drawable mDial;
    private int mDialWidth;
    private int mDialHeight;

    private final Handler mHandler = new Handler();
    private float mHour;
    private float mMinutes;
    private float mSecond;
    private boolean mChanged;
    private Paint mPaint;
    private Runnable mTicker;
    private boolean mTickerStopped = false;
    private boolean isStop = true;
    private float stop;
    private float move;
    private float downX;
    private float downY;
    private double angle = 0;
    private String currentNumber;
    private int h;
    private int w;
    private int x, y;  // 时钟中心点位置（相对于视图）
    private int ChangeTimeType;//改变时针或分针  1 :时针 ，2 :分针

    public CustomAnalogClock(Context context) {
        this(context, null);
    }

    public CustomAnalogClock(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomAnalogClock(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        Resources r = getContext().getResources();

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CustomAnalogClock, defStyle, 0);
        mDial = a.getDrawable(R.styleable.CustomAnalogClock_dial);
        mHourHand = a.getDrawable(R.styleable.CustomAnalogClock_hand_hour);
        mMinuteHand = a.getDrawable(R.styleable.CustomAnalogClock_hand_minute);
        //    mSecondHand = a.getDrawable(R.styleable.CustomAnalogClock_hand_second);


        if (mDial == null || mHourHand == null || mMinuteHand == null) {
            mDial = r.getDrawable(R.drawable.clock_dial);
            mHourHand = r.getDrawable(R.drawable.appwidget_clock_hour);
            mMinuteHand = r.getDrawable(R.drawable.appwidget_clock_minute);
            mSecondHand = r.getDrawable(R.drawable.appwidget_clock_second);
        }
        a.recycle();
        mDialWidth = mDial.getIntrinsicWidth();
        mDialHeight = mDial.getIntrinsicHeight();
        mPaint = new Paint();
        mPaint.setColor(Color.parseColor("#3399ff"));
        mPaint.setTypeface(Typeface.DEFAULT_BOLD);
        mPaint.setFakeBoldText(true);
        mPaint.setAntiAlias(true);
        if (mCalendar == null) {
            mCalendar = new Time();
        }
    }

    public void setChangeTime(int ChangeTimeType) {
        this.ChangeTimeType = ChangeTimeType;

    }


    private void onTimeChanged() {
        mCalendar.setToNow();// 取当前时间
        int hour = mCalendar.hour;
        int minute = mCalendar.minute;
        int second = mCalendar.second;
        // mDay = String.valueOf(mCalendar.year) + "-"
        // + String.valueOf(mCalendar.month + 1) + "-"
        // + String.valueOf(mCalendar.monthDay);
        // mWeek = this.getWeek(mCalendar.weekDay);

        mHour = hour + mMinutes / 60.0f + mSecond / 3600.0f;
        mMinutes = minute + second / 60.0f;
//        mSecond = second;
//        mHour = 12;
//        mMinutes = 0;
        // mSecond = second;
        if (isStop) stop = second;
        else mSecond = move;
        if (isStop) mSecond = second;
        else mSecond = stop;


        mChanged = true;

        updateContentDescription(mCalendar);
    }

    @Override
    protected void onAttachedToWindow() {
        mTickerStopped = false;
        super.onAttachedToWindow();

        /**
         * requests a tick on the next hard-second boundary
         */

        mTicker = new Runnable() {
            public void run() {
                if (mTickerStopped)
                    return;
                onTimeChanged();
                invalidate();
                long now = SystemClock.uptimeMillis();
                long next = now + (1000 - now % 1000);
                mHandler.postAtTime(mTicker, next);
            }
        };
        mTicker.run();

    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mTickerStopped = true;
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);

        int widthSize = MeasureSpec.getSize(widthMeasureSpec);

        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        float hScale = 1.0f;
        float vScale = 1.0f;

        if (widthMode != MeasureSpec.UNSPECIFIED && widthSize < mDialWidth) {
            hScale = (float) widthSize / (float) mDialWidth;
        }

        if (heightMode != MeasureSpec.UNSPECIFIED && heightSize < mDialHeight) {
            vScale = (float) heightSize / (float) mDialHeight;
        }

        float scale = Math.min(hScale, vScale);

        w = resolveSizeAndState((int) (mDialWidth * scale),
                widthMeasureSpec, 0);
        h = resolveSizeAndState((int) (mDialHeight * scale),
                heightMeasureSpec, 0);
        setMeasuredDimension(
                resolveSizeAndState((int) (mDialWidth * scale),
                        widthMeasureSpec, 0),
                resolveSizeAndState((int) (mDialHeight * scale),
                        heightMeasureSpec, 0));
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mChanged = true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        boolean changed = mChanged;

        if (changed) {
            mChanged = false;
        }

        int availableWidth = getRight() - getLeft();
        int availableHeight = getBottom() - getTop();

        x = availableWidth / 2;
        y = availableHeight / 2;

        final Drawable dial = mDial;
        int w = dial.getIntrinsicWidth();
        int h = dial.getIntrinsicHeight();

        // int dialWidth = w;
        int dialHeight = h;
        boolean scaled = false;

        if (availableWidth < w || availableHeight < h) {
            scaled = true;
            float scale = Math.min((float) availableWidth / (float) w,
                    (float) availableHeight / (float) h);
            canvas.save();
            canvas.scale(scale, scale, x, y);
        }

        if (changed) {
            dial.setBounds(x - (w / 2), y - (h / 2), x + (w / 2), y + (h / 2));
        }
        dial.draw(canvas);
        canvas.save();

        canvas.rotate(mHour / 12.0f * 360.0f, x, y);
        final Drawable hourHand = mHourHand;
        if (changed) {
            w = hourHand.getIntrinsicWidth();
            h = hourHand.getIntrinsicHeight();
            hourHand.setBounds(x - (w / 2), y - (h / 1), x + (w / 2), y
                    + (h / 2));
        }
        hourHand.draw(canvas);
        canvas.restore();

        canvas.save();

        canvas.rotate(mMinutes / 60.0f * 360.0f, x, y);
        final Drawable minuteHand = mMinuteHand;
        if (changed) {
            w = minuteHand.getIntrinsicWidth();
            h = minuteHand.getIntrinsicHeight();
            minuteHand.setBounds(x - (w / 2), y - (h / 1), x + (w / 2), y
                    + (h / 2));
        }
        minuteHand.draw(canvas);
        canvas.restore();

        canvas.save();

        canvas.rotate(mSecond / 60.0f * 360.0f, x, y);
//        final Drawable secondHand = mSecondHand;
//        if (changed) {
//            w = secondHand.getIntrinsicWidth();
//            h = secondHand.getIntrinsicHeight();
//            secondHand.setBounds(x - (w / 2), y - (h / 2), x + (w / 2), y
//                    + (h / 2));
//        }
//        secondHand.draw(canvas);
//        canvas.restore();

        if (scaled) {
            canvas.restore();
        }

//        Paint paint = new Paint();
//		paint.setColor(Color.RED);
//
//		canvas.drawLine(0, y, canvas.getWidth(), y, paint);
//
//		canvas.drawLine(x, 0, x, canvas.getHeight(), paint);

    }


    private void updateContentDescription(Time time) {
        final int flags = DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_24HOUR;
        String contentDescription = DateUtils.formatDateTime(getContext(),
                time.toMillis(false), flags);
        setContentDescription(contentDescription);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //  isStop = false;

                break;
            case MotionEvent.ACTION_MOVE:
                //  isStop = false;

                int rx = (int) event.getX() - x;
                int ry = -((int) event.getY() - y);
                Point point = new Point(rx, ry);
                int pos = MyDegreeAdapter.GetRadianByPos(point);
                Log.i(TAG, "POS" + String.valueOf(pos));
                if (ChangeTimeType == 1) {  //移动时针
                    pos = pos / 30;
                    mHour = pos;
                } else {
                    pos = pos / 6;
                    mMinutes = pos;
                }

                postInvalidate();
                break;
            case MotionEvent.ACTION_UP:
                isStop = true;
                break;
        }
        return true;
    }

    /**
     * @param dx
     * @param dy 根据事件坐标更新表示时间
     */
    public void calcDegree(int dx, int dy) {
        int rx = dx - x;
        int ry = -(dy - y);
        Point point = new Point(rx, ry);
        int a = MyDegreeAdapter.GetRadianByPos(point);

        Log.i("TAA", "AAAAAAAAA=========" + String.valueOf(a));
        a = a / 30;
        Log.i("TAA", "AAAAAAAAA=========" + String.valueOf(a));
        mHour = a;
    }
}

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
import android.view.MotionEvent;
import android.view.View;

import com.ysp.smartwatch.R;

public class AnalogClock extends View {

    private static final String TAG = AnalogClock.class.getName();
    private final Resources r;
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
    public int ChangeTimeType = 1;//改变时针或分针  1 :时针 ，2 :分针
    private ChangeTimeListener changetimelistener;  //监听时间变换
    private boolean isChangedTime = false;
    private Context mContext;
    private boolean isMinutestMove = false;
    private boolean isHourMove = false;

    public AnalogClock(Context context) {
        this(context, null);
    }

    public AnalogClock(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AnalogClock(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        r = getContext().getResources();

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CustomAnalogClock, defStyle, 0);
        mDial = a.getDrawable(R.styleable.CustomAnalogClock_dial);
        mHourHand = a.getDrawable(R.styleable.CustomAnalogClock_hand_hour);
        mMinuteHand = a.getDrawable(R.styleable.CustomAnalogClock_hand_minute);
        // mSecondHand = a.getDrawable(R.styleable.CustomAnalogClock_hand_second);


        if (mDial == null || mMinuteHand == null) {
            mDial = r.getDrawable(R.drawable.page12_biaopan);
            // mHourHand = r.getDrawable(R.drawable.page12_hour_selected);
            mMinuteHand = r.getDrawable(R.drawable.page12_minute_selected);
          //  mSecondHand = r.getDrawable(R.drawable.appwidget_clock_second);



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

    public void setChangeTimeListener(ChangeTimeListener changetimelistener) {
        this.changetimelistener = changetimelistener;
    }


    public void setChangeTimeType(int ChangeTimeType) {
        this.ChangeTimeType = ChangeTimeType;

    }

    public void setDialDrawable(int drawable) {
        mDial = r.getDrawable(drawable);
    }

    public  void setHourDrawable(int drawable){
        mHourHand = r.getDrawable(drawable);
    }

    public  void setMinuteDrawable(int drawable){
        mMinuteHand = r.getDrawable(drawable);
    }

    public int getChangeTimeType() {
        return this.ChangeTimeType;
    }

    public  int  getHourValue(){
        return  (int)mHour;
    }
    public  int  getMinuteValue(){
        return  (int)mMinutes;
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
        if (!isChangedTime) {
            if (isStop) stop = second;
            else mSecond = move;
            if (isStop) mSecond = second;
            else mSecond = stop;
            mChanged = true;

            updateContentDescription(mCalendar);
        }
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
                // onTimeChanged();
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
        if (!isChangedTime) {

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
        }
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

        int dialWidth = w;
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

        if (mHourHand != null) {
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
        }
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

    }

    private void updateContentDescription(Time time) {
        final int flags = DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_24HOUR;
        String contentDescription = DateUtils.formatDateTime(getContext(),
                time.toMillis(false), flags);
        setContentDescription(contentDescription);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int rx = (int) event.getX() - x;
        int ry = -((int) event.getY() - y);
        Point point = new Point(rx, ry);
        int Tiemvalue = MyDegreeAdapter.GetRadianByPos(point);

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                  int mmintes=(int)mMinutes;

                Tiemvalue = Tiemvalue / 6;
                if (Tiemvalue == mmintes||Tiemvalue+1 == mmintes||Tiemvalue +2== mmintes||Tiemvalue+3 == mmintes||Tiemvalue+4 == mmintes||
                        Tiemvalue -1== mmintes||Tiemvalue-2 == mmintes||Tiemvalue -3== mmintes||Tiemvalue-4 == mmintes)
                    isMinutestMove = true;
                else
                    isMinutestMove = false;
                break;

            case MotionEvent.ACTION_MOVE:

                if (ChangeTimeType == 1) {  //移动时针
                        Tiemvalue = Tiemvalue / 30;
                        mHour = Tiemvalue;
                } else {
                    if (isMinutestMove) {
                        Tiemvalue = Tiemvalue / 6;
                        mMinutes = Tiemvalue;
                    }
                }
                if (changetimelistener != null)
                    changetimelistener.ChangeTimeListener(Tiemvalue);
                isChangedTime = true;
                postInvalidate();
                break;
            case MotionEvent.ACTION_UP:

                break;
        }
        return true;
    }

    public int getHourTimeValue() {
        return (int) mHour;
    }

    /***
     * @param TimeType  1 是时针  , 2是分针
     * @param TimeValue 设置时间值
     */

    public void setTimeValue(int TimeType, int TimeValue) {
        if (TimeType == 1)
            mHour = TimeValue;
        else
            mMinutes = TimeValue;
        isChangedTime = true;
        postInvalidate();

    }

    public int getMinutesTimeValue() {

        return (int) mMinutes;
    }


    public interface ChangeTimeListener {
        void ChangeTimeListener(int TimeValue);
    }
}

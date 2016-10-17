package com.xyy.Gazella.utils;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Window;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.xyy.Gazella.activity.PersonActivity;
import com.ysp.smartwatch.R;

import butterknife.BindView;

/**
 * Created by Administrator on 2016/10/17.
 */

public class CalendarDialog extends Dialog {
    MaterialCalendarView cal;
    private Context context;

    public CalendarDialog(Context context) {
        super(context, R.style.dialog);
        this.context=context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.calendar_dialog);
        cal= (MaterialCalendarView) findViewById(R.id.cal);

        cal.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                if(selected){
                    PersonActivity.tvBirth.setText(date.getYear()+" - "+date.getMonth()+" - "+date.getDay());
                    PersonActivity.tvBirth.setTextColor(context.getResources().getColor(R.color.white));
                    dismiss();
                }
            }
        });
    }
}

// Generated code from Butter Knife. Do not modify!
package com.xyy.Gazella.activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.ysp.smartwatch.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class StepActivity_ViewBinding<T extends StepActivity> implements Unbinder {
  protected T target;

  private View view2131624170;

  private View view2131624171;

  private View view2131624172;

  private View view2131624092;

  private View view2131624093;

  private View view2131624094;

  @UiThread
  public StepActivity_ViewBinding(final T target, View source) {
    this.target = target;

    View view;
    target.widget = Utils.findRequiredViewAsType(source, R.id.calendarView, "field 'widget'", MaterialCalendarView.class);
    view = Utils.findRequiredView(source, R.id.btnExit, "field 'btnExit' and method 'onClick'");
    target.btnExit = Utils.castView(view, R.id.btnExit, "field 'btnExit'", LinearLayout.class);
    view2131624170 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.btnOpt, "field 'btnOpt' and method 'onClick'");
    target.btnOpt = Utils.castView(view, R.id.btnOpt, "field 'btnOpt'", Button.class);
    view2131624171 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.btnDate, "field 'btnDate' and method 'onClick'");
    target.btnDate = Utils.castView(view, R.id.btnDate, "field 'btnDate'", Button.class);
    view2131624172 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    target.TVTitle = Utils.findRequiredViewAsType(source, R.id.TVTitle, "field 'TVTitle'", TextView.class);
    view = Utils.findRequiredView(source, R.id.button_day, "field 'butDay' and method 'onClick'");
    target.butDay = Utils.castView(view, R.id.button_day, "field 'butDay'", Button.class);
    view2131624092 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.button_week, "field 'butWeek' and method 'onClick'");
    target.butWeek = Utils.castView(view, R.id.button_week, "field 'butWeek'", Button.class);
    view2131624093 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.button_month, "field 'butMonth' and method 'onClick'");
    target.butMonth = Utils.castView(view, R.id.button_month, "field 'butMonth'", Button.class);
    view2131624094 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    target.viewpager = Utils.findRequiredViewAsType(source, R.id.viewpager, "field 'viewpager'", ViewPager.class);
    target.llCheckDate = Utils.findRequiredViewAsType(source, R.id.ll_check_date, "field 'llCheckDate'", LinearLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    T target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");

    target.widget = null;
    target.btnExit = null;
    target.btnOpt = null;
    target.btnDate = null;
    target.TVTitle = null;
    target.butDay = null;
    target.butWeek = null;
    target.butMonth = null;
    target.viewpager = null;
    target.llCheckDate = null;

    view2131624170.setOnClickListener(null);
    view2131624170 = null;
    view2131624171.setOnClickListener(null);
    view2131624171 = null;
    view2131624172.setOnClickListener(null);
    view2131624172 = null;
    view2131624092.setOnClickListener(null);
    view2131624092 = null;
    view2131624093.setOnClickListener(null);
    view2131624093 = null;
    view2131624094.setOnClickListener(null);
    view2131624094 = null;

    this.target = null;
  }
}

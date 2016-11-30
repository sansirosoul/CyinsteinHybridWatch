// Generated code from Butter Knife. Do not modify!
package com.xyy.Gazella.fragment;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.github.mikephil.charting.charts.BarChart;
import com.ysp.smartwatch.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class StepWeekFragment_ViewBinding<T extends StepWeekFragment> implements Unbinder {
  protected T target;

  private View view2131427457;

  private View view2131427458;

  private View view2131427559;

  @UiThread
  public StepWeekFragment_ViewBinding(final T target, View source) {
    this.target = target;

    View view;
    target.mChart = Utils.findRequiredViewAsType(source, R.id.chart1, "field 'mChart'", BarChart.class);
    target.llDate = Utils.findRequiredViewAsType(source, R.id.ll_date, "field 'llDate'", LinearLayout.class);
    target.tvDate = Utils.findRequiredViewAsType(source, R.id.tv_date, "field 'tvDate'", TextView.class);
    view = Utils.findRequiredView(source, R.id.iv_left, "field 'ivLeft' and method 'onClick'");
    target.ivLeft = Utils.castView(view, R.id.iv_left, "field 'ivLeft'", ImageView.class);
    view2131427457 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.iv_right, "field 'ivRight' and method 'onClick'");
    target.ivRight = Utils.castView(view, R.id.iv_right, "field 'ivRight'", ImageView.class);
    view2131427458 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.ll_step_week, "field 'llStepWeek' and method 'onClick'");
    target.llStepWeek = Utils.castView(view, R.id.ll_step_week, "field 'llStepWeek'", LinearLayout.class);
    view2131427559 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    target.llSetpBata = Utils.findRequiredViewAsType(source, R.id.ll_setp_bata, "field 'llSetpBata'", LinearLayout.class);
    target.scrollView = Utils.findRequiredViewAsType(source, R.id.scrollView, "field 'scrollView'", ScrollView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    T target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");

    target.mChart = null;
    target.llDate = null;
    target.tvDate = null;
    target.ivLeft = null;
    target.ivRight = null;
    target.llStepWeek = null;
    target.llSetpBata = null;
    target.scrollView = null;

    view2131427457.setOnClickListener(null);
    view2131427457 = null;
    view2131427458.setOnClickListener(null);
    view2131427458 = null;
    view2131427559.setOnClickListener(null);
    view2131427559 = null;

    this.target = null;
  }
}

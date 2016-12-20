// Generated code from Butter Knife. Do not modify!
package com.xyy.Gazella.fragment;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
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

public class StepDayFragment_ViewBinding<T extends StepDayFragment> implements Unbinder {
  protected T target;

  private View view2131689648;

  private View view2131689649;

  private View view2131689789;

  @UiThread
  public StepDayFragment_ViewBinding(final T target, View source) {
    this.target = target;

    View view;
    target.mChart = Utils.findRequiredViewAsType(source, R.id.chart1, "field 'mChart'", BarChart.class);
    target.llDate = Utils.findRequiredViewAsType(source, R.id.ll_date, "field 'llDate'", LinearLayout.class);
    target.tvDate = Utils.findRequiredViewAsType(source, R.id.tv_date, "field 'tvDate'", TextView.class);
    view = Utils.findRequiredView(source, R.id.iv_left, "field 'ivLeft' and method 'onClick'");
    target.ivLeft = Utils.castView(view, R.id.iv_left, "field 'ivLeft'", LinearLayout.class);
    view2131689648 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.iv_right, "field 'ivRight' and method 'onClick'");
    target.ivRight = Utils.castView(view, R.id.iv_right, "field 'ivRight'", LinearLayout.class);
    view2131689649 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.ll_step_day, "field 'llStepDay' and method 'onClick'");
    target.llStepDay = Utils.castView(view, R.id.ll_step_day, "field 'llStepDay'", LinearLayout.class);
    view2131689789 = view;
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
    target.llStepDay = null;
    target.llSetpBata = null;
    target.scrollView = null;

    view2131689648.setOnClickListener(null);
    view2131689648 = null;
    view2131689649.setOnClickListener(null);
    view2131689649 = null;
    view2131689789.setOnClickListener(null);
    view2131689789 = null;

    this.target = null;
  }
}

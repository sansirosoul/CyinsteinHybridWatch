// Generated code from Butter Knife. Do not modify!
package com.xyy.Gazella.fragment;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.ysp.smartwatch.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class StepFragment_ViewBinding<T extends StepFragment> implements Unbinder {
  protected T target;

  private View view2131427534;

  @UiThread
  public StepFragment_ViewBinding(final T target, View source) {
    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.circle, "field 'circle' and method 'onClick'");
    target.circle = Utils.castView(view, R.id.circle, "field 'circle'", ImageView.class);
    view2131427534 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    target.stepNum = Utils.findRequiredViewAsType(source, R.id.step_num, "field 'stepNum'", TextView.class);
    target.stepTarget = Utils.findRequiredViewAsType(source, R.id.step_target, "field 'stepTarget'", TextView.class);
    target.time = Utils.findRequiredViewAsType(source, R.id.time, "field 'time'", TextView.class);
    target.distance = Utils.findRequiredViewAsType(source, R.id.distance, "field 'distance'", TextView.class);
    target.cal = Utils.findRequiredViewAsType(source, R.id.cal, "field 'cal'", TextView.class);
    target.details = Utils.findRequiredViewAsType(source, R.id.details, "field 'details'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    T target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");

    target.circle = null;
    target.stepNum = null;
    target.stepTarget = null;
    target.time = null;
    target.distance = null;
    target.cal = null;
    target.details = null;

    view2131427534.setOnClickListener(null);
    view2131427534 = null;

    this.target = null;
  }
}

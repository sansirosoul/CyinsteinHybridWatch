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

public class SleepFragment_ViewBinding<T extends SleepFragment> implements Unbinder {
  protected T target;

  private View view2131427534;

  @UiThread
  public SleepFragment_ViewBinding(final T target, View source) {
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
    target.tvHour = Utils.findRequiredViewAsType(source, R.id.tv_hour, "field 'tvHour'", TextView.class);
    target.tvMin = Utils.findRequiredViewAsType(source, R.id.tv_min, "field 'tvMin'", TextView.class);
    target.quality = Utils.findRequiredViewAsType(source, R.id.quality, "field 'quality'", TextView.class);
    target.deepTime = Utils.findRequiredViewAsType(source, R.id.deep_time, "field 'deepTime'", TextView.class);
    target.lowTime = Utils.findRequiredViewAsType(source, R.id.low_time, "field 'lowTime'", TextView.class);
    target.wakeTime = Utils.findRequiredViewAsType(source, R.id.wake_time, "field 'wakeTime'", TextView.class);
    target.details = Utils.findRequiredViewAsType(source, R.id.details, "field 'details'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    T target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");

    target.circle = null;
    target.tvHour = null;
    target.tvMin = null;
    target.quality = null;
    target.deepTime = null;
    target.lowTime = null;
    target.wakeTime = null;
    target.details = null;

    view2131427534.setOnClickListener(null);
    view2131427534 = null;

    this.target = null;
  }
}

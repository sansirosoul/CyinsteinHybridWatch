// Generated code from Butter Knife. Do not modify!
package com.xyy.Gazella.activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.ysp.smartwatch.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class HealthyActivity_ViewBinding<T extends HealthyActivity> implements Unbinder {
  protected T target;

  private View view2131427431;

  private View view2131427432;

  private View view2131427522;

  @UiThread
  public HealthyActivity_ViewBinding(final T target, View source) {
    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.step, "field 'step' and method 'onClick'");
    target.step = Utils.castView(view, R.id.step, "field 'step'", TextView.class);
    view2131427431 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.sleep, "field 'sleep' and method 'onClick'");
    target.sleep = Utils.castView(view, R.id.sleep, "field 'sleep'", TextView.class);
    view2131427432 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.btnExit, "field 'btnExit' and method 'onClick'");
    target.btnExit = Utils.castView(view, R.id.btnExit, "field 'btnExit'", Button.class);
    view2131427522 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    target.btnOpt = Utils.findRequiredViewAsType(source, R.id.btnOpt, "field 'btnOpt'", Button.class);
    target.TVTitle = Utils.findRequiredViewAsType(source, R.id.TVTitle, "field 'TVTitle'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    T target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");

    target.step = null;
    target.sleep = null;
    target.btnExit = null;
    target.btnOpt = null;
    target.TVTitle = null;

    view2131427431.setOnClickListener(null);
    view2131427431 = null;
    view2131427432.setOnClickListener(null);
    view2131427432 = null;
    view2131427522.setOnClickListener(null);
    view2131427522 = null;

    this.target = null;
  }
}

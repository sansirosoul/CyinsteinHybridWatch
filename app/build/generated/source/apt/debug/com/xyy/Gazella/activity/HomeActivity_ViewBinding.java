// Generated code from Butter Knife. Do not modify!
package com.xyy.Gazella.activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.LinearLayout;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.ysp.smartwatch.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class HomeActivity_ViewBinding<T extends HomeActivity> implements Unbinder {
  protected T target;

  private View view2131689616;

  private View view2131689619;

  private View view2131689621;

  private View view2131689622;

  private View view2131689623;

  private View view2131689624;

  @UiThread
  public HomeActivity_ViewBinding(final T target, View source) {
    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.ll_time, "field 'llTime' and method 'onClick'");
    target.llTime = Utils.castView(view, R.id.ll_time, "field 'llTime'", LinearLayout.class);
    view2131689616 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.ll_notice, "field 'llNotice' and method 'onClick'");
    target.llNotice = Utils.castView(view, R.id.ll_notice, "field 'llNotice'", LinearLayout.class);
    view2131689619 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.ll_healthy, "field 'llHealthy' and method 'onClick'");
    target.llHealthy = Utils.castView(view, R.id.ll_healthy, "field 'llHealthy'", LinearLayout.class);
    view2131689621 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.ll_settings, "field 'llSettings' and method 'onClick'");
    target.llSettings = Utils.castView(view, R.id.ll_settings, "field 'llSettings'", LinearLayout.class);
    view2131689622 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.ll_introduce, "field 'llIntroduce' and method 'onClick'");
    target.llIntroduce = Utils.castView(view, R.id.ll_introduce, "field 'llIntroduce'", LinearLayout.class);
    view2131689623 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.ll_other, "field 'llOther' and method 'onClick'");
    target.llOther = Utils.castView(view, R.id.ll_other, "field 'llOther'", LinearLayout.class);
    view2131689624 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    T target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");

    target.llTime = null;
    target.llNotice = null;
    target.llHealthy = null;
    target.llSettings = null;
    target.llIntroduce = null;
    target.llOther = null;

    view2131689616.setOnClickListener(null);
    view2131689616 = null;
    view2131689619.setOnClickListener(null);
    view2131689619 = null;
    view2131689621.setOnClickListener(null);
    view2131689621 = null;
    view2131689622.setOnClickListener(null);
    view2131689622 = null;
    view2131689623.setOnClickListener(null);
    view2131689623 = null;
    view2131689624.setOnClickListener(null);
    view2131689624 = null;

    this.target = null;
  }
}

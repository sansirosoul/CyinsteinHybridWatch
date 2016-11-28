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

  private View view2131624080;

  private View view2131624083;

  private View view2131624085;

  private View view2131624086;

  private View view2131624087;

  @UiThread
  public HomeActivity_ViewBinding(final T target, View source) {
    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.ll_time, "field 'llTime' and method 'onClick'");
    target.llTime = Utils.castView(view, R.id.ll_time, "field 'llTime'", LinearLayout.class);
    view2131624080 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.ll_notice, "field 'llNotice' and method 'onClick'");
    target.llNotice = Utils.castView(view, R.id.ll_notice, "field 'llNotice'", LinearLayout.class);
    view2131624083 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.ll_healthy, "field 'llHealthy' and method 'onClick'");
    target.llHealthy = Utils.castView(view, R.id.ll_healthy, "field 'llHealthy'", LinearLayout.class);
    view2131624085 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.ll_settings, "field 'llSettings' and method 'onClick'");
    target.llSettings = Utils.castView(view, R.id.ll_settings, "field 'llSettings'", LinearLayout.class);
    view2131624086 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.ll_introduce, "field 'llIntroduce' and method 'onClick'");
    target.llIntroduce = Utils.castView(view, R.id.ll_introduce, "field 'llIntroduce'", LinearLayout.class);
    view2131624087 = view;
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

    view2131624080.setOnClickListener(null);
    view2131624080 = null;
    view2131624083.setOnClickListener(null);
    view2131624083 = null;
    view2131624085.setOnClickListener(null);
    view2131624085 = null;
    view2131624086.setOnClickListener(null);
    view2131624086 = null;
    view2131624087.setOnClickListener(null);
    view2131624087 = null;

    this.target = null;
  }
}

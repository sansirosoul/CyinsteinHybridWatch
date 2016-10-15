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

  private View view2131427426;

  private View view2131427427;

  @UiThread
  public HomeActivity_ViewBinding(final T target, View source) {
    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.ll_time, "field 'llTime' and method 'onClick'");
    target.llTime = Utils.castView(view, R.id.ll_time, "field 'llTime'", LinearLayout.class);
    view2131427426 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.ll_notice, "field 'llNotice' and method 'onClick'");
    target.llNotice = Utils.castView(view, R.id.ll_notice, "field 'llNotice'", LinearLayout.class);
    view2131427427 = view;
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

    view2131427426.setOnClickListener(null);
    view2131427426 = null;
    view2131427427.setOnClickListener(null);
    view2131427427 = null;

    this.target = null;
  }
}

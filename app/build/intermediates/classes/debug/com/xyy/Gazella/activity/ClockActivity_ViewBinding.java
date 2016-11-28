// Generated code from Butter Knife. Do not modify!
package com.xyy.Gazella.activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.RelativeLayout;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.xyy.Gazella.view.ListViewForScrollView;
import com.ysp.smartwatch.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ClockActivity_ViewBinding<T extends ClockActivity> implements Unbinder {
  protected T target;

  private View view2131427483;

  private View view2131427484;

  @UiThread
  public ClockActivity_ViewBinding(final T target, View source) {
    this.target = target;

    View view;
    target.listview = Utils.findRequiredViewAsType(source, R.id.listview, "field 'listview'", ListViewForScrollView.class);
    view = Utils.findRequiredView(source, R.id.back, "field 'back' and method 'onClick'");
    target.back = Utils.castView(view, R.id.back, "field 'back'", RelativeLayout.class);
    view2131427483 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.add, "field 'add' and method 'onClick'");
    target.add = Utils.castView(view, R.id.add, "field 'add'", RelativeLayout.class);
    view2131427484 = view;
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

    target.listview = null;
    target.back = null;
    target.add = null;

    view2131427483.setOnClickListener(null);
    view2131427483 = null;
    view2131427484.setOnClickListener(null);
    view2131427484 = null;

    this.target = null;
  }
}

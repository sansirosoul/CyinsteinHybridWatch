// Generated code from Butter Knife. Do not modify!
package com.xyy.Gazella.activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.ysp.smartwatch.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class UpdateHardware_ViewBinding<T extends UpdateHardware> implements Unbinder {
  protected T target;

  private View view2131689751;

  private View view2131689917;

  @UiThread
  public UpdateHardware_ViewBinding(final T target, View source) {
    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.btnExit, "field 'btnExit' and method 'onClick'");
    target.btnExit = Utils.castView(view, R.id.btnExit, "field 'btnExit'", LinearLayout.class);
    view2131689751 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    target.TVTitle = Utils.findRequiredViewAsType(source, R.id.TVTitle, "field 'TVTitle'", TextView.class);
    target.watchVer = Utils.findRequiredViewAsType(source, R.id.watch_ver, "field 'watchVer'", TextView.class);
    target.watchSN = Utils.findRequiredViewAsType(source, R.id.watch_sn, "field 'watchSN'", TextView.class);
    target.appVer = Utils.findRequiredViewAsType(source, R.id.app_ver, "field 'appVer'", TextView.class);
    view = Utils.findRequiredView(source, R.id.update, "field 'update' and method 'onClick'");
    target.update = Utils.castView(view, R.id.update, "field 'update'", Button.class);
    view2131689917 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    target.battery = Utils.findRequiredViewAsType(source, R.id.battery, "field 'battery'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    T target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");

    target.btnExit = null;
    target.TVTitle = null;
    target.watchVer = null;
    target.watchSN = null;
    target.appVer = null;
    target.update = null;
    target.battery = null;

    view2131689751.setOnClickListener(null);
    view2131689751 = null;
    view2131689917.setOnClickListener(null);
    view2131689917 = null;

    this.target = null;
  }
}

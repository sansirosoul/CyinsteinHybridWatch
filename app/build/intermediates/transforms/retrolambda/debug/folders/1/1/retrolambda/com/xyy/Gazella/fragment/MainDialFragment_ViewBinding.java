// Generated code from Butter Knife. Do not modify!
package com.xyy.Gazella.fragment;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.xyy.Gazella.view.AnalogClock;
import com.ysp.smartwatch.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class MainDialFragment_ViewBinding<T extends MainDialFragment> implements Unbinder {
  protected T target;

  @UiThread
  public MainDialFragment_ViewBinding(T target, View source) {
    this.target = target;

    target.analogclock = Utils.findRequiredViewAsType(source, R.id.analogclock, "field 'analogclock'", AnalogClock.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    T target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");

    target.analogclock = null;

    this.target = null;
  }
}

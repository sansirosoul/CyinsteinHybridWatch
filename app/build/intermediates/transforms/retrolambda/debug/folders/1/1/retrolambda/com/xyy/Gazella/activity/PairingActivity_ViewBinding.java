// Generated code from Butter Knife. Do not modify!
package com.xyy.Gazella.activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.xyy.Gazella.view.AnalogClock;
import com.ysp.smartwatch.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class PairingActivity_ViewBinding<T extends PairingActivity> implements Unbinder {
  protected T target;

  @UiThread
  public PairingActivity_ViewBinding(T target, View source) {
    this.target = target;

    target.clock = Utils.findRequiredViewAsType(source, R.id.clock, "field 'clock'", AnalogClock.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    T target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");

    target.clock = null;

    this.target = null;
  }
}

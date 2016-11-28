// Generated code from Butter Knife. Do not modify!
package com.xyy.Gazella.activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.xyy.Gazella.view.PickerViewHour;
import com.xyy.Gazella.view.PickerViewMinute;
import com.ysp.smartwatch.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class EditClockActivity_ViewBinding<T extends EditClockActivity> implements Unbinder {
  protected T target;

  private View view2131427466;

  private View view2131427467;

  private View view2131427475;

  private View view2131427470;

  private View view2131427472;

  @UiThread
  public EditClockActivity_ViewBinding(final T target, View source) {
    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.cancel, "field 'cancel' and method 'onClick'");
    target.cancel = Utils.castView(view, R.id.cancel, "field 'cancel'", RelativeLayout.class);
    view2131427466 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.save, "field 'save' and method 'onClick'");
    target.save = Utils.castView(view, R.id.save, "field 'save'", RelativeLayout.class);
    view2131427467 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.del_clock, "field 'delClock' and method 'onClick'");
    target.delClock = Utils.castView(view, R.id.del_clock, "field 'delClock'", Button.class);
    view2131427475 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.rl_ringtime, "field 'rlRingtime' and method 'onClick'");
    target.rlRingtime = Utils.castView(view, R.id.rl_ringtime, "field 'rlRingtime'", RelativeLayout.class);
    view2131427470 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.rl_repeatrate, "field 'rlRepeatrate' and method 'onClick'");
    target.rlRepeatrate = Utils.castView(view, R.id.rl_repeatrate, "field 'rlRepeatrate'", RelativeLayout.class);
    view2131427472 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    target.pvHour = Utils.findRequiredViewAsType(source, R.id.pv_hour, "field 'pvHour'", PickerViewHour.class);
    target.pvMinute = Utils.findRequiredViewAsType(source, R.id.pv_minute, "field 'pvMinute'", PickerViewMinute.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    T target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");

    target.cancel = null;
    target.save = null;
    target.delClock = null;
    target.rlRingtime = null;
    target.rlRepeatrate = null;
    target.pvHour = null;
    target.pvMinute = null;

    view2131427466.setOnClickListener(null);
    view2131427466 = null;
    view2131427467.setOnClickListener(null);
    view2131427467 = null;
    view2131427475.setOnClickListener(null);
    view2131427475 = null;
    view2131427470.setOnClickListener(null);
    view2131427470 = null;
    view2131427472.setOnClickListener(null);
    view2131427472 = null;

    this.target = null;
  }
}

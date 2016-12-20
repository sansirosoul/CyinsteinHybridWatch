// Generated code from Butter Knife. Do not modify!
package com.xyy.Gazella.activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.ysp.smartwatch.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class TargetActivity_ViewBinding<T extends TargetActivity> implements Unbinder {
  protected T target;

  private View view2131689751;

  private View view2131689752;

  private View view2131689754;

  private View view2131689644;

  private View view2131689645;

  @UiThread
  public TargetActivity_ViewBinding(final T target, View source) {
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
    view = Utils.findRequiredView(source, R.id.btnOpt, "field 'btnOpt' and method 'onClick'");
    target.btnOpt = Utils.castView(view, R.id.btnOpt, "field 'btnOpt'", Button.class);
    view2131689752 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.TVTitle, "field 'TVTitle' and method 'onClick'");
    target.TVTitle = Utils.castView(view, R.id.TVTitle, "field 'TVTitle'", TextView.class);
    view2131689754 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    target.targetRunSeekBar = Utils.findRequiredViewAsType(source, R.id.target_run_SeekBar, "field 'targetRunSeekBar'", SeekBar.class);
    target.targetSleepSeekBar = Utils.findRequiredViewAsType(source, R.id.target_sleep_SeekBar, "field 'targetSleepSeekBar'", SeekBar.class);
    target.targetWalkText = Utils.findRequiredViewAsType(source, R.id.target_walk_text, "field 'targetWalkText'", TextView.class);
    target.targetSleepHourText = Utils.findRequiredViewAsType(source, R.id.target_sleep_hour_text, "field 'targetSleepHourText'", TextView.class);
    target.targetSleepMinuteText = Utils.findRequiredViewAsType(source, R.id.target_sleep_minute_text, "field 'targetSleepMinuteText'", TextView.class);
    view = Utils.findRequiredView(source, R.id.but_save, "field 'butSave' and method 'onClick'");
    target.butSave = Utils.castView(view, R.id.but_save, "field 'butSave'", Button.class);
    view2131689644 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.but_default, "field 'butDefault' and method 'onClick'");
    target.butDefault = Utils.castView(view, R.id.but_default, "field 'butDefault'", Button.class);
    view2131689645 = view;
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

    target.btnExit = null;
    target.btnOpt = null;
    target.TVTitle = null;
    target.targetRunSeekBar = null;
    target.targetSleepSeekBar = null;
    target.targetWalkText = null;
    target.targetSleepHourText = null;
    target.targetSleepMinuteText = null;
    target.butSave = null;
    target.butDefault = null;

    view2131689751.setOnClickListener(null);
    view2131689751 = null;
    view2131689752.setOnClickListener(null);
    view2131689752 = null;
    view2131689754.setOnClickListener(null);
    view2131689754 = null;
    view2131689644.setOnClickListener(null);
    view2131689644 = null;
    view2131689645.setOnClickListener(null);
    view2131689645 = null;

    this.target = null;
  }
}

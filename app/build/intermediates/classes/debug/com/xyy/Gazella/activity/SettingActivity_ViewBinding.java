// Generated code from Butter Knife. Do not modify!
package com.xyy.Gazella.activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.ysp.smartwatch.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class SettingActivity_ViewBinding<T extends SettingActivity> implements Unbinder {
  protected T target;

  private View view2131427522;

  private View view2131427627;

  private View view2131427628;

  private View view2131427629;

  private View view2131427630;

  private View view2131427631;

  private View view2131427632;

  private View view2131427633;

  private View view2131427634;

  private View view2131427637;

  private View view2131427639;

  private View view2131427641;

  @UiThread
  public SettingActivity_ViewBinding(final T target, View source) {
    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.btnExit, "field 'btnExit' and method 'onClick'");
    target.btnExit = Utils.castView(view, R.id.btnExit, "field 'btnExit'", Button.class);
    view2131427522 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    target.TVTitle = Utils.findRequiredViewAsType(source, R.id.TVTitle, "field 'TVTitle'", TextView.class);
    view = Utils.findRequiredView(source, R.id.rl_user_setting, "field 'rlUserSetting' and method 'onClick'");
    target.rlUserSetting = Utils.castView(view, R.id.rl_user_setting, "field 'rlUserSetting'", RelativeLayout.class);
    view2131427627 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.rl_update_hardware, "field 'rlUpdateHardware' and method 'onClick'");
    target.rlUpdateHardware = Utils.castView(view, R.id.rl_update_hardware, "field 'rlUpdateHardware'", RelativeLayout.class);
    view2131427628 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.rl_change_watch, "field 'rlChangeWatch' and method 'onClick'");
    target.rlChangeWatch = Utils.castView(view, R.id.rl_change_watch, "field 'rlChangeWatch'", RelativeLayout.class);
    view2131427629 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.rl_rename_watch, "field 'rlRenameWatch' and method 'onClick'");
    target.rlRenameWatch = Utils.castView(view, R.id.rl_rename_watch, "field 'rlRenameWatch'", RelativeLayout.class);
    view2131427630 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.rl_clock, "field 'rlClock' and method 'onClick'");
    target.rlClock = Utils.castView(view, R.id.rl_clock, "field 'rlClock'", RelativeLayout.class);
    view2131427631 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.rl_clean_watch, "field 'rlCleanWatch' and method 'onClick'");
    target.rlCleanWatch = Utils.castView(view, R.id.rl_clean_watch, "field 'rlCleanWatch'", RelativeLayout.class);
    view2131427632 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.rl_clean_phone, "field 'rlCleanPhone' and method 'onClick'");
    target.rlCleanPhone = Utils.castView(view, R.id.rl_clean_phone, "field 'rlCleanPhone'", RelativeLayout.class);
    view2131427633 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.rl_anti_lost, "field 'rlAntiLost' and method 'onClick'");
    target.rlAntiLost = Utils.castView(view, R.id.rl_anti_lost, "field 'rlAntiLost'", RelativeLayout.class);
    view2131427634 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.rl_ble, "field 'rlBle' and method 'onClick'");
    target.rlBle = Utils.castView(view, R.id.rl_ble, "field 'rlBle'", RelativeLayout.class);
    view2131427637 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.rl_update_bsl, "field 'rlUpdateBsl' and method 'onClick'");
    target.rlUpdateBsl = Utils.castView(view, R.id.rl_update_bsl, "field 'rlUpdateBsl'", RelativeLayout.class);
    view2131427639 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.rl_target, "field 'rlTarget' and method 'onClick'");
    target.rlTarget = Utils.castView(view, R.id.rl_target, "field 'rlTarget'", RelativeLayout.class);
    view2131427641 = view;
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
    target.TVTitle = null;
    target.rlUserSetting = null;
    target.rlUpdateHardware = null;
    target.rlChangeWatch = null;
    target.rlRenameWatch = null;
    target.rlClock = null;
    target.rlCleanWatch = null;
    target.rlCleanPhone = null;
    target.rlAntiLost = null;
    target.rlBle = null;
    target.rlUpdateBsl = null;
    target.rlTarget = null;

    view2131427522.setOnClickListener(null);
    view2131427522 = null;
    view2131427627.setOnClickListener(null);
    view2131427627 = null;
    view2131427628.setOnClickListener(null);
    view2131427628 = null;
    view2131427629.setOnClickListener(null);
    view2131427629 = null;
    view2131427630.setOnClickListener(null);
    view2131427630 = null;
    view2131427631.setOnClickListener(null);
    view2131427631 = null;
    view2131427632.setOnClickListener(null);
    view2131427632 = null;
    view2131427633.setOnClickListener(null);
    view2131427633 = null;
    view2131427634.setOnClickListener(null);
    view2131427634 = null;
    view2131427637.setOnClickListener(null);
    view2131427637 = null;
    view2131427639.setOnClickListener(null);
    view2131427639 = null;
    view2131427641.setOnClickListener(null);
    view2131427641 = null;

    this.target = null;
  }
}

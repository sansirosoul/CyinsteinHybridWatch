// Generated code from Butter Knife. Do not modify!
package com.xyy.Gazella.activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.xyy.Gazella.view.SwitchView;
import com.ysp.smartwatch.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class SettingActivity_ViewBinding<T extends SettingActivity> implements Unbinder {
  protected T target;

  private View view2131689751;

  private View view2131689864;

  private View view2131689865;

  private View view2131689866;

  private View view2131689867;

  private View view2131689868;

  private View view2131689869;

  private View view2131689870;

  private View view2131689878;

  private View view2131689871;

  private View view2131689873;

  private View view2131689875;

  @UiThread
  public SettingActivity_ViewBinding(final T target, View source) {
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
    view = Utils.findRequiredView(source, R.id.rl_user_setting, "field 'rlUserSetting' and method 'onClick'");
    target.rlUserSetting = Utils.castView(view, R.id.rl_user_setting, "field 'rlUserSetting'", RelativeLayout.class);
    view2131689864 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.rl_update_hardware, "field 'rlUpdateHardware' and method 'onClick'");
    target.rlUpdateHardware = Utils.castView(view, R.id.rl_update_hardware, "field 'rlUpdateHardware'", RelativeLayout.class);
    view2131689865 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.rl_change_watch, "field 'rlChangeWatch' and method 'onClick'");
    target.rlChangeWatch = Utils.castView(view, R.id.rl_change_watch, "field 'rlChangeWatch'", RelativeLayout.class);
    view2131689866 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.rl_rename_watch, "field 'rlRenameWatch' and method 'onClick'");
    target.rlRenameWatch = Utils.castView(view, R.id.rl_rename_watch, "field 'rlRenameWatch'", RelativeLayout.class);
    view2131689867 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.rl_clock, "field 'rlClock' and method 'onClick'");
    target.rlClock = Utils.castView(view, R.id.rl_clock, "field 'rlClock'", RelativeLayout.class);
    view2131689868 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.rl_clean_watch, "field 'rlCleanWatch' and method 'onClick'");
    target.rlCleanWatch = Utils.castView(view, R.id.rl_clean_watch, "field 'rlCleanWatch'", RelativeLayout.class);
    view2131689869 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.rl_clean_phone, "field 'rlCleanPhone' and method 'onClick'");
    target.rlCleanPhone = Utils.castView(view, R.id.rl_clean_phone, "field 'rlCleanPhone'", RelativeLayout.class);
    view2131689870 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.rl_target, "field 'rlTarget' and method 'onClick'");
    target.rlTarget = Utils.castView(view, R.id.rl_target, "field 'rlTarget'", RelativeLayout.class);
    view2131689878 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.rl_search_watch, "field 'rlSearchWatch' and method 'onClick'");
    target.rlSearchWatch = Utils.castView(view, R.id.rl_search_watch, "field 'rlSearchWatch'", RelativeLayout.class);
    view2131689871 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.rl_close_bluetooth, "field 'rlCloseBluetooth' and method 'onClick'");
    target.rlCloseBluetooth = Utils.castView(view, R.id.rl_close_bluetooth, "field 'rlCloseBluetooth'", RelativeLayout.class);
    view2131689873 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    target.vSwitch = Utils.findRequiredViewAsType(source, R.id.v_switch, "field 'vSwitch'", SwitchView.class);
    view = Utils.findRequiredView(source, R.id.rl_update_bsl, "method 'onClick'");
    view2131689875 = view;
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
    target.rlTarget = null;
    target.rlSearchWatch = null;
    target.rlCloseBluetooth = null;
    target.vSwitch = null;

    view2131689751.setOnClickListener(null);
    view2131689751 = null;
    view2131689864.setOnClickListener(null);
    view2131689864 = null;
    view2131689865.setOnClickListener(null);
    view2131689865 = null;
    view2131689866.setOnClickListener(null);
    view2131689866 = null;
    view2131689867.setOnClickListener(null);
    view2131689867 = null;
    view2131689868.setOnClickListener(null);
    view2131689868 = null;
    view2131689869.setOnClickListener(null);
    view2131689869 = null;
    view2131689870.setOnClickListener(null);
    view2131689870 = null;
    view2131689878.setOnClickListener(null);
    view2131689878 = null;
    view2131689871.setOnClickListener(null);
    view2131689871 = null;
    view2131689873.setOnClickListener(null);
    view2131689873 = null;
    view2131689875.setOnClickListener(null);
    view2131689875 = null;

    this.target = null;
  }
}

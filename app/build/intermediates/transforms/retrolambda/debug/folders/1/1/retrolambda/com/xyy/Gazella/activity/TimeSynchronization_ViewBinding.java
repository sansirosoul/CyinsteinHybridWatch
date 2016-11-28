// Generated code from Butter Knife. Do not modify!
package com.xyy.Gazella.activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.xyy.Gazella.view.MyViewPage;
import com.ysp.smartwatch.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class TimeSynchronization_ViewBinding<T extends TimeSynchronization> implements Unbinder {
  protected T target;

  private View view2131427459;

  private View view2131427460;

  private View view2131427461;

  private View view2131427462;

  private View view2131427463;

  private View view2131427464;

  private View view2131427465;

  private View view2131427522;

  private View view2131427523;

  private View view2131427525;

  private View view2131427457;

  private View view2131427458;

  @UiThread
  public TimeSynchronization_ViewBinding(final T target, View source) {
    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.but_reduce, "field 'butReduce' and method 'onClick'");
    target.butReduce = Utils.castView(view, R.id.but_reduce, "field 'butReduce'", ImageButton.class);
    view2131427459 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.but_add, "field 'butAdd' and method 'onClick'");
    target.butAdd = Utils.castView(view, R.id.but_add, "field 'butAdd'", ImageButton.class);
    view2131427460 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.but_hour, "field 'butHour' and method 'onClick'");
    target.butHour = Utils.castView(view, R.id.but_hour, "field 'butHour'", Button.class);
    view2131427461 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.but_muinutes, "field 'butMuinutes' and method 'onClick'");
    target.butMuinutes = Utils.castView(view, R.id.but_muinutes, "field 'butMuinutes'", Button.class);
    view2131427462 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.but_second, "field 'butSecond' and method 'onClick'");
    target.butSecond = Utils.castView(view, R.id.but_second, "field 'butSecond'", Button.class);
    view2131427463 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.but_reset, "field 'butReset' and method 'onClick'");
    target.butReset = Utils.castView(view, R.id.but_reset, "field 'butReset'", Button.class);
    view2131427464 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.but_synchronization, "field 'butSynchronization' and method 'onClick'");
    target.butSynchronization = Utils.castView(view, R.id.but_synchronization, "field 'butSynchronization'", Button.class);
    view2131427465 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    target.activityTimeSynchronization = Utils.findRequiredViewAsType(source, R.id.activity_time_synchronization, "field 'activityTimeSynchronization'", LinearLayout.class);
    view = Utils.findRequiredView(source, R.id.btnExit, "field 'btnExit' and method 'onClick'");
    target.btnExit = Utils.castView(view, R.id.btnExit, "field 'btnExit'", Button.class);
    view2131427522 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.btnOpt, "field 'btnOpt' and method 'onClick'");
    target.btnOpt = Utils.castView(view, R.id.btnOpt, "field 'btnOpt'", Button.class);
    view2131427523 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.TVTitle, "field 'TVTitle' and method 'onClick'");
    target.TVTitle = Utils.castView(view, R.id.TVTitle, "field 'TVTitle'", TextView.class);
    view2131427525 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.iv_left, "field 'ivLeft' and method 'onClick'");
    target.ivLeft = Utils.castView(view, R.id.iv_left, "field 'ivLeft'", ImageView.class);
    view2131427457 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.iv_right, "field 'ivRight' and method 'onClick'");
    target.ivRight = Utils.castView(view, R.id.iv_right, "field 'ivRight'", ImageView.class);
    view2131427458 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    target.viewpager = Utils.findRequiredViewAsType(source, R.id.viewpager, "field 'viewpager'", MyViewPage.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    T target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");

    target.butReduce = null;
    target.butAdd = null;
    target.butHour = null;
    target.butMuinutes = null;
    target.butSecond = null;
    target.butReset = null;
    target.butSynchronization = null;
    target.activityTimeSynchronization = null;
    target.btnExit = null;
    target.btnOpt = null;
    target.TVTitle = null;
    target.ivLeft = null;
    target.ivRight = null;
    target.viewpager = null;

    view2131427459.setOnClickListener(null);
    view2131427459 = null;
    view2131427460.setOnClickListener(null);
    view2131427460 = null;
    view2131427461.setOnClickListener(null);
    view2131427461 = null;
    view2131427462.setOnClickListener(null);
    view2131427462 = null;
    view2131427463.setOnClickListener(null);
    view2131427463 = null;
    view2131427464.setOnClickListener(null);
    view2131427464 = null;
    view2131427465.setOnClickListener(null);
    view2131427465 = null;
    view2131427522.setOnClickListener(null);
    view2131427522 = null;
    view2131427523.setOnClickListener(null);
    view2131427523 = null;
    view2131427525.setOnClickListener(null);
    view2131427525 = null;
    view2131427457.setOnClickListener(null);
    view2131427457 = null;
    view2131427458.setOnClickListener(null);
    view2131427458 = null;

    this.target = null;
  }
}

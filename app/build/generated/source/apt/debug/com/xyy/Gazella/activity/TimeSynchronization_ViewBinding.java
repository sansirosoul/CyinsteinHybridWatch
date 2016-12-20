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

  private View view2131689650;

  private View view2131689651;

  private View view2131689652;

  private View view2131689653;

  private View view2131689654;

  private View view2131689655;

  private View view2131689656;

  private View view2131689751;

  private View view2131689752;

  private View view2131689754;

  private View view2131689648;

  private View view2131689649;

  @UiThread
  public TimeSynchronization_ViewBinding(final T target, View source) {
    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.but_reduce, "field 'butReduce' and method 'onClick'");
    target.butReduce = Utils.castView(view, R.id.but_reduce, "field 'butReduce'", ImageButton.class);
    view2131689650 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.but_add, "field 'butAdd' and method 'onClick'");
    target.butAdd = Utils.castView(view, R.id.but_add, "field 'butAdd'", ImageButton.class);
    view2131689651 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.but_hour, "field 'butHour' and method 'onClick'");
    target.butHour = Utils.castView(view, R.id.but_hour, "field 'butHour'", Button.class);
    view2131689652 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.but_muinutes, "field 'butMuinutes' and method 'onClick'");
    target.butMuinutes = Utils.castView(view, R.id.but_muinutes, "field 'butMuinutes'", Button.class);
    view2131689653 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.but_second, "field 'butSecond' and method 'onClick'");
    target.butSecond = Utils.castView(view, R.id.but_second, "field 'butSecond'", Button.class);
    view2131689654 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.but_reset, "field 'butReset' and method 'onClick'");
    target.butReset = Utils.castView(view, R.id.but_reset, "field 'butReset'", Button.class);
    view2131689655 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.but_synchronization, "field 'butSynchronization' and method 'onClick'");
    target.butSynchronization = Utils.castView(view, R.id.but_synchronization, "field 'butSynchronization'", Button.class);
    view2131689656 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
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
    view = Utils.findRequiredView(source, R.id.iv_left, "field 'ivLeft' and method 'onClick'");
    target.ivLeft = Utils.castView(view, R.id.iv_left, "field 'ivLeft'", ImageView.class);
    view2131689648 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.iv_right, "field 'ivRight' and method 'onClick'");
    target.ivRight = Utils.castView(view, R.id.iv_right, "field 'ivRight'", ImageView.class);
    view2131689649 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    target.viewpager = Utils.findRequiredViewAsType(source, R.id.viewpager, "field 'viewpager'", MyViewPage.class);
    target.activityTimeSynchronization = Utils.findRequiredViewAsType(source, R.id.activity_time_synchronization, "field 'activityTimeSynchronization'", LinearLayout.class);
    target.tvHint = Utils.findRequiredViewAsType(source, R.id.tv_hint, "field 'tvHint'", TextView.class);
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
    target.btnExit = null;
    target.btnOpt = null;
    target.TVTitle = null;
    target.ivLeft = null;
    target.ivRight = null;
    target.viewpager = null;
    target.activityTimeSynchronization = null;
    target.tvHint = null;

    view2131689650.setOnClickListener(null);
    view2131689650 = null;
    view2131689651.setOnClickListener(null);
    view2131689651 = null;
    view2131689652.setOnClickListener(null);
    view2131689652 = null;
    view2131689653.setOnClickListener(null);
    view2131689653 = null;
    view2131689654.setOnClickListener(null);
    view2131689654 = null;
    view2131689655.setOnClickListener(null);
    view2131689655 = null;
    view2131689656.setOnClickListener(null);
    view2131689656 = null;
    view2131689751.setOnClickListener(null);
    view2131689751 = null;
    view2131689752.setOnClickListener(null);
    view2131689752 = null;
    view2131689754.setOnClickListener(null);
    view2131689754 = null;
    view2131689648.setOnClickListener(null);
    view2131689648 = null;
    view2131689649.setOnClickListener(null);
    view2131689649 = null;

    this.target = null;
  }
}

// Generated code from Butter Knife. Do not modify!
package com.xyy.Gazella.activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ToggleButton;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.ysp.smartwatch.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class UserSetting_ViewBinding<T extends UserSetting> implements Unbinder {
  protected T target;

  private View view2131427599;

  private View view2131427601;

  private View view2131427605;

  private View view2131427607;

  private View view2131427483;

  private View view2131427467;

  @UiThread
  public UserSetting_ViewBinding(final T target, View source) {
    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.head, "field 'head' and method 'onClick'");
    target.head = Utils.castView(view, R.id.head, "field 'head'", ImageView.class);
    view2131427599 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    target.edName = Utils.findRequiredViewAsType(source, R.id.ed_name, "field 'edName'", EditText.class);
    target.tvBirth = Utils.findRequiredViewAsType(source, R.id.tv_birth, "field 'tvBirth'", TextView.class);
    view = Utils.findRequiredView(source, R.id.ll_birth, "field 'llBirth' and method 'onClick'");
    target.llBirth = Utils.castView(view, R.id.ll_birth, "field 'llBirth'", LinearLayout.class);
    view2131427601 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    target.tgMale = Utils.findRequiredViewAsType(source, R.id.tg_male, "field 'tgMale'", ToggleButton.class);
    target.tgFemale = Utils.findRequiredViewAsType(source, R.id.tg_female, "field 'tgFemale'", ToggleButton.class);
    target.tvHeight = Utils.findRequiredViewAsType(source, R.id.tv_height, "field 'tvHeight'", TextView.class);
    view = Utils.findRequiredView(source, R.id.ll_height, "field 'llHeight' and method 'onClick'");
    target.llHeight = Utils.castView(view, R.id.ll_height, "field 'llHeight'", LinearLayout.class);
    view2131427605 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    target.tvWeight = Utils.findRequiredViewAsType(source, R.id.tv_weight, "field 'tvWeight'", TextView.class);
    view = Utils.findRequiredView(source, R.id.ll_weight, "field 'llWeight' and method 'onClick'");
    target.llWeight = Utils.castView(view, R.id.ll_weight, "field 'llWeight'", LinearLayout.class);
    view2131427607 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.back, "field 'back' and method 'onClick'");
    target.back = Utils.castView(view, R.id.back, "field 'back'", RelativeLayout.class);
    view2131427483 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.save, "field 'save' and method 'onClick'");
    target.save = Utils.castView(view, R.id.save, "field 'save'", ImageView.class);
    view2131427467 = view;
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

    target.head = null;
    target.edName = null;
    target.tvBirth = null;
    target.llBirth = null;
    target.tgMale = null;
    target.tgFemale = null;
    target.tvHeight = null;
    target.llHeight = null;
    target.tvWeight = null;
    target.llWeight = null;
    target.back = null;
    target.save = null;

    view2131427599.setOnClickListener(null);
    view2131427599 = null;
    view2131427601.setOnClickListener(null);
    view2131427601 = null;
    view2131427605.setOnClickListener(null);
    view2131427605 = null;
    view2131427607.setOnClickListener(null);
    view2131427607 = null;
    view2131427483.setOnClickListener(null);
    view2131427483 = null;
    view2131427467.setOnClickListener(null);
    view2131427467 = null;

    this.target = null;
  }
}

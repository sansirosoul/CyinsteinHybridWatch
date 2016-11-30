// Generated code from Butter Knife. Do not modify!
package com.xyy.Gazella.activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ToggleButton;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.ysp.smartwatch.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class PersonActivity_ViewBinding<T extends PersonActivity> implements Unbinder {
  protected T target;

  private View view2131427601;

  private View view2131427605;

  private View view2131427607;

  private View view2131427483;

  private View view2131427609;

  private View view2131427599;

  @UiThread
  public PersonActivity_ViewBinding(final T target, View source) {
    this.target = target;

    View view;
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
    view = Utils.findRequiredView(source, R.id.ll_height, "field 'llHeight' and method 'onClick'");
    target.llHeight = Utils.castView(view, R.id.ll_height, "field 'llHeight'", LinearLayout.class);
    view2131427605 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
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
    target.back = Utils.castView(view, R.id.back, "field 'back'", Button.class);
    view2131427483 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.go, "field 'go' and method 'onClick'");
    target.go = Utils.castView(view, R.id.go, "field 'go'", Button.class);
    view2131427609 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
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
  }

  @Override
  @CallSuper
  public void unbind() {
    T target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");

    target.llBirth = null;
    target.tgMale = null;
    target.tgFemale = null;
    target.llHeight = null;
    target.llWeight = null;
    target.back = null;
    target.go = null;
    target.head = null;
    target.edName = null;

    view2131427601.setOnClickListener(null);
    view2131427601 = null;
    view2131427605.setOnClickListener(null);
    view2131427605 = null;
    view2131427607.setOnClickListener(null);
    view2131427607 = null;
    view2131427483.setOnClickListener(null);
    view2131427483 = null;
    view2131427609.setOnClickListener(null);
    view2131427609 = null;
    view2131427599.setOnClickListener(null);
    view2131427599 = null;

    this.target = null;
  }
}

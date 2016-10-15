// Generated code from Butter Knife. Do not modify!
package com.xyy.Gazella.fragment;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.ysp.smartwatch.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class StepFragment_ViewBinding<T extends StepFragment> implements Unbinder {
  protected T target;

  private View view2131427412;

  private View view2131427425;

  @UiThread
  public StepFragment_ViewBinding(final T target, View source) {
    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.image_but, "field 'imageBut' and method 'onClick'");
    target.imageBut = Utils.castView(view, R.id.image_but, "field 'imageBut'", ImageButton.class);
    view2131427412 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.text, "field 'text' and method 'onClick'");
    target.text = Utils.castView(view, R.id.text, "field 'text'", TextView.class);
    view2131427425 = view;
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

    target.imageBut = null;
    target.text = null;

    view2131427412.setOnClickListener(null);
    view2131427412 = null;
    view2131427425.setOnClickListener(null);
    view2131427425 = null;

    this.target = null;
  }
}

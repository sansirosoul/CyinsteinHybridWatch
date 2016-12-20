// Generated code from Butter Knife. Do not modify!
package com.xyy.Gazella.activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.ysp.smartwatch.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class HelpActivity_ViewBinding<T extends HelpActivity> implements Unbinder {
  protected T target;

  private View view2131689751;

  @UiThread
  public HelpActivity_ViewBinding(final T target, View source) {
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
  }

  @Override
  @CallSuper
  public void unbind() {
    T target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");

    target.btnExit = null;
    target.TVTitle = null;

    view2131689751.setOnClickListener(null);
    view2131689751 = null;

    this.target = null;
  }
}

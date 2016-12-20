// Generated code from Butter Knife. Do not modify!
package com.xyy.Gazella.utils;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ImageView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.ysp.smartwatch.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class GuideShowDialog_ViewBinding<T extends GuideShowDialog> implements Unbinder {
  protected T target;

  private View view2131689764;

  @UiThread
  public GuideShowDialog_ViewBinding(final T target, View source) {
    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.guide_image, "field 'guideImage' and method 'onClick'");
    target.guideImage = Utils.castView(view, R.id.guide_image, "field 'guideImage'", ImageView.class);
    view2131689764 = view;
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

    target.guideImage = null;

    view2131689764.setOnClickListener(null);
    view2131689764 = null;

    this.target = null;
  }
}

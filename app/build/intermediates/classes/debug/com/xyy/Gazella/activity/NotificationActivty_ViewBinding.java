// Generated code from Butter Knife. Do not modify!
package com.xyy.Gazella.activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ToggleButton;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.xyy.Gazella.view.SwitchView;
import com.ysp.smartwatch.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class NotificationActivty_ViewBinding<T extends NotificationActivty> implements Unbinder {
  protected T target;

  private View view2131427522;

  @UiThread
  public NotificationActivty_ViewBinding(final T target, View source) {
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
    target.all = Utils.findRequiredViewAsType(source, R.id.all, "field 'all'", SwitchView.class);
    target.tel = Utils.findRequiredViewAsType(source, R.id.tel, "field 'tel'", ToggleButton.class);
    target.email = Utils.findRequiredViewAsType(source, R.id.email, "field 'email'", ToggleButton.class);
    target.twitter = Utils.findRequiredViewAsType(source, R.id.twitter, "field 'twitter'", ToggleButton.class);
    target.line = Utils.findRequiredViewAsType(source, R.id.line, "field 'line'", ToggleButton.class);
    target.qq = Utils.findRequiredViewAsType(source, R.id.qq, "field 'qq'", ToggleButton.class);
    target.facebook = Utils.findRequiredViewAsType(source, R.id.facebook, "field 'facebook'", ToggleButton.class);
    target.message = Utils.findRequiredViewAsType(source, R.id.message, "field 'message'", ToggleButton.class);
    target.skype = Utils.findRequiredViewAsType(source, R.id.skype, "field 'skype'", ToggleButton.class);
    target.wechat = Utils.findRequiredViewAsType(source, R.id.wechat, "field 'wechat'", ToggleButton.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    T target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");

    target.btnExit = null;
    target.TVTitle = null;
    target.all = null;
    target.tel = null;
    target.email = null;
    target.twitter = null;
    target.line = null;
    target.qq = null;
    target.facebook = null;
    target.message = null;
    target.skype = null;
    target.wechat = null;

    view2131427522.setOnClickListener(null);
    view2131427522 = null;

    this.target = null;
  }
}

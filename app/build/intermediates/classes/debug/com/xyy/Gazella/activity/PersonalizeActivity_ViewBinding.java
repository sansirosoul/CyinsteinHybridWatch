// Generated code from Butter Knife. Do not modify!
package com.xyy.Gazella.activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.xyy.Gazella.view.MViewOne;
import com.ysp.smartwatch.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class PersonalizeActivity_ViewBinding<T extends PersonalizeActivity> implements Unbinder {
  protected T target;

  @UiThread
  public PersonalizeActivity_ViewBinding(T target, View source) {
    this.target = target;

    target.num = Utils.findRequiredViewAsType(source, R.id.num, "field 'num'", TextView.class);
    target.circle = Utils.findRequiredViewAsType(source, R.id.circle, "field 'circle'", MViewOne.class);
    target.text = Utils.findRequiredViewAsType(source, R.id.text, "field 'text'", TextView.class);
    target.textName = Utils.findRequiredViewAsType(source, R.id.text_name, "field 'textName'", TextView.class);
    target.textSex = Utils.findRequiredViewAsType(source, R.id.text_sex, "field 'textSex'", TextView.class);
    target.textBirth = Utils.findRequiredViewAsType(source, R.id.text_Birth, "field 'textBirth'", TextView.class);
    target.textHeight = Utils.findRequiredViewAsType(source, R.id.text_height, "field 'textHeight'", TextView.class);
    target.textWeight = Utils.findRequiredViewAsType(source, R.id.text_weight, "field 'textWeight'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    T target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");

    target.num = null;
    target.circle = null;
    target.text = null;
    target.textName = null;
    target.textSex = null;
    target.textBirth = null;
    target.textHeight = null;
    target.textWeight = null;

    this.target = null;
  }
}

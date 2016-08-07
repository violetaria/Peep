// Generated code from Butter Knife. Do not modify!
package com.getlosthere.apps.peep.activities;

import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Finder;
import com.getlosthere.apps.peep.R;
import java.lang.IllegalStateException;
import java.lang.Object;
import java.lang.Override;

public class ComposeActivity_ViewBinding<T extends ComposeActivity> implements Unbinder {
  protected T target;

  public ComposeActivity_ViewBinding(T target, Finder finder, Object source) {
    this.target = target;

    target.tvLetterCount = finder.findRequiredViewAsType(source, R.id.tvLetterCount, "field 'tvLetterCount'", TextView.class);
    target.etBody = finder.findRequiredViewAsType(source, R.id.etPeepBody, "field 'etBody'", EditText.class);
    target.btnPeep = finder.findRequiredViewAsType(source, R.id.btnPeep, "field 'btnPeep'", Button.class);
  }

  @Override
  public void unbind() {
    T target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");

    target.tvLetterCount = null;
    target.etBody = null;
    target.btnPeep = null;

    this.target = null;
  }
}

// Generated code from Butter Knife. Do not modify!
package com.getlosthere.apps.peep.activities;

import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Finder;
import com.getlosthere.apps.peep.R;
import java.lang.IllegalStateException;
import java.lang.Object;
import java.lang.Override;

public class DetailActivity_ViewBinding<T extends DetailActivity> implements Unbinder {
  protected T target;

  public DetailActivity_ViewBinding(T target, Finder finder, Object source) {
    this.target = target;

    target.ivProfileImage = finder.findRequiredViewAsType(source, R.id.ivProfileImage, "field 'ivProfileImage'", ImageView.class);
    target.tvBody = finder.findRequiredViewAsType(source, R.id.tvBody, "field 'tvBody'", TextView.class);
    target.tvLikeCount = finder.findRequiredViewAsType(source, R.id.tvLikeCount, "field 'tvLikeCount'", TextView.class);
    target.tvScreenName = finder.findRequiredViewAsType(source, R.id.tvScreenName, "field 'tvScreenName'", TextView.class);
    target.tvPostDate = finder.findRequiredViewAsType(source, R.id.tvPostDate, "field 'tvPostDate'", TextView.class);
    target.tvUsername = finder.findRequiredViewAsType(source, R.id.tvUsername, "field 'tvUsername'", TextView.class);
  }

  @Override
  public void unbind() {
    T target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");

    target.ivProfileImage = null;
    target.tvBody = null;
    target.tvLikeCount = null;
    target.tvScreenName = null;
    target.tvPostDate = null;
    target.tvUsername = null;

    this.target = null;
  }
}

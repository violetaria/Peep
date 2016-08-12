// Generated code from Butter Knife. Do not modify!
package com.getlosthere.apps.peep.fragments;

import android.widget.ImageView;
import butterknife.internal.Finder;
import com.getlosthere.apps.peep.R;
import java.lang.Object;
import java.lang.Override;

public class UserTimelineFragment_ViewBinding<T extends UserTimelineFragment> extends TweetsListFragment_ViewBinding<T> {
  public UserTimelineFragment_ViewBinding(T target, Finder finder, Object source) {
    super(target, finder, source);

    target.ivProfileImage = finder.findRequiredViewAsType(source, R.id.ivProfileImage, "field 'ivProfileImage'", ImageView.class);
  }

  @Override
  public void unbind() {
    T target = this.target;
    super.unbind();

    target.ivProfileImage = null;
  }
}

// Generated code from Butter Knife. Do not modify!
package com.getlosthere.apps.peep.activities;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Finder;
import com.getlosthere.apps.peep.R;
import java.lang.IllegalStateException;
import java.lang.Object;
import java.lang.Override;

public class ProfileActivity_ViewBinding<T extends ProfileActivity> implements Unbinder {
  protected T target;

  public ProfileActivity_ViewBinding(T target, Finder finder, Object source) {
    this.target = target;

    target.mIvProfileImage = finder.findRequiredViewAsType(source, R.id.ivProfileImage, "field 'mIvProfileImage'", ImageView.class);
    target.mTvName = finder.findRequiredViewAsType(source, R.id.tvName, "field 'mTvName'", TextView.class);
    target.mTvTagline = finder.findRequiredViewAsType(source, R.id.tvTagline, "field 'mTvTagline'", TextView.class);
    target.mRlUserHeader = finder.findRequiredViewAsType(source, R.id.rlUserHeader, "field 'mRlUserHeader'", RelativeLayout.class);
    target.mTvFollwers = finder.findRequiredViewAsType(source, R.id.tvFollwers, "field 'mTvFollwers'", TextView.class);
    target.mTvFollowing = finder.findRequiredViewAsType(source, R.id.tvFollowing, "field 'mTvFollowing'", TextView.class);
    target.mLlCounts = finder.findRequiredViewAsType(source, R.id.llCounts, "field 'mLlCounts'", LinearLayout.class);
  }

  @Override
  public void unbind() {
    T target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");

    target.mIvProfileImage = null;
    target.mTvName = null;
    target.mTvTagline = null;
    target.mRlUserHeader = null;
    target.mTvFollwers = null;
    target.mTvFollowing = null;
    target.mLlCounts = null;

    this.target = null;
  }
}

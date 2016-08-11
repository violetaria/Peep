// Generated code from Butter Knife. Do not modify!
package com.getlosthere.apps.peep.activities;

import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import butterknife.Unbinder;
import butterknife.internal.Finder;
import com.astuetz.PagerSlidingTabStrip;
import com.getlosthere.apps.peep.R;
import java.lang.IllegalStateException;
import java.lang.Object;
import java.lang.Override;

public class TimelineActivity_ViewBinding<T extends TimelineActivity> implements Unbinder {
  protected T target;

  public TimelineActivity_ViewBinding(T target, Finder finder, Object source) {
    this.target = target;

    target.rvBottomSheet = finder.findRequiredViewAsType(source, R.id.design_bottom_sheet, "field 'rvBottomSheet'", RecyclerView.class);
    target.coordinatorLayout = finder.findRequiredViewAsType(source, R.id.main_content, "field 'coordinatorLayout'", CoordinatorLayout.class);
    target.btnFab = finder.findRequiredViewAsType(source, R.id.fab, "field 'btnFab'", FloatingActionButton.class);
    target.viewPager = finder.findRequiredViewAsType(source, R.id.viewpager, "field 'viewPager'", ViewPager.class);
    target.tabStrip = finder.findRequiredViewAsType(source, R.id.tabs, "field 'tabStrip'", PagerSlidingTabStrip.class);
  }

  @Override
  public void unbind() {
    T target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");

    target.rvBottomSheet = null;
    target.coordinatorLayout = null;
    target.btnFab = null;
    target.viewPager = null;
    target.tabStrip = null;

    this.target = null;
  }
}

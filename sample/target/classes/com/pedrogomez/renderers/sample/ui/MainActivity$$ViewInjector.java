// Generated code from Butter Knife. Do not modify!
package com.pedrogomez.renderers.sample.ui;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class MainActivity$$ViewInjector {
  public static void inject(Finder finder, final com.pedrogomez.renderers.sample.ui.MainActivity target, Object source) {
    View view;
    view = finder.findById(source, 2131230721);
    if (view == null) {
      throw new IllegalStateException("Required view with id '2131230721' for method 'openListViewSample' was not found. If this view is optional add '@Optional' annotation.");
    }
    view.setOnClickListener(
      new android.view.View.OnClickListener() {
        @Override public void onClick(
          android.view.View p0
        ) {
          target.openListViewSample();
        }
      });
    view = finder.findById(source, 2131230722);
    if (view == null) {
      throw new IllegalStateException("Required view with id '2131230722' for method 'openRecyclerViewSample' was not found. If this view is optional add '@Optional' annotation.");
    }
    view.setOnClickListener(
      new android.view.View.OnClickListener() {
        @Override public void onClick(
          android.view.View p0
        ) {
          target.openRecyclerViewSample();
        }
      });
  }

  public static void reset(com.pedrogomez.renderers.sample.ui.MainActivity target) {
  }
}

// Generated code from Butter Knife. Do not modify!
package com.pedrogomez.renderers.sample.ui;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class RecyclerViewActivity$$ViewInjector {
  public static void inject(Finder finder, final com.pedrogomez.renderers.sample.ui.RecyclerViewActivity target, Object source) {
    View view;
    view = finder.findById(source, 2131230723);
    if (view == null) {
      throw new IllegalStateException("Required view with id '2131230723' for field 'recyclerView' was not found. If this view is optional add '@Optional' annotation.");
    }
    target.recyclerView = (android.support.v7.widget.RecyclerView) view;
  }

  public static void reset(com.pedrogomez.renderers.sample.ui.RecyclerViewActivity target) {
    target.recyclerView = null;
  }
}

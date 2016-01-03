// Generated code from Butter Knife. Do not modify!
package com.pedrogomez.renderers.sample.ui;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class ListViewActivity$$ViewInjector {
  public static void inject(Finder finder, final com.pedrogomez.renderers.sample.ui.ListViewActivity target, Object source) {
    View view;
    view = finder.findById(source, 2131230720);
    if (view == null) {
      throw new IllegalStateException("Required view with id '2131230720' for field 'listView' was not found. If this view is optional add '@Optional' annotation.");
    }
    target.listView = (android.widget.ListView) view;
  }

  public static void reset(com.pedrogomez.renderers.sample.ui.ListViewActivity target) {
    target.listView = null;
  }
}

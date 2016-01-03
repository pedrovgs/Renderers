// Generated code from Butter Knife. Do not modify!
package com.pedrogomez.renderers.sample.ui.renderers;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class LiveVideoRenderer$$ViewInjector {
  public static void inject(Finder finder, final com.pedrogomez.renderers.sample.ui.renderers.LiveVideoRenderer target, Object source) {
    com.pedrogomez.renderers.sample.ui.renderers.VideoRenderer$$ViewInjector.inject(finder, target, source);

    View view;
    view = finder.findById(source, 2131230729);
    if (view == null) {
      throw new IllegalStateException("Required view with id '2131230729' for field 'date' was not found. If this view is optional add '@Optional' annotation.");
    }
    target.date = (android.widget.TextView) view;
  }

  public static void reset(com.pedrogomez.renderers.sample.ui.renderers.LiveVideoRenderer target) {
    com.pedrogomez.renderers.sample.ui.renderers.VideoRenderer$$ViewInjector.reset(target);

    target.date = null;
  }
}

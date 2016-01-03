// Generated code from Butter Knife. Do not modify!
package com.pedrogomez.renderers.sample.ui.renderers;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class VideoRenderer$$ViewInjector {
  public static void inject(Finder finder, final com.pedrogomez.renderers.sample.ui.renderers.VideoRenderer target, Object source) {
    View view;
    view = finder.findById(source, 2131230724);
    if (view == null) {
      throw new IllegalStateException("Required view with id '2131230724' for field 'thumbnail' and method 'onVideoClicked' was not found. If this view is optional add '@Optional' annotation.");
    }
    target.thumbnail = (android.widget.ImageView) view;
    view.setOnClickListener(
      new android.view.View.OnClickListener() {
        @Override public void onClick(
          android.view.View p0
        ) {
          target.onVideoClicked();
        }
      });
    view = finder.findById(source, 2131230728);
    if (view == null) {
      throw new IllegalStateException("Required view with id '2131230728' for field 'title' was not found. If this view is optional add '@Optional' annotation.");
    }
    target.title = (android.widget.TextView) view;
    view = finder.findById(source, 2131230726);
    if (view == null) {
      throw new IllegalStateException("Required view with id '2131230726' for field 'marker' was not found. If this view is optional add '@Optional' annotation.");
    }
    target.marker = (android.widget.ImageView) view;
    view = finder.findById(source, 2131230727);
    if (view == null) {
      throw new IllegalStateException("Required view with id '2131230727' for field 'label' was not found. If this view is optional add '@Optional' annotation.");
    }
    target.label = (android.widget.TextView) view;
  }

  public static void reset(com.pedrogomez.renderers.sample.ui.renderers.VideoRenderer target) {
    target.thumbnail = null;
    target.title = null;
    target.marker = null;
    target.label = null;
  }
}

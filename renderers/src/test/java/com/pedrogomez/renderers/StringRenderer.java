package com.pedrogomez.renderers;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Renderer created only for testing purposes.
 *
 * @author Rostyslav Roshak.
 */
public class StringRenderer extends Renderer<String> {

  private View view;

  @Override protected void setUpView(View rootView) {

  }

  @Override protected void hookListeners(View rootView) {

  }

  @Override protected View inflate(LayoutInflater inflater, ViewGroup parent) {
    return view;
  }

  @Override public void render() {

  }

  public void setView(View view) {
    this.view = view;
  }
}

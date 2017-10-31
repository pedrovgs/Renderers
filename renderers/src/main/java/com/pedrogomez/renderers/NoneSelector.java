package com.pedrogomez.renderers;

import java.util.Collections;
import java.util.Map;

/**
 * None selector implementation. Used in the ListView Renderer implementation because ListView
 * has its own selection behavior.
 *
 * @author Arturo Gutiérrez Díaz-Guerra.
 */
class NoneSelector<T> implements Selector<T> {

  @Override public boolean isSelectable() {
    return false;
  }

  @Override public void setSelectable(boolean isSelectable) {
    // Nothing
  }

  @Override public void setSelected(boolean isSelected, String itemId, T item) {
    // Nothing
  }

  @Override
  public void onBindRenderer(Renderer<T> renderer) {
    // Nothing
  }

  @Override public boolean isSelected(String itemId) {
    return false;
  }

  @Override
  public Map<String, T> getSelectedItems() {
    return Collections.emptyMap();
  }
}

package com.pedrogomez.renderers;

import java.util.Collections;
import java.util.Set;

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

  @Override public void setSelected(boolean isSelected, String itemId) {
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
  public Set<String> getSelectedItemIds() {
    return Collections.emptySet();
  }
}

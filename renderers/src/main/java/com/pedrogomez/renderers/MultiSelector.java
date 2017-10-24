package com.pedrogomez.renderers;

import java.util.HashSet;
import java.util.Set;

/**
 * TBD
 *
 * @author Arturo Gutiérrez Díaz-Guerra.
 */
public class MultiSelector<T> implements Selector<T> {

  private final Set<T> selectedItems = new HashSet<>();
  private boolean isSelectable = false;

  @Override public void setSelectable(boolean isSelectable) {
    this.isSelectable = isSelectable;
    if (!isSelectable) {
      selectedItems.clear();
    }
  }

  @Override public void setSelected(boolean isSelected, T item) {
    if (!isSelectable) {
      return;
    }

    if (isSelected) {
      selectedItems.add(item);
    } else {
      selectedItems.remove(item);
    }
  }

  @Override public boolean isSelected(T item) {
    return selectedItems.contains(item);
  }

  @Override
  public Set<T> getSelectedItems() {
    return selectedItems;
  }
}

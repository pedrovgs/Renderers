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

  @Override public void setSelectable(boolean isSelectable) {

  }

  @Override public void setSelected(boolean isSelected, T item) {
    if (isSelected) {
      selectedItems.add(item);
    } else {
      selectedItems.remove(item);
    }
  }

  @Override public boolean isSelected(T item) {
    return selectedItems.contains(item);
  }
}

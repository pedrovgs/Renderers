package com.pedrogomez.renderers;

import java.util.Set;

/**
 * TBD
 *
 * @author Arturo Gutiérrez Díaz-Guerra.
 */
public interface Selector<T> {

  void setSelectable(boolean isSelectable);

  void setSelected(boolean isSelected, T item);

  boolean isSelected(T item);

  Set<T> getSelectedItems();
}

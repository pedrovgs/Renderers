package com.pedrogomez.renderers;

/**
 * TBD
 *
 * @author Arturo Gutiérrez Díaz-Guerra.
 */
public class NoneSelector<T> implements Selector<T> {

  @Override public void setSelectable(boolean isSelectable) {
    // Nothing
  }

  @Override public void setSelected(boolean isSelected, T item) {
    // Nothing
  }

  @Override public boolean isSelected(T item) {
    return false;
  }
}

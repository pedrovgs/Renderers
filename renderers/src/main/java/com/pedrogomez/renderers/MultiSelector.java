package com.pedrogomez.renderers;

/**
 * TBD
 *
 * @author Arturo Gutiérrez Díaz-Guerra.
 */
public class MultiSelector<T> implements Selector<T> {

  @Override public void setSelectable(boolean isSelectable) {

  }

  @Override public void setSelected(boolean isSelected, T item) {

  }

  @Override public boolean isSelected(T content) {
    return false;
  }
}

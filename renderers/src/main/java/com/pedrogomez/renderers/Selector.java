package com.pedrogomez.renderers;

/**
 * TBD
 *
 * @author Arturo Gutiérrez Díaz-Guerra.
 */
public interface Selector<T> {

  void setSelectable(boolean isSelectable);

  void setSelected(boolean isSelected, T item);

  boolean isSelected(T content);
}

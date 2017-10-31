package com.pedrogomez.renderers;

import java.util.Set;

/**
 * Interface created to represent a items selector over Renderers.
 *
 * @author Arturo Gutiérrez Díaz-Guerra.
 */
public interface Selector<T> {

  boolean isSelectable();

  void setSelectable(boolean isSelectable);

  boolean isSelected(String itemId);

  void setSelected(boolean isSelected, String itemId);

  void onBindRenderer(Renderer<T> renderer);

  Set<String> getSelectedItemIds();
}

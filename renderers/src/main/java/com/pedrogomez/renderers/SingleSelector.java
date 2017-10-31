package com.pedrogomez.renderers;

import java.util.Map;

/**
 * Implementation of selector for single selection. Basically, it's the multi selector
 * implementation but deselecting the previous selected items before selecting the new one.
 *
 * @author Arturo Gutiérrez Díaz-Guerra.
 */
class SingleSelector<T> extends MultiSelector<T> {

  @Override public void setSelected(boolean isSelected, String itemId, T item) {
    if (isSelected) {
      for (Map.Entry<String, T> entry : getSelectedItems().entrySet()) {
        if (!itemId.equals(entry.getKey())) {
          super.setSelected(false, entry.getKey(), item);
        }
      }
    }

    super.setSelected(isSelected, itemId, item);
  }
}

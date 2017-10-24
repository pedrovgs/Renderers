package com.pedrogomez.renderers;

/**
 * Implementation of selector for single selection. Basically, it's the multi selector
 * implementation but deselecting the previous selected items before selecting the new one.
 *
 * @author Arturo Gutiérrez Díaz-Guerra.
 */
class SingleSelector<T> extends MultiSelector<T> {

    @Override
    public void setSelected(boolean isSelected, String itemId) {
        if (isSelected) {
            for (String selectedItemId : getSelectedItemIds()) {
                if (!itemId.equals(selectedItemId)) {
                    super.setSelected(false, selectedItemId);
                }
            }
        }

        super.setSelected(isSelected, itemId);
    }
}

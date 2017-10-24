package com.pedrogomez.renderers;

/**
 * TBD
 *
 * @author Arturo Gutiérrez Díaz-Guerra.
 */
public class SingleSelector<T> extends MultiSelector<T> {

    @Override
    public void setSelected(boolean isSelected, T item) {
        if (isSelected) {
            for (T selectedItem : getSelectedItems()) {
                if (!selectedItem.equals(item)) {
                    super.setSelected(false, selectedItem);
                }
            }
        }

        super.setSelected(isSelected, item);
    }
}

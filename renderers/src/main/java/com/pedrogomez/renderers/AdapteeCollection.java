package com.pedrogomez.renderers;

import java.util.Collection;

/**
 * Interface created to represent the adaptee collection used in RendererAdapter. RendererAdapter will be created with
 * a RendererBuilder and an AdapteeCollection that store all the content to show in a list view.
 *
 * @author Pedro Vicente Gómez Sánchez.
 */
public interface AdapteeCollection<T> {

    /**
     * @return size of the adaptee collection.
     */
    int size();

    /**
     * Search an element using the index passed as argument.
     *
     * @param index to search in the collection.
     * @return the element stored at index passed as argument.
     */
    T get(int index);

    /**
     * Add a new element to the adaptee collection.
     *
     * @param element to add.
     */
    void add(T element);

    /**
     * Remove one element from the adatee collection.
     *
     * @param element
     */
    void remove(T element);

    /**
     * Add on element collection to the adaptee collection.
     *
     * @param elements to add.
     */
    void addAll(Collection<T> elements);

    /**
     * Remove on element collection to the adaptee collection.
     * @param elements
     */
    void removeAll(Collection<T> elements);
}

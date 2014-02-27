package com.pedrogomez.renderers;

import java.util.Collection;

/**
 * Interface created to represent the adaptee collection used in RendererAdapter.
 *
 * @author Pedro Vicente Gómez Sánchez.
 */
public interface AdapteeCollection<T> {

    int size();

    T get(int index);

    void add(T element);

    void remove(T element);

    void addAll(Collection<T> elements);

    void removeAll(Collection<T> elements);
}

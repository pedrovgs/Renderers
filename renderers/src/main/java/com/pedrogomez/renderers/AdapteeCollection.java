package com.pedrogomez.renderers;

import java.util.Collection;

/**
 * Interface created to represent the adaptee collection used in RendererAdapter.
 *
 * @author Pedro Vicente Gómez Sánchez.
 */
public interface AdapteeCollection<T> {

    public int size();

    public T get(int index);

    public void add(T element);

    public void remove(T element);

    public void addAll(Collection<T> elements);

    public void removeAll(Collection<T> elements);
}

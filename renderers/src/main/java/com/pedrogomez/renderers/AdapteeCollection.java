package com.pedrogomez.renderers;

/**
 * Interface created to represent the adaptee collection used in RendererAdapter.
 *
 * @author Pedro Vicente Gómez Sánchez.
 */
public interface AdapteeCollection<T> {

    public int size();

    public T get(int index);
}

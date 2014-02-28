/*
 * Copyright (C) 2014 Pedro Vicente Gómez Sánchez.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.pedrogomez.renderers;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import com.pedrogomez.renderers.exception.NullRendererBuiltException;

import java.util.Collection;

/**
 * BaseAdapter created to work RendererBuilders and Renderers. Other adapters have to use this one to create new lists.
 * <p/>
 * This class is the heart of this library. It's used to avoid the library users declare a new renderer each time they
 * have to implement a new ListView.
 * <p/>
 * RendererAdapter<T> has to be constructed with a LayoutInflater to inflate views, one RendererBuilder to provide
 * Renderer to RendererAdapterdapter and one AdapteeCollection to provide the elements to render.
 *
 * @author Pedro Vicente Gómez Sánchez.
 */
public class RendererAdapter<T> extends BaseAdapter {

    /*
     * Attributes
     */

    private LayoutInflater layoutInflater;
    private RendererBuilder<T> rendererBuilder;
    private AdapteeCollection<T> collection;


    /*
     * Constructor
     */

    public RendererAdapter(LayoutInflater layoutInflater, RendererBuilder rendererBuilder, AdapteeCollection<T> collection) {
        this.layoutInflater = layoutInflater;
        this.rendererBuilder = rendererBuilder;
        this.collection = collection;
    }

    /*
     * Implemented methods
     */

    @Override
    public int getCount() {
        return collection.size();
    }

    @Override
    public T getItem(int position) {
        return collection.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * Main method of RendererAdapter. This method has the responsibility of update renderer builder values and create
     * or recycle a new rendere. Once the renderer has been obtained the RendereBuilder will call the render method
     * in the renderer and will return the renderer root view to the ListView.
     * <p/>
     * If rendererBuilder returns a null renderer this method will throw a NullRendererBuiltException.
     *
     * @param position    to render.
     * @param convertView to use to recycle.
     * @param parent      used to inflate views.
     * @return view rendered.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        T content = getItem(position);
        rendererBuilder.withContent(content);
        rendererBuilder.withConvertView(convertView);
        rendererBuilder.withParent(parent);
        rendererBuilder.withLayoutInflater(layoutInflater);
        Renderer<T> renderer = rendererBuilder.build();
        if (renderer == null) {
            throw new NullRendererBuiltException("RendererBuilder have to return a not null renderer");
        }
        updateRendererExtraValues(content, renderer, position);
        renderer.render();
        return renderer.getRootView();
    }

    /*
     * Recycle methods.
     * This methods has to be implemented to allow the ListView recycle our renderers.
     * The implementation has been delegated to RendereBuilder.
     */

    /**
     * Indicate to the ListView the type of renderer used to one position using a numeric value.
     *
     * @param position to analyze.
     * @return the id associated to the renderer used to render the content at position position.
     */
    @Override
    public int getItemViewType(int position) {
        T content = getItem(position);
        return rendererBuilder.getItemViewType(content);
    }

    /**
     * Indicate to the ListView the number of different renderers are the RendererBuilder to use.
     *
     * @return amount of different renderers.
     */
    @Override
    public int getViewTypeCount() {
        return rendererBuilder.getViewTypeCount();
    }

    /**
     * Add an element to the AdapteeCollection<T>.
     *
     * @param element to add.
     */
    public void add(T element) {
        collection.add(element);
    }

    /**
     * Remove an element from the AdapteeCollection<T>.
     *
     * @param element to remove.
     */
    public void remove(T element) {
        collection.remove(element);
    }

    /**
     * Add a Collection<T> of elements to the AdapteeCollection.
     *
     * @param elements to add.
     */
    public void addAll(Collection<T> elements) {
        collection.addAll(elements);
    }

    /**
     * Remove a Collection<T> of elements to the AdapteeCollection.
     *
     * @param elements to remove.
     */
    public void removeAll(Collection<T> elements) {
        collection.removeAll(elements);
    }

    /**
     * Allows the client code to access the AdapteeCollection<T> from subtypes of RendererAdapter.
     *
     * @return collection used in the adapter as the adaptee class.
     */
    protected AdapteeCollection<T> getCollection() {
        return collection;
    }

    /**
     * Empty implementation created to allow the client code to extend this class without override getView method.
     * This method is called before render the renderer and can be used in RendererAdapter extension to add extra
     * info to the renderer created.
     *
     * @param content  to be rendered.
     * @param renderer to be used to paint the content.
     * @param position of the content.
     */
    protected void updateRendererExtraValues(T content, Renderer<T> renderer, int position) {
        //Empty implementation
    }

}

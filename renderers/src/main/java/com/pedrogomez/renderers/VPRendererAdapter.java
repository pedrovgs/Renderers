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

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pedrogomez.renderers.exception.NullRendererBuiltException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * PagerAdapter extension created to work RendererBuilders and Renderer instances. Other
 * adapters have to use this one to show information into ViewPager widgets.
 * <p>
 * This class is the heart of this library. It's used to avoid the library users declare a new
 * renderer each time they have to show information into a ViewPager.
 * <p>
 * VPRendererAdapter has to be constructed with a RendererBuilder to provide Renderer to
 * VPRendererAdapter and one list to provide the elements to render.
 *
 * @author Jc Miñarro.
 */
public class VPRendererAdapter<T> extends PagerAdapter {

    private final RendererBuilder<T> rendererBuilder;
    private List<T> list;

    public VPRendererAdapter(RendererBuilder<T> rendererBuilder) {
        this(rendererBuilder, new ArrayList<T>());
    }

    /**
     * @deprecated Use {@link #VPRendererAdapter(RendererBuilder, List)} function instead.
     * This constructor is going to be removed in upcoming version.
     */
    @Deprecated
    public VPRendererAdapter(RendererBuilder<T> rendererBuilder, AdapteeCollection<T> collection) {
        this.rendererBuilder = rendererBuilder;
        try {
            this.list = (List) collection;
        } catch (ClassCastException exception) {
            throw new ClassCastException("collection parameter needs to implement List interface. "
                    + "AdapteeCollection has been deprecated and will disappear in upcoming version");
        }
    }

    public VPRendererAdapter(RendererBuilder<T> rendererBuilder, List<T> list) {
        this.rendererBuilder = rendererBuilder;
        this.list = list;
    }

    /**
     * Main method of VPRendererAdapter. This method has the responsibility of update the
     * RendererBuilder values and create or recycle a new Renderer. Once the renderer has been
     * obtained the RendereBuilder will call the render method in the renderer and will return the
     * Renderer root view to the ViewPager.
     *
     * If RendererBuilder returns a null Renderer this method will throw a
     * NullRendererBuiltException.
     *
     * @param parent The containing View in which the page will be shown.
     * @param position to render.
     * @return view rendered.
     */
    @Override public Object instantiateItem(ViewGroup parent, int position) {
        T content = getItem(position);
        rendererBuilder.withContent(content);
        rendererBuilder.withParent(parent);
        rendererBuilder.withLayoutInflater(LayoutInflater.from(parent.getContext()));
        Renderer<T> renderer = rendererBuilder.build();
        if (renderer == null) {
            throw new NullRendererBuiltException("RendererBuilder have to return a not null Renderer");
        }
        updateRendererExtraValues(content, renderer, position);
        renderer.render();
        View view = renderer.getRootView();
        parent.addView(view);
        return view;
    }

    /**
     * Remove a view for the given position.  The adapter is responsible
     * for removing the view from its container.
     *
     * @param container The containing View from which the view will be removed.
     * @param position The view position to be removed.
     * @param object The same object that was returned by
     * {@link #instantiateItem(ViewGroup, int)}.
     */
    @Override public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override public boolean isViewFromObject(View view, Object o) {
        return view == o;
    }

    @Override public int getCount() {
        return list.size();
    }

    public T getItem(int position) {
        return list.get(position);
    }

    /**
     * Add an element to the AdapteeCollection.
     *
     * @param element to add.
     * @return if the element has been added.
     */
    public boolean add(T element) {
        return list.add(element);
    }

    /**
     * Remove an element from the AdapteeCollection.
     *
     * @param element to remove.
     * @return if the element has been removed.
     */
    public boolean remove(Object element) {
        return list.remove(element);
    }

    /**
     * Add a Collection of elements to the AdapteeCollection.
     *
     * @param elements to add.
     */
    public void addAll(Collection<? extends T> elements) {
        list.addAll(elements);
    }

    /**
     * Remove a Collection of elements to the AdapteeCollection.
     *
     * @param elements to remove.
     * @return if the elements have been removed.
     */
    public boolean removeAll(Collection<?> elements) {
        return list.removeAll(elements);
    }

    /**
     * Remove all elements inside the AdapteeCollection.
     */
    public void clear() {
        list.clear();
    }

    /**
     * @deprecated Use {@link #getList()} function instead.
     * This method is going to be removed in upcoming version.
     *
     * Allows the client code to access the AdapteeCollection from subtypes of RendererAdapter.
     *
     * @return collection used in the adapter as the adaptee class.
     */
    @Deprecated
    protected AdapteeCollection<T> getCollection() {
        return new ListAdapteeCollection(list);
    }

    /**
     * Allows the client code to access the list from subtypes of RendererAdapter.
     *
     * @return collection used in the adapter as the adaptee class.
     */
    protected List<T> getList() {
        return list;
    }

    /**
     * @deprecated Use {@link #setList(List)} function instead.
     * This method is going to be removed in upcoming version.
     */
    public void setCollection(AdapteeCollection<T> collection) {
        try {
            setList((List) collection);
        } catch (ClassCastException exception) {
            throw new ClassCastException("collection parameter needs to implement List interface. "
                    + "AdapteeCollection has been deprecated and will disappear in upcoming version");
        }
    }

    public void setList(@NonNull List<T> list) {
        if (list == null) {
            throw new IllegalArgumentException("The list configured can't be null");
        }

        this.list = list;
    }

    /**
     * Empty implementation created to allow the client code to extend this class without override
     * getView method.
     *
     * This method is called before render the Renderer and can be used in RendererAdapter extension
     * to add extra info to the renderer created like the position in the ListView/RecyclerView.
     *
     * @param content to be rendered.
     * @param renderer to be used to paint the content.
     * @param position of the content.
     */
    protected void updateRendererExtraValues(T content, Renderer<T> renderer, int position) {
        //Empty implementation
    }
}

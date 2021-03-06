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

import androidx.annotation.NonNull;

import com.pedrogomez.renderers.exception.NullRendererBuiltException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * BaseAdapter created to work RendererBuilders and Renderer instances. Other adapters have to use
 * this one to show information into ListView widgets.
 *
 * This class is the heart of this library. It's used to avoid the library users declare a new
 * renderer each time they have to show information into a ListView.
 *
 * RendererAdapter has to be constructed with a LayoutInflater to inflate views, one
 * RendererBuilder to provide Renderer to RendererAdapter and one list to
 * provide the elements to render.
 *
 * @author Pedro Vicente Gómez Sánchez.
 */
public class RendererAdapter<T> extends BaseAdapter {

  private final RendererBuilder<T> rendererBuilder;
  private List<T> list;

  public RendererAdapter(RendererBuilder<T> rendererBuilder) {
    this(rendererBuilder, new ArrayList<T>());
  }

  /**
   * @deprecated Use {@link #RendererAdapter(RendererBuilder, List)} function instead.
   * This constructor is going to be removed in upcoming version.
   */
  @Deprecated
  public RendererAdapter(RendererBuilder<T> rendererBuilder, AdapteeCollection<T> collection) {
    this.rendererBuilder = rendererBuilder;
    try {
      this.list = (List) collection;
    } catch (ClassCastException exception) {
      throw new ClassCastException("collection parameter needs to implement List interface. "
              + "AdapteeCollection has been deprecated and will disappear in upcoming version");
    }
  }

  public RendererAdapter(RendererBuilder<T> rendererBuilder, List<T> list) {
    this.rendererBuilder = rendererBuilder;
    this.list = list;
  }

  @Override public int getCount() {
    return list.size();
  }

  @Override public T getItem(int position) {
    return list.get(position);
  }

  @Override public long getItemId(int position) {
    return position;
  }

  /**
   * @deprecated Use {@link #setList(List)} function instead.
   * This method is going to be removed in upcoming version.
   */
  @Deprecated
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
   * Main method of RendererAdapter. This method has the responsibility of update the
   * RendererBuilder values and create or recycle a new Renderer. Once the renderer has been
   * obtained the RendereBuilder will call the render method in the renderer and will return the
   * Renderer root view to the ListView.
   *
   * If rRendererBuilder returns a null Renderer this method will throw a
   * NullRendererBuiltException.
   *
   * @param position to render.
   * @param convertView to use to recycle.
   * @param parent used to inflate views.
   * @return view rendered.
   */
  @Override public View getView(int position, View convertView, ViewGroup parent) {
    T content = getItem(position);
    rendererBuilder.withContent(content);
    rendererBuilder.withConvertView(convertView);
    rendererBuilder.withParent(parent);
    rendererBuilder.withLayoutInflater(LayoutInflater.from(parent.getContext()));
    Renderer<T> renderer = rendererBuilder.build();
    if (renderer == null) {
      throw new NullRendererBuiltException("RendererBuilder have to return a not null Renderer");
    }
    updateRendererExtraValues(content, renderer, position);
    renderer.render();
    return renderer.getRootView();
  }

  /**
   * Indicate to the ListView the type of Renderer used to one position using a numeric value.
   *
   * @param position to analyze.
   * @return the id associated to the Renderer used to render the content given a position.
   */
  @Override public int getItemViewType(int position) {
    T content = getItem(position);
    return rendererBuilder.getItemViewType(content);
  }

  /**
   * Indicate to the ListView the number of different how many Renderer implementations are in the
   * RendererBuilder ready to use.
   *
   * @return amount of different Renderer types.
   */
  @Override public int getViewTypeCount() {
    return rendererBuilder.getViewTypeCount();
  }

  /**
   * Add an element to the list.
   *
   * @param element to add.
   */
  public void add(T element) {
    list.add(element);
  }

  /**
   * Remove an element from the list.
   *
   * @param element to remove.
   */
  public void remove(Object element) {
    list.remove(element);
  }

  /**
   * Add a Collection of elements to the list.
   *
   * @param elements to add.
   */
  public void addAll(Collection<? extends T> elements) {
    list.addAll(elements);
  }

  /**
   * Remove a Collection of elements to the list.
   *
   * @param elements to remove.
   */
  public void removeAll(Collection<?> elements) {
    list.removeAll(elements);
  }

  /**
   * Remove all elements inside the list.
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
  protected AdapteeCollection<T> getCollection() {
    return new ListAdapteeCollection(list);
  }

  protected List<T> getList() {
    return list;
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

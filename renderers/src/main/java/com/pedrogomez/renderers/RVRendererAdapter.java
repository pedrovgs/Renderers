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

import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.pedrogomez.renderers.exception.NullRendererBuiltException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * RecyclerView.Adapter extension created to work RendererBuilders and Renderer instances. Other
 * adapters have to use this one or RVListRendererAdapter to show information into RecyclerView widgets.
 * <p>
 * This class is the heart of this library. It's used to avoid the library users declare a new
 * renderer each time they have to show information into a RecyclerView.
 * <p>
 * RVRendererAdapter has to be constructed with a LayoutInflater to inflate views, one
 * RendererBuilder to provide Renderer to RVRendererAdapter and one List to
 * provide the elements to render.
 *
 * @author Pedro Vicente Gómez Sánchez.
 */
public class RVRendererAdapter<T> extends RecyclerView.Adapter<RendererViewHolder> {

  private final RendererBuilder<T> rendererBuilder;
  private List<T> list;

  public RVRendererAdapter(RendererBuilder<T> rendererBuilder) {
    this(rendererBuilder, new ArrayList<T>());
  }

  /**
   * @deprecated Use {@link #RVRendererAdapter(RendererBuilder, List)} function instead.
   * This constructor is going to be removed in upcoming version.
   */
  public RVRendererAdapter(RendererBuilder<T> rendererBuilder, AdapteeCollection<T> collection) {
    this.rendererBuilder = rendererBuilder;
    try {
      this.list = (List) collection;
    } catch (ClassCastException exception) {
      throw new ClassCastException("collection parameter needs to implement List interface. "
              + "AdapteeCollection has been deprecated and will disappear in upcoming version");
    }
  }

  public RVRendererAdapter(RendererBuilder<T> rendererBuilder, List<T> list) {
    this.rendererBuilder = rendererBuilder;
    this.list = list;
  }

  @Override public int getItemCount() {
    return list.size();
  }

  public T getItem(int position) {
    return list.get(position);
  }

  @Override public long getItemId(int position) {
    return position;
  }

  /**
   * @deprecated Use {@link #setList} function instead.
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

  public void setList(List<T> list) {
    if (list == null) {
      throw new IllegalArgumentException("The List configured can't be null");
    }

    this.list = list;
  }

  /**
   * Indicate to the RecyclerView the type of Renderer used to one position using a numeric value.
   *
   * @param position to analyze.
   * @return the id associated to the Renderer used to render the content given a position.
   */
  @Override public int getItemViewType(int position) {
    T content = getItem(position);
    return rendererBuilder.getItemViewType(content);
  }

  /**
   * One of the two main methods in this class. Creates a RendererViewHolder instance with a
   * Renderer inside ready to be used. The RendererBuilder to create a RendererViewHolder using the
   * information given as parameter.
   *
   * @param viewGroup used to create the ViewHolder.
   * @param viewType associated to the renderer.
   * @return ViewHolder extension with the Renderer it has to use inside.
   */
  @Override public RendererViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
    rendererBuilder.withParent(viewGroup);
    rendererBuilder.withLayoutInflater(LayoutInflater.from(viewGroup.getContext()));
    rendererBuilder.withViewType(viewType);
    RendererViewHolder viewHolder = rendererBuilder.buildRendererViewHolder();
    if (viewHolder == null) {
      throw new NullRendererBuiltException("RendererBuilder have to return a not null viewHolder");
    }
    return viewHolder;
  }

  /**
   * Given a RendererViewHolder passed as argument and a position renders the view using the
   * Renderer previously stored into the RendererViewHolder.
   *
   * @param viewHolder with a Renderer class inside.
   * @param position to render.
   */
  @Override public void onBindViewHolder(RendererViewHolder viewHolder, int position) {
    T content = getItem(position);
    Renderer<T> renderer = viewHolder.getRenderer();
    if (renderer == null) {
      throw new NullRendererBuiltException("RendererBuilder have to return a not null renderer");
    }
    renderer.setContent(content);
    updateRendererExtraValues(content, renderer, position);
    renderer.render();
  }

  /**
   * Add an element to the list.
   *
   * @param element to add.
   * @return if the element has been added.
   */
  public boolean add(T element) {
    return list.add(element);
  }

  /**
   * Remove an element from the list.
   *
   * @param element to remove.
   * @return if the element has been removed.
   */
  public boolean remove(T element) {
    return list.remove(element);
  }

  /**
   * Add a Collection of elements to the list.
   *
   * @param elements to add.
   * @return if the elements have been added.
   */
  public boolean addAll(Collection<? extends T> elements) {
    return list.addAll(elements);
  }

  /**
   * Remove a Collection of elements to the list.
   *
   * @param elements to remove.
   * @return if the elements have been removed.
   */
  public boolean removeAll(Collection<?> elements) {
    return list.removeAll(elements);
  }

  /**
   * Remove all elements inside the list.
   */
  public void clear() {
    list.clear();
  }

  /**
   * @deprecated AdapteeCollection has been deprecated. Use {@link #getList()} method instead.
   * This method will be removed in upcoming version.
   *
   * Allows the client code to access the AdapteeCollection from subtypes of RendererAdapter.
   *
   * @return list used in the adapter.
   */
  @Deprecated
  protected AdapteeCollection<T> getCollection() {
    return new ListAdapteeCollection(list);
  }

  /** Allows the client code to access the list from subtypes of RendererAdapter.
   *
   * @return list used in the adapter.
   */
  protected List<T> getList() {
    return list;
  }

  /**
   * Empty implementation created to allow the client code to extend this class without override
   * getView method.
   * <p>
   * This method is called before render the Renderer and can be used in RendererAdapter extension
   * to add extra info to the renderer created like the position in the ListView/RecyclerView.
   *
   * @param content to be rendered.
   * @param renderer to be used to paint the content.
   * @param position of the content.
   */
  protected void updateRendererExtraValues(T content, Renderer<T> renderer, int position) {

  }

  /**
   * Provides a ready to use diff update for our adapter based on the implementation of the
   * standard equals method from Object.
   *
   * @param newList to refresh our content
   */
  public void diffUpdate(List<T> newList) {
    if (getList().size() == 0) {
      addAll(newList);
      notifyDataSetChanged();
    } else {
      DiffCallback<T> diffCallback = new DiffCallback<>(list, newList);
      DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallback);
      clear();
      addAll(newList);
      diffResult.dispatchUpdatesTo(this);
    }
  }
}

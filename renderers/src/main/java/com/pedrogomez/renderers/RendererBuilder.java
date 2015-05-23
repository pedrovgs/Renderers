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

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.pedrogomez.renderers.exception.NeedsPrototypesException;
import com.pedrogomez.renderers.exception.NullContentException;
import com.pedrogomez.renderers.exception.NullLayoutInflaterException;
import com.pedrogomez.renderers.exception.NullParentException;
import com.pedrogomez.renderers.exception.NullPrototypeClassException;
import com.pedrogomez.renderers.exception.PrototypeNotFoundException;
import java.util.Collection;

/**
 * Class created to work as builder for renderers. This class provides methods to create a
 * renderer.
 * <p/>
 * The library users have to extends RendererBuilder and create a new one with prototypes. The
 * RendererBuilder implementation will have to declare the mapping between objects from the adaptee
 * collection and renderers passed int the prototypes collection.
 *
 * @author Pedro Vicente Gómez Sánchez
 */
public abstract class RendererBuilder<T> {

  private Collection<Renderer<T>> prototypes;

  private T content;
  private View convertView;
  private ViewGroup parent;
  private LayoutInflater layoutInflater;
  private int viewType;
  private RecyclerView.ViewHolder viewHolder;

  public RendererBuilder() {

  }

  public RendererBuilder(Collection<Renderer<T>> prototypes) {
    if (prototypes == null || prototypes.isEmpty()) {
      throw new NeedsPrototypesException(
          "RendererBuilder have to be created with a non empty collection of"
              + "Collection<Renderer<T> to provide new or recycled renderers");
    }
    this.prototypes = prototypes;
  }

  RendererBuilder withContent(T content) {
    this.content = content;
    return this;
  }

  protected RendererBuilder withConvertView(View convertView) {
    this.convertView = convertView;
    return this;
  }

  RendererBuilder withParent(ViewGroup parent) {
    this.parent = parent;
    return this;
  }

  RendererBuilder withLayoutInflater(LayoutInflater layoutInflater) {
    this.layoutInflater = layoutInflater;
    return this;
  }

  RendererBuilder withViewType(int viewType) {
    this.viewType = viewType;
    return this;
  }

  RendererBuilder withViewHolder(RendererViewHolder viewHolder) {
    this.viewHolder = viewHolder;
    return this;
  }

  /**
   * Return the item view type used by the adapter to implement recycle mechanism.
   *
   * @param content to be rendered.
   * @return an integer that represents the renderer inside the adapter.
   */
  int getItemViewType(T content) {
    Class prototypeClass = getPrototypeClass(content);
    validatePrototypeClass(prototypeClass);
    return getItemViewType(prototypeClass);
  }

  /**
   * Return the amount of renderers to be used in the ListView. This method has to be implemented
   * to support the ListView recycle mechanism.
   *
   * @return prototypes size collection.
   */
  int getViewTypeCount() {
    return prototypes.size();
  }

  /**
   * Main method of this class. This method is the responsible of recycle or create a new renderer
   * with all the needed information to implement the rendering. This method will validate all the
   * attributes passed in the builder constructor and will check if can recycle or has to create a
   * new renderer.
   */
  protected Renderer build() {
    validateAttributes();
    Renderer renderer;
    if (isRecyclable(convertView, content)) {
      renderer = recycle(convertView, content);
    } else {
      renderer = createRenderer(content, parent);
    }
    return renderer;
  }


  protected RendererViewHolder buildRendererViewHolder() {
    validateAttributes();
    Renderer renderer = getPrototypeByIndex(viewType);
    renderer.onCreate(null, layoutInflater, parent);
    return new RendererViewHolder(renderer);
  }

  protected Renderer buildRendererForRecyclerView() {
    validateAttributes();
    return createRVRenderer(parent,viewType);
  }

  /**
   * Recycle the renderer getting it from the tag associated to the renderer root view.
   *
   * @param convertView that contains the tag.
   * @param content to be updated in the recycled renderer.
   * @return a recycled renderer.
   */
  private Renderer recycle(View convertView, T content) {
    Renderer renderer = (Renderer) convertView.getTag();
    renderer.onRecycle(content);
    return renderer;
  }

  /**
   * Create a renderer getting a copy from the prototypes collection.
   *
   * @param content to render.
   * @param parent used to inflate the view.
   * @return a new renderer.
   */
  private Renderer createRenderer(T content, ViewGroup parent) {
    int prototypeIndex = getPrototypeIndex(content);
    Renderer renderer = getPrototypeByIndex(prototypeIndex).copy();
    renderer.onCreate(content, layoutInflater, parent);
    return renderer;
  }

  private Renderer createRVRenderer(ViewGroup parent,int viewType) {
    int prototypeIndex = getPrototypeIndex(content);
    Renderer renderer = getPrototypeByIndex(prototypeIndex).copy();
    renderer.onCreate(content, layoutInflater, parent);
    return renderer;
  }

  /**
   * Search one prototype using the index. This method has to be implemented because prototypes
   * member is declared with Collection and that interface doesn't allow the client code to get one
   * element by index.
   *
   * @param prototypeIndex used to search.
   * @return prototype renderer.
   */
  private Renderer getPrototypeByIndex(final int prototypeIndex) {
    Renderer prototypeSelected = null;
    int i = 0;
    for (Renderer prototype : prototypes) {
      if (i == prototypeIndex) {
        prototypeSelected = prototype;
      }
      i++;
    }
    return prototypeSelected;
  }


  /**
   * Check if one renderer is recyclable getting it from the convertView's tag and checking the
   * class used.
   *
   * @param convertView to get the renderer if is not null.
   * @param content used to get the prototype class.
   * @return true if the renderer is recyclable.
   */
  private boolean isRecyclable(View convertView, T content) {
    boolean isRecyclable = false;
    if (convertView != null && convertView.getTag() != null) {
      Class prototypeClass = getPrototypeClass(content);
      validatePrototypeClass(prototypeClass);
      isRecyclable = prototypeClass.equals(convertView.getTag().getClass());
    }
    return isRecyclable;
  }

  private void validatePrototypeClass(Class prototypeClass) {
    if (prototypeClass == null) {
      throw new NullPrototypeClassException(
          "Your getPrototypeClass method implementation can't return a null class");
    }
  }

  /**
   * Returns the index of the prototype index using the content.
   *
   * @param content used to get the prototype index.
   * @return the prototype index.
   */
  private int getPrototypeIndex(T content) {
    return getItemViewType(content);
  }

  /**
   * Return the renderer class associated to the prototype.
   *
   * @param prototypeClass used to search the renderer in the prototypes collection.
   * @return the prototype index associated to the prototypeClass.
   */
  private int getItemViewType(Class prototypeClass) {
    int itemViewType = -1;
    for (Renderer renderer : prototypes) {
      if (renderer.getClass().equals(prototypeClass)) {
        itemViewType = getPrototypeIndex(renderer);
        break;
      }
    }
    if (itemViewType == -1) {
      throw new PrototypeNotFoundException(
          "Review your RendererBuilder implementation, you are returning one"
              + " prototype class not found in prototypes collection");
    }
    return itemViewType;
  }

  /**
   * Return the index associated to the renderer.
   *
   * @param renderer used to search in the prototypes collection.
   * @return the prototype index associated to the renderer passed as argument.
   */
  private int getPrototypeIndex(Renderer renderer) {
    int index = 0;
    for (Renderer prototype : prototypes) {
      if (prototype.getClass().equals(renderer.getClass())) {
        break;
      }
      index++;
    }
    return index;
  }

  /**
   * Throws one RendererException if the content parent or layoutInflater are null.
   */
  private void validateAttributes() {
    if (content == null) {
      throw new NullContentException("RendererBuilder needs content to create renderers");
    }

    if (parent == null) {
      throw new NullParentException("RendererBuilder needs a parent to inflate renderers");
    }

    if (layoutInflater == null) {
      throw new NullLayoutInflaterException(
          "RendererBuilder needs a LayoutInflater to inflate renderers");
    }
  }

  /**
   * Method to be implemented by the RendererBuilder subtypes. In this method the library user will
   * define the mapping between content and renderer class.
   *
   * @param content used to map object-renderers.
   * @return the class associated to the renderer.
   */
  protected abstract Class getPrototypeClass(T content);

  /**
   * Get access to the prototypes collection used to create one Rendererbuilder.
   *
   * @return prototypes collection.
   */
  protected final Collection<Renderer<T>> getPrototypes() {
    return prototypes;
  }

  /**
   * Configure prototypes used as Renderers.
   */
  protected final void setPrototypes(Collection<Renderer<T>> prototypes) {
    if (prototypes == null || prototypes.isEmpty()) {
      throw new NeedsPrototypesException(
          "RendererBuilder have to be created with a non empty collection of"
              + "Collection<Renderer<T> to provide new or recycled renderers");
    }
    this.prototypes = prototypes;
  }

}

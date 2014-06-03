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

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.pedrogomez.renderers.exception.NotInflateViewException;

/**
 * Base class created to work as a root ViewHolder in the classic list rootView / adapter
 * implementation. This entity will
 * be extended by other renderes.
 * <p/>
 * A renderer have to encapsulate the presentation logic for ech row of your ListView. Every
 * renderer have inside the
 * view is rendering and the content is using to get the info.
 *
 * @author Pedro Vicente Gómez Sánchez.
 */
public abstract class Renderer<T> implements Cloneable {

  private View rootView;
  private T content;

  /**
   * Method called when the renderer is going to be created. This method has the responsibility of
   * inflate the xml
   * layout using the layoutInflater and the parent ViewGroup, set itself to the tag and call
   * setUpView and hookListeners.
   *
   * @param content to render.
   * @param layoutInflater used to inflate the view.
   * @param parent used to inflate the view.
   */
  public void onCreate(T content, LayoutInflater layoutInflater, ViewGroup parent) {
    this.content = content;
    this.rootView = inflate(layoutInflater, parent);
    if (rootView == null) {
      throw new NotInflateViewException(
          "Renderers have to return a not null view in inflateView method");
    }
    this.rootView.setTag(this);
    setUpView(rootView);
    hookListeners(rootView);
  }

  /**
   * Method called when the renderer has been recycled. This method has the responsibility of update
   * the content stored
   * in the renderer.
   *
   * @param content to render.
   */
  public void onRecycle(T content) {
    this.content = content;
  }

  /**
   * Method to access the root view rendered in the renderer.
   *
   * @return top view in the view hierarchy of one renderer.
   */
  public View getRootView() {
    return rootView;
  }

  /**
   * @return the content stored in the renderer.
   */
  protected final T getContent() {
    return content;
  }

  /**
   * Map all the widgets from the rootView to renderer members.
   */
  protected abstract void setUpView(View rootView);

  /**
   * Set all the listeners to members mapped in setUpView method.
   */
  protected abstract void hookListeners(View rootView);

  /**
   * Inflate renderer layout. The view inflated can't be null. If this method returns a null view a
   * NotInflateViewException will be thrown.
   *
   * @param inflater LayoutInflater service to inflate.
   * @return View with the inflated layout.
   */
  protected abstract View inflate(LayoutInflater inflater, ViewGroup parent);

  /**
   * Method where the presentation logic algorithm have to be declared or implemented.
   */
  public abstract void render();

  /**
   * Create a clone of the renderer. This method is the base of the prototype mechanism implemented
   * to avoid create
   * new objects from RendererBuilder. Pay an special attention implementing clone method in
   * Renderer subtypes.
   *
   * @return a copy of the current renderer.
   */
  Renderer copy() {
    Renderer copy = null;
    try {
      copy = (Renderer) this.clone();
    } catch (CloneNotSupportedException e) {
      Log.e("Renderer", "All your renderers should be clonables.");
    }
    return copy;
  }
}

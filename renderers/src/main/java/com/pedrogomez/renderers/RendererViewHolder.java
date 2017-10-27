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

/**
 * RecyclerView.ViewHolder extension created to be able to use Renderer classes in RecyclerView
 * widgets. This class will be completely hidden to the library clients.
 */
public class RendererViewHolder extends RecyclerView.ViewHolder {

  private final Renderer renderer;

  public RendererViewHolder(Renderer renderer) {
    super(renderer.getRootView());
    this.renderer = renderer;
  }

  public Renderer getRenderer() {
    return renderer;
  }
}

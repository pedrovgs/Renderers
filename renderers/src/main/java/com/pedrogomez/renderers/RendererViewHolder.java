package com.pedrogomez.renderers;

import android.support.v7.widget.RecyclerView;

class RendererViewHolder extends RecyclerView.ViewHolder {

  private final Renderer renderer;

  RendererViewHolder(Renderer renderer) {
    super(renderer.getRootView());
    this.renderer = renderer;
  }

  public Renderer getRenderer() {
    return renderer.copy();
  }
}

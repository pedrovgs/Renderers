package com.pedrogomez.renderers;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

public abstract class RVRenderer<T> extends RecyclerView.ViewHolder{

  public RVRenderer(View itemView) {
    super(itemView);
  }

  /**
   * Method where the presentation logic algorithm have to be declared or implemented.
   */
  public abstract void render();

  /**
   * Create a clone of the renderer. This method is the base of the prototype mechanism implemented
   * to avoid create new objects from RendererBuilder. Pay an special attention implementing clone
   * method in Renderer subtypes.
   *
   * @return a copy of the current renderer.
   */
  RVRenderer<T> copy() {
    RVRenderer copy = null;
    try {
      copy = (RVRenderer) this.clone();
    } catch (CloneNotSupportedException e) {
      Log.e("RVRenderer", "All your renderers should be cloneables.");
    }
    return copy;
  }
}

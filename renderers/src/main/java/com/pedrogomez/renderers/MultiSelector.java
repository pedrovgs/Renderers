package com.pedrogomez.renderers;

import android.support.annotation.Nullable;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Implementation of selector to give support for multi-selection. It tracks the selected
 * items and refresh the corresponding renderers when it's necessary.
 *
 * @author Arturo Gutiérrez Díaz-Guerra.
 */
class MultiSelector<T> implements Selector<T> {

  private final Set<String> selectedItemIds = new HashSet<>();
  private final Map<String, WeakReference<Renderer<T>>> trackedRenderers = new HashMap<>();

  private boolean isSelectable = false;

  @Override public void setSelectable(boolean isSelectable) {
    this.isSelectable = isSelectable;
    if (!isSelectable) {
      selectedItemIds.clear();
      refreshAllRenderers();
    }
  }

  @Override public void setSelected(boolean isSelected, String itemId) {
    if (!isSelectable) {
      return;
    }

    if (isSelected) {
      selectedItemIds.add(itemId);
    } else {
      selectedItemIds.remove(itemId);
    }

    refreshPosition(itemId);
  }

  @Override public void onBindRenderer(Renderer<T> renderer) {
    trackedRenderers.put(renderer.getItemId(), new WeakReference<>(renderer));
  }

  @Override public boolean isSelected(String itemId) {
    return selectedItemIds.contains(itemId);
  }

  @Override public Set<String> getSelectedItemIds() {
    return selectedItemIds;
  }

  private void refreshPosition(String itemId) {
    Renderer<T> renderer = getRendererForItemId(itemId);
    if (renderer != null) {
      renderer.render();
    }
  }

  private void refreshAllRenderers() {
    for (WeakReference<Renderer<T>> weakReference : trackedRenderers.values()) {
      Renderer<T> renderer = weakReference.get();
      if (renderer != null) {
        refreshRenderer(renderer);
      }
    }
  }

  private void refreshRenderer(Renderer<T> renderer) {
    renderer.render();
  }

  @Nullable private Renderer<T> getRendererForItemId(String itemId) {
    WeakReference<Renderer<T>> weakReference = trackedRenderers.get(itemId);
    Renderer<T> renderer = weakReference.get();
    if (renderer != null) {
      String rendererItemId = renderer.getItemId();
      if (itemId.equals(rendererItemId)) {
        return renderer;
      }
      trackedRenderers.remove(itemId);
    }
    return null;
  }
}

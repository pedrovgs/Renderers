package com.pedrogomez.renderers;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.pedrogomez.renderers.exception.NullRendererBuiltException;
import java.util.Collection;

/**
 * Base Adapter for RecyclerView widgets created to work RendererBuilders and Renderers. Other
 * adapters have to
 * extend from this one to create new lists.
 * <p/>
 * This class is the heart of this library. It's used to avoid the library users declare a new
 * renderer each time they have to implement a new RecyclerView.
 * <p/>
 * RendererAdapter<T> has to be constructed with a LayoutInflater to inflate views, one
 * RendererBuilder to provide Renderer to RendererAdatper and one AdapteeCollection to
 * provide the elements to render.
 *
 * @author Pedro Vicente Gómez Sánchez.
 */
public class RVRendererAdapter<T> extends RecyclerView.Adapter<RendererViewHolder> {

  private final LayoutInflater layoutInflater;
  private final RendererBuilder<T> rendererBuilder;
  private final AdapteeCollection<T> collection;

  public RVRendererAdapter(LayoutInflater layoutInflater, RendererBuilder<T> rendererBuilder,
      AdapteeCollection<T> collection) {
    this.layoutInflater = layoutInflater;
    this.rendererBuilder = rendererBuilder;
    this.collection = collection;
  }

  @Override public int getItemCount() {
    return collection.size();
  }

  public T getItem(int position) {
    return collection.get(position);
  }

  @Override public int getItemViewType(int position) {
    T content = getItem(position);
    return rendererBuilder.getItemViewType(content);
  }

  @Override public RendererViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
    rendererBuilder.withParent(viewGroup);
    rendererBuilder.withLayoutInflater(layoutInflater);
    rendererBuilder.withViewType(viewType);
    RendererViewHolder viewHolder = rendererBuilder.buildRendererViewHolder();
    if (viewHolder == null) {
      throw new NullRendererBuiltException("RendererBuilder have to return a not null viewHolder");
    }
    return viewHolder;
  }

  @Override public void onBindViewHolder(RendererViewHolder viewHolder, int position) {
    T content = getItem(position);
    rendererBuilder.withContent(content);
    Renderer<T> renderer = viewHolder.getRenderer();
    renderer.setContent(content);
    if (renderer == null) {
      throw new NullRendererBuiltException("RendererBuilder have to return a not null renderer");
    }
    updateRendererExtraValues(content, renderer, position);
    renderer.render();
  }

  public void add(T element) {
    collection.add(element);
  }

  public void remove(T element) {
    collection.remove(element);
  }

  public void addAll(Collection<T> elements) {
    collection.addAll(elements);
  }

  public void removeAll(Collection<T> elements) {
    collection.removeAll(elements);
  }

  public void clear() {
    collection.clear();
  }

  protected AdapteeCollection<T> getCollection() {
    return collection;
  }

  protected void updateRendererExtraValues(T content, com.pedrogomez.renderers.Renderer<T> renderer,
      int position) {
    //Empty implementation
  }
}

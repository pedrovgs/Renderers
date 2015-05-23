package com.pedrogomez.renderers;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.pedrogomez.renderers.exception.NullRendererBuiltException;
import java.util.Collection;

/**
 * Base RecyclerViewAdapter created to work RendererBuilders and Renderers. Other adapters have to
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
public class RVRendererAdapter<T,R extends RVRenderer<T>> extends RecyclerView.Adapter<R> {

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

  public T getItem(int position){
    return collection.get(position);
  }

  @Override public int getItemViewType(int position) {
    T content = getItem(position);
    return rendererBuilder.getItemViewType(content);
  }

  @Override public R onCreateViewHolder(ViewGroup viewGroup, int viewType) {
    rendererBuilder.withParent(viewGroup);
    rendererBuilder.withLayoutInflater(layoutInflater);
    rendererBuilder.withViewType(viewType);
    R renderer = (R) rendererBuilder.buildRendererViewHolder();
    if (renderer == null) {
      throw new NullRendererBuiltException("RendererBuilder have to return a not null renderer");
    }
    return renderer;
  }

  /**
   * Main method of RecyclerViewRendererAdapter. This method has the responsibility of update
   * renderer builder values and recycle the view. Once the renderer has been obtained the
   * RendereBuilder will call the render method in the renderer.
   *
   * @param viewHolder
   * @param position
   */
  @Override public void onBindViewHolder(R viewHolder, int position) {
    T content = getItem(position);
    rendererBuilder.withContent(content);
    com.pedrogomez.renderers.Renderer<T> renderer = rendererBuilder.build();
    if (renderer == null) {
      throw new NullRendererBuiltException("RendererBuilder have to return a not null renderer");
    }
    updateRendererExtraValues(content, renderer, position);
    renderer.render();
  }

  /**
   * Add an element to the AdapteeCollection<T>.
   *
   * @param element to add.
   */
  public void add(T element) {
    collection.add(element);
  }

  /**
   * Remove an element from the AdapteeCollection<T>.
   *
   * @param element to remove.
   */
  public void remove(T element) {
    collection.remove(element);
  }

  /**
   * Add a Collection<T> of elements to the AdapteeCollection.
   *
   * @param elements to add.
   */
  public void addAll(Collection<T> elements) {
    collection.addAll(elements);
  }

  /**
   * Remove a Collection<T> of elements to the AdapteeCollection.
   *
   * @param elements to remove.
   */
  public void removeAll(Collection<T> elements) {
    collection.removeAll(elements);
  }

  /**
   * Remove all elements inside the AdapteeCollection.
   */
  public void clear() {
    collection.clear();
  }

  /**
   * Allows the client code to access the AdapteeCollection<T> from subtypes of RendererAdapter.
   *
   * @return collection used in the adapter as the adaptee class.
   */
  protected AdapteeCollection<T> getCollection() {
    return collection;
  }

  /**
   * Empty implementation created to allow the client code to extend this class without override
   * onBindView method.
   * This method is called before render the renderer and can be used in RendererAdapter extension
   * to add extra info to the renderer created.
   *
   * @param content to be rendered.
   * @param renderer to be used to paint the content.
   * @param position of the content.
   */
  protected void updateRendererExtraValues(T content, com.pedrogomez.renderers.Renderer<T> renderer, int position) {
    //Empty implementation
  }

}

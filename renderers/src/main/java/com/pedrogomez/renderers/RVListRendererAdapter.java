package com.pedrogomez.renderers;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.AsyncDifferConfig;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.pedrogomez.renderers.exception.NullRendererBuiltException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * ListAdapter extension created to work with RendererBuilders and Renderer instances. Other
 * adapters have to use this one or RVRendererAdapter to show information into RecyclerView widgets.
 * <p>
 * This class is used to avoid the library users declare a new renderer each time they have to show
 * information into a RecyclerView. The main difference between RVRendererAdapter and RVListRendererAdapter
 * is that RVListRendererAdapter performs the adapter elements' diffing calculation in a background thread.
 * <p>
 * RVListRendererAdapter has to be constructed with a RendererBuilder to provide Renderer
 * to RvListRendererAdapter, one of DiffUtil.ItemCallback or AsyncDifferConfig to provide the
 * diffing configuration and one AdapteeCollection to provide the elements to render.
 *
 * @author Víctor Julián García Granado.
 */
public class RVListRendererAdapter<T> extends ListAdapter<T, RendererViewHolder> {

    private final RendererBuilder<T> rendererBuilder;
    private List<T> list;

    public RVListRendererAdapter(RendererBuilder<T> rendererBuilder) {
        this(rendererBuilder, new DefaultDiffUtilItemCallback<T>(), new ArrayList<T>());
    }

    /**
     * @deprecated Use {@link #RVListRendererAdapter(RendererBuilder, List)} function instead.
     * This constructor is going to be removed in upcoming version.
     */
    @Deprecated
    public RVListRendererAdapter(RendererBuilder<T> rendererBuilder, AdapteeCollection<T> collection) {
        super(new DefaultDiffUtilItemCallback<T>());
        this.rendererBuilder = rendererBuilder;
        try {
            this.list = (List) collection;
        } catch (ClassCastException exception) {
            throw new ClassCastException("collection parameter needs to implement List interface. "
                    + "AdapteeCollection has been deprecated and will disappear in upcoming version");
        }
    }

    public RVListRendererAdapter(RendererBuilder<T> rendererBuilder, List<T> list) {
        super(new DefaultDiffUtilItemCallback<T>());
        this.rendererBuilder = rendererBuilder;
        this.list = list;
    }

    public RVListRendererAdapter(RendererBuilder<T> rendererBuilder, @NonNull DiffUtil.ItemCallback diffCallback) {
        this(rendererBuilder, diffCallback, new ArrayList<T>());
    }

    /**
     * @deprecated Use RVListRendererAdapter(RendererBuilder, DiffUtil.ItemCallback, List) constructor instead.
     * This constructor is going to be removed in upcoming version.
     */
    @Deprecated
    public RVListRendererAdapter(RendererBuilder<T> rendererBuilder,
                                 @NonNull DiffUtil.ItemCallback diffCallback,
                                 AdapteeCollection<T> collection) {
        super(diffCallback);
        this.rendererBuilder = rendererBuilder;
        try {
            this.list = (List) collection;
        } catch (ClassCastException exception) {
            throw new ClassCastException("collection parameter needs to implement List interface. "
                    + "AdapteeCollection has been deprecated and will disappear in upcoming version");
        }
    }

    public RVListRendererAdapter(RendererBuilder<T> rendererBuilder,
                                 @NonNull DiffUtil.ItemCallback diffCallback,
                                 List<T> list) {
        super(diffCallback);
        this.rendererBuilder = rendererBuilder;
        this.list = list;
    }

    public RVListRendererAdapter(RendererBuilder<T> rendererBuilder, @NonNull AsyncDifferConfig config) {
        this(rendererBuilder, config, new ListAdapteeCollection<T>());
    }

    public RVListRendererAdapter(RendererBuilder<T> rendererBuilder,
                                 @NonNull AsyncDifferConfig config,
                                 List<T> list) {
        super(config);
        this.rendererBuilder = rendererBuilder;
        this.list = list;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public T getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
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
    @Override
    public int getItemViewType(int position) {
        T content = getItem(position);
        return rendererBuilder.getItemViewType(content);
    }

    /**
     * One of the two main methods in this class. Creates a RendererViewHolder instance with a
     * Renderer inside ready to be used. The RendererBuilder to create a RendererViewHolder using the
     * information given as parameter.
     *
     * @param viewGroup used to create the ViewHolder.
     * @param viewType  associated to the renderer.
     * @return ViewHolder extension with the Renderer it has to use inside.
     */
    @Override
    public RendererViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
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
     * @param position   to render.
     */
    @Override
    public void onBindViewHolder(RendererViewHolder viewHolder, int position) {
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
     * Add an element to the AdapteeCollection.
     *
     * @param element to add.
     * @return if the element has been added.
     */
    public boolean add(T element) {
        return list.add(element);
    }

    /**
     * Remove an element from the AdapteeCollection.
     *
     * @param element to remove.
     * @return if the element has been removed.
     */
    public boolean remove(Object element) {
        return list.remove(element);
    }

    /**
     * Add a Collection of elements to the AdapteeCollection.
     *
     * @param elements to add.
     * @return if the elements have been added.
     */
    public boolean addAll(Collection<? extends T> elements) {
        return list.addAll(elements);
    }

    /**
     * Remove a Collection of elements to the AdapteeCollection.
     *
     * @param elements to remove.
     * @return if the elements have been removed.
     */
    public boolean removeAll(Collection<?> elements) {
        return list.removeAll(elements);
    }

    /**
     * Remove all elements inside the AdapteeCollection.
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
        return new ListAdapteeCollection(getList());
    }

    /**
     * Allows the client code to access the list from subtypes of RendererAdapter.
     *
     * @return collection used in the adapter as the adaptee class.
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
     * @param content  to be rendered.
     * @param renderer to be used to paint the content.
     * @param position of the content.
     */
    protected void updateRendererExtraValues(T content, Renderer<T> renderer, int position) {

    }

    /**
     * Provides a ready to use diff update for our adapter based on the diffing configuration
     * passed to the adapter on its instantiation. The diffing calculation is performed on a
     * background thread.
     *
     * @param newList to refresh our content
     */
    @Override
    public void submitList(@Nullable List<T> newList) {
        if (getList().size() > 0) {
            clear();
        }
        addAll(newList);

        super.submitList(newList);
    }
}

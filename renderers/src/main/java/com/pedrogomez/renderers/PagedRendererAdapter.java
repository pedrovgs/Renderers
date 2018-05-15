package com.pedrogomez.renderers;

import android.arch.paging.PagedListAdapter;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.pedrogomez.renderers.exception.NullRendererBuiltException;

public class PagedRendererAdapter<T> extends PagedListAdapter<T, RendererViewHolder> {

    private final RendererBuilder<T> rendererBuilder;
    private T item;

    public PagedRendererAdapter(RendererBuilder<T> rendererBuilder, @NonNull DiffUtil.ItemCallback<T> diffCallback) {
        super(diffCallback);
        this.rendererBuilder = rendererBuilder;
    }

    @NonNull
    @Override
    public RendererViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        rendererBuilder.withParent(parent);
        rendererBuilder.withLayoutInflater(LayoutInflater.from(parent.getContext()));
        rendererBuilder.withViewType(viewType);
        RendererViewHolder rendererViewHolder = rendererBuilder.buildRendererViewHolder();
        if (rendererViewHolder == null) {
            throw new NullRendererBuiltException("RendererBuilder have to return a not null viewHolder");
        }
        return rendererViewHolder;
    }

    public void setDefaultItem(T item) {
        this.item = item;
    }

    @Override
    public void onBindViewHolder(@NonNull RendererViewHolder holder, int position) {
        Renderer<T> renderer = holder.getRenderer();
        if (renderer == null) {
            throw new NullRendererBuiltException("RendererBuilder have to return a not null renderer");
        }
        T item = getItem(position);
        if (item == null) {
            item = this.item;
        }
        renderer.setContent(item);
        updateRendererExtraValues(item, renderer, position);
        renderer.render();
    }

    @Override
    public int getItemViewType(int position) {
        return rendererBuilder.getItemViewType(getItem(position));
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    protected void updateRendererExtraValues(T content, Renderer<T> renderer, int position) {

    }
}

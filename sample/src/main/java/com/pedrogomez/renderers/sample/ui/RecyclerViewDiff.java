package com.pedrogomez.renderers.sample.ui;

import com.pedrogomez.renderers.DiffRVRendererAdapter;
import com.pedrogomez.renderers.sample.model.Video;

class RecyclerViewDiff extends DiffRVRendererAdapter.Diff<Video> {

    @Override public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        Video oldItem = getOldList().get(oldItemPosition);
        Video newItem = getNewList().get(newItemPosition);

        return oldItem.getClass().equals(newItem.getClass());
    }

    @Override public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        Video oldItem = getOldList().get(oldItemPosition);
        Video newItem = getNewList().get(newItemPosition);

        return oldItem.equals(newItem);
    }
}

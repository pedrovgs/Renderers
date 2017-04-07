package com.pedrogomez.renderers;

import android.support.v7.util.DiffUtil;

import java.util.List;

public class DiffRVRendererAdapter<T> extends RVRendererAdapter<T> {

    private final Diff diff;

    public static abstract class Diff<T> {

        private List<T> oldList;
        private List<T> newList;

        /*
        Compare ids
         */
        public abstract boolean areItemsTheSame(int oldItemPosition, int newItemPosition);

        /*
        Compare contents
         */
        public abstract boolean areContentsTheSame(int oldItemPosition, int newItemPosition);

        protected List<T> getOldList() {
            return oldList;
        }

        void setOldList(List<T> oldList) {
            this.oldList = oldList;
        }

        protected List<T> getNewList() {
            return newList;
        }

        void setNewList(List<T> newList) {
            this.newList = newList;
        }
    }

    public DiffRVRendererAdapter(RendererBuilder<T> rendererBuilder, Diff diff) {
        super(rendererBuilder);
        this.diff = diff;
    }

    public DiffRVRendererAdapter(RendererBuilder<T> rendererBuilder, AdapteeCollection<T> collection, Diff diff) {
        super(rendererBuilder, collection);
        this.diff = diff;
    }

    public void update(List<T> newList) {
        if (getCollection().size() == 0) {
            this.addAll(newList);
            notifyDataSetChanged();
        } else {
            DiffCallbacks diffCallbacks = new DiffCallbacks(diff, (List) getCollection(), newList);
            DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallbacks);

            this.clear();
            this.addAll(newList);
            diffResult.dispatchUpdatesTo(this);
        }
    }

    private class DiffCallbacks extends DiffUtil.Callback {

        private final Diff diff;

        DiffCallbacks(Diff diff, List oldList, List newList) {
            this.diff = diff;
            this.diff.setOldList(oldList);
            this.diff.setNewList(newList);
        }

        @Override public int getOldListSize() {
            return this.diff.getOldList().size();
        }

        @Override public int getNewListSize() {
            return this.diff.getNewList().size();
        }

        @Override public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
            return diff.areItemsTheSame(oldItemPosition, newItemPosition);
        }

        @Override public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
            return diff.areContentsTheSame(oldItemPosition, newItemPosition);
        }
    }
}
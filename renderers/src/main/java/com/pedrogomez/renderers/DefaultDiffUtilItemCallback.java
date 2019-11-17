package com.pedrogomez.renderers;

import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;

public class DefaultDiffUtilItemCallback<T> extends DiffUtil.ItemCallback<T> {

    @Override
    public boolean areItemsTheSame(@NonNull T oldItem, @NonNull T newItem) {
        return oldItem == newItem;
    }

    @Override
    public boolean areContentsTheSame(@NonNull T oldItem, @NonNull T newItem) {
        return oldItem.equals(newItem);
    }
}

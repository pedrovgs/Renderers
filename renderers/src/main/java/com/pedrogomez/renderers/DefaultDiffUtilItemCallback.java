package com.pedrogomez.renderers;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

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

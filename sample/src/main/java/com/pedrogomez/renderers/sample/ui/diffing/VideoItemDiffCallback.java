package com.pedrogomez.renderers.sample.ui.diffing;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.pedrogomez.renderers.sample.model.Video;

public class VideoItemDiffCallback extends DiffUtil.ItemCallback<Video> {

    @Override
    public boolean areItemsTheSame(@NonNull Video oldVideo, @NonNull Video newVideo) {
        return oldVideo == newVideo;
    }

    @Override
    public boolean areContentsTheSame(@NonNull Video oldVideo, @NonNull Video newVideo) {
        return oldVideo.equals(newVideo);
    }
}

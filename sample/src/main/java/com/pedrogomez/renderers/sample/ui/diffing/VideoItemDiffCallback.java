package com.pedrogomez.renderers.sample.ui.diffing;

import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
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

package com.pedrogomez.renderers.sample.ui.renderers;

import com.pedrogomez.renderers.sample.R;
import com.pedrogomez.renderers.sample.model.Video;

import butterknife.OnClick;

public class RemovableVideoRenderer extends VideoRenderer {

    private Listener removeItemListener;

    public interface Listener {

        void removeItem(Video video);
    }

    public RemovableVideoRenderer(Listener removeItemListener) {
        this.removeItemListener = removeItemListener;
    }

    @Override protected void renderLabel() {
        String deleteLabel = getContext().getString(R.string.delete_label);
        getLabel().setText(deleteLabel);
    }

    @Override protected void renderMarker(Video video) {

    }

    @OnClick(R.id.tv_label) void clickOnDelete() {
        removeItemListener.removeItem(getContent());
    }

    @OnClick(R.id.iv_marker) void clickOnLike() {
        getContent().setLiked(!getContent().isLiked());
        renderMarker(getContent());
    }
}

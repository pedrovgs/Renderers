package com.pedrogomez.renderers.sample.ui.renderers;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pedrogomez.renderers.sample.R;
import com.pedrogomez.renderers.sample.model.Video;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnLongClick;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

public class SelectableVideoRenderer extends VideoRenderer {

    @Bind(R.id.v_selection) View selection;

    private Listener removeItemListener;

    public interface Listener {

        void onLongPressClicked(SelectableVideoRenderer renderer);

        void onRemoveButtonTapped(Video video);
    }

    public SelectableVideoRenderer(Listener removeItemListener) {
        this.removeItemListener = removeItemListener;
    }

    @Override protected View inflate(LayoutInflater inflater, ViewGroup parent) {
        View inflatedView = inflater.inflate(R.layout.selectable_video_renderer, parent, false);
        ButterKnife.bind(this, inflatedView);
        return inflatedView;
    }

    @Override
    protected String getItemId() {
        return getContent().getId();
    }

    @Override
    public void render() {
        super.render();
        renderSelection(isSelected());
    }

    @Override protected void renderLabel() {
        String deleteLabel = getContext().getString(R.string.delete_label);
        getLabel().setText(deleteLabel);
    }

    @Override protected void renderMarker(Video video) {

    }

    @OnClick(R.id.iv_thumbnail) void onVideoClicked() {
        toggleSelection();
    }

    @OnClick(R.id.tv_label) void clickOnDelete() {
        setSelected(false);
        removeItemListener.onRemoveButtonTapped(getContent());
    }

    @OnLongClick(R.id.iv_thumbnail) boolean longClickOnItem() {
        setSelected(true);
        removeItemListener.onLongPressClicked(this);
        return true;
    }

    private void renderSelection(boolean isSelected) {
        selection.setVisibility(isSelected ? VISIBLE : INVISIBLE);
    }
}

package com.pedrogomez.renderers.ui.renderers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.pedrogomez.renderers.R;
import com.pedrogomez.renderers.Renderer;
import com.pedrogomez.renderers.model.Video;
import com.squareup.picasso.Picasso;

/**
 * Abstract class that works as base renderer for video renderers.
 *
 * @author Pedro Vicente Gómez Sánchez.
 */
public abstract class VideoRenderer extends Renderer<Video> {

    /*
     * Attributes
     */
    private final Context context;

    private OnVideoClicked listener;

    /*
     * Constructor
     */

    public VideoRenderer(Context context) {
        this.context = context;
    }
    /*
     * Widgets
     */

    private ImageView thumbnail;
    private TextView title;
    private ImageView marker;
    private TextView label;

    @Override
    protected View inflate(LayoutInflater inflater, ViewGroup parent) {
        return inflater.inflate(R.layout.video_renderer, parent, false);
    }

    @Override
    protected void setUpView(View rootView) {
        thumbnail = (ImageView) rootView.findViewById(R.id.iv_thumbnail);
        title = (TextView) rootView.findViewById(R.id.tv_title);
        marker = (ImageView) rootView.findViewById(R.id.iv_marker);
        label = (TextView) rootView.findViewById(R.id.tv_label);
    }

    @Override
    protected void hookListeners(View rootView) {
        rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    Video video = getContent();
                    listener.onVideoClicked(video);
                }
            }
        });
    }

    @Override
    protected void render() {
        Video video = getContent();
        renderThumbnail(video);
        renderTitle(video);
        renderMarker(video);
        renderLabel();
    }


    private void renderThumbnail(Video video) {
        Picasso.with(context).load(video.getResourceThumbnail()).placeholder(R.drawable.placeholder).into(thumbnail);
    }


    private void renderTitle(Video video) {
        this.title.setText(video.getTitle());
    }

    public void setListener(OnVideoClicked listener) {
        this.listener = listener;
    }


    protected TextView getLabel() {
        return label;
    }

    protected ImageView getMarker() {
        return marker;
    }

    protected Context getContext() {
        return context;
    }

    /*
     * Abstract methods
     */


    protected abstract void renderLabel();

    protected abstract void renderMarker(Video video);

    /*
     * Interface to represent a vide click.
     */

    public interface OnVideoClicked {
        void onVideoClicked(final Video video);
    }

}

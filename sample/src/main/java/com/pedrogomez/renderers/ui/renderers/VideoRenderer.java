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
 * Abstract class that works as base renderer for Renderer<Video>. This class implements the main render algorithm
 * and declare some abstract methods to be implemented by subtypes.
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

    /**
     * Inflate the main layout used to render videos in the list view.
     *
     * @param inflater LayoutInflater service to inflate.
     * @param parent   ViewGroup used to inflate xml.
     * @return view inflated.
     */
    @Override
    protected View inflate(LayoutInflater inflater, ViewGroup parent) {
        return inflater.inflate(R.layout.video_renderer, parent, false);
    }

    /**
     * Maps all the view elements from the xml declaration to members of this renderer.
     *
     * @param rootView
     */
    @Override
    protected void setUpView(View rootView) {
        thumbnail = (ImageView) rootView.findViewById(R.id.iv_thumbnail);
        title = (TextView) rootView.findViewById(R.id.tv_title);
        marker = (ImageView) rootView.findViewById(R.id.iv_marker);
        label = (TextView) rootView.findViewById(R.id.tv_label);
    }

    /**
     * Insert external listeners in some widgets.
     *
     * @param rootView
     */
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

    /**
     * Main render algorithm based on render the video thumbnail, render the title, render the marker and the label.
     */
    @Override
    protected void render() {
        Video video = getContent();
        renderThumbnail(video);
        renderTitle(video);
        renderMarker(video);
        renderLabel();
    }

    /**
     * Use picasso to render the video thumbnail into the thumbnail widget using a temporal placeholder.
     *
     * @param video to get the rendered thumbnail.
     */
    private void renderThumbnail(Video video) {
        Picasso.with(context).load(video.getResourceThumbnail()).placeholder(R.drawable.placeholder).into(thumbnail);
    }


    /**
     * Render video title into the title widget.
     *
     * @param video to get the video title.
     */
    private void renderTitle(Video video) {
        this.title.setText(video.getTitle());
    }

    public void setListener(OnVideoClicked listener) {
        this.listener = listener;
    }

    /*
     * Protected methods
     */

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
     * Abstract methods.
     *
     * This methods are part of the render algorithm and are going to be implemented by VideoRenderer subtypes.
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

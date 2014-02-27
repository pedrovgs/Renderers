package com.pedrogomez.renderers.renderers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.pedrogomez.renderers.R;
import com.pedrogomez.renderers.Renderer;
import com.pedrogomez.renderers.Video;
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
    protected final Context context;

    /*
     * Constructor
     */

    public VideoRenderer(Context context) {
        this.context = context;
    }
    /*
     * Widgets
     */

    protected ImageView thumbnail;
    protected TextView title;
    protected ImageView playButton;
    protected ImageView marker;
    protected TextView label;

    @Override
    protected View inflate(LayoutInflater inflater, ViewGroup parent) {
        return inflater.inflate(R.layout.video_renderer, parent, false);
    }

    @Override
    protected void setUpView(View rootView) {
        thumbnail = (ImageView) rootView.findViewById(R.id.iv_thumbnail);
        title = (TextView) rootView.findViewById(R.id.tv_title);
        playButton = (ImageView) rootView.findViewById(R.id.iv_play);
        marker = (ImageView) rootView.findViewById(R.id.iv_marker);
        label = (TextView) rootView.findViewById(R.id.tv_label);
    }

    @Override
    protected void hookListeners(View rootView) {
        //Empty
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
        String title = video.getTitle();
        this.title.setText(title);
    }


    /*
     * Abstract methods
     */


    protected abstract void renderLabel();

    protected abstract void renderMarker(Video video);


}

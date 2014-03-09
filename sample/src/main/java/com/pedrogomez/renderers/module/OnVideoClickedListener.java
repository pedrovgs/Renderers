package com.pedrogomez.renderers.module;

import android.content.Context;
import android.widget.Toast;
import com.pedrogomez.renderers.model.Video;
import com.pedrogomez.renderers.ui.renderers.VideoRenderer;

import javax.inject.Inject;

/**
 * @author Pedro Vicente Gómez Sánchez.
 */
public class OnVideoClickedListener implements VideoRenderer.OnVideoClicked {

    /*
     * Attributes
     */
    private Context context;

    /*
     * Constructor
     */

    @Inject
    public OnVideoClickedListener(Context context) {
        this.context = context;
    }

    /*
     * Implemented methods
     */

    @Override
    public void onVideoClicked(Video video) {
        Toast.makeText(context, "Video clicked. Title = " + video.getTitle(), Toast.LENGTH_LONG).show();
    }
}

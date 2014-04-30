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

    private Context context;

    @Inject
    public OnVideoClickedListener(Context context) {
        this.context = context;
    }

    @Override
    public void onVideoClicked(Video video) {
        Toast.makeText(context, "Video clicked. Title = " + video.getTitle(), Toast.LENGTH_LONG).show();
    }
}

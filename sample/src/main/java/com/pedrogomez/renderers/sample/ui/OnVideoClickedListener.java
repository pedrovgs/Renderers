package com.pedrogomez.renderers.sample.ui;

import android.content.Context;
import android.widget.Toast;
import com.pedrogomez.renderers.sample.model.Video;
import com.pedrogomez.renderers.sample.ui.renderers.VideoRenderer;

/**
 * @author Pedro Vicente Gómez Sánchez.
 */
public class OnVideoClickedListener implements VideoRenderer.OnVideoClicked {

  private Context context;

  public OnVideoClickedListener(Context context) {
    this.context = context;
  }

  @Override public void onVideoClicked(Video video) {
    Toast.makeText(context, "Video clicked. Title = " + video.getTitle(), Toast.LENGTH_LONG).show();
  }
}

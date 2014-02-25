package com.pedro.renderers.renderers;

import android.content.Context;
import com.pedro.renderers.R;
import com.pedro.renderers.Video;
import com.squareup.picasso.Picasso;

/**
 * VideoRenderer created to contains the liked video renderer presentation logic.
 *
 * @author Pedro Vicente Gómez Sánchez.
 */
public class LikeVideoRenderer extends VideoRenderer {

    /*
     * Constructor
     */

    public LikeVideoRenderer(Context context) {
        super(context);
    }

    /*
     * Implemented methods
     */

    @Override
    protected void renderLabel() {
        label.setText("LIKED");
    }

    @Override
    protected void renderMarker(Video video) {
        int resource = video.isLiked() ? R.drawable.like_active : R.drawable.like_unactive;
        Picasso.with(context).load(resource).into(marker);
    }

}

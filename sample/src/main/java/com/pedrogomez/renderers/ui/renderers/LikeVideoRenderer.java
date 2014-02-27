package com.pedrogomez.renderers.ui.renderers;

import android.content.Context;
import com.pedrogomez.renderers.R;
import com.pedrogomez.renderers.model.Video;
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
        Video video = getContent();
        String labelText = video.isLiked() ? "DISLIKE" : "LIKE";
        getLabel().setText(labelText);
    }

    @Override
    protected void renderMarker(Video video) {
        int resource = video.isLiked() ? R.drawable.like_active : R.drawable.like_unactive;
        Picasso.with(getContext()).load(resource).into(getMarker());
    }

}

package com.pedro.renderers.renderers;

import com.pedro.renderers.Renderer;
import com.pedro.renderers.RendererBuilder;
import com.pedro.renderers.Video;

import java.util.List;

/**
 * RendererBuilder extension created to work with videos.
 *
 * @author Pedro Vicente Gómez Sánchez.
 */
public class VideoRendererBuilder extends RendererBuilder<Video> {


    public VideoRendererBuilder(List<Renderer<Video>> prototypes) {
        super(prototypes);
    }

    @Override
    protected Class getPrototypeClass(Video content) {
        Class prototypeClass;
        if (content.isFavorite()) {
            prototypeClass = FavoriteVideoRenderer.class;
        } else if (content.isLive()) {
            prototypeClass = LiveVideoRenderer.class;
        } else {
            prototypeClass = LikeVideoRenderer.class;
        }
        return prototypeClass;
    }
}

package com.pedrogomez.renderers.ui.builder;

import com.pedrogomez.renderers.Renderer;
import com.pedrogomez.renderers.RendererBuilder;
import com.pedrogomez.renderers.model.Video;
import com.pedrogomez.renderers.ui.renderers.FavoriteVideoRenderer;
import com.pedrogomez.renderers.ui.renderers.LikeVideoRenderer;
import com.pedrogomez.renderers.ui.renderers.LiveVideoRenderer;

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

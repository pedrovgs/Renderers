package com.pedrogomez.renderers.ui.builder;

import com.pedrogomez.renderers.Renderer;
import com.pedrogomez.renderers.RendererBuilder;
import com.pedrogomez.renderers.model.Video;
import com.pedrogomez.renderers.ui.renderers.FavoriteVideoRenderer;
import com.pedrogomez.renderers.ui.renderers.LikeVideoRenderer;
import com.pedrogomez.renderers.ui.renderers.LiveVideoRenderer;

import java.util.List;

/**
 * RendererBuilder extension created to work with videos. This class works as connector between RendererAdapter and
 * VideoRenderers. Define the mapping between Videos and VideoRenderers.
 *
 * @author Pedro Vicente Gómez Sánchez.
 */
public class VideoRendererBuilder extends RendererBuilder<Video> {


    public VideoRendererBuilder(List<Renderer<Video>> prototypes) {
        super(prototypes);
    }

    /**
     * Method to declare Video-VideoRenderer mapping.
     * Favorite videos will be rendered using FavoriteVideoRenderer.
     * Live videos will be rendered using LiveVideoRenderer.
     * Liked videos will be rendered using LikeVideoRenderer.
     *
     * @param content used to map object-renderers.
     * @return VideoRenderer subtype class.
     */
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

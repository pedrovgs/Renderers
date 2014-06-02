/*
 * Copyright (C) 2014 Pedro Vicente Gómez Sánchez.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.pedrogomez.renderers.ui.builder;

import com.pedrogomez.renderers.Renderer;
import com.pedrogomez.renderers.RendererBuilder;
import com.pedrogomez.renderers.model.Video;
import com.pedrogomez.renderers.ui.renderers.FavoriteVideoRenderer;
import com.pedrogomez.renderers.ui.renderers.LikeVideoRenderer;
import com.pedrogomez.renderers.ui.renderers.LiveVideoRenderer;
import java.util.List;
import javax.inject.Inject;

/**
 * RendererBuilder extension created to work with videos. This class works as connector between
 * RendererAdapter and
 * VideoRenderers. Define the mapping between Videos and VideoRenderers.
 *
 * @author Pedro Vicente Gómez Sánchez.
 */
public class VideoRendererBuilder extends RendererBuilder<Video> {

  @Inject
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

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
package com.pedrogomez.renderers.sample.ui.renderers;

import android.content.Context;
import com.pedrogomez.renderers.sample.R;
import com.pedrogomez.renderers.sample.model.Video;

/**
 * Favorite video renderer created to implement the presentation logic for videos. This
 * VideoRenderer subtype only
 * override renderLabel and renderMarker methods.
 *
 * @author Pedro Vicente Gómez Sánchez.
 */
public class FavoriteVideoRenderer extends VideoRenderer {

  public FavoriteVideoRenderer(Context context) {
    super(context);
  }

  @Override protected void renderLabel() {
    String label = getContext().getString(R.string.favorite_label);
    getLabel().setText(label);
  }

  @Override protected void renderMarker(Video video) {
    getMarker().setImageResource(R.drawable.fav_active);
  }
}

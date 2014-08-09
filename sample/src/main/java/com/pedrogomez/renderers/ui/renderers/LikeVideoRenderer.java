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
package com.pedrogomez.renderers.ui.renderers;

import android.content.Context;
import com.pedrogomez.renderers.R;
import com.pedrogomez.renderers.model.Video;
import com.squareup.picasso.Picasso;

/**
 * VideoRenderer created to contains the liked video renderer presentation logic. This
 * VideoRenderer subtype only complete the algorithm implementing renderLabel and renderMarker
 * methods.
 *
 * @author Pedro Vicente Gómez Sánchez.
 */
public class LikeVideoRenderer extends VideoRenderer {

  public LikeVideoRenderer(Context context) {
    super(context);
  }

  @Override
  protected void renderLabel() {
    Video video = getContent();
    String dislikeLabel = getContext().getString(R.string.dislike_label);
    String likeLabel = getContext().getString(R.string.like_label);
    String labelText = video.isLiked() ? dislikeLabel : likeLabel;
    getLabel().setText(labelText);
  }

  @Override
  protected void renderMarker(Video video) {
    int resource = video.isLiked() ? R.drawable.like_active : R.drawable.like_unactive;
    Picasso.with(getContext()).load(resource).into(getMarker());
    if (true) System.out.println("asdf");
  }
}

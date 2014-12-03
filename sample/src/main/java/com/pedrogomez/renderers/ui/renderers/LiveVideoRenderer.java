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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.pedrogomez.renderers.R;
import com.pedrogomez.renderers.model.Video;
import java.util.Date;

/**
 * VideoRenderer created to contains the live video presentation logic. This VideoRenderer subtype
 * change the inflated
 * layout and override the renderer algorithm to add a new phase to render the date.
 *
 * @author Pedro Vicente Gómez Sánchez.
 */
public class LiveVideoRenderer extends VideoRenderer {

  @InjectView(R.id.date) TextView date;

  public LiveVideoRenderer(Context context) {
    super(context);
  }

  @Override protected View inflate(LayoutInflater inflater, ViewGroup parent) {
    View inflatedView = inflater.inflate(R.layout.live_video_renderer, parent, false);
    ButterKnife.inject(this, inflatedView);
    return inflatedView;
  }

  @Override protected void setUpView(View rootView) {
         /*
          * Empty implementation substituted with the usage of ButterKnife library by Jake Wharton.
          */
  }

  @Override protected void renderLabel() {
    getLabel().setText(getContext().getString(R.string.live_label));
  }

  @Override protected void renderMarker(Video video) {
    getMarker().setVisibility(View.GONE);
  }

  @Override public void render() {
    super.render();
    renderDate();
  }

  private void renderDate() {
    String now = new Date().toLocaleString();
    date.setText(now);
  }
}


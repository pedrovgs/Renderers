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
package com.pedrogomez.renderers.module;

import android.content.Context;
import android.view.LayoutInflater;
import com.pedrogomez.renderers.RendererAdapter;
import com.pedrogomez.renderers.SampleApplication;
import com.pedrogomez.renderers.model.RandomVideoCollectionGenerator;
import com.pedrogomez.renderers.model.Video;
import com.pedrogomez.renderers.model.VideoCollection;
import com.pedrogomez.renderers.ui.MainActivity;
import com.pedrogomez.renderers.ui.builder.VideoRendererBuilder;
import dagger.Module;
import dagger.Provides;

/**
 * Dagger main module created to provide main dependencies.
 *
 * @author Pedro Vicente Gómez Sánchez.
 */
@Module(injects = {
    SampleApplication.class, MainActivity.class,
})
public class MainModule {

  private static final int VIDEO_COUNT = 100;

  private Context context;

  public MainModule(Context context) {
    this.context = context;
  }

  @Provides RendererAdapter<Video> provideVideoRendererAdapter(
      RandomVideoCollectionGenerator randomVideoCollectionGenerator, LayoutInflater layoutInflater,
      VideoRendererBuilder rendererBuilder) {
    VideoCollection videoCollection = randomVideoCollectionGenerator.generate(VIDEO_COUNT);
    RendererAdapter<Video> adapter =
        new RendererAdapter<Video>(layoutInflater, rendererBuilder, videoCollection);
    return adapter;
  }

  @Provides VideoRendererBuilder provideVideoRendererBuilder(VideoRendererBuilder impl) {
    return impl;
  }

  @Provides LayoutInflater provideLayoutInflater() {
    return LayoutInflater.from(context);
  }

  @Provides Context provideContext() {
    return context;
  }
}

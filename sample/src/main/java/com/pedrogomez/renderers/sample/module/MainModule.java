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
package com.pedrogomez.renderers.sample.module;

import android.content.Context;
import android.view.LayoutInflater;
import com.pedrogomez.renderers.AdapteeCollection;
import com.pedrogomez.renderers.RVRendererAdapter;
import com.pedrogomez.renderers.RendererAdapter;
import com.pedrogomez.renderers.sample.SampleApplication;
import com.pedrogomez.renderers.sample.model.RandomVideoCollectionGenerator;
import com.pedrogomez.renderers.sample.model.Video;
import com.pedrogomez.renderers.sample.ui.ListViewActivity;
import com.pedrogomez.renderers.sample.ui.RecyclerViewActivity;
import com.pedrogomez.renderers.sample.ui.builder.VideoRendererBuilder;
import com.pedrogomez.renderers.sample.ui.renderers.VideoRenderer;
import dagger.Module;
import dagger.Provides;

/**
 * Dagger main module created to provide main dependencies.
 *
 * @author Pedro Vicente Gómez Sánchez.
 */
@Module(injects = {
    SampleApplication.class, ListViewActivity.class, RecyclerViewActivity.class
}) public class MainModule {

  private static final int VIDEO_COUNT = 100;

  private Context context;

  public MainModule(Context context) {
    this.context = context;
  }

  /*
   * This is a sample of how to inject a RendererAdapter<T> in your code you can inject an empty one
   * or create a new instance an then assign it to the ListView. Initialize here the RendererAdapter
   * data is completely optional.
   */
  @Provides RendererAdapter<Video> provideVideoRendererAdapter(
      RandomVideoCollectionGenerator randomVideoCollectionGenerator, LayoutInflater layoutInflater,
      VideoRendererBuilder rendererBuilder) {
    AdapteeCollection<Video> videoCollection = getVideoAdapteeCollection(randomVideoCollectionGenerator);
    RendererAdapter<Video> adapter =
        new RendererAdapter<Video>(layoutInflater, rendererBuilder, videoCollection);
    return adapter;
  }

  private AdapteeCollection<Video> getVideoAdapteeCollection(
      RandomVideoCollectionGenerator randomVideoCollectionGenerator) {
    return randomVideoCollectionGenerator.generateListAdapteeVideoCollection(VIDEO_COUNT);
  }

  /*
   * This is a sample of how to inject a RVRendererAdapter<T> in your code you can inject an empty
   * one or create a new instance an then assign it to the ListView. Initialize here the
   * RVRendererAdapter data is completely optional.
   */
  @Provides RVRendererAdapter<Video> provideVideoRVRendererAdapter(
      RandomVideoCollectionGenerator randomVideoCollectionGenerator, LayoutInflater layoutInflater,
      VideoRendererBuilder rendererBuilder) {
    AdapteeCollection<Video> videoCollection = getVideoAdapteeCollection(randomVideoCollectionGenerator);
    RVRendererAdapter<Video> adapter = new RVRendererAdapter<Video>(layoutInflater, rendererBuilder, videoCollection);
    return adapter;
  }

  @Provides LayoutInflater provideLayoutInflater() {
    return LayoutInflater.from(context);
  }

  @Provides Context provideContext() {
    return context;
  }

  @Provides VideoRenderer.OnVideoClicked provideOnVideoClicked(OnVideoClickedListener impl) {
    return impl;
  }
}

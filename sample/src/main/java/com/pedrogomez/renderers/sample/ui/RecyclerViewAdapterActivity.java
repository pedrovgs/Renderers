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
package com.pedrogomez.renderers.sample.ui;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.pedrogomez.renderers.AdapteeCollection;
import com.pedrogomez.renderers.RVRendererAdapter;
import com.pedrogomez.renderers.RendererBuilder;
import com.pedrogomez.renderers.sample.R;
import com.pedrogomez.renderers.sample.model.RandomVideoCollectionGenerator;
import com.pedrogomez.renderers.sample.model.Video;
import com.pedrogomez.renderers.sample.ui.renderers.RemovableVideoRenderer;

import java.util.ArrayList;
import java.util.Collection;

import butterknife.BindView;

/**
 * RecyclerViewAdapterActivity for the Renderers demo.
 *
 * @author Pedro Vicente Gómez Sánchez.
 */
public class RecyclerViewAdapterActivity extends BaseActivity {

  private static final int VIDEO_COUNT = 100;

  @BindView(R.id.rv_renderers) RecyclerView recyclerView;

  private RVRendererAdapter<Video> adapter;

  @Override protected void onCreate(Bundle savedInstanceState) {
    setContentView(R.layout.activity_recycler_view);
    super.onCreate(savedInstanceState);
    initAdapter();
    initRecyclerView();
  }

  /**
   * Initialize RVRendererAdapter
   */
  private void initAdapter() {
    RandomVideoCollectionGenerator randomVideoCollectionGenerator =
        new RandomVideoCollectionGenerator();
    final AdapteeCollection<Video> videoCollection =
        randomVideoCollectionGenerator.generateListAdapteeVideoCollection(VIDEO_COUNT);
    RendererBuilder<Video> rendererBuilder = new RendererBuilder<Video>().withPrototype(
        new RemovableVideoRenderer(new RemovableVideoRenderer.Listener() {
          @Override public void onRemoveButtonTapped(Video video) {
            ArrayList<Video> clonedList =
                new ArrayList<>((Collection<? extends Video>) videoCollection);
            clonedList.remove(video);
            adapter.diffUpdate(clonedList);
          }
        })).bind(Video.class, RemovableVideoRenderer.class);

    adapter = new RVRendererAdapter<>(rendererBuilder, videoCollection);
  }

  /**
   * Initialize ListVideo with our RVRendererAdapter.
   */
  private void initRecyclerView() {
    recyclerView.setLayoutManager(new LinearLayoutManager(this));
    recyclerView.setAdapter(adapter);
  }
}

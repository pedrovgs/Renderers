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
import butterknife.Bind;
import com.pedrogomez.renderers.AdapteeCollection;
import com.pedrogomez.renderers.DiffRVRendererAdapter;
import com.pedrogomez.renderers.Renderer;
import com.pedrogomez.renderers.RendererBuilder;
import com.pedrogomez.renderers.sample.R;
import com.pedrogomez.renderers.sample.model.RandomVideoCollectionGenerator;
import com.pedrogomez.renderers.sample.model.Video;
import com.pedrogomez.renderers.sample.ui.renderers.FavoriteVideoRenderer;
import com.pedrogomez.renderers.sample.ui.renderers.LikeVideoRenderer;
import com.pedrogomez.renderers.sample.ui.renderers.LiveVideoRenderer;
import com.pedrogomez.renderers.sample.ui.renderers.RemovableVideoRenderer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * RecyclerViewActivity for the Renderers demo.
 *
 * @author Pedro Vicente Gómez Sánchez.
 */
public class RecyclerViewActivity extends BaseActivity {

  private static final int VIDEO_COUNT = 100;

  @Bind(R.id.rv_renderers) RecyclerView recyclerView;

  private DiffRVRendererAdapter<Video> adapter;

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
    RendererBuilder<Video> rendererBuilder = new RendererBuilder<Video>()
        .withPrototype(new RemovableVideoRenderer(new RemovableVideoRenderer.RemoveItemCallback() {
          @Override public void removeItem(Video video) {
            ArrayList<Video> clonedList = new ArrayList<>((Collection<? extends Video>) videoCollection);
            clonedList.remove(video);
            adapter.update(clonedList);
          }
        }))
        .bind(Video.class, RemovableVideoRenderer.class);

    adapter = new DiffRVRendererAdapter<Video>(rendererBuilder, videoCollection, new RecyclerViewDiff());
  }

  /**
   * Initialize ListVideo with our RVRendererAdapter.
   */
  private void initRecyclerView() {
    recyclerView.setLayoutManager(new LinearLayoutManager(this));
    recyclerView.setAdapter(adapter);
  }

  /**
   * Create a list of prototypes to configure RendererBuilder.
   * The list of Renderer<Video> that contains all the possible renderers that our RendererBuilder
   * is going to use.
   *
   * @return Renderer<Video> prototypes for RendererBuilder.
   */
  private List<Renderer<Video>> getRendererVideoPrototypes() {
    List<Renderer<Video>> prototypes = new LinkedList<Renderer<Video>>();
    LikeVideoRenderer likeVideoRenderer = new LikeVideoRenderer();
    prototypes.add(likeVideoRenderer);

    FavoriteVideoRenderer favoriteVideoRenderer = new FavoriteVideoRenderer();
    prototypes.add(favoriteVideoRenderer);

    LiveVideoRenderer liveVideoRenderer = new LiveVideoRenderer();
    prototypes.add(liveVideoRenderer);

    return prototypes;
  }
}

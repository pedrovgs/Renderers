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
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;

import butterknife.Bind;
import com.pedrogomez.renderers.AdapteeCollection;
import com.pedrogomez.renderers.RVRendererAdapter;
import com.pedrogomez.renderers.RVRendererAdapter.SelectionMode;
import com.pedrogomez.renderers.RendererBuilder;
import com.pedrogomez.renderers.sample.R;
import com.pedrogomez.renderers.sample.model.RandomVideoCollectionGenerator;
import com.pedrogomez.renderers.sample.model.Video;
import com.pedrogomez.renderers.sample.ui.renderers.SelectableVideoRenderer;

import java.util.ArrayList;
import java.util.Collection;

import static com.pedrogomez.renderers.RVRendererAdapter.SelectionMode.MULTI;
import static com.pedrogomez.renderers.RVRendererAdapter.SelectionMode.SINGLE;

/**
 * RecyclerViewActivity for the Renderers demo.
 *
 * @author Pedro Vicente Gómez Sánchez.
 */
public class MultiSelectRecyclerViewActivity extends BaseActivity {

  public static final String IS_MULTI_SELECT_EXTRA = "multiselect";

  private static final int VIDEO_COUNT = 100;

  @Bind(R.id.rv_renderers) RecyclerView recyclerView;

  private RVRendererAdapter<Video> adapter;

  private ActionMode.Callback actionModeCallback = new ActionMode.Callback() {

    @Override
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
      return true;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
      return false;
    }

    @Override
    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
      return false;
    }

    @Override
    public void onDestroyActionMode(ActionMode mode) {
      adapter.setSelectable(false);
    }
  };

  @Override protected void onCreate(Bundle savedInstanceState) {
    setContentView(R.layout.activity_recycler_view);
    super.onCreate(savedInstanceState);
    boolean isMultiSelect = getIntent().getBooleanExtra(IS_MULTI_SELECT_EXTRA, false);
    initAdapter(isMultiSelect);
    initRecyclerView();
  }

  private void initAdapter(boolean isMultiSelect) {
    RandomVideoCollectionGenerator randomVideoCollectionGenerator =
        new RandomVideoCollectionGenerator();
    final AdapteeCollection<Video> videoCollection =
        randomVideoCollectionGenerator.generateListAdapteeVideoCollection(VIDEO_COUNT);

    RendererBuilder<Video> rendererBuilder = new RendererBuilder<Video>()
            .withPrototype(new SelectableVideoRenderer(new SelectableVideoRenderer.Listener() {
              @Override
              public void onLongPressClicked(SelectableVideoRenderer renderer) {
                adapter.setSelectable(true);
                renderer.setSelected(true);

                startActionMode(actionModeCallback);
              }

              @Override
              public void onRemoveButtonTapped(Video video) {
                ArrayList<Video> clonedList =
                        new ArrayList<>((Collection<? extends Video>) videoCollection);
                clonedList.remove(video);
                adapter.diffUpdate(clonedList);
              }
            })).bind(Video.class, SelectableVideoRenderer.class);

    SelectionMode selectionMode = isMultiSelect ? MULTI : SINGLE;
    adapter = new RVRendererAdapter<>(rendererBuilder, videoCollection, selectionMode);
  }

  /**
   * Initialize ListVideo with our RVRendererAdapter.
   */
  private void initRecyclerView() {
    recyclerView.setLayoutManager(new LinearLayoutManager(this));
    recyclerView.setAdapter(adapter);
  }
}

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
import android.widget.ListView;
import com.pedrogomez.renderers.AdapteeCollection;
import com.pedrogomez.renderers.RendererAdapter;
import com.pedrogomez.renderers.sample.R;
import com.pedrogomez.renderers.sample.model.RandomVideoCollectionGenerator;
import com.pedrogomez.renderers.sample.model.Video;
import com.pedrogomez.renderers.sample.ui.builder.VideoRendererBuilder;

import butterknife.BindView;

/**
 * ListViewActivity for the Renderers demo.
 *
 * @author Pedro Vicente Gómez Sánchez.
 */
public class ListViewActivity extends BaseActivity {

  private static final int VIDEO_COUNT = 100;

  private RendererAdapter<Video> adapter;

  @BindView(R.id.lv_renderers) ListView listView;

  @Override protected void onCreate(Bundle savedInstanceState) {
    setContentView(R.layout.activity_list_view);
    super.onCreate(savedInstanceState);
    initAdapter();
    initListView();
  }

  /**
   * Initialize RendererAdapter
   */
  private void initAdapter() {
    RandomVideoCollectionGenerator randomVideoCollectionGenerator =
        new RandomVideoCollectionGenerator();
    AdapteeCollection<Video> videoCollection =
        randomVideoCollectionGenerator.generateListAdapteeVideoCollection(VIDEO_COUNT);
    adapter = new RendererAdapter<Video>(new VideoRendererBuilder(), videoCollection);
  }

  /**
   * Initialize ListVideo with our RendererAdapter.
   */
  private void initListView() {
    listView.setAdapter(adapter);
  }
}

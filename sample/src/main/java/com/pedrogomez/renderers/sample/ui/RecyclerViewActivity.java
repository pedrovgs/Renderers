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
import com.pedrogomez.renderers.RVRendererAdapter;
import com.pedrogomez.renderers.sample.R;
import com.pedrogomez.renderers.sample.model.Video;
import javax.inject.Inject;

/**
 * RecyclerViewActivity for the Renderers demo.
 *
 * @author Pedro Vicente Gómez Sánchez.
 */
public class RecyclerViewActivity extends BaseActivity {

  @Inject RVRendererAdapter<Video> adapter;

  @Bind(R.id.rv_renderers) RecyclerView recyclerView;

  @Override protected void onCreate(Bundle savedInstanceState) {
    setContentView(R.layout.activity_recycler_view);
    super.onCreate(savedInstanceState);
    initRecyclerView();
  }

  /**
   * Initialize ListVideo with our RendererAdapter.
   */
  private void initRecyclerView() {
    recyclerView.setLayoutManager(new LinearLayoutManager(this));
    recyclerView.setAdapter(adapter);
  }
}

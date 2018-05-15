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
import android.support.v4.view.ViewPager;

import com.pedrogomez.renderers.AdapteeCollection;
import com.pedrogomez.renderers.VPRendererAdapter;
import com.pedrogomez.renderers.sample.R;
import com.pedrogomez.renderers.sample.model.RandomVideoCollectionGenerator;
import com.pedrogomez.renderers.sample.model.Video;
import com.pedrogomez.renderers.sample.ui.builder.VideoRendererBuilder;

import butterknife.BindView;


/**
 * ViewPagerActivity for the Renderers demo.
 *
 * @author Jc Miñarro
 */
public class ViewPagerActivity extends BaseActivity {

  private static final int VIDEO_COUNT = 100;

  private VPRendererAdapter<Video> adapter;

  @BindView(R.id.vp_renderers) ViewPager viewPager;

  @Override protected void onCreate(Bundle savedInstanceState) {
    setContentView(R.layout.activity_view_pager);
    super.onCreate(savedInstanceState);
    initAdapter();
    initListView();
  }

  /**
   * Initialize VPRendererAdapter
   */
  private void initAdapter() {
    RandomVideoCollectionGenerator randomVideoCollectionGenerator =
        new RandomVideoCollectionGenerator();
    AdapteeCollection<Video> videoCollection =
        randomVideoCollectionGenerator.generateListAdapteeVideoCollection(VIDEO_COUNT);
    adapter = new VPRendererAdapter<Video>(new VideoRendererBuilder(), videoCollection);
  }

  /**
   * Initialize ViewPager with our VPRendererAdapter.
   */
  private void initListView() {
    viewPager.setAdapter(adapter);
  }
}

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
import butterknife.InjectView;
import com.pedrogomez.renderers.RendererAdapter;
import com.pedrogomez.renderers.sample.R;
import com.pedrogomez.renderers.sample.model.Video;
import javax.inject.Inject;

/**
 * ListViewActivity for the Renderers demo.
 *
 * @author Pedro Vicente Gómez Sánchez.
 */
public class ListViewActivity extends BaseActivity {

  @Inject RendererAdapter<Video> adapter;

  @InjectView(R.id.lv_renderers) ListView listView;

  @Override protected void onCreate(Bundle savedInstanceState) {
    setContentView(R.layout.activity_list_view);
    super.onCreate(savedInstanceState);
    initListView();
  }

  /**
   * Initialize ListVideo with our RendererAdapter.
   */
  private void initListView() {
    listView.setAdapter(adapter);
  }
}

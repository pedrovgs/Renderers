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
 * WIVideoHOUVideo WARRANVideoIES OR CONDIVideoIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.pedrogomez.renderers.sample.ui;

import android.arch.paging.DataSource;
import android.arch.paging.PagedList;
import android.arch.paging.PositionalDataSource;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.pedrogomez.renderers.ListAdapteeCollection;
import com.pedrogomez.renderers.PagedRendererAdapter;
import com.pedrogomez.renderers.RendererBuilder;
import com.pedrogomez.renderers.sample.R;
import com.pedrogomez.renderers.sample.model.RandomVideoCollectionGenerator;
import com.pedrogomez.renderers.sample.model.Video;
import com.pedrogomez.renderers.sample.ui.renderers.RemovableVideoRenderer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import butterknife.BindView;

public class PagedRecyclerViewActivity extends BaseActivity {

  private static final int VIDEO_COUNT = 20;
  private static final int PAGE_SIZE = 10;

  @BindView(R.id.rv_renderers) RecyclerView recyclerView;

  private PagedRendererAdapter<Video> adapter;

  @Override protected void onCreate(Bundle savedInstanceState) {
    setContentView(R.layout.activity_recycler_view);
    super.onCreate(savedInstanceState);
    initAdapter();
    initRecyclerView();
  }

  private void initAdapter() {
    RandomVideoCollectionGenerator randomVideoCollectionGenerator =
        new RandomVideoCollectionGenerator();
    final ListAdapteeCollection<Video> videoCollection =
            (ListAdapteeCollection<Video>) randomVideoCollectionGenerator
                    .generateListAdapteeVideoCollection(VIDEO_COUNT);

    RendererBuilder<Video> rendererBuilder = new RendererBuilder<Video>().withPrototype(
        new RemovableVideoRenderer(new RemovableVideoRenderer.Listener() {

          @Override
          public void onRemoveButtonTapped(Video video) {
            ArrayList<Video> clonedList =
                    new ArrayList<>(videoCollection);
            clonedList.remove(video);
          }
        })).bind(Video.class, RemovableVideoRenderer.class);

    DiffUtil.ItemCallback<Video> itemCallback = getDIffItemCallback();
    adapter = new PagedRendererAdapter<>(rendererBuilder, itemCallback);

    /*
    The following lines of code will probably not be needed in real apps.
    I needed to create a DataSource from a ListAdapteeCollection<Video>
    since this data is not backed by a database.
    In most cases you would get a DataSource.Factory from Room.
    This PositionalDataSource implementation is very naive
    since it hard codes the page size. In reality, Video should have a property used
    to determine to which page it belongs.
    The only code of interest is the PagedList.BoundaryCallback<Video>,
    it allows you to fetch more data when the RecyclerView reaches the bottom of the page.
     */
    final DataSource.Factory<Integer, Video> factory = getDataSourceFactory(videoCollection);
    final PositionalDataSource<Video> dataSource = getVideoPositionalDataSource(factory);
    final PagedList<Video> pagedList = getPagedList(dataSource);

    adapter.submitList(pagedList);

  }

  @NonNull
  private DataSource.Factory<Integer, Video> getDataSourceFactory(final ListAdapteeCollection<Video> videoCollection) {
    return new DataSource.Factory<Integer, Video>() {
      @Override
      public DataSource<Integer, Video> create() {
        return new PositionalDataSource<Video>() {
          @Override
          public void loadInitial(@NonNull LoadInitialParams params, @NonNull LoadInitialCallback<Video> callback) {
            final int totalCount = videoCollection.size();
            List<Video> sublist = videoCollection.subList(0, PAGE_SIZE);
            callback.onResult(sublist, 0, totalCount);
          }

          @Override
          public void loadRange(@NonNull LoadRangeParams params, @NonNull LoadRangeCallback<Video> callback) {
            callback.onResult(videoCollection.subList(params.startPosition,
                    params.startPosition + params.loadSize));
          }
        };
      }
    };
  }

  @NonNull
  private DiffUtil.ItemCallback<Video> getDIffItemCallback() {
    return new DiffUtil.ItemCallback<Video>() {
      @Override
      public boolean areItemsTheSame(Video oldItem, Video newItem) {
        return oldItem.getTitle().equals(newItem.getTitle());
      }

      @Override
      public boolean areContentsTheSame(Video oldItem, Video newItem) {
        return oldItem.equals(newItem);
      }
    };
  }

  @NonNull
  private PagedList<Video> getPagedList(final PositionalDataSource<Video> dataSource) {
    return new PagedList.Builder<>(dataSource, PAGE_SIZE)
            .setFetchExecutor(Executors.newSingleThreadExecutor())
            .setNotifyExecutor(new MainThreadExecutor())
            .setBoundaryCallback(new PagedList.BoundaryCallback<Video>() {
              @Override
              public void onItemAtEndLoaded(@NonNull Video itemAtEnd) {

                final PositionalDataSource.LoadRangeParams loadRangeParams =
                        new PositionalDataSource.LoadRangeParams(PAGE_SIZE, 10);
                final PositionalDataSource.LoadRangeCallback<Video> loadRangeCallback =
                        new PositionalDataSource.LoadRangeCallback<Video>() {
                  @Override
                  public void onResult(@NonNull List<Video> data) {

                  }
                };

                Executors.newSingleThreadExecutor().execute(new Runnable() {
                  @Override
                  public void run() {
                    try {
                      Thread.sleep(5000);
                    } catch (InterruptedException e) {
                      e.printStackTrace();
                    }
                    dataSource.loadRange(loadRangeParams, loadRangeCallback);
                  }
                });
              }
            })
            .build();
  }

  private PositionalDataSource<Video> getVideoPositionalDataSource(DataSource.Factory<Integer, Video> factory) {
    return (PositionalDataSource<Video>) factory.create();
  }

  private static class MainThreadExecutor implements Executor {
    final Handler mHandler = new Handler(Looper.getMainLooper());
    @Override
    public void execute(@NonNull Runnable command) {
      mHandler.post(command);
    }

  }

  private void initRecyclerView() {
    recyclerView.setLayoutManager(new LinearLayoutManager(this));
    recyclerView.setAdapter(adapter);
  }
}

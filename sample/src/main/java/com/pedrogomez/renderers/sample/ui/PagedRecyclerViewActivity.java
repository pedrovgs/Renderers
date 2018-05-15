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

import android.arch.paging.PagedList;
import android.arch.paging.RxPagedListBuilder;
import android.arch.persistence.room.Room;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.pedrogomez.renderers.ListAdapteeCollection;
import com.pedrogomez.renderers.PagedRendererAdapter;
import com.pedrogomez.renderers.RendererBuilder;
import com.pedrogomez.renderers.sample.R;
import com.pedrogomez.renderers.sample.database.Database;
import com.pedrogomez.renderers.sample.model.RandomVideoCollectionGenerator;
import com.pedrogomez.renderers.sample.model.Video;
import com.pedrogomez.renderers.sample.ui.renderers.RemovableVideoRenderer;

import butterknife.BindView;
import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class PagedRecyclerViewActivity extends BaseActivity {

    private static final int VIDEO_COUNT = 20;
    private static final int PAGE_SIZE = 10;
    private ListAdapteeCollection<Video> videoCollection;

    @BindView(R.id.rv_renderers)
    RecyclerView recyclerView;

    private PagedRendererAdapter<Video> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_recycler_view);
        super.onCreate(savedInstanceState);
        initAdapter();
        initRecyclerView();
    }

    private void initAdapter() {
        RandomVideoCollectionGenerator randomVideoCollectionGenerator =
                new RandomVideoCollectionGenerator();
        videoCollection =
                (ListAdapteeCollection<Video>) randomVideoCollectionGenerator
                        .generateListAdapteeVideoCollection(VIDEO_COUNT);

        RendererBuilder<Video> rendererBuilder = new RendererBuilder<>(
                new RemovableVideoRenderer(new RemovableVideoRenderer.Listener() {
            @Override
            public void onRemoveButtonTapped(Video video) {

            }
        }));

        DiffUtil.ItemCallback<Video> itemCallback = getDIffItemCallback();
        adapter = new PagedRendererAdapter<>(rendererBuilder, itemCallback);

        //If you support place holders (enabled by default), set a default item to display
        // while the actual item loads

        Video defaultVideo = getDefaultVideoToUseForPlaceholder();
        adapter.setDefaultItem(defaultVideo);

        //This is just for simplicity, please do not create a database on your Activity's onCreate()
        final Database data = Room.databaseBuilder(this, Database.class, "renderers.db").build();
        PagedList.Config.Builder configBuilder = new PagedList.Config.Builder();
        configBuilder.setPageSize(PAGE_SIZE);
        PagedList.Config config = configBuilder.build();
        new RxPagedListBuilder<>(data.videoDao().videos(), config)
                .setBoundaryCallback(new PagedList.BoundaryCallback<Video>() {
                    @Override
                    public void onZeroItemsLoaded() {
                        Completable.fromAction(new Action() {
                            @Override
                            public void run() throws Exception {
                                data.videoDao().insertVideos(videoCollection);

                            }
                        }).subscribeOn(Schedulers.single()).subscribe();
                    }

                    @Override
                    public void onItemAtEndLoaded(@NonNull Video itemAtEnd) {
                        super.onItemAtEndLoaded(itemAtEnd);
                        RandomVideoCollectionGenerator randomVideoCollectionGenerator =
                                new RandomVideoCollectionGenerator();
                        videoCollection =
                                (ListAdapteeCollection<Video>) randomVideoCollectionGenerator
                                        .generateListAdapteeVideoCollection(VIDEO_COUNT);
                        Completable.fromAction(new Action() {
                            @Override
                            public void run() throws Exception {
                                data.videoDao().insertVideos(videoCollection);

                            }
                        }).subscribeOn(Schedulers.single()).subscribe();
                    }
                }).buildObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<PagedList<Video>>() {
                    @Override
                    public void accept(PagedList<Video> videos) throws Exception {
                        adapter.submitList(videos);
                    }
                });

    }

    @NonNull
    private Video getDefaultVideoToUseForPlaceholder() {
        Video item = new Video();
        item.setFavorite(true);
        item.setThumbnail("http://example.com");
        item.setTitle("PLACEHOLDER");
        item.setLiked(false);
        item.setLive(true);
        return item;
    }

    @NonNull
    private DiffUtil.ItemCallback<Video> getDIffItemCallback() {
        return new DiffUtil.ItemCallback<Video>() {
            @Override
            public boolean areItemsTheSame(Video oldItem, Video newItem) {
                //They will never be the same. This is on purpose to demonstrate placeholder support
                return oldItem.id == newItem.id;
            }

            @Override
            public boolean areContentsTheSame(Video oldItem, Video newItem) {
                return oldItem.equals(newItem);
            }
        };
    }

    private void initRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }
}

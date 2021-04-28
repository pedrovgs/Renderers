package com.pedrogomez.renderers.sample.ui;

import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import com.pedrogomez.renderers.RVListRendererAdapter;
import com.pedrogomez.renderers.sample.R;
import com.pedrogomez.renderers.sample.model.RandomVideoCollectionGenerator;
import com.pedrogomez.renderers.sample.model.Video;
import com.pedrogomez.renderers.sample.ui.builder.VideoRendererBuilder;
import com.pedrogomez.renderers.sample.ui.diffing.VideoItemDiffCallback;

import java.util.List;

public class RecyclerViewListAdapterActivity extends BaseActivity {

    private static final int VIDEO_COUNT = 100;

    @BindView(R.id.rv_renderers) RecyclerView recyclerView;

    private RVListRendererAdapter<Video> adapter;

    @Override protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_recycler_view);
        super.onCreate(savedInstanceState);
        initAdapter();
        initRecyclerView();
    }

    private void initAdapter() {
        final RandomVideoCollectionGenerator randomVideoCollectionGenerator = new RandomVideoCollectionGenerator();
        final List<Video> videoCollection = randomVideoCollectionGenerator.generate(VIDEO_COUNT);
        adapter = new RVListRendererAdapter<>(new VideoRendererBuilder(), new VideoItemDiffCallback(), videoCollection);
    }

    private void initRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }
}

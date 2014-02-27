package com.pedrogomez.renderers.ui;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.ListView;
import android.widget.Toast;
import com.pedrogomez.renderers.R;
import com.pedrogomez.renderers.Renderer;
import com.pedrogomez.renderers.RendererAdapter;
import com.pedrogomez.renderers.RendererBuilder;
import com.pedrogomez.renderers.model.RandomVideoCollectionGenerator;
import com.pedrogomez.renderers.model.Video;
import com.pedrogomez.renderers.model.VideoCollection;
import com.pedrogomez.renderers.ui.builder.VideoRendererBuilder;
import com.pedrogomez.renderers.ui.renderers.FavoriteVideoRenderer;
import com.pedrogomez.renderers.ui.renderers.LikeVideoRenderer;
import com.pedrogomez.renderers.ui.renderers.LiveVideoRenderer;
import com.pedrogomez.renderers.ui.renderers.VideoRenderer;

import java.util.LinkedList;
import java.util.List;

/**
 * MainActivity for the Renderers demo.
 *
 * @author Pedro Vicente Gómez Sánchez.
 */
public class MainActivity extends Activity {

    /*
     * Constants
     */

    private static final int VIDEO_COUNT = 50;

    /*
     * Attributes
     */

    private RandomVideoCollectionGenerator randomVideoCollectionGenerator = new RandomVideoCollectionGenerator();

    private VideoCollection videos;

    /*
     * Widgets
     */

    private ListView listView;
    private RendererAdapter<Video> adapter;


    /*
     * Activity lifecycle
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mapGUI();
        initFakeData();
        initAdapter();
        initListView();
    }

    /*
     * Auxiliary methods
     */

    private void mapGUI() {
        listView = (ListView) findViewById(R.id.lv_renderers);
    }


    private void initFakeData() {
        videos = randomVideoCollectionGenerator.generate(VIDEO_COUNT);
    }


    private void initAdapter() {
        RendererBuilder rendererBuilder = getVideoRendererBuilder();
        LayoutInflater layoutInflater = getLayoutInflater();
        adapter = new RendererAdapter<Video>(layoutInflater, rendererBuilder, videos);
    }

    private RendererBuilder getVideoRendererBuilder() {
        List<Renderer<Video>> prototypes = getPrototypes();
        return new VideoRendererBuilder(prototypes);
    }

    private List<Renderer<Video>> getPrototypes() {
        Context context = getBaseContext();

        List<Renderer<Video>> prototypes = new LinkedList<Renderer<Video>>();
        LikeVideoRenderer likeVideoRenderer = new LikeVideoRenderer(context);
        likeVideoRenderer.setListener(onVideoClickedListener);
        FavoriteVideoRenderer favoriteVideoRenderer = new FavoriteVideoRenderer(context);
        favoriteVideoRenderer.setListener(onVideoClickedListener);
        LiveVideoRenderer liveVideoRenderer = new LiveVideoRenderer(context);
        liveVideoRenderer.setListener(onVideoClickedListener);

        prototypes.add(likeVideoRenderer);
        prototypes.add(favoriteVideoRenderer);
        prototypes.add(liveVideoRenderer);

        return prototypes;
    }


    private void initListView() {
        listView.setAdapter(adapter);
    }

    private VideoRenderer.OnVideoClicked onVideoClickedListener = new VideoRenderer.OnVideoClicked() {
        @Override
        public void onVideoClicked(Video video) {
            Toast.makeText(getBaseContext(), "Video clicked. Title = " + video.getTitle(), Toast.LENGTH_LONG).show();
        }
    };

}

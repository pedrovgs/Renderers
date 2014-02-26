package com.pedrogomez.renderers;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.ListView;
import com.pedro.renderers.Renderer;
import com.pedro.renderers.RendererAdapter;
import com.pedro.renderers.RendererBuilder;
import com.pedrogomez.renderers.renderers.FavoriteVideoRenderer;
import com.pedrogomez.renderers.renderers.LikeVideoRenderer;
import com.pedrogomez.renderers.renderers.LiveVideoRenderer;
import com.pedrogomez.renderers.renderers.VideoRendererBuilder;

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

    private List<Video> videos;

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
        RendererBuilder<Video> videoRendererBuilder = new VideoRendererBuilder(prototypes);
        return videoRendererBuilder;
    }

    private List<Renderer<Video>> getPrototypes() {
        Context context = getBaseContext();

        List<Renderer<Video>> prototypes = new LinkedList<Renderer<Video>>();
        LikeVideoRenderer likeVideoRenderer = new LikeVideoRenderer(context);
        FavoriteVideoRenderer favoriteVideoRenderer = new FavoriteVideoRenderer(context);
        LiveVideoRenderer liveVideoRenderer = new LiveVideoRenderer(context);

        prototypes.add(likeVideoRenderer);
        prototypes.add(favoriteVideoRenderer);
        prototypes.add(liveVideoRenderer);

        return prototypes;
    }


    private void initListView() {
        listView.setAdapter(adapter);
    }

}

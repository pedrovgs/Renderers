Renderers
=========


Are you bored of creating adapters again and again each time you have to implement a ListView?

Are you bored of using ViewHolders and create getView methods with thousand of lines full of if/else if/else sentences?

Renderers it's an Android library created to avoid all the Adapter/ListView boilerplate needed to create a new adapter and all the spaghetti code that developers used to create following the ViewHolder classic implementation.

This Android library offers you two main classes to extend and create your own rendering algorithms out of your adapter implementation.

Renderers is an easy way to work with android ListView and Adapter classes. With this library you only have to create your renderers and declare the mapping between the object to render and the renderer.

You can find implementation details in this talks:

[Software Design Patterns on Android Video][4]

[Software Design Patterns on Android Slides][5]


Screenshots
-----------

![Demo Screenshot][1]

Usage
-----

To use Renderers Android library and get your ListView working you only have to follow three steps:

* 1. Create your renderer or renderers extending ```Renderer<T>``. Inside your renderers you will have to implement some methods to inflate the layout you want to render and implement the rendering algorithm.

```java
public abstract class VideoRenderer extends Renderer<Video> {

    /*
     * Attributes
     */
    private final Context context;

    private OnVideoClicked listener;

    /*
     * Constructor
     */

    public VideoRenderer(Context context) {
        this.context = context;
    }
    /*
     * Widgets
     */

    private ImageView thumbnail;
    private TextView title;
    private ImageView marker;
    private TextView label;

    /**
     * Inflate the main layout used to render videos in the list view.
     *
     * @param inflater LayoutInflater service to inflate.
     * @param parent   ViewGroup used to inflate xml.
     * @return view inflated.
     */
    @Override
    protected View inflate(LayoutInflater inflater, ViewGroup parent) {
        return inflater.inflate(R.layout.video_renderer, parent, false);
    }

    /**
     * Maps all the view elements from the xml declaration to members of this renderer.
     *
     * @param rootView
     */
    @Override
    protected void setUpView(View rootView) {
        thumbnail = (ImageView) rootView.findViewById(R.id.iv_thumbnail);
        title = (TextView) rootView.findViewById(R.id.tv_title);
        marker = (ImageView) rootView.findViewById(R.id.iv_marker);
        label = (TextView) rootView.findViewById(R.id.tv_label);
    }

    /**
     * Insert external listeners in some widgets.
     *
     * @param rootView
     */
    @Override
    protected void hookListeners(View rootView) {
        rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    Video video = getContent();
                    listener.onVideoClicked(video);
                }
            }
        });
    }

    /**
     * Main render algorithm based on render the video thumbnail, render the title, render the marker and the label.
     */
    @Override
    protected void render() {
        Video video = getContent();
        renderThumbnail(video);
        renderTitle(video);
        renderMarker(video);
        renderLabel();
    }

    /**
     * Use picasso to render the video thumbnail into the thumbnail widget using a temporal placeholder.
     *
     * @param video to get the rendered thumbnail.
     */
    private void renderThumbnail(Video video) {
        Picasso.with(context).load(video.getResourceThumbnail()).placeholder(R.drawable.placeholder).into(thumbnail);
    }


    /**
     * Render video title into the title widget.
     *
     * @param video to get the video title.
     */
    private void renderTitle(Video video) {
        this.title.setText(video.getTitle());
    }

    public void setListener(OnVideoClicked listener) {
        this.listener = listener;
    }

    /*
     * Protected methods
     */

    protected TextView getLabel() {
        return label;
    }

    protected ImageView getMarker() {
        return marker;
    }

    protected Context getContext() {
        return context;
    }

    /*
     * Abstract methods.
     *
     * This methods are part of the render algorithm and are going to be implemented by VideoRenderer subtypes.
     */

    protected abstract void renderLabel();

    protected abstract void renderMarker(Video video);

    /*
     * Interface to represent a video click.
     */

    public interface OnVideoClicked {
        void onVideoClicked(final Video video);
    }

}
```


You can use [Jake Wharton's][2] [Butterknife][3] library to avoid findViewById calls inside your renderers if you want.


* 2. Create a RendererBuilder with a renderer prototype collection and declare the mapping between the content to render
and the renderer used.

```java
public class VideoRendererBuilder extends RendererBuilder<Video> {


    public VideoRendererBuilder(List<Renderer<Video>> prototypes) {
        super(prototypes);
    }

    /**
     * Method to declare Video-VideoRenderer mapping.
     * Favorite videos will be rendered using FavoriteVideoRenderer.
     * Live videos will be rendered using LiveVideoRenderer.
     * Liked videos will be rendered using LikeVideoRenderer.
     *
     * @param content used to map object-renderers.
     * @return VideoRenderer subtype class.
     */
    @Override
    protected Class getPrototypeClass(Video content) {
        Class prototypeClass;
        if (content.isFavorite()) {
            prototypeClass = FavoriteVideoRenderer.class;
        } else if (content.isLive()) {
            prototypeClass = LiveVideoRenderer.class;
        } else {
            prototypeClass = LikeVideoRenderer.class;
        }
        return prototypeClass;
    }

}
```

* 3. Instantiate a new RendererAdapter using the RendererBuilder and one AdapteeCollection.

```java
private void initAdapter() {
    RendererBuilder rendererBuilder = getVideoRendererBuilder();
    LayoutInflater layoutInflater = getLayoutInflater();
    adapter = new RendererAdapter<Video>(layoutInflater, rendererBuilder, videos);
    listView.setAdapter(adapter);
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
    prototypes.add(likeVideoRenderer);

    FavoriteVideoRenderer favoriteVideoRenderer = new FavoriteVideoRenderer(context);
    favoriteVideoRenderer.setListener(onVideoClickedListener);
    prototypes.add(favoriteVideoRenderer);

    LiveVideoRenderer liveVideoRenderer = new LiveVideoRenderer(context);
    liveVideoRenderer.setListener(onVideoClickedListener);
    prototypes.add(liveVideoRenderer);

    return prototypes;
}

```

Usage
-----

Download the project, compile it using ```mvn clean instal``` import ``renderers-1.0.0.jar`` into your project.

This library will be uploaded to maven central repository asap.

Developed By
------------

* Pedro Vicente Gómez Sánchez - <pedrovicente.gomez@gmail.com>

<a href="https://twitter.com/pedro_g_s">
  <img alt="Follow me on Twitter" src="http://imageshack.us/a/img812/3923/smallth.png" />
</a>
<a href="http://www.linkedin.com/in/pedrovg">
  <img alt="Add me to Linkedin" src="http://imageshack.us/a/img41/7877/smallld.png" />
</a>



License
-------

    Copyright 2014 Pedro Vicente Gómez Sánchez

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.


[1]: https://github.com/pedrovgs/Renderers/blob/master/art/Screenshot_demo_1.png
[2]: https://github.com/JakeWharton
[3]: https://github.com/JakeWharton/butterknife
[4]: https://vimeo.com/87450999
[5]: http://www.slideshare.net/PedroVicenteGmezSnch/newsfeed?nf_redirect=true
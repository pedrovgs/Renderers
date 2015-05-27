Renderers [![Build Status](https://travis-ci.org/pedrovgs/Renderers.svg?branch=master)](https://travis-ci.org/pedrovgs/Renderers)  [![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.pedrovgs/renderers/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.github.pedrovgs/renderers) [![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-Renderers-brightgreen.svg?style=flat)](https://android-arsenal.com/details/1/1195)
=========


Are you bored of creating adapters again and again each time you have to implement a ListView or a RecyclerView?

Are you bored of using ViewHolders and create getView/onCreateViewHolder/onBindViewHolder methods with thousand of lines full of if/else if/else sentences?

Renderers is an Android library created to avoid all the Adapter/ListView/RecyclerView boilerplate needed to create a new adapter and all the spaghetti code that developers used to create following the ViewHolder classic implementation.

This Android library offers you two main classes to extend and create your own rendering algorithms out of your adapter implementation.

Renderers is an easy way to work with android ListView/RecyclerView and Adapter classes. With this library you only have to create your Renderer classes and declare the mapping between the object to render and the Renderer.

You can find implementation details in this talks:

[Software Design Patterns on Android Video][4]

[Software Design Patterns on Android Slides][5]


Screenshots
-----------

![Demo Screenshot][1]

Usage
-----

To use Renderers Android library and get your ListView/RecyclerView working you only have to follow three steps:

* 1. Create your Renderer or Renderers extending ``Renderer<T>``. Inside your Renderer classes you will have to implement some methods to inflate the layout you want to render and implement the rendering algorithm.

```java
public abstract class VideoRenderer extends Renderer<Video> {

       private final Context context;

       private OnVideoClicked listener;


       public VideoRenderer(Context context) {
           this.context = context;
       }

       @InjectView(R.id.iv_thumbnail)
       ImageView thumbnail;
       @InjectView(R.id.tv_title)
       TextView title;
       @InjectView(R.id.iv_marker)
       ImageView marker;
       @InjectView(R.id.tv_label)
       TextView label;

       @Override
       protected View inflate(LayoutInflater inflater, ViewGroup parent) {
           View inflatedView = inflater.inflate(R.layout.video_renderer, parent, false);
           ButterKnife.inject(this, inflatedView);
           return inflatedView;
       }


       @OnClick(R.id.iv_thumbnail)
       void onVideoClicked() {
           if (listener != null) {
               Video video = getContent();
               listener.onVideoClicked(video);
           }
       }

       @Override
       protected void render() {
           Video video = getContent();
           renderThumbnail(video);
           renderTitle(video);
           renderMarker(video);
           renderLabel();
       }

       private void renderThumbnail(Video video) {
           Picasso.with(context).load(video.getResourceThumbnail()).placeholder(R.drawable.placeholder).into(thumbnail);
       }

       private void renderTitle(Video video) {
           this.title.setText(video.getTitle());
       }

       public void setListener(OnVideoClicked listener) {
           this.listener = listener;
       }

       protected TextView getLabel() {
           return label;
       }

       protected ImageView getMarker() {
           return marker;
       }

       protected Context getContext() {
           return context;
       }

       protected abstract void renderLabel();

       protected abstract void renderMarker(Video video);



}
```


You can use [Jake Wharton's][2] [Butterknife][3] library to avoid findViewById calls inside your Renderers if you want and [Jake Wharton's][2] [Dagger] [6] library to inject all your dependencies and keep your activities clean of the library initialization code. But use third party libraries is not mandatory.


* 2. Create a RendererBuilder with a Renderer prototypes collection and declare the mapping between the content to render and the Renderer used.

```java
public class VideoRendererBuilder extends RendererBuilder<Video> {

  @Inject
  public VideoRendererBuilder(Context context, VideoRenderer.OnVideoClicked onVideoClicked) {
    Collection<Renderer<Video>> prototypes = getPrototypes(context, onVideoClicked);
    setPrototypes(prototypes);
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

  /**
   * Create a list of prototypes to configure RendererBuilder.
   * The list of Renderer<Video> that contains all the possible renderers that our RendererBuilder
   * is going to use.
   *
   * @return Renderer<Video> prototypes for RendererBuilder.
   */
  private List<Renderer<Video>> getPrototypes(Context context,
      VideoRenderer.OnVideoClicked onVideoClickedListener) {
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
}
```

* 3. Initialize your ListView/RecyclerView with your RendererBuilder<T> and your AdapteeCollection inside Activities and Fragments. **You can use ListAdapteeCollection or create your own implementation creating a class which implements AdapteeCollection to configure your RendererAdapter/RVRendererAdapter.**

```java
private void initListView() {
    listView.setAdapter(adapter);
}
```

or

```java
private void initListView() {
    recyclerView.setAdapter(adapter);
}
```

The sample code is using [Dagger][6] and [ButterKnife][4] library to avoid initialize some entities and findViewById() methods, but you can use this library without third party libraries and provide that dependencies yourself.

**Remember if you are going to use RecyclerView instead of ListView you'll have to use RVRendererAdapter instead of RendererAdapter.** 

**If you was using Renderers v1.5 or a lower version and you want to use your Renderers with RecyclerView the only thing you only have to do is to replace your RendererAdapter extension with a RVRendererAdapter.**

Usage
-----

Download the project, compile it using ```mvn clean install``` import ``renderers-1.5.jar`` into your project.

Or declare it into your pom.xml

```xml
<dependency>
    <groupId>com.github.pedrovgs</groupId>
    <artifactId>renderers</artifactId>
    <version>1.5</version>
</dependency>
```


Or into your build.gradle
```groovy
dependencies{
    compile 'com.github.pedrovgs:renderers:1.5'
}
```


Developed By
------------

* Pedro Vicente Gómez Sánchez - <pedrovicente.gomez@gmail.com>

<a href="https://twitter.com/pedro_g_s">
  <img alt="Follow me on Twitter" src="http://imageshack.us/a/img812/3923/smallth.png" />
</a>
<a href="https://es.linkedin.com/in/pedrovgs">
  <img alt="Add me to Linkedin" src="http://imageshack.us/a/img41/7877/smallld.png" />
</a>

Who's using it
--------------

* [El Rubius Vídeos][7]
* [Tuenti][8]
* [Finge Gesture Launcher][9]
* [Cabify] [10]
* [InfoJobs] [11]

*Does your app use Renderers? If you want to be featured on this list drop me a line.*

Contributors
------------

* [Pedro Vicente Gómez Sánchez][12]
* [Rayco Araña][13]

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


[1]: http://raw.github.com/pedrovgs/Renderers/master/art/Screenshot_demo_1.png
[2]: https://github.com/JakeWharton
[3]: https://github.com/JakeWharton/butterknife
[4]: http://media.fib.upc.edu/fibtv/streamingmedia/view/2/930
[5]: http://www.slideshare.net/PedroVicenteGmezSnch/software-design-patterns-on-android
[6]: https://github.com/square/dagger
[7]: https://play.google.com/store/apps/details?id=com.nero.elrubiusomg
[8]: https://play.google.com/store/apps/details?hl=es&id=com.tuenti.messenger
[9]: https://play.google.com/store/apps/details?id=com.carlosdelachica.finger
[10]: https://play.google.com/store/apps/details?id=com.cabify.rider
[11]: https://play.google.com/store/apps/details?id=net.infojobs.mobile.android
[12]: https://github.com/pedrovgs
[13]: https://github.com/raycoarana

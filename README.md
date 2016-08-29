Renderers [![Build Status](https://travis-ci.org/pedrovgs/Renderers.svg?branch=master)](https://travis-ci.org/pedrovgs/Renderers) [![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.pedrovgs/renderers/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.github.pedrovgs/renderers) [![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-Renderers-green.svg?style=true)](https://android-arsenal.com/details/1/1195)
=========

Are you bored of creating adapters again and again when you have to implement a ``ListView`` or a ``RecyclerView``?

Are you bored of using ``ViewHolders`` and create getView/onCreateViewHolder/onBindViewHolder methods with thousands of lines full of if/else if/else sentences?

**Renderers is an Android library created to avoid all the Adapter/ListView/RecyclerView boilerplate** needed to create a new adapter and all the spaghetti code that developers used to create following the ``ViewHolder`` classic implementation.

This Android library offers you two main classes to instantiate or extend and create your own rendering algorithms out of your adapter implementation.

**Renderers is an easy way to work with android ListView/RecyclerView and Adapter classes**. With this library you only have to create your ``Renderer`` classes and declare the mapping between the object to render and the ``Renderer``. The ``Renderer`` will use the model information to draw your user interface.

You can find implementation details in this talks:

[Software Design Patterns on Android Video][4]

[Software Design Patterns on Android Slides][5]


Screenshots
-----------

![Demo Screenshot][1]

Usage
-----

To use Renderers Android library and get your ListView/RecyclerView working you only have to follow three steps:

* 1. Create your ``Renderer`` class or classes extending ``Renderer<T>``. Inside your ``Renderer`` classes you will have to implement some methods to inflate the layout you want to render and implement the rendering algorithm.

```java
public class VideoRenderer extends Renderer<Video> {

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
           Video video = getContent();
           Log.d("Renderer", "Clicked: " + video.getTitle());
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

       protected TextView getLabel() {
           return label;
       }

       protected ImageView getMarker() {
           return marker;
       }
       
       //If you don't use ButterKnife you have to implement these methods.
       
       /**
        * Maps all the view elements from the xml declaration to members of this renderer.
        */
       @Override protected void setUpView(View rootView) {
        /*
         * Empty implementation substituted with the usage of ButterKnife library by Jake Wharton.
         */
       }

       /**
        * Insert external listeners in some widgets.
       */
       @Override protected void hookListeners(View rootView) {
        /*
         * Empty implementation substituted with the usage of ButterKnife library by Jake Wharton.
         */
       }
}
```

You can use [Jake Wharton's][2] [Butterknife][3] library to avoid findViewById calls inside your Renderers if you want. But the usage of third party libraries is not mandatory.

* 2. Instantiate a ``RendererBuilder`` with a ``Renderer``.

```java
Renderer<Video> renderer = new LikeVideoRenderer();
RendererBuilder<Video> rendererBuilder = new RendererBuilder<Video>(renderer);
```

If you need to map different object instances to different ``Renderer`` implementations you can use ``RendererBuilder.bind`` methods:

```java
RendererBuilder<Video> rendererBuilder = new RendererBuilder<Video>()
        .bind(Video.class, new LikeVideoRenderer());
```

If your binding is more complex and it's not based on different classes but in properties of these classes you can also extend ``RendererBuilder`` and override ``getPrototypeClass`` to customize your binding:

```java
public class VideoRendererBuilder extends RendererBuilder<Video> {

  public VideoRendererBuilder() {
    List<Renderer<Video>> prototypes = getVideoRendererPrototypes();
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
  private List<Renderer<Video>> getVideoRendererPrototypes() {
    List<Renderer<Video>> prototypes = new LinkedList<Renderer<Video>>();
    LikeVideoRenderer likeVideoRenderer = new LikeVideoRenderer();
    prototypes.add(likeVideoRenderer);

    FavoriteVideoRenderer favoriteVideoRenderer = new FavoriteVideoRenderer();
    prototypes.add(favoriteVideoRenderer);

    LiveVideoRenderer liveVideoRenderer = new LiveVideoRenderer();
    prototypes.add(liveVideoRenderer);

    return prototypes;
  }
}
```

* 3. Initialize your ``ListView`` or ``RecyclerView`` with your ``RendererBuilder`` and your ``AdapteeCollection`` instances inside your Activity or Fragment. **You can use ``ListAdapteeCollection`` or create your own implementation creating a class which implements ``AdapteeCollection`` to configure your ``RendererAdapter`` or ``RVRendererAdapter``.**

```java
private void initListView() {
    adapter = new RendererAdapter<Video>(rendererBuilder, adapteeCollection);
    listView.setAdapter(adapter);
}
```

or

```java
private void initListView() {
    adapter = new RVRendererAdapter<Video>(rendererBuilder, adapteeCollection);
    recyclerView.setAdapter(adapter);
}
```

**Remember if you are going to use ``RecyclerView`` instead of ``ListView`` you'll have to use ``RVRendererAdapter`` instead of ``RendererAdapter``.**

Usage
-----

Download the project, compile it using ```mvn clean install``` import ``renderers-3.0.0.aar`` into your project.

Or declare it into your pom.xml

```xml
<dependency>
    <groupId>com.github.pedrovgs</groupId>
    <artifactId>renderers</artifactId>
    <version>3.2.0</version>
    <type>aar</type>
</dependency>
```


Or into your build.gradle
```groovy
dependencies{
    compile 'com.github.pedrovgs:renderers:3.2.0'
}
```


Developed By
------------

* Pedro Vicente Gómez Sánchez - <pedrovicente.gomez@gmail.com>

<a href="https://twitter.com/pedro_g_s">
  <img alt="Follow me on Twitter" src="https://image.freepik.com/iconos-gratis/twitter-logo_318-40209.jpg" height="60" width="60"/>
</a>
<a href="https://es.linkedin.com/in/pedrovgs">
  <img alt="Add me to Linkedin" src="https://image.freepik.com/iconos-gratis/boton-del-logotipo-linkedin_318-84979.png" height="60" width="60"/>
</a>

License
-------

    Copyright 2016 Pedro Vicente Gómez Sánchez

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

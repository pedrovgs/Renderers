Renderers [![Build Status](https://travis-ci.org/pedrovgs/Renderers.svg?branch=master)](https://travis-ci.org/pedrovgs/Renderers) [![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.pedrovgs/renderers/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.github.pedrovgs/renderers)
=========

**Renderers is an Android library created to avoid all the RecyclerView/Adapter boilerplate** needed to create a list/grid   of data in your app and all the spaghetti code that developers used to create following the ``ViewHolder`` classic implementation. **As performance is also important for us, we've added a new ``diffUpdate`` and a ``RVListRendererAdapter`` method supporting differential updated transparently in the main thread and a background thred respectively.**

With this library you can improve your RecyclerView/Adapter/ViewHolder code. The one sometimes we copy and paste again and again :smiley:. Using this library you won't need to create any new class extending from ``RecyclerViewAdapter``.

Create your ``Renderer`` classes and declare the mapping between the object to render and the ``Renderer``. The ``Renderer`` will use the model information to draw your user interface. You can reuse them in all your RecyclerView and ListView implementations easily. That's it!

Screenshots
-----------

![Demo Screenshot][1]

Usage
-----

To use Renderers Android library you only have to follow three steps:

* 1. Create your ``Renderer`` class or classes extending ``Renderer<T>``. Inside your ``Renderer`` classes. You will have to implement some methods to inflate the layout you want to render and implement the rendering algorithm.

```java
public class VideoRenderer extends Renderer<Video> {

       @BindView(R.id.iv_thumbnail)
       ImageView thumbnail;
       @BindView(R.id.tv_title)
       TextView title;
       @BindView(R.id.iv_marker)
       ImageView marker;
       @BindView(R.id.tv_label)
       TextView label;

       @Override
       protected View inflate(LayoutInflater inflater, ViewGroup parent) {
           View inflatedView = inflater.inflate(R.layout.video_renderer, parent, false);
           ButterKnife.bind(this, inflatedView);
           return inflatedView;
       }

       @Override
       protected void render() {
           Video video = getContent();
           renderThumbnail(video);
           renderTitle(video);
           renderMarker(video);
           renderLabel();
       }

       @OnClick(R.id.iv_thumbnail)
       void onVideoClicked() {
           Video video = getContent();
           Log.d("Renderer", "Clicked: " + video.getTitle());
       }

       private void renderThumbnail(Video video) {
           Picasso.with(context).load(video.getResourceThumbnail()).placeholder(R.drawable.placeholder).into(thumbnail);
       }

       private void renderTitle(Video video) {
           this.title.setText(video.getTitle());
       }
}
```

You can use [Jake Wharton's][2] [Butterknife][3] library to avoid findViewById calls inside your Renderers if you want. But the usage of third party libraries is not mandatory.

* 2. **If you have just one type of item in your list**, instantiate a ``RendererBuilder`` with a ``Renderer`` instance and you are ready to go:

```java
Renderer<Video> renderer = new LikeVideoRenderer();
RendererBuilder<Video> rendererBuilder = new RendererBuilder<Video>(renderer);
```

**If you need to render different objects** into your list/grid you can use ``RendererBuilder.bind`` fluent API and that's it:

```java
RendererBuilder<Video> rendererBuilder = new RendererBuilder<Video>()
         .bind(VideoHeader.class, new VideoHeaderRenderer())
         .bind(Video.class, new LikeVideoRenderer());
```

* 3. Initialize your ``ListView`` or ``RecyclerView`` with your ``RendererBuilder`` and an optional ``List`` inside your Activity or Fragment. **You should provide a list of items to configure your ``RendererAdapter`` or ``RVRendererAdapter``.**

```java
private void initListView() {
    adapter = new RendererAdapter<Video>(rendererBuilder, list);
    listView.setAdapter(adapter);
}
```

or

```java
private void initListView() {
    adapter = new RVRendererAdapter<Video>(rendererBuilder, list);
    recyclerView.setAdapter(adapter);
}
```

**Remember, if you are going to use ``RecyclerView`` instead of ``ListView`` you'll have to use ``RVRendererAdapter`` instead of ``RendererAdapter``.**

* 4. **Diff updates:**

***If the ``RecyclerView`` performance is crucial in your application* remember you can use ``diffUpdate`` method in your ``RVRendererAdapter`` instance to update just the items changed in your adapter and not the whole list/grid.***

```java
adapter.diffUpdate(newList)
```

This method provides a ready to use diff update for our adapter based on the implementation of the standard ``equals`` and ``hashCode`` methods from the ``Object`` Java class. The classes associated to your renderers will have to implement ``equals`` and ``hashCode`` methods properly. Your ``hashCode`` implementation can be based on the item ID if you have one. You can use your ``hashCode`` implementation as an identifier of the object you want to represent graphically. We know this implementation is not perfect, but is the best we can do wihtout adding a new interface you have to implement to the library breaking all your existing code. Here you can review the [DiffUtil.Callback implementation](https://github.com/pedrovgs/Renderers/blob/master/renderers/src/main/java/com/pedrogomez/renderers/DiffCallback.java) used in this library. If you can't follow this implementation you can always use [a different approach](https://medium.com/@iammert/using-diffutil-in-android-recyclerview-bdca8e4fbb00) combined with your already implemented renderers.

Also, `RVListRendererAdapter` provides a way to perform diff updates in a background thread transparently. When using `RVListRendererAdapter` you'll have a default `DiffUtil.ItemCallback` implementation (https://developer.android.com/reference/android/support/v7/util/DiffUtil.ItemCallback)) based on referencial equality for `areItemsTheSame` method and structural equality for `areContentsTheSame` method. You also have constructors on this class to provide your own implementation for `DiffUtil.ItemCallback`. You can even configure the threads used to perform the calculations through `AsynDifferConfig` class (https://developer.android.com/reference/android/support/v7/recyclerview/extensions/AsyncDifferConfig).



***This library can also be used to show views inside a ``ViewPager``. Take a look at ``VPRendererAdapter`` :smiley:***

Usage
-----

Add this dependency to your ``build.gradle``:

```groovy
dependencies{
    implementation 'com.github.pedrovgs:renderers:4.1.0'
}
```

Complex binding
---------------

If your renderers binding is complex and it's not based on different classes but in properties of these classes, you can also extend ``RendererBuilder`` and override ``getPrototypeClass`` to customize your binding as follows:

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


References
----------

You can find implementation details in these talks:

[Software Design Patterns on Android Video][4]

[Software Design Patterns on Android Slides][5]

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

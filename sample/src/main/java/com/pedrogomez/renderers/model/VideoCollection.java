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
package com.pedrogomez.renderers.model;

import com.pedrogomez.renderers.AdapteeCollection;
import java.util.Collection;
import java.util.List;

/**
 * Class created to represent a list of videos. This class has been created to store videos,
 * encapsulate the collection
 * usage and implements the AdapteeCollection interface needed by RendererAdapter.
 *
 * @author Pedro Vicente Gómez Sánchez.
 */
public class VideoCollection implements AdapteeCollection<Video> {

  private final List<Video> videos;

  public VideoCollection(List<Video> videos) {
    this.videos = videos;
  }

  @Override
  public int size() {
    return videos.size();
  }

  @Override
  public Video get(final int index) {
    return videos.get(index);
  }

  @Override
  public void add(Video element) {
    videos.add(element);
  }

  @Override
  public void remove(Video element) {
    videos.remove(element);
  }

  @Override
  public void addAll(Collection<Video> elements) {
    videos.addAll(elements);
  }

  @Override
  public void removeAll(Collection<Video> elements) {
    videos.removeAll(elements);
  }
}

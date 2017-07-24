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
package com.pedrogomez.renderers.sample.model;

/**
 * Main domain class created to simulate fake data for the example.
 *
 * @author Pedro Vicente Gómez Sánchez.
 */
public class Video {

  private boolean favorite;
  private boolean liked;
  private boolean live;
  private String thumbnail;
  private String title;

  public boolean isFavorite() {
    return favorite;
  }

  public void setFavorite(boolean favorite) {
    this.favorite = favorite;
  }

  public boolean isLiked() {
    return liked;
  }

  public void setLiked(boolean liked) {
    this.liked = liked;
  }

  public String getThumbnail() {
    return thumbnail;
  }

  public void setThumbnail(String resourceThumbnail) {
    this.thumbnail = resourceThumbnail;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public boolean isLive() {
    return live;
  }

  public void setLive(boolean live) {
    this.live = live;
  }

  @Override public boolean equals(Object obj) {
    if (obj instanceof Video) {
      Video other = (Video) obj;
      return title.equals(other.title)
              && thumbnail.equals(other.thumbnail)
              && favorite == other.favorite
              && liked == other.liked
              && live == other.live;
    } else {
      return false;
    }
  }

  /*
   * In this example we use the thumbnail property as hashCode because we assume every thumbnail is
   * going to be different for every video. It can be used as an identifier. This is needed to
   * support differential updates properly. You can use your item ID if you have one.
   */
  @Override
  public int hashCode() {
    return thumbnail.hashCode();
  }
}

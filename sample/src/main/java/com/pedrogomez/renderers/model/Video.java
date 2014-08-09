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

/**
 * Main domain class created to simulate fake data for the example.
 *
 * @author Pedro Vicente Gómez Sánchez.
 */
public class Video {

  private boolean favorite;
  private boolean liked;
  private boolean live;
  private String resourceThumbnail;
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

  public String getResourceThumbnail() {
    return resourceThumbnail;
  }

  public void setResourceThumbnail(String resourceThumbnail) {
    this.resourceThumbnail = resourceThumbnail;
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
}

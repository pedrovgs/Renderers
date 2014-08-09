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

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import javax.inject.Inject;

/**
 * Auxiliary class created to generate a VideoCollection with random data.
 *
 * @author Pedro Vicente Gómez Sánchez.
 */
public class RandomVideoCollectionGenerator {

  private static final Map<String, String> VIDEO_INFO = new HashMap<String, String>();

  private Random random;

  @Inject
  public RandomVideoCollectionGenerator() {
    this.random = new Random();
    initializeVideoInfo();
  }

  /**
   * Generate a VideoCollection with random data obtained form VIDEO_INFO map.
   *
   * @param videoCount size of the collection.
   * @return VideoCollection generated.
   */
  public VideoCollection generate(final int videoCount) {
    List<Video> videos = new LinkedList<Video>();
    for (int i = 0; i < videoCount; i++) {
      Video video = generateRandomVideo();
      videos.add(video);
    }
    return new VideoCollection(videos);
  }

  /**
   * Initialize VIDEO_INFO data.
   */
  private void initializeVideoInfo() {
    VIDEO_INFO.put("The Big Bang Theory",
        "http://thetvdb.com/banners/fanart/original/80379-38.jpg");
    VIDEO_INFO.put("Breking Bad", "http://thetvdb.com/banners/fanart/original/81189-21.jpg");
    VIDEO_INFO.put("Arrow", "http://thetvdb.com/banners/fanart/original/257655-16.jpg");
    VIDEO_INFO.put("Game of Thrones", "http://thetvdb.com/banners/fanart/original/121361-15.jpg");
    VIDEO_INFO.put("Lost", "http://thetvdb.com/banners/fanart/original/73739-53.jpg");
    VIDEO_INFO.put("How I met your mother",
        "http://thetvdb.com/banners/fanart/original/75760-58.jpg");
    VIDEO_INFO.put("Dexter", "http://thetvdb.com/banners/fanart/original/79349-16.jpg");
    VIDEO_INFO.put("Sleepy Hollow", "http://thetvdb.com/banners/fanart/original/269578-8.jpg");
    VIDEO_INFO.put("The Vampire Diaries",
        "http://thetvdb.com/banners/fanart/original/95491-68.jpg");
    VIDEO_INFO.put("Friends", "http://thetvdb.com/banners/fanart/original/79168-6.jpg");
    VIDEO_INFO.put("New Girl", "http://thetvdb.com/banners/fanart/original/248682-11.jpg");
    VIDEO_INFO.put("The Mentalist", "http://thetvdb.com/banners/fanart/original/82459-12.jpg");
    VIDEO_INFO.put("Sons of Anarchy", "http://thetvdb.com/banners/fanart/original/82696-65.jpg");
  }

  /**
   * Create a random video.
   *
   * @return random video.
   */
  private Video generateRandomVideo() {
    Video video = new Video();
    configureFavoriteStatus(video);
    configureLikeStatus(video);
    configureLiveStatus(video);
    configureTitleAndThumbnail(video);
    return video;
  }

  private void configureLikeStatus(final Video video) {
    boolean liked = random.nextBoolean();
    video.setLiked(liked);
  }

  private void configureFavoriteStatus(final Video video) {
    boolean favorite = random.nextBoolean();
    video.setFavorite(favorite);
  }

  private void configureLiveStatus(final Video video) {
    boolean live = random.nextBoolean();
    video.setLive(live);
  }

  private void configureTitleAndThumbnail(final Video video) {
    int maxInt = VIDEO_INFO.size();
    int randomIndex = random.nextInt(maxInt);
    String title = getKeyForIndex(randomIndex);
    video.setTitle(title);
    String thumbnail = getValueForIndex(randomIndex);
    video.setThumbnail(thumbnail);
  }

  private String getKeyForIndex(int randomIndex) {
    int i = 0;
    for (String index : VIDEO_INFO.keySet()) {
      if (i == randomIndex) {
        return index;
      }
      i++;
    }
    return null;
  }

  private String getValueForIndex(int randomIndex) {
    int i = 0;
    for (String index : VIDEO_INFO.keySet()) {
      if (i == randomIndex) {
        return VIDEO_INFO.get(index);
      }
      i++;
    }
    return "";
  }
}

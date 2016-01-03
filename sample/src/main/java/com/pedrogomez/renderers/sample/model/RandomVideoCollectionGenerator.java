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

import com.pedrogomez.renderers.AdapteeCollection;
import com.pedrogomez.renderers.ListAdapteeCollection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Auxiliary class created to generate a VideoCollection with random data.
 *
 * @author Pedro Vicente Gómez Sánchez.
 */
public class RandomVideoCollectionGenerator {

  private static final Map<String, String> VIDEO_INFO = new HashMap<String, String>();

  private Random random;

  public RandomVideoCollectionGenerator() {
    this.random = new Random();
    initializeVideoInfo();
  }

  /**
   * Generate a VideoCollection with random data obtained form VIDEO_INFO map. You don't need o
   * create your own AdapteeCollections. Review ListAdapteeCollection if needed.
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

  public AdapteeCollection<Video> generateListAdapteeVideoCollection(int videoCount) {
    List<Video> videos = new LinkedList<Video>();
    for (int i = 0; i < videoCount; i++) {
      Video video = generateRandomVideo();
      videos.add(video);
    }
    return new ListAdapteeCollection<Video>(videos);
  }

  /**
   * Initialize VIDEO_INFO data.
   */
  private void initializeVideoInfo() {
    VIDEO_INFO.put("The Big Bang Theory", "http://thetvdb.com/banners/_cache/posters/80379-9.jpg");
    VIDEO_INFO.put("Breaking Bad", "http://thetvdb.com/banners/_cache/posters/81189-22.jpg");
    VIDEO_INFO.put("Arrow", "http://thetvdb.com/banners/_cache/posters/257655-15.jpg");
    VIDEO_INFO.put("Game of Thrones", "http://thetvdb.com/banners/_cache/posters/121361-26.jpg");
    VIDEO_INFO.put("Lost", "http://thetvdb.com/banners/_cache/posters/73739-2.jpg");
    VIDEO_INFO.put("How I met your mother",
        "http://thetvdb.com/banners/_cache/posters/75760-29.jpg");
    VIDEO_INFO.put("Dexter", "http://thetvdb.com/banners/_cache/posters/79349-24.jpg");
    VIDEO_INFO.put("Sleepy Hollow", "http://thetvdb.com/banners/_cache/posters/269578-5.jpg");
    VIDEO_INFO.put("The Vampire Diaries", "http://thetvdb.com/banners/_cache/posters/95491-27.jpg");
    VIDEO_INFO.put("Friends", "http://thetvdb.com/banners/_cache/posters/79168-4.jpg");
    VIDEO_INFO.put("New Girl", "http://thetvdb.com/banners/_cache/posters/248682-9.jpg");
    VIDEO_INFO.put("The Mentalist", "http://thetvdb.com/banners/_cache/posters/82459-1.jpg");
    VIDEO_INFO.put("Sons of Anarchy", "http://thetvdb.com/banners/_cache/posters/82696-1.jpg");
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

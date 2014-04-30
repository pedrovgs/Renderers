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

import com.pedrogomez.renderers.R;

import javax.inject.Inject;
import java.util.*;

/**
 * Auxiliary class created to generate a VideoCollection with random data.
 *
 * @author Pedro Vicente Gómez Sánchez.
 */
public class RandomVideoCollectionGenerator {

    private static final Map<String, Integer> VIDEO_INFO = new HashMap<String, Integer>();

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
        VIDEO_INFO.put("Video 1", R.drawable.video1);
        VIDEO_INFO.put("Video 2", R.drawable.video2);
        VIDEO_INFO.put("Video 3", R.drawable.video3);
        VIDEO_INFO.put("Video 4", R.drawable.video4);
        VIDEO_INFO.put("Video 5", R.drawable.video5);
        VIDEO_INFO.put("Video 6", R.drawable.video6);
    }

    /**
     * Create a random video.
     *
     * @return random video.
     */
    private Video generateRandomVideo() {
        Video video = new Video();
        setFavorite(video);
        setLiked(video);
        setLive(video);
        setTitle(video);
        setThumbnail(video);
        return video;
    }

    private void setLiked(Video video) {
        boolean liked = random.nextBoolean();
        video.setLiked(liked);
    }

    private void setFavorite(final Video video) {
        boolean favorite = random.nextBoolean();
        video.setFavorite(favorite);
    }

    private void setLive(final Video video) {
        boolean live = random.nextBoolean();
        video.setLive(live);
    }

    private void setTitle(final Video video) {
        int maxInt = VIDEO_INFO.size();
        int randomIndex = random.nextInt(maxInt);
        String title = getKeyForIndex(randomIndex);
        video.setTitle(title);
    }


    private void setThumbnail(final Video video) {
        int maxInt = VIDEO_INFO.size();
        int randomIndex = random.nextInt(maxInt);
        int thumbanil = getValueForIndex(randomIndex);
        video.setResourceThumbnail(thumbanil);
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

    private int getValueForIndex(int randomIndex) {
        int i = 0;
        for (String index : VIDEO_INFO.keySet()) {
            if (i == randomIndex) {
                return VIDEO_INFO.get(index);
            }
            i++;
        }
        return -1;
    }
}

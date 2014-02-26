package com.pedrogomez.renderers;

import java.util.*;

/**
 * Auxiliary class created to generate video collection with random data.
 *
 * @author Pedro Vicente Gómez Sánchez.
 */
public class RandomVideoCollectionGenerator {

    /*
     * Constants
     */
    private static final Map<String, Integer> VIDEO_INFO = new HashMap<String, Integer>();

    /*
     * Attributes
     */

    private Random random;

    /*
     * Constructor
     */

    public RandomVideoCollectionGenerator() {
        this.random = new Random();
        initializeVideoInfo();
    }

    public VideoCollection generate(final int videoCount) {
        List<Video> videos = new LinkedList<Video>();
        for (int i = 0; i < videoCount; i++) {
            Video video = generateRandomVideo();
            videos.add(video);
        }
        return new VideoCollection(videos);
    }


    /*
     * Auxiliary methods
     */

    private void initializeVideoInfo() {
        VIDEO_INFO.put("Video 1", R.drawable.video1);
        VIDEO_INFO.put("Video 2", R.drawable.video2);
        VIDEO_INFO.put("Video 3", R.drawable.video3);
        VIDEO_INFO.put("Video 4", R.drawable.video4);
        VIDEO_INFO.put("Video 5", R.drawable.video5);
        VIDEO_INFO.put("Video 6", R.drawable.video6);
    }


    private Video generateRandomVideo() {
        Video video = new Video();
        setLiked(video);
        setLive(video);
        setTitle(video);
        setThumbnail(video);
        return video;
    }

    private void setLiked(final Video video) {
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

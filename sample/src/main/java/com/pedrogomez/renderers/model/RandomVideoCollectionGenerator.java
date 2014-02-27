package com.pedrogomez.renderers.model;

import com.pedrogomez.renderers.R;

import java.util.*;

/**
 * Auxiliary class created to generate a VideoCollection with random data.
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

    /**
     * Generate a VideoCollection with random data obtainded form VIDEO_INFO map.
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


    /*
     * Auxiliary methods
     */

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

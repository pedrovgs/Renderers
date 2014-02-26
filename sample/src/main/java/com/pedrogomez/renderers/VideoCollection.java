package com.pedrogomez.renderers;

import java.util.List;

/**
 * Class created to represent a list of videos
 *
 * @author Pedro Vicente Gómez Sánchez.
 */
public class VideoCollection implements AdapteeCollection<Video> {

    /*
     * Attributes
     */
    private final List<Video> videos;

    public VideoCollection(List<Video> videos) {
        this.videos = videos;
    }

    /*
     * Implemented methods
     */

    @Override
    public int size() {
        return videos.size();
    }

    @Override
    public Video get(final int index) {
        return videos.get(index);
    }
}

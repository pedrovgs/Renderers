package com.pedrogomez.renderers.model;

import com.pedrogomez.renderers.AdapteeCollection;

import java.util.Collection;
import java.util.List;

/**
 * Class created to represent a list of videos. This class has been created to store videos, encapsulate the collection
 * usage and implements the AdapteeCollection interface needed by RendererAdapter.
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

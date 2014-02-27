package com.pedrogomez.renderers.model;

/**
 * Video class created to simulate fake data for the example.
 *
 * @author Pedro Vicente Gómez Sánchez.
 */
public class Video {

    /*
     * Attributes
     */

    private boolean favorite;
    private boolean liked;
    private boolean live;
    private int resourceThumbnail;
    private String title;

    /*
     * Getters and setters
     */

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

    public int getResourceThumbnail() {
        return resourceThumbnail;
    }

    public void setResourceThumbnail(int resourceThumbnail) {
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

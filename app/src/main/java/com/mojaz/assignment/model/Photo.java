package com.mojaz.assignment.model;

import com.google.gson.annotations.SerializedName;

/**
 * An immutable Photo model
 */
public final class Photo {

    @SerializedName("id")
    private final int id;

    @SerializedName("title")
    private final String title;

    @SerializedName("thumbnailUrl")
    private final String thumbnailUrl;

    @SerializedName("albumId")
    private final int albumId;

    public Photo(int id, String title, String thumbnailUrl, int albumId) {
        this.id = id;
        this.title = title;
        this.thumbnailUrl = thumbnailUrl;
        this.albumId = albumId;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public int getAlbumId() {
        return albumId;
    }

    @Override
    public String toString() {
        return "{ id: " + id + ", title: " + title + ", thumbnailUrl: " + thumbnailUrl +
                ", albumId: " + albumId + " }";
    }
}

package com.mojaz.assignment.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * An immutable Photo model
 */
public final class Photo implements Parcelable {

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

    // Parcelable implementation
    private Photo(Parcel in) {
        id = in.readInt();
        title = in.readString();
        thumbnailUrl = in.readString();
        albumId = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(title);
        parcel.writeString(thumbnailUrl);
        parcel.writeInt(albumId);
    }

    public static final Creator<Photo> CREATOR = new Creator<Photo>() {
        @Override
        public Photo createFromParcel(Parcel in) {
            return new Photo(in);
        }

        @Override
        public Photo[] newArray(int size) {
            return new Photo[size];
        }
    };

    @Override
    public String toString() {
        return "{ id: " + id + ", title: " + title + ", thumbnailUrl: " + thumbnailUrl +
                ", albumId: " + albumId + " }";
    }
}

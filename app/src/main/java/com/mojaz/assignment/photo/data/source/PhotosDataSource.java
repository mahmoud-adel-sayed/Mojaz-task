package com.mojaz.assignment.photo.data.source;

import android.support.annotation.NonNull;

import com.mojaz.assignment.model.Photo;

import java.util.List;

/**
 * Main entry point for accessing Photos data.
 */
public interface PhotosDataSource {

    interface LoadPhotosCallback {
        void onPhotosLoaded(List<Photo> photoList);
        void onDataNotAvailable(int reason, String message);
    }

    void getPhotos(@NonNull LoadPhotosCallback callback);

    void cancelRequest();

}

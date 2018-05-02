package com.mojaz.assignment.photo.data.source.repository;

import android.support.annotation.NonNull;

import com.mojaz.assignment.photo.data.source.PhotosDataSource;

import static com.mojaz.assignment.util.GeneralUtil.checkNotNull;

/**
 * Concrete implementation to load Photos from the data sources.
 */
public class PhotosRepository implements PhotosDataSource {

    private static PhotosRepository INSTANCE;

    private final PhotosDataSource mRemoteDataSource;

    // Prevent direct instantiation.
    private PhotosRepository(PhotosDataSource remoteDataSource) {
        mRemoteDataSource = checkNotNull(remoteDataSource);
    }

    /**
     * Returns the single instance of this class, creating it if necessary.
     *
     * @param remoteDataSource the backend data source
     * @return the {@link PhotosRepository} instance
     */
    public static synchronized PhotosRepository getInstance(
            @NonNull PhotosDataSource remoteDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new PhotosRepository(remoteDataSource);
        }
        return INSTANCE;
    }

    @Override
    public void getPhotos(@NonNull LoadPhotosCallback callback) {
        mRemoteDataSource.getPhotos(callback);
    }

    @Override
    public void cancelRequest() {
        mRemoteDataSource.cancelRequest();
    }
}

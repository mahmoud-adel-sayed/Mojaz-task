package com.mojaz.assignment.photo;

import android.util.SparseBooleanArray;

import com.mojaz.assignment.model.Photo;
import com.mojaz.assignment.photo.data.source.PhotosDataSource;
import com.mojaz.assignment.photo.data.source.repository.PhotosRepository;

import java.util.ArrayList;
import java.util.List;

import static com.mojaz.assignment.Config.INTERNET_CONNECTION_ERROR;
import static com.mojaz.assignment.Config.SERVER_ERROR;

/**
 * Listens to user actions from the UI ({@link PhotosFragment}), retrieves the data and updates the
 * UI as required.
 */
class PhotosPresenter implements PhotosContract.Presenter {

    public static final int MINIMUM_ITEMS = 1;

    public static final int MAXIMUM_ITEMS = 10;

    private final PhotosRepository mRepository;

    private final PhotosContract.View mView;

    PhotosPresenter(PhotosRepository photosRepository, PhotosContract.View photosView) {
        mRepository = photosRepository;
        mView = photosView;
    }

    @Override
    public void start() {
        getPhotos();
    }

    @Override
    public void getPhotos() {
        mRepository.getPhotos(mCallback);
    }

    @Override
    public void doneFiltering(SparseBooleanArray selectedPhotos, List<Photo> allPhotos) {
        List<Photo> filteredList = new ArrayList<>();

        for (int i = 0; i < selectedPhotos.size(); i++) {
            if (selectedPhotos.get(i)) {
                // Add this photo to the filtered list
                filteredList.add(allPhotos.get(i));

                // User selected more than ten items
                if (filteredList.size() > MAXIMUM_ITEMS) {
                    mView.showMessage(MAXIMUM_ITEMS);
                    return;
                }
            }
        }

        // Check if user selected at least one item
        if (filteredList.isEmpty()) {
            mView.showMessage(MINIMUM_ITEMS);
        }
        else {
            // Show filtered photos
            mView.filterList(filteredList);
        }
    }

    @Override
    public void cancelRequest() {
        mRepository.cancelRequest();
    }

    private PhotosDataSource.LoadPhotosCallback mCallback = new PhotosDataSource.LoadPhotosCallback() {
        @Override
        public void onPhotosLoaded(List<Photo> photoList) {
            if (!mView.isActive()) return;

            mView.setLoadingIndicator(false);
            mView.showPhotos(photoList);
        }

        @Override
        public void onDataNotAvailable(int reason, String message) {
            if (!mView.isActive()) return;

            mView.setLoadingIndicator(false);

            switch (reason) {
                case INTERNET_CONNECTION_ERROR:
                    mView.showNoInternetConnection();
                    break;
                case SERVER_ERROR:
                    mView.showError(message);
                    break;
            }
        }
    };
}

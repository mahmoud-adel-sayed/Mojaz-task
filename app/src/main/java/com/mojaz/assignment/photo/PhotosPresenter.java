package com.mojaz.assignment.photo;

import com.mojaz.assignment.model.Photo;
import com.mojaz.assignment.photo.data.source.PhotosDataSource;
import com.mojaz.assignment.photo.data.source.repository.PhotosRepository;

import java.util.List;

import static com.mojaz.assignment.Config.INTERNET_CONNECTION_ERROR;
import static com.mojaz.assignment.Config.SERVER_ERROR;

/**
 * Listens to user actions from the UI ({@link PhotosFragment}), retrieves the data and updates the
 * UI as required.
 */
public class PhotosPresenter implements PhotosContract.Presenter {

    private final PhotosRepository mRepository;

    private final PhotosContract.View mView;

    public PhotosPresenter(PhotosRepository photosRepository, PhotosContract.View photosView) {
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

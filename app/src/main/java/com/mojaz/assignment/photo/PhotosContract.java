package com.mojaz.assignment.photo;

import com.mojaz.assignment.BasePresenter;
import com.mojaz.assignment.BaseView;
import com.mojaz.assignment.model.Photo;

import java.util.List;

/**
 * This specifies the contract between the view and the presenter.
 */
public interface PhotosContract {

    interface Presenter extends BasePresenter {
        void getPhotos();
    }

    interface View extends BaseView {

        void setPresenter(Presenter presenter);

        void showPhotos(List<Photo> photoList);

        void showError(String error);
    }
}

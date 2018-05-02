package com.mojaz.assignment.photo;

import android.util.SparseBooleanArray;

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

        void doneFiltering(SparseBooleanArray selectedPhotos, List<Photo> allPhotos);
    }

    interface View extends BaseView {

        void setPresenter(Presenter presenter);

        void showPhotos(List<Photo> photoList);

        void filterList(List<Photo> filteredList);

        void showError(String error);

        void showMessage(int code);
    }
}

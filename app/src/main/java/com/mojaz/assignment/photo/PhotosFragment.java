package com.mojaz.assignment.photo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.mojaz.assignment.R;
import com.mojaz.assignment.model.Photo;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class PhotosFragment extends Fragment implements PhotosContract.View {

    private View mProgress;

    private TextView mErrorTV;

    private List<Photo> mPhotoList;

    private PhotosAdapter mAdapter;

    private PhotosContract.Presenter mPresenter;

    public PhotosFragment() {
        // Required empty public constructor
    }

    public static PhotosFragment newInstance() {
        return new PhotosFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Create list of photos and adapter
        mPhotoList = new ArrayList<>();
        mAdapter = new PhotosAdapter(getActivity(), mPhotoList);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_photos, container, false);

        mProgress = view.findViewById(R.id.loading_spinner);
        mErrorTV = (TextView) view.findViewById(R.id.empty_view);

        // Listen for done button click
        view.findViewById(R.id.done_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SparseBooleanArray selectedPhotos = mAdapter.getSelectedPhotos();
                mPresenter.doneFiltering(selectedPhotos, mPhotoList);
            }
        });

        // Setup ListView
        ListView listView = (ListView) view.findViewById(R.id.photos_list);
        listView.setAdapter(mAdapter);

        // Start the presenter to get data
        mPresenter.start();

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        mPresenter.cancelRequest();
    }

    @Override
    public void setPresenter(PhotosContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showPhotos(List<Photo> photoList) {
        if (mPhotoList.isEmpty() && photoList.isEmpty()) {
            mErrorTV.setText(getString(R.string.no_photos_label));
        }
        else {
            mPhotoList.addAll(photoList);
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void filterList(List<Photo> filteredList) {
        // notify the activity to replace the fragment
        if (getActivity() instanceof PhotosActivity) {
            ((PhotosActivity)getActivity()).replaceWithFiltered(filteredList);
        }
    }

    @Override
    public void showError(String error) {
        Toast.makeText(getActivity(), error, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showMessage(int code) {
        switch (code) {
            case PhotosPresenter.MAXIMUM_ITEMS:
                Toast.makeText(getActivity(), R.string.max_items_err, Toast.LENGTH_LONG).show();
                break;
            case PhotosPresenter.MINIMUM_ITEMS:
                Toast.makeText(getActivity(), R.string.min_items_err, Toast.LENGTH_LONG).show();
                break;
        }
    }

    @Override
    public void showNoInternetConnection() {
        mProgress.setVisibility(View.GONE);
        mErrorTV.setText(getString(R.string.no_internet_connection_error));
    }

    @Override
    public void setLoadingIndicator(boolean active) {
        mProgress.setVisibility(active? View.VISIBLE : View.INVISIBLE);
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }
}

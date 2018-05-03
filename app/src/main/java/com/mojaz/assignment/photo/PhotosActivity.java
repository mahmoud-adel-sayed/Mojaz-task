package com.mojaz.assignment.photo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.mojaz.assignment.R;
import com.mojaz.assignment.photo.filter.FilteredPhotosFragment;
import com.mojaz.assignment.model.Photo;
import com.mojaz.assignment.photo.data.source.remote.PhotosRemoteDataSource;
import com.mojaz.assignment.photo.data.source.repository.PhotosRepository;

import java.util.ArrayList;
import java.util.List;

import static com.mojaz.assignment.util.GeneralUtil.replaceFragment;

public class PhotosActivity extends AppCompatActivity {

    private TextView mToolbarTitleTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photos);

        // Setup toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        // Set toolbar title
        mToolbarTitleTV = (TextView) toolbar.findViewById(R.id.toolbar_title);
        mToolbarTitleTV.setText(R.string.all_photos_label);

        // Create View
        PhotosFragment fragment = PhotosFragment.newInstance();

        // Create Repository
        PhotosRepository repository = PhotosRepository.getInstance(
                new PhotosRemoteDataSource(this));

        // Create presenter
        PhotosPresenter presenter = new PhotosPresenter(repository, fragment);

        // Link view & presenter
        fragment.setPresenter(presenter);

        // Add fragment to this activity
        replaceFragment(getSupportFragmentManager(), fragment, R.id.main_content, null);
    }

    public void replaceWithFiltered(List<Photo> filteredList) {
        // Change toolbar title to filtered photos
        mToolbarTitleTV.setText(R.string.filtered_photos_label);

        replaceFragment(
                getSupportFragmentManager(),
                FilteredPhotosFragment.newInstance((ArrayList<Photo>) filteredList),
                R.id.main_content,
                null);
    }
}

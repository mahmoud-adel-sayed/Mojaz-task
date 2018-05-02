package com.mojaz.assignment.photo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.mojaz.assignment.R;
import com.mojaz.assignment.photo.data.source.remote.PhotosRemoteDataSource;
import com.mojaz.assignment.photo.data.source.repository.PhotosRepository;

import static com.mojaz.assignment.util.GeneralUtil.addFragmentToContainer;

public class PhotosActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
        addFragmentToContainer(getSupportFragmentManager(), fragment, R.id.main_content, null);
    }
}

package com.mojaz.assignment.photo.filter;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mojaz.assignment.R;
import com.mojaz.assignment.model.Photo;

import java.util.ArrayList;
import java.util.List;

public class FilteredPhotosFragment extends Fragment {

    private static final String FILTERED_LIST_KEY = "FILTERED_LIST_KEY";

    private List<Photo> mFilteredList;

    public static FilteredPhotosFragment newInstance(ArrayList<Photo> filteredList) {
        Bundle args = new Bundle();
        args.putParcelableArrayList(FILTERED_LIST_KEY, filteredList);

        FilteredPhotosFragment fragment = new FilteredPhotosFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        if (args != null && args.containsKey(FILTERED_LIST_KEY)) {
            mFilteredList = args.getParcelableArrayList(FILTERED_LIST_KEY);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_filtered_photos, container, false);

        if (mFilteredList != null) {
            // Add filtered photos to container
            LinearLayout linearLayout = view.findViewById(R.id.items_container);
            addItems(linearLayout, mFilteredList);
        }

        return view;
    }

    private void addItems(LinearLayout filteredItemsContainer, List<Photo> photoList) {

        for (Photo photo : photoList) {
            // Inflate the layout for the item
            View item = getActivity().getLayoutInflater()
                    .inflate(R.layout.filtered_photo_list_item, null);

            // Set image
            Glide.with(this)
                    .load(photo.getThumbnailUrl())
                    .crossFade()
                    .into((ImageView) item.findViewById(R.id.item_image));

            // Set title
            TextView titleTV = (TextView) item.findViewById(R.id.item_title);
            titleTV.setText(photo.getTitle());

            // Set album id
            TextView albumTV = (TextView) item.findViewById(R.id.item_album);
            albumTV.setText(String.format(getString(R.string.album_id_format), photo.getAlbumId()));

            // Add this item to the container
            filteredItemsContainer.addView(item);
        }
    }
}

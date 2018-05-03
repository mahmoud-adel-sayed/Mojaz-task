package com.mojaz.assignment.photo;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mojaz.assignment.R;
import com.mojaz.assignment.model.Photo;

import java.util.List;

class PhotosAdapter extends ArrayAdapter<Photo> implements CompoundButton.OnCheckedChangeListener {

    private final Context mContext;

    private List<Photo> mPhotoList;

    private final Drawable mPlaceHolder;

    private SparseBooleanArray mSelectedPhotos;

    PhotosAdapter(Context context, List<Photo> photoList){
        super(context, 0, photoList);

        mContext = context;
        mPhotoList = photoList;
        mPlaceHolder = new ColorDrawable(Color.GRAY);
        mSelectedPhotos = new SparseBooleanArray();
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if an existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        ViewHolder viewHolder;

        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.photo_list_item, parent, false);

            viewHolder = new ViewHolder(listItemView);
            listItemView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) listItemView.getTag();
        }

        // Get current photo object
        Photo photo = mPhotoList.get(position);

        // Set title
        viewHolder.title.setText(photo.getTitle());

        // Set Album id
        viewHolder.album.setText(String.format(
                mContext.getString(R.string.album_id_format), photo.getAlbumId()));

        // Set image
        Glide.with(mContext)
                .load(photo.getThumbnailUrl())
                .placeholder(mPlaceHolder)
                .crossFade()
                .into(viewHolder.image);

        // Checkbox
        viewHolder.checkBox.setTag(position);
        viewHolder.checkBox.setChecked(mSelectedPhotos.get(position, false));
        viewHolder.checkBox.setOnCheckedChangeListener(this);

        return listItemView;
    }

    public SparseBooleanArray getSelectedPhotos() {
        return mSelectedPhotos;
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
        mSelectedPhotos.put((Integer) compoundButton.getTag(), isChecked);
    }

    private static class ViewHolder {
        private ImageView image;
        private TextView title;
        private TextView album;
        private CheckBox checkBox;

        private ViewHolder(View v){
            title = (TextView) v.findViewById(R.id.item_title);
            album = (TextView) v.findViewById(R.id.item_album);
            image = (ImageView) v.findViewById(R.id.item_image);
            checkBox = (CheckBox) v.findViewById(R.id.check_box);
        }
    }
}

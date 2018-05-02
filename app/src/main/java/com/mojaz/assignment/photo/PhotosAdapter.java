package com.mojaz.assignment.photo;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mojaz.assignment.R;
import com.mojaz.assignment.model.Photo;

import java.util.List;

public class PhotosAdapter extends ArrayAdapter<Photo> {

    private final Context mContext;

    private List<Photo> mPhotoList;

    private final Drawable mPlaceHolder;

    PhotosAdapter(Context context, List<Photo> photoList){
        super(context, 0, photoList);

        mContext = context;
        mPhotoList = photoList;
        mPlaceHolder = new ColorDrawable(Color.GRAY);
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

        Photo photo = mPhotoList.get(position);

        viewHolder.title.setText(photo.getTitle());

        // Set image
        Glide.with(mContext)
                .load(photo.getThumbnailUrl())
                .placeholder(mPlaceHolder)
                .crossFade()
                .into(viewHolder.image);

        return listItemView;
    }

    private static class ViewHolder {
        private final ImageView image;
        private final TextView title;
        private final CheckBox checkBox;

        private ViewHolder(View v){
            title = (TextView) v.findViewById(R.id.item_title);
            image = (ImageView) v.findViewById(R.id.item_image);
            checkBox = (CheckBox) v.findViewById(R.id.check_box);
        }
    }
}

package com.mojaz.assignment.photo.data.source.remote;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.mojaz.assignment.api.ApiClient;
import com.mojaz.assignment.api.ApiInterface;
import com.mojaz.assignment.model.Photo;
import com.mojaz.assignment.photo.data.source.PhotosDataSource;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.mojaz.assignment.Config.INTERNET_CONNECTION_ERROR;
import static com.mojaz.assignment.util.GeneralUtil.showResponseError;

/**
 * Implementation of the data source from server.
 */
public class PhotosRemoteDataSource implements PhotosDataSource {

    private static final String TAG = PhotosRemoteDataSource.class.getSimpleName();

    private final Context mContext;

    private Call<List<Photo>> mCall;

    public PhotosRemoteDataSource(Context context) {
        mContext = context;
    }

    @Override
    public void getPhotos(@NonNull LoadPhotosCallback callback) {
        ApiInterface api = ApiClient.getInstance().create(ApiInterface.class);
        mCall = api.getPhotos();
        mCall.enqueue(new PhotosCallback(callback));
    }

    @Override
    public void cancelRequest() {
        if (mCall != null) mCall.cancel();
    }

    private final class PhotosCallback implements Callback<List<Photo>> {

        private final LoadPhotosCallback callback;

        private PhotosCallback(LoadPhotosCallback callback) {
            this.callback = callback;
        }

        @Override
        public void onResponse(Call<List<Photo>> call, Response<List<Photo>> response) {
            if (!response.isSuccessful()) {
                showResponseError(mContext, callback);
                return;
            }

            List<Photo> photoList = response.body();
            callback.onPhotosLoaded(photoList);

            Log.e(TAG, photoList.toString());
        }

        @Override
        public void onFailure(Call<List<Photo>> call, Throwable t) {
            if (t instanceof IOException) {
                callback.onDataNotAvailable(INTERNET_CONNECTION_ERROR, null);
                Log.e(TAG, "Network error.");
            }
            else {
                showResponseError(mContext, callback);
                Log.e(TAG, "Server error.");
            }
        }
    }
}

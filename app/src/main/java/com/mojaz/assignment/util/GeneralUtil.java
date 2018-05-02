package com.mojaz.assignment.util;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.content.Context;

import com.mojaz.assignment.R;
import com.mojaz.assignment.photo.data.source.PhotosDataSource;

import static com.mojaz.assignment.Config.SERVER_ERROR;

public class GeneralUtil {

    /**
     * helper method to notify the view of data errors
     *
     * @param context The context of the view.
     * @param callback The callback interface.
     */
    public static void showResponseError(Context context,
                                         PhotosDataSource.LoadPhotosCallback callback) {
        String reason = context.getString(R.string.general_err);
        callback.onDataNotAvailable(SERVER_ERROR, reason);
    }

    public static <T> T checkNotNull(T object) {
        if (object == null) {
            throw new NullPointerException();
        }
        return object;
    }

    public static <T> T checkNotNull(T object, String message) {
        if (object == null) {
            throw new NullPointerException(message);
        }
        return object;
    }

    /**
     * The {@code fragment} is added to the container view with id {@code frameId} and tag {@code tag}.
     * The operation is performed by the {@code fragmentManager}.
     */
    public static void addFragmentToContainer(FragmentManager fragmentManager,
                                              Fragment fragment, int frameId, String tag) {
        fragmentManager.beginTransaction()
                .add(frameId, fragment, tag)
                .commit();
    }

}

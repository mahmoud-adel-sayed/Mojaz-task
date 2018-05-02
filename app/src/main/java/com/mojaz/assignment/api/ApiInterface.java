package com.mojaz.assignment.api;

import com.mojaz.assignment.model.Photo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {

    // Request to get all photos
    @GET("photos")
    Call<List<Photo>> getPhotos();
}

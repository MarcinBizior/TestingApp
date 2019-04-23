package com.example.kanum.testingapp;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Api {

    @GET("api/json/get/bVNyUGXpOW?indent=2")
    Call<List<Json>> getJson();

}

package com.example.testapp.network;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiTest {
    @GET("random")
    Call<TestResponse> getResponse();
}

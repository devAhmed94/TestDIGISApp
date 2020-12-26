package com.example.testapp.network;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.testapp.util.Constants;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static ApiClient instance;
    private final ApiTest apiTest;
    private Retrofit retrofit;

    private ApiClient(){
        retrofit  = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
         apiTest = retrofit.create(ApiTest.class);
    }
    public static ApiClient getInstance(){
        if (instance==null){
            instance = new ApiClient();
        }
        return instance;
    }

    public  Call<TestResponse> getResponse(){
        return apiTest.getResponse();
    }
}

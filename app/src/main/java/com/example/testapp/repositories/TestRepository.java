package com.example.testapp.repositories;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.testapp.network.ApiClient;
import com.example.testapp.network.TestResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TestRepository {
     private ApiClient apiClient;
     private MutableLiveData<TestResponse> liveResponse = new MutableLiveData<TestResponse>();
     private static TestRepository instance;

    private TestRepository() {
        apiClient = ApiClient.getInstance();
        liveResponse = new MutableLiveData<TestResponse>();
    }

    public static TestRepository getInstance(){
         if (instance==null){
             instance= new TestRepository();
         }
         return instance;
     }

    public MutableLiveData<TestResponse> getLiveResponse() {
        return liveResponse;
    }

    public void getResponse(){
        apiClient.getResponse().enqueue(new Callback<TestResponse>() {
            @Override
            public void onResponse(Call<TestResponse> call, Response<TestResponse> response) {
                TestResponse body = response.body();
                liveResponse.setValue(body);
                if (body !=null){
                    Log.d("Aly", "onResponse: "+body.getRSRP());
                }
            }

            @Override
            public void onFailure(Call<TestResponse> call, Throwable t) {

                Log.d("Aly", "onFailure: "+t.getMessage());
            }
        });
     }


}

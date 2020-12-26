package com.example.testapp.viewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.testapp.network.TestResponse;
import com.example.testapp.repositories.TestRepository;

public class TestViewModel  extends ViewModel {
    private TestRepository testRepository ;
    public MutableLiveData<TestResponse>liveResponse ;



    public void init() {
        if (liveResponse==null){
            this.testRepository = TestRepository.getInstance();
             liveResponse = testRepository.getLiveResponse();
        }
        return;
    }

    public MutableLiveData<TestResponse> getLiveResponse() {
        return liveResponse;
    }
    public void getResponse(){
        testRepository.getResponse();
    }



}

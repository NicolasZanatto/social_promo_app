package com.example.socialpromoapp.ui.feed;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.socialpromoapp.models.PostagemModel;
import com.example.socialpromoapp.repositories.postagem.PostagemRepository;

import java.util.ArrayList;

public class FeedViewModel extends ViewModel {

    private MutableLiveData<ArrayList<PostagemModel>> postagens;

    public FeedViewModel() {
    }

    public void init(){
        if(postagens!= null){
            return;
        }

        postagens = PostagemRepository.getInstance().getPostagens();
    }

    public LiveData<ArrayList<PostagemModel>> getPostagens() {
        return postagens;
    }
}
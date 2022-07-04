package com.example.socialpromoapp.ui.feed;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.socialpromoapp.models.LikeModel;
import com.example.socialpromoapp.models.PostagemModel;
import com.example.socialpromoapp.repositories.like.LikeRepository;
import com.example.socialpromoapp.repositories.postagem.PostagemRepository;

import java.util.ArrayList;

public class FeedViewModel extends ViewModel {

    private MutableLiveData<ArrayList<PostagemModel>> postagens;
    private MutableLiveData<ArrayList<LikeModel>> likes;

    public FeedViewModel() {
    }

    public void init(){
        postagens = PostagemRepository.getInstance().getPostagens();
        likes = LikeRepository.getInstance().getLikes();
    }

    public LiveData<ArrayList<PostagemModel>> getPostagens() {
        return postagens;
    }
    public LiveData<ArrayList<LikeModel>> getLikes() {
        return likes;
    }
}
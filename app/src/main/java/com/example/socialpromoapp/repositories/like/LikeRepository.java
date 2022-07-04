package com.example.socialpromoapp.repositories.like;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.socialpromoapp.models.LikeModel;
import com.example.socialpromoapp.models.PostagemModel;
import com.example.socialpromoapp.repositories.postagem.PostagemRepository;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class LikeRepository {
    private FirebaseAuth mAuth;
    static LikeRepository instance;
    private ArrayList<LikeModel> likeModels = new ArrayList<>();
    private MutableLiveData<ArrayList<LikeModel>> likes = new MutableLiveData<>();
    public LikeRepository() {
        mAuth = FirebaseAuth.getInstance();

    }

    public static LikeRepository getInstance(){

        if(instance == null){
            instance = new LikeRepository();
        }
        return instance;
    }

    public void inserirLike(@NonNull LikeModel likeModel){
        likeModel.setId(UUID.randomUUID().toString());
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        reference.child("likes").child(likeModel.getId()).setValue(likeModel);
    }

    public void atualizarLike(LikeModel likeModel) {
        // Create new post at /user-posts/$userid/$postid and at
        // /posts/$postid simultaneously
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        reference.child("likes").child(likeModel.getId()).setValue(likeModel);
        String key = reference.child("likes").push().getKey();
        Map<String, Object> postValues = likeModel.toMap();
        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/" + likeModel.getId() + "/" + key, postValues);

        reference.updateChildren(childUpdates);
    }

    //read
    public MutableLiveData<ArrayList<LikeModel>> getLikes(){
        loadLikesByUser();
        likes.setValue(likeModels);
        return likes;
    }

    public MutableLiveData<ArrayList<LikeModel>> getLikesFromPostagem(String idPostagem){
        loadLikesByPostagem(idPostagem);
        likes.setValue(likeModels);
        return likes;
    }

    private void loadLikesByUser(){
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("likes");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                likeModels.clear();
                for (DataSnapshot data : dataSnapshot.getChildren()){
                    LikeModel likeModel = data.getValue(LikeModel.class);
                    if(likeModel.getIdUsuario() != null && likeModel.getIdUsuario().equals(mAuth.getUid()))
                        likeModels.add(likeModel);
                }
                likes.postValue(likeModels);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }

    private void loadLikesByPostagem(String idPostagem){
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("likes");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                likeModels.clear();
                for (DataSnapshot data : dataSnapshot.getChildren()){
                    LikeModel likeModel = data.getValue(LikeModel.class);
                    if(likeModel.getIdPostagem() != null && likeModel.getIdPostagem().equals(idPostagem) && likeModel.isReacao())
                        likeModels.add(likeModel);
                }
                likes.postValue(likeModels);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }
}

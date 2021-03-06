package com.example.socialpromoapp.repositories.postagem;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.socialpromoapp.models.PostagemModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PostagemRepository {
    private FirebaseAuth mAuth;
    private FirebaseStorage storage;
    private StorageReference storageRef;
    static PostagemRepository instance;
    private ArrayList<PostagemModel> postagensModels = new ArrayList<>();
    private MutableLiveData<ArrayList<PostagemModel>> postagens = new MutableLiveData<>();
    private MutableLiveData<PostagemModel> postagemId = new MutableLiveData<>();

    public PostagemRepository() {
        mAuth = FirebaseAuth.getInstance();
        storage = FirebaseStorage.getInstance();
        // Create a storage reference from our app
        storageRef = storage.getReference();
    }

    public static PostagemRepository getInstance(){

        if(instance == null){
            instance = new PostagemRepository();
        }
        return instance;
    }


    // Write
    public void cadastrarAtualizarPostagem(PostagemModel postagemModel, final Runnable funcSucesso, final Runnable funcFalha) {
        adicionarImagemAoStorage(postagemModel, funcSucesso, funcFalha);
    }

    private void adicionarImagemAoStorage(PostagemModel postagemModel,final Runnable funcSucesso, final Runnable funcFalha){
        String uid;
        if(!TextUtils.isEmpty(postagemModel.getId())){
            uid = postagemModel.getId();
        }
        else{
            uid = UUID.randomUUID().toString();
        }
        // Create a reference to 'images/mountains.jpg'
        StorageReference postagemRef = storageRef.child("imagens"+ "/" + postagemModel.getId() + ".jpg");

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        postagemModel.getBitmapImagem().compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = postagemRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
                funcFalha.run();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                taskSnapshot.getMetadata().getReference().getDownloadUrl().toString();
                Task<Uri> result = taskSnapshot.getStorage().getDownloadUrl();
                result.addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        String imageUrl = uri.toString();
                        postagemModel.setCaminhoImagemUrl(imageUrl);
                        if(TextUtils.isEmpty(postagemModel.getId())){
                            cadastrarPostagem(postagemModel, uid);
                        }
                        else{
                            atualizarPostagem(postagemModel);
                        }

                    }
                });
                funcSucesso.run();
            }
        });
    }

    private void cadastrarPostagem(@NonNull PostagemModel postagemModel, String uid){
        postagemModel.setId(uid);
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        reference.child("postagens").child(postagemModel.getId()).setValue(postagemModel);
    }
    private void atualizarPostagem(PostagemModel postagemModel) {
        // Create new post at /user-posts/$userid/$postid and at
        // /posts/$postid simultaneously
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        reference.child("postagens").child(postagemModel.getId()).setValue(postagemModel);
        String key = reference.child("postagens").push().getKey();
        Map<String, Object> postValues = postagemModel.toMap();
        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/" + postagemModel.getId() + "/" + key, postValues);

        reference.updateChildren(childUpdates);
    }

    //Read
    public MutableLiveData<ArrayList<PostagemModel>> getPostagens(){
        loadPostagens();
        postagens.setValue(postagensModels);
        return postagens;
    }

    public MutableLiveData<PostagemModel> getPostagemById(String id){
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("postagens");

        databaseReference.child(id).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    postagemId.setValue(task.getResult().getValue(PostagemModel.class));
                }
            }
        });

        return postagemId;
    }

    private void loadPostagens(){
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("postagens");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                postagensModels.clear();
                for (DataSnapshot data : dataSnapshot.getChildren()){
                    Log.w(PostagemRepository.this.toString(), data.toString());
                    PostagemModel postagemModel = data.getValue(PostagemModel.class);
                    postagensModels.add(postagemModel);
                }
                postagens.postValue(postagensModels);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }
}

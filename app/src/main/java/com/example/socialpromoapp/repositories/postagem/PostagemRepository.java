package com.example.socialpromoapp.repositories.postagem;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.socialpromoapp.models.PostagemModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class PostagemRepository {
    private FirebaseAuth mAuth;
    private FirebaseStorage storage;
    private StorageReference storageRef;
    static PostagemRepository instance;
    private ArrayList<PostagemModel> postagensModels = new ArrayList<>();
    private MutableLiveData<ArrayList<PostagemModel>> postagens = new MutableLiveData<>();

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
    public void cadastrarPostagem(PostagemModel postagemModel, final Runnable funcSucesso, final Runnable funcFalha) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        reference.child("postagens").child(postagemModel.getId()).setValue(postagemModel);
        adicionarImagemAoStorage(postagemModel.getBitmapImagem(),postagemModel.getId(), funcSucesso, funcFalha);
        funcSucesso.run();
    }

    private void adicionarImagemAoStorage(Bitmap imagemBitmap, String postagemUID,final Runnable funcSucesso, final Runnable funcFalha){
        // Create a reference to 'images/mountains.jpg'
        StorageReference postagemRef = storageRef.child("imagens"+ "/" + postagemUID + ".jpg");

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        imagemBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
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
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                // ...
                funcSucesso.run();
            }
        });
    }

    //Read
    public MutableLiveData<ArrayList<PostagemModel>> getPostagens(){
        loadPostagens();

        postagens.setValue(postagensModels);
        return postagens;
    }

    private void loadPostagens(){
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("postagens");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()){
                    Log.w(PostagemRepository.this.toString(), data.toString());
                    postagensModels.add(data.getValue(PostagemModel.class));
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

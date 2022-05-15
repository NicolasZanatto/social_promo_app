package com.example.socialpromoapp.repositories.postagem;

import android.graphics.Bitmap;

import androidx.annotation.NonNull;

import com.example.socialpromoapp.models.PostagemModel;
import com.example.socialpromoapp.models.UsuarioModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.UUID;

public class PostagemRepository {
    FirebaseAuth mAuth;
    FirebaseStorage storage;
    StorageReference storageRef;
    public PostagemRepository() {

        mAuth = FirebaseAuth.getInstance();
        storage = FirebaseStorage.getInstance();
        // Create a storage reference from our app
        storageRef = storage.getReference();

    }

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
}

package com.example.socialpromoapp.repositories.login;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.socialpromoapp.models.CategoriaModel;
import com.example.socialpromoapp.models.UsuarioModel;
import com.example.socialpromoapp.repositories.postagem.PostagemRepository;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class UsuarioRepository {
    FirebaseAuth mAuth;
    private DatabaseReference databaseReference;
    static UsuarioRepository instance;
    public UsuarioRepository() {
        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("usuarios");
    }

    public static UsuarioRepository getInstance(){

        if(instance == null){
            instance = new UsuarioRepository();
        }
        return instance;
    }

    public void cadastrarUsuario(UsuarioModel usuarioModel, final Runnable funcSucesso, final Runnable funcFalha){
        mAuth.createUserWithEmailAndPassword(usuarioModel.getEmail(),usuarioModel.getSenha()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    usuarioModel.setId(mAuth.getUid());
                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
                    reference.child("usuarios").child(usuarioModel.getId()).setValue(usuarioModel);
                    funcSucesso.run();
                }else{
                    funcFalha.run();
                }
            }
        });
    }

    public void obterUsuario(String id, MutableLiveData<String> usuario){
        if(id == null) return;
        databaseReference.child(id).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    UsuarioModel usuarioModel = task.getResult().getValue(UsuarioModel.class);
                    usuario.setValue(usuarioModel.getNome());
                    Log.d("firebase", String.valueOf(task.getResult().getValue()));
                }
            }
        });
    }
}

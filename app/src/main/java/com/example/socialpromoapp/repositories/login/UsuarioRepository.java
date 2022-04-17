package com.example.socialpromoapp.repositories.login;

import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.socialpromoapp.activities.LoginActivity;
import com.example.socialpromoapp.activities.RegisterActivity;
import com.example.socialpromoapp.models.UsuarioModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UsuarioRepository {
    FirebaseAuth mAuth;
    public UsuarioRepository() {
        mAuth = FirebaseAuth.getInstance();
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
}

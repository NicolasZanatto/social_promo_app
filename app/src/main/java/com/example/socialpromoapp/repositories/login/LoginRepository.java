package com.example.socialpromoapp.repositories.login;

import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.socialpromoapp.activities.LoginActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginRepository {
    FirebaseAuth mAuth;

    public LoginRepository(){
        mAuth = FirebaseAuth.getInstance();
    }

    public void realizarLogin(String email, String password, final Runnable funcSucesso, final Runnable funcFalha){
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    funcSucesso.run();
                }else{
                    funcFalha.run();
                }
            }
        });

    }
}

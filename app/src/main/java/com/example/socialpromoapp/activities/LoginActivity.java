package com.example.socialpromoapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.socialpromoapp.MainActivity;
import com.example.socialpromoapp.R;
import com.example.socialpromoapp.models.UsuarioModel;
import com.example.socialpromoapp.repositories.login.LoginRepository;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    TextInputEditText etLoginEmail;
    TextInputEditText etLoginPassword;
    TextView tvRegisterHere;
    Button btnLogin;

    FirebaseAuth mAuth;
    LoginRepository loginRepository;
    UsuarioModel usuarioModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etLoginEmail = findViewById(R.id.etLoginEmail);
        etLoginPassword = findViewById(R.id.etLoginPass);
        tvRegisterHere = findViewById(R.id.tvRegisterHere);
        btnLogin = findViewById(R.id.btnLogin);

        mAuth = FirebaseAuth.getInstance();
        loginRepository = new LoginRepository();
        btnLogin.setOnClickListener(view -> {
            usuarioModel = new UsuarioModel(etLoginEmail.getText().toString(),etLoginPassword.getText().toString());
            loginUser();
        });
        tvRegisterHere.setOnClickListener(view ->{
            startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
        });
    }

    private void loginUser(){
        if(!usuarioModel.validarLogin(etLoginEmail,etLoginPassword )) return;

        Runnable taskSuccesfully = new Runnable() {
            public void run() {
                Toast.makeText(LoginActivity.this, "Usuário logado com sucesso", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
            }
        };

        Runnable taskFailed = new Runnable() {
            public void run() {
                Toast.makeText(LoginActivity.this, "Login Inválido: ", Toast.LENGTH_SHORT).show();
            }
        };

        usuarioModel.realizarLogin(taskSuccesfully, taskFailed);
    }

}
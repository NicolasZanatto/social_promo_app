package com.example.socialpromoapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.socialpromoapp.MainActivity;
import com.example.socialpromoapp.R;
import com.example.socialpromoapp.models.Login;
import com.example.socialpromoapp.repositories.login.LoginRepository;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    TextInputEditText etLoginEmail;
    TextInputEditText etLoginPassword;
    TextView tvRegisterHere;
    Button btnLogin;

    FirebaseAuth mAuth;
    LoginRepository loginRepository;
    Login loginModel;

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
            loginModel = new Login(etLoginEmail.getText().toString(),etLoginPassword.getText().toString());
            loginUser();
        });
        tvRegisterHere.setOnClickListener(view ->{
            startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
        });
    }

    private void loginUser(){
        if(!loginModel.validarLogin(etLoginEmail,etLoginPassword )) return;

        Runnable taskSuccesfully = new Runnable() {
            public void run() {
                Toast.makeText(LoginActivity.this, "User logged in successfully", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
            }
        };

        Runnable taskFailed = new Runnable() {
            public void run() {
                Toast.makeText(LoginActivity.this, "Login Inválido: ", Toast.LENGTH_SHORT).show();
            }
        };

        loginModel.realizarLogin(taskSuccesfully, taskFailed);
    }

}
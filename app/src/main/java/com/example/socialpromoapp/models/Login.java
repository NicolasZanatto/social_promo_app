package com.example.socialpromoapp.models;

import android.text.TextUtils;

import com.example.socialpromoapp.repositories.login.LoginRepository;
import com.google.android.material.textfield.TextInputEditText;

public class Login {
    private String email;
    private String senha;

    public Login(String email, String senha) {
        this.email = email;
        this.senha = senha;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public boolean validarLogin(TextInputEditText etLoginEmail,TextInputEditText etLoginPassword) {
        if (TextUtils.isEmpty(this.email)) {
            etLoginEmail.setError("Email cannot be empty");
            etLoginEmail.requestFocus();
            return false;
        }
        else if (TextUtils.isEmpty(this.senha)) {
            etLoginPassword.setError("Password cannot be empty");
            etLoginPassword.requestFocus();
            return false;
        }

        return true;
    }

    public void realizarLogin(final Runnable funcSucesso, final Runnable funcFalha){
        LoginRepository loginRepository = new LoginRepository();
        loginRepository.realizarLogin(this.email,this.senha,funcSucesso,funcFalha);
    }
}

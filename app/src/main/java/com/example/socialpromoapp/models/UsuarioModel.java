package com.example.socialpromoapp.models;

import android.text.TextUtils;

import com.example.socialpromoapp.repositories.login.LoginRepository;
import com.example.socialpromoapp.repositories.login.UsuarioRepository;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;

public class UsuarioModel extends PessoaModel {
    private String email;
    private String senha;

    public UsuarioModel(){}
    public UsuarioModel(String email, String senha) {
        super();
        this.email = email;
        this.senha = senha;
    }

    public UsuarioModel(String nome, Date dataNascimento, String celular, String email, String senha) {
        super(nome, celular, dataNascimento);
        this.email = email;
        this.senha = senha;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Exclude
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

    public boolean validarCadastro(TextInputEditText etRegNome, TextInputEditText etRegPassword, TextInputEditText etRegEmail, TextInputEditText etRegCelular){
        if (TextUtils.isEmpty(this.getNome())){
            etRegNome.setError("Nome não pode ser vazio");
            etRegNome.requestFocus();
            return false;
        }
        else if (TextUtils.isEmpty(this.getCelular())){
            etRegCelular.setError("Celular não pode ser vazio");
            etRegCelular.requestFocus();
            return false;
        }
        else if (TextUtils.isEmpty(this.getEmail())){
            etRegEmail.setError("Email não pode ser vazio");
            etRegEmail.requestFocus();
            return false;
        }else if (TextUtils.isEmpty(this.getSenha())) {
            etRegPassword.setError("Senha não pode ser vazio");
            etRegPassword.requestFocus();
            return false;
        }
        return true;
    }

    public void cadastrarUsuario(final Runnable funcSucesso, final Runnable funcFalha){
        UsuarioRepository usuarioRepository = new UsuarioRepository();
        usuarioRepository.cadastrarUsuario(this, funcSucesso, funcFalha);
    }
}

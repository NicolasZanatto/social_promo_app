package com.example.socialpromoapp.models;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;

public class PessoaModel {
    private String id;
    private String nome;
    private String celular;
    private Date dataNascimento;
    private String email;
    public PessoaModel(){}
    public PessoaModel(String nome, String celular, Date dataNascimento) {
        this.nome = nome;
        this.celular = celular;
        this.dataNascimento = dataNascimento;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }
}

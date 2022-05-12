package com.example.socialpromoapp.models;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class EstabelecimentoModel {
    public Integer id;
    public String descricao;

    public EstabelecimentoModel() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}

package com.example.socialpromoapp.models;

public class ComentarioModel {
    public String id;
    public String idUsuario;
    public String descricao;
    public String idPostagem;




    public ComentarioModel(){

    }
    public ComentarioModel(String id, String idPostagem, String idUsuario, String descricao){
        this.id = id;
        this.idUsuario = idUsuario;
        this.descricao = descricao;
        this.idPostagem = idPostagem;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    public String getIdPostagem() {
        return idPostagem;
    }

    public void setIdPostagem(String idPostagem) {
        this.idPostagem = idPostagem;
    }
}

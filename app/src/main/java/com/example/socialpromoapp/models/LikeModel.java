package com.example.socialpromoapp.models;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class LikeModel {
    public String id;
    public String idUsuario;
    public boolean reacao;
    public String idPostagem;

    public LikeModel(){}
    public LikeModel(String id, String idUsuario, boolean reacao, String idPostagem){
        this.id = id;
        this.idUsuario = idUsuario;
        this.reacao = reacao;
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

    public boolean isReacao() {
        return reacao;
    }

    public void setReacao(boolean reacao) {
        this.reacao = reacao;
    }

    public String getIdPostagem() {
        return idPostagem;
    }

    public void setIdPostagem(String idPostagem) {
        this.idPostagem = idPostagem;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("id", id);
        result.put("idUsuario", idUsuario);
        result.put("reacao",reacao);
        result.put("idPostagem",idPostagem);

        return result;
    }
}

package com.example.socialpromoapp.models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ComentarioModel {
    public String id;
    public String idUsuario;
    public String descricao;
    public String idPostagem;
    public String data;




    public ComentarioModel(){

    }
    public ComentarioModel(String id, String idPostagem, String idUsuario, String descricao){
        this.id = id;
        this.idUsuario = idUsuario;
        this.descricao = descricao;
        this.idPostagem = idPostagem;
        this.data = setDataFormatada();
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
    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String setDataFormatada(){
        String pattern = "dd/MM/yyyy HH:mm";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

        return simpleDateFormat.format(new Date());
    }

    public Date getDataDateFormat() throws ParseException {
        String pattern = "dd/MM/yyyy HH:mm";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        return simpleDateFormat.parse(this.data);
    }
}

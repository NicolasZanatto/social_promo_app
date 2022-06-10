package com.example.socialpromoapp.models;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class EstabelecimentoModel {
    public Integer id;
    public String descricao;
    public String latitude;
    public String longitude;
    public EstabelecimentoModel() {}

    public String getLongitude() {
        return longitude;
    }
    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
    public String getLatitude() {
        return latitude;
    }
    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }
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

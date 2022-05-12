package com.example.socialpromoapp.models;

import android.graphics.Bitmap;

import com.example.socialpromoapp.repositories.login.UsuarioRepository;
import com.example.socialpromoapp.repositories.postagem.PostagemRepository;
import com.google.firebase.database.IgnoreExtraProperties;


@IgnoreExtraProperties
public class PostagemModel {
    public String id;
    public String titulo;
    public Long preco;
    public String descricao;
    public Integer idCategoria;
    public Integer idEstabelecimento;
    public String caminhoImagem;

    public PostagemModel(){}

    public PostagemModel(String id, String titulo, Long preco, String descricao, Integer idCategoria, Integer idEstabelecimento) {
        this.id = id;
        this.titulo = titulo;
        this.preco = preco;
        this.descricao = descricao;
        this.idCategoria = idCategoria;
        this.idEstabelecimento = idEstabelecimento;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Long getPreco() {
        return preco;
    }

    public void setPreco(Long preco) {
        this.preco = preco;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Integer getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(Integer idCategoria) {
        this.idCategoria = idCategoria;
    }

    public Integer getIdEstabelecimento() {
        return idEstabelecimento;
    }

    public void setIdEstabelecimento(Integer idEstabelecimento) {
        this.idEstabelecimento = idEstabelecimento;
    }

    public String getCaminhoImagem() {
        return caminhoImagem;
    }

    public void setCaminhoImagem(String caminhoImagem) {
        this.caminhoImagem = caminhoImagem;
    }

    public void cadastrarPostagem(Bitmap imagem, final Runnable funcSucesso, final Runnable funcFalha){
        PostagemRepository postagemRepository = new PostagemRepository();
        postagemRepository.cadastrarPostagem(this, imagem, funcSucesso, funcFalha);
    }
}

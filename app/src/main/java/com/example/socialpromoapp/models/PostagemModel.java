package com.example.socialpromoapp.models;

import android.graphics.Bitmap;
import android.text.TextUtils;
import android.widget.AutoCompleteTextView;

import com.example.socialpromoapp.repositories.estabelecimento.EstabelecimentoRepository;
import com.example.socialpromoapp.repositories.login.UsuarioRepository;
import com.example.socialpromoapp.repositories.postagem.PostagemRepository;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;


public class PostagemModel {
    public String id;
    public String idUsuario;
    public String titulo;
    public Double preco;
    public String descricao;
    public Integer idCategoria;
    public String categoriaDesc;
    public Integer idEstabelecimento;

    public String getEstabelecimentoDesc() {
        return estabelecimentoDesc;
    }

    public void setEstabelecimentoDesc(String estabelecimentoDesc) {
        this.estabelecimentoDesc = estabelecimentoDesc;
    }

    public String estabelecimentoDesc;
    public String caminhoImagemUrl;

    @Exclude
    public Bitmap bitmapImagem;

    public PostagemModel(){}

    public PostagemModel(String id, String idUsuario, String titulo, Double preco, String descricao, Integer idCategoria, String categoriaDesc, Integer idEstabelecimento, String estabelecimentoDesc, Bitmap bitmapImagem) {
        this.id = id;
        this.idUsuario = idUsuario;
        this.titulo = titulo;
        this.preco = preco;
        this.descricao = descricao;
        this.idCategoria = idCategoria;
        this.categoriaDesc = categoriaDesc;
        this.idEstabelecimento = idEstabelecimento;
        this.estabelecimentoDesc = estabelecimentoDesc;
        this.bitmapImagem = bitmapImagem;
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

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
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

    public String getCategoriaDesc() {
        return categoriaDesc;
    }

    public void setCategoriaDesc(String categoriaDesc) {
        this.categoriaDesc = categoriaDesc;
    }

    @Exclude
    public Bitmap getBitmapImagem() {
        return this.bitmapImagem;
    }

    public void setBitmapImagem(Bitmap bitmap){ this.bitmapImagem = bitmap;}

    public void setCaminhoImagemUrl(String url){
        this.caminhoImagemUrl = url;
    }

    public String getCaminhoImagemUrl(){
        return this.caminhoImagemUrl;
    }

    public boolean Valid(TextInputEditText etTitulo, TextInputEditText etPreco, AutoCompleteTextView etCategorias, AutoCompleteTextView etEstabelecimentos){
        if (TextUtils.isEmpty(this.titulo)){
            etTitulo.setError("Título não pode ser vazio");
            etTitulo.requestFocus();
            return false;
        }

        if(this.preco == 0){
            etPreco.setError("Preço não pode ser vazio");
            etPreco.requestFocus();
            return false;
        }

        if(this.idCategoria == 0 || this.idCategoria < 0){
            etCategorias.setError("Categoria não pode ser vazio");
            etCategorias.requestFocus();
            return false;
        }

        if(this.idEstabelecimento == 0 || this.idEstabelecimento < 0){
            etEstabelecimentos.setError("Estabelecimento não pode ser vazio");
            etEstabelecimentos.requestFocus();
            return false;
        }

        return true;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("id", id);
        result.put("caminhoImagemUrl", caminhoImagemUrl);
        result.put("categoriaDesc", categoriaDesc);
        result.put("descricao", descricao);
        result.put("estabelecimentoDesc", estabelecimentoDesc);
        result.put("idCategoria", idCategoria);
        result.put("idEstabelecimento", idEstabelecimento);
        result.put("idUsuario",idUsuario);
        result.put("preco",preco);
        result.put("titulo",titulo);

        return result;
    }

    public void CadastrarPostagem(Runnable funcSucesso, Runnable funcFalha){
        PostagemRepository postagemRepository = new PostagemRepository();
        postagemRepository.cadastrarAtualizarPostagem(this,funcSucesso, funcFalha);
    }
}

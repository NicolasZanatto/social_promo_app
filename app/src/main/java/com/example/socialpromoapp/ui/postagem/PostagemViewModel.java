package com.example.socialpromoapp.ui.postagem;

import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.socialpromoapp.models.CategoriaModel;
import com.example.socialpromoapp.models.EstabelecimentoModel;
import com.example.socialpromoapp.repositories.categoria.CategoriaRepository;
import com.example.socialpromoapp.repositories.estabelecimento.EstabelecimentoRepository;

import java.util.ArrayList;
import java.util.List;

public class PostagemViewModel extends ViewModel {

    private EstabelecimentoRepository estabelecimentoRepository;
    private CategoriaRepository categoriaRepository;
    private final MutableLiveData<List<EstabelecimentoModel>> estabelecimentos;
    private final MutableLiveData<List<CategoriaModel>> categorias;
    private String categoriaSelecionada;
    private String estabelecimentoSelecionado;

    public PostagemViewModel() {
        this.estabelecimentoRepository = new EstabelecimentoRepository();
        this.categoriaRepository = new CategoriaRepository();
        this.estabelecimentos = new MutableLiveData<>();
        this.categorias = new MutableLiveData<>();

        estabelecimentoRepository.getAll(this.estabelecimentos);
        categoriaRepository.getAll(this.categorias);
    }

    public LiveData<List<EstabelecimentoModel>> getEstabelecimentos(){
        return this.estabelecimentos;
    }
    public LiveData<List<CategoriaModel>> getCategorias(){
        return this.categorias;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public Integer getCategoriaId(){
        List<CategoriaModel> categorias = this.categorias.getValue();
        if(categorias == null) return 0;

        CategoriaModel categoriaModel = categorias.stream()
        .filter((categoria) -> categoria.getDescricao().equals(this.categoriaSelecionada))
        .findFirst()
        .orElse(new CategoriaModel());

        return categoriaModel.getId();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public String getCategoriaDesc(){
        List<CategoriaModel> categorias = this.categorias.getValue();
        if(categorias == null) return "";

        CategoriaModel categoriaModel = categorias.stream()
                .filter((categoria) -> categoria.getDescricao().equals(this.categoriaSelecionada))
                .findFirst()
                .orElse(new CategoriaModel());

        return categoriaModel.getDescricao();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public Integer getEstabelecimentoId(){
        List<EstabelecimentoModel> estabelecimentos = this.estabelecimentos.getValue();
        if(estabelecimentos == null) return 0;

        EstabelecimentoModel estabelecimentoModel = estabelecimentos.stream()
                .filter((estabelecimento) -> estabelecimento.getDescricao().equals(this.estabelecimentoSelecionado))
                .findFirst()
                .orElse(new EstabelecimentoModel());

        return estabelecimentoModel.getId();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public String getEstabelecimentoDesc(){
        List<EstabelecimentoModel> estabelecimentos = this.estabelecimentos.getValue();
        if(estabelecimentos == null) return "";

        EstabelecimentoModel estabelecimentoModel = estabelecimentos.stream()
                .filter((estabelecimento) -> estabelecimento.getDescricao().equals(this.estabelecimentoSelecionado))
                .findFirst()
                .orElse(new EstabelecimentoModel());

        return estabelecimentoModel.getDescricao();
    }

    public void setEstabelecimentoSelecionado(String descricao){
        this.estabelecimentoSelecionado = descricao;
    }
    public void setCategoriaSelecionada(String descricao){
        this.categoriaSelecionada = descricao;
    }
}
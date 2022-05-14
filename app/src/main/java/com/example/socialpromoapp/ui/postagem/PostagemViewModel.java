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
    public Integer getCategoriaId(String descricao){
        List<CategoriaModel> categorias = this.categorias.getValue();

        CategoriaModel categoriaModel = categorias.stream()
        .filter((categoria) -> categoria.getDescricao().equals(descricao))
        .findFirst()
        .orElse(new CategoriaModel());

        return categoriaModel.getId();
    }
}
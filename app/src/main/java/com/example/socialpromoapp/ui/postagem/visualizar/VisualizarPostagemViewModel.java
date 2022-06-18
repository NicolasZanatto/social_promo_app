package com.example.socialpromoapp.ui.postagem.visualizar;

import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.socialpromoapp.models.CategoriaModel;
import com.example.socialpromoapp.models.ComentarioModel;
import com.example.socialpromoapp.models.EstabelecimentoModel;
import com.example.socialpromoapp.models.PostagemModel;
import com.example.socialpromoapp.repositories.categoria.CategoriaRepository;
import com.example.socialpromoapp.repositories.comentario.ComentarioRepository;
import com.example.socialpromoapp.repositories.estabelecimento.EstabelecimentoRepository;
import com.example.socialpromoapp.repositories.postagem.PostagemRepository;

import java.util.ArrayList;
import java.util.List;

public class VisualizarPostagemViewModel extends ViewModel {

    private MutableLiveData<PostagemModel> postagem;
    private MutableLiveData<EstabelecimentoModel> estabelecimentoId;
    private MutableLiveData<ArrayList<ComentarioModel>> comentarios;

    public VisualizarPostagemViewModel() {
    }

    public void init(String id){
        postagem = PostagemRepository.getInstance().getPostagemById(id);
        comentarios = ComentarioRepository.getInstance().getComentarios(id);
    }
    public void initEstabelecimento(String id){
        estabelecimentoId = EstabelecimentoRepository.getInstance().getEstabelecimentoById(id);
    }

    public LiveData<PostagemModel> getPostagem() {
        return postagem;
    }

    public LiveData<EstabelecimentoModel> getEstabelecimento(){
        return estabelecimentoId;
    }

    public LiveData<ArrayList<ComentarioModel>> getComentarios() {
        return comentarios;
    }

}
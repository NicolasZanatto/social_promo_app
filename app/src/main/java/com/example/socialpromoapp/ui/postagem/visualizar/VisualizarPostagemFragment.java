package com.example.socialpromoapp.ui.postagem.visualizar;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.socialpromoapp.databinding.FragmentVisualizarPostagemBinding;
import com.example.socialpromoapp.models.PostagemModel;


public class VisualizarPostagemFragment extends Fragment {
    private FragmentVisualizarPostagemBinding binding;
    VisualizarPostagemViewModel cadastroViewModel;

    @RequiresApi(api = Build.VERSION_CODES.N)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        cadastroViewModel =
                new ViewModelProvider(this).get(VisualizarPostagemViewModel.class);
        cadastroViewModel.init(getArguments().getString("id"));
        binding = FragmentVisualizarPostagemBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView txtTitulo = binding.etTitulo;
        final TextView txtPreco = binding.etPreco;
        final TextView txtDescricao = binding.etDescricao;
        final TextView txtEstabelecimento = binding.etEstabelecimento;
        final TextView txtCategoria = binding.etCategoria;
        cadastroViewModel.getPostagem().observe(getViewLifecycleOwner(), new Observer<PostagemModel>() {
            @Override
            public void onChanged(PostagemModel postagemModel) {
                txtTitulo.setText(postagemModel.getTitulo());
                String preco = postagemModel.getPreco().toString().replace('.',',');
                txtPreco.setText("R$ " + preco);
                txtDescricao.setText(postagemModel.getTitulo());
                txtEstabelecimento.setText(postagemModel.getEstabelecimentoDesc());
                txtCategoria.setText(postagemModel.getCategoriaDesc());
                Glide.with(getContext()).load(postagemModel.getCaminhoImagemUrl()).into(binding.imgPostagem);
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}

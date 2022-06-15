package com.example.socialpromoapp.ui.postagem;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;
import com.example.socialpromoapp.R;
import com.example.socialpromoapp.databinding.FragmentPostagemBinding;
import com.example.socialpromoapp.models.CategoriaModel;
import com.example.socialpromoapp.models.EstabelecimentoModel;
import com.example.socialpromoapp.models.PostagemModel;
import com.example.socialpromoapp.ui.shared.SharedFragment;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class PostagemFragment extends SharedFragment {

    private FragmentPostagemBinding binding;
    AutoCompleteTextView editTextEstabelecimentos;
    AutoCompleteTextView editTextCategorias;
    TextView tvLoginHere;
    Button btnPostar;
    PostagemViewModel cadastroViewModel;
    ImageView imagePostagem;
    Button btnTirarFoto;
    String id;

    private void loadComboEstabelecimentos(List<String> listaEstabelecimentos){
        ArrayAdapter<String> adapterEstabelecimentos =
                new ArrayAdapter<String>(
                        getActivity(),
                        R.layout.dropdown_menu_popup_item,
                        listaEstabelecimentos);

        editTextEstabelecimentos.setAdapter(adapterEstabelecimentos);

        editTextEstabelecimentos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String value = (String)adapterView.getItemAtPosition(i);
                cadastroViewModel.setEstabelecimentoSelecionado(value);
            }
        });
    }

    private void loadComboCategorias(List<String> listaCategorias){
        ArrayAdapter<String> adapterCategorias =
                new ArrayAdapter<String>(
                        getActivity(),
                        R.layout.dropdown_menu_popup_item,
                        listaCategorias);

        editTextCategorias = binding.categorias;
        editTextCategorias.setAdapter(adapterCategorias);

        editTextCategorias.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String value = (String)adapterView.getItemAtPosition(i);
                cadastroViewModel.setCategoriaSelecionada(value);
            }
        });
    }

    // You can do the assignment inside onAttach or onCreate, i.e, before the activity is displayed
    ActivityResultLauncher<Intent> activityResultTirarFotoLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        // There are no request codes
                        Intent data = result.getData();
                        Bundle extras = data.getExtras();

                        Bitmap imagem = (Bitmap)extras.get("data");
                        imagePostagem.setImageBitmap(imagem);
                        imagePostagem.setVisibility(View.VISIBLE);
                    }
                }
            });

    private void loadImagePostagem(){
        imagePostagem = binding.imgPostagem;
        btnTirarFoto = binding.btnTirarFoto;
        btnTirarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                activityResultTirarFotoLauncher.launch(intent);
            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        super.init(getActivity());
        if(ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.CAMERA}, 0);
        }
        cadastroViewModel =
                new ViewModelProvider(this).get(PostagemViewModel.class);

        binding = FragmentPostagemBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        if(getArguments() !=null) {
            id = getArguments().getString("id");
            cadastroViewModel.initEdicao(id);
            cadastroViewModel.getPostagem().observe(getViewLifecycleOwner(), new Observer<PostagemModel>() {
                @Override
                public void onChanged(PostagemModel postagemModel) {
                    binding.etTitulo.setText(postagemModel.getTitulo());
                    String preco = postagemModel.getPreco().toString().replace('.',',');
                    binding.etPreco.setText("R$ " + preco);
                    binding.etDescricao.setText(postagemModel.getTitulo());
                    binding.estabelecimentos.setText(postagemModel.getEstabelecimentoDesc());
                    binding.categorias.setText(postagemModel.getCategoriaDesc());
                    Glide.with(getContext()).load(postagemModel.getCaminhoImagemUrl()).into(binding.imgPostagem);
                }
            });
        }


        editTextEstabelecimentos = binding.estabelecimentos;
        List<String> listaEstabelecimentos = new ArrayList<>();
        cadastroViewModel.getEstabelecimentos().observe(getViewLifecycleOwner(), new Observer<List<EstabelecimentoModel>>() {
            @Override
            public void onChanged(@Nullable final List<EstabelecimentoModel> terms) {
                for (EstabelecimentoModel term : terms) {
                    listaEstabelecimentos.add(term.getDescricao());
                }
                loadComboEstabelecimentos(listaEstabelecimentos);
            }
        });

        editTextCategorias = binding.categorias;
        List<String> listaCategorias = new ArrayList<>();
        cadastroViewModel.getCategorias().observe(getViewLifecycleOwner(), new Observer<List<CategoriaModel>>() {
            @Override
            public void onChanged(@Nullable final List<CategoriaModel> terms) {
                for (CategoriaModel term : terms) {
                    listaCategorias.add(term.getDescricao());
                }
                loadComboCategorias(listaCategorias);
            }
        });

        loadImagePostagem();

        btnPostar = binding.btnPostar;
        btnPostar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double preco;
                String strPreco = binding.etPreco.getText().toString().replace("R$","").replace(",",".");
                if(TextUtils.isEmpty(strPreco)){
                    preco = 0;
                }
                else {
                    preco = Double.parseDouble(strPreco.trim());
                }

                if((BitmapDrawable)imagePostagem.getDrawable() == null){
                    Toast.makeText(getActivity(), "É necessário adicionar uma imagem a postagem", Toast.LENGTH_SHORT).show();
                    return;
                }

                PostagemModel postagemModel = new PostagemModel(
                        id,
                        mAuth.getUid(),
                        binding.etTitulo.getText().toString(),
                        preco,
                        binding.etDescricao.getText().toString(),
                        cadastroViewModel.getCategoriaId(binding.categorias.getText().toString()),
                        binding.categorias.getText().toString(),
                        cadastroViewModel.getEstabelecimentoId(binding.estabelecimentos.getText().toString()),
                        cadastroViewModel.getEstabelecimentoDesc(),
                        ((BitmapDrawable)imagePostagem.getDrawable()).getBitmap()
                );

                if(postagemModel.Valid(binding.etTitulo, binding.etPreco, binding.categorias, binding.estabelecimentos)){

                    postagemModel.CadastrarPostagem(funcSucesso, funcFalha);
                }

            }
        });

        return root;
    }

    Runnable funcSucesso = new Runnable() {
        public void run() {
            Toast.makeText(getActivity(), "Postagem cadastrada com sucesso", Toast.LENGTH_SHORT).show();
            navController.navigate(R.id.nav_feed);
        }
    };

    Runnable funcFalha = new Runnable() {
        public void run() {
            Toast.makeText(getActivity(), "Falha ao cadastrar a postagem", Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
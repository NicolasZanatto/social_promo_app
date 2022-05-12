package com.example.socialpromoapp.ui.postagem;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import com.example.socialpromoapp.R;
import com.example.socialpromoapp.databinding.FragmentPostagemBinding;
import com.example.socialpromoapp.models.CategoriaModel;
import com.example.socialpromoapp.models.EstabelecimentoModel;
import com.example.socialpromoapp.models.PostagemModel;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PostagemFragment extends Fragment {

    private FragmentPostagemBinding binding;
    TextInputEditText etRegEmail;
    TextInputEditText etRegPassword;
    TextInputEditText etRegDataNascimento;
    TextInputEditText etRegCelular;
    TextInputEditText etRegNome;
    AutoCompleteTextView editTextEstabelecimentos;
    AutoCompleteTextView editTextCategorias;
    TextView tvLoginHere;
    Button btnPostar;
    FirebaseAuth mAuth;
    PostagemViewModel cadastroViewModel;
    ImageView imagePostagem;
    Button btnTirarFoto;
    private NavController navController;

    private void loadComboEstabelecimentos(List<String> listaEstabelecimentos){
        ArrayAdapter<String> adapterEstabelecimentos =
                new ArrayAdapter<String>(
                        getActivity(),
                        R.layout.dropdown_menu_popup_item,
                        listaEstabelecimentos);

        editTextEstabelecimentos.setAdapter(adapterEstabelecimentos);
    }

    private void loadComboCategorias(List<String> listaCategorias){
        ArrayAdapter<String> adapterCategorias =
                new ArrayAdapter<String>(
                        getActivity(),
                        R.layout.dropdown_menu_popup_item,
                        listaCategorias);

        editTextCategorias = binding.categorias;
        editTextCategorias.setAdapter(adapterCategorias);
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


    Runnable funcSucesso = new Runnable() {
        public void run() {
            Toast.makeText(getActivity(), "Usuário cadastrado com sucesso", Toast.LENGTH_SHORT).show();
            navController.navigate(R.id.nav_feed);
        }
    };

    Runnable funcFalha = new Runnable() {
        public void run() {
            Toast.makeText(getActivity(), "Falha ao cadastrar o usuário", Toast.LENGTH_SHORT).show();
        }
    };

    @RequiresApi(api = Build.VERSION_CODES.N)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        if(ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.CAMERA}, 0);
        }
        navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment_content_feed);
        cadastroViewModel =
                new ViewModelProvider(this).get(PostagemViewModel.class);

        binding = FragmentPostagemBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

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
                PostagemModel postagemModel = new PostagemModel();
                //TODO
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
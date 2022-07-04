package com.example.socialpromoapp.ui.postagem.visualizar;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.socialpromoapp.R;
import com.example.socialpromoapp.databinding.FragmentVisualizarPostagemBinding;
import com.example.socialpromoapp.models.ComentarioModel;
import com.example.socialpromoapp.models.EstabelecimentoModel;
import com.example.socialpromoapp.models.PostagemModel;
import com.example.socialpromoapp.repositories.comentario.ComentarioRepository;
import com.example.socialpromoapp.ui.feed.FeedAdapter;
import com.example.socialpromoapp.ui.postagem.cadastrar.componentes.ComentarioAdapter;
import com.example.socialpromoapp.ui.shared.SharedFragment;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.UUID;

public class VisualizarPostagemFragment extends SharedFragment implements OnMapReadyCallback {
    private FragmentVisualizarPostagemBinding binding;
    private GoogleMap mMap;
    private RecyclerView rvComentarios;
    private RecyclerView.Adapter adapter;
    private String id;
    VisualizarPostagemViewModel cadastroViewModel;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        super.init(getActivity());
        id = getArguments().getString("id");
        cadastroViewModel =
                new ViewModelProvider(this).get(VisualizarPostagemViewModel.class);
        cadastroViewModel.init(id);
        binding = FragmentVisualizarPostagemBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        final TextView txtTitulo = binding.etTitulo;
        final TextView txtPreco = binding.etPreco;
        final TextView txtDescricao = binding.etDescricao;
        final TextView txtEstabelecimento = binding.etEstabelecimento;
        final TextView txtCategoria = binding.etCategoria;
        final Button buttonEditar = binding.btnEditar;
        buttonEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("id",id);
                navController.navigate(R.id.nav_postagem, bundle);
            }
        });

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
                cadastroViewModel.initEstabelecimento(postagemModel.getIdEstabelecimento().toString());

                if(acaoRealizadaPeloUsuarioLogado(postagemModel.getIdUsuario()) ){
                    buttonEditar.setVisibility(View.VISIBLE);
                }
                else{
                    buttonEditar.setVisibility(View.INVISIBLE);
                }
            }
        });

        rvComentarios = binding.rvComentarios;
        rvComentarios.setHasFixedSize(true);
        rvComentarios.setLayoutManager(new LinearLayoutManager(getContext()));
        cadastroViewModel.getComentarios().observe(getViewLifecycleOwner(), new Observer<ArrayList<ComentarioModel>>() {
                    @Override
                    public void onChanged(ArrayList<ComentarioModel> comentarioModels) {
                        adapter.notifyDataSetChanged();
                        binding.lblComentarios.setText("Comentários("+ comentarioModels.size() + ")");
                    }
                });
        adapter = new ComentarioAdapter(getContext(), cadastroViewModel.getComentarios().getValue(), navController, getViewLifecycleOwner());
        rvComentarios.setAdapter(adapter);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment supportMapFragment=(SupportMapFragment)
                getChildFragmentManager().findFragmentById(R.id.map_fragment);

        supportMapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull GoogleMap googleMap) {
                cadastroViewModel.getEstabelecimento().observe(getViewLifecycleOwner(), new Observer<EstabelecimentoModel>() {
                    @Override
                    public void onChanged(EstabelecimentoModel estabelecimentoModel) {
                        if (estabelecimentoModel != null) {
                            // Add a marker in Sydney and move the camera
                            Double lat = Double.parseDouble(estabelecimentoModel.getLatitude().trim());
                            Double lng = Double.parseDouble(estabelecimentoModel.getLongitude().trim());

                            LatLng estabLatLng = new LatLng(lat, lng);
                            googleMap.addMarker(new MarkerOptions()
                                    .position(estabLatLng)
                                    .title(estabelecimentoModel.getDescricao()));
                            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(estabLatLng,15));
                        }
                    }
                });
            }
        });
        TextInputEditText txtComentario = binding.txtNovoComentario;
        txtComentario.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    // Perform action on key press
                    ComentarioRepository.getInstance().cadastrarComentario(new ComentarioModel(UUID.randomUUID().toString(),id, mAuth.getUid(),txtComentario.getText().toString()));
                    txtComentario.setText("");
                    return true;
                }
                return false;
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
    }
}

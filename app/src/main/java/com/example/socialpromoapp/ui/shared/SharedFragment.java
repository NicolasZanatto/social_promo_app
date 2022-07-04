package com.example.socialpromoapp.ui.shared;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.socialpromoapp.R;
import com.google.firebase.auth.FirebaseAuth;

public class SharedFragment extends Fragment {
    protected NavController navController;
    protected FirebaseAuth mAuth;

    public void init(FragmentActivity fragmentActivity){
        NavHostFragment navHostFragment = (NavHostFragment) fragmentActivity.getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment_content_feed);
        this.navController = navHostFragment.getNavController();
        this.mAuth = FirebaseAuth.getInstance();
    }

    public boolean acaoRealizadaPeloUsuarioLogado(String id){
        String idUsuario = mAuth.getUid();
        return idUsuario != null && idUsuario.equals(id);
    }

    public boolean usuarioEstaLogado(){
        String idUsuario = mAuth.getUid();
        return idUsuario != null;
    }
}

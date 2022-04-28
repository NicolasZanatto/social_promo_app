package com.example.socialpromoapp.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.socialpromoapp.MainActivity;
import com.example.socialpromoapp.R;
import com.example.socialpromoapp.databinding.FragmentLoginBinding;
import com.example.socialpromoapp.models.UsuarioModel;
import com.example.socialpromoapp.repositories.login.LoginRepository;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

public class LoginFragment extends Fragment {

    private FragmentLoginBinding binding;
    TextInputEditText etLoginEmail;
    TextInputEditText etLoginPassword;
    TextView tvRegisterHere;
    Button btnLogin;
    private NavController navController;


    FirebaseAuth mAuth;
    LoginRepository loginRepository;
    UsuarioModel usuarioModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        LoginViewModel loginViewModel =
                new ViewModelProvider(this).get(LoginViewModel.class);

        binding = FragmentLoginBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        etLoginEmail = binding.etLoginEmail;
        etLoginPassword = binding.etLoginPass;
        tvRegisterHere = binding.tvRegisterHere;
        btnLogin = binding.btnLogin;

        mAuth = FirebaseAuth.getInstance();
        navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment_content_feed);

        loginRepository = new LoginRepository();
        btnLogin.setOnClickListener(view -> {
            usuarioModel = new UsuarioModel(etLoginEmail.getText().toString(),etLoginPassword.getText().toString());
            loginUser();
        });
        tvRegisterHere.setOnClickListener(view ->{
            navController.navigate(R.id.nav_registrar);
        });

        return root;
    }

    private void loginUser(){
        if(!usuarioModel.validarLogin(etLoginEmail,etLoginPassword )) return;

        Runnable taskSuccesfully = new Runnable() {
            public void run() {
                Toast.makeText(getActivity(), "Usuário logado com sucesso", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getActivity(), MainActivity.class));
            }
        };

        Runnable taskFailed = new Runnable() {
            public void run() {
                Toast.makeText(getActivity(), "Login Inválido", Toast.LENGTH_SHORT).show();
            }
        };

        usuarioModel.realizarLogin(taskSuccesfully, taskFailed);
    }
}
package com.example.socialpromoapp.ui.cadastro;

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

import com.example.socialpromoapp.R;
import com.example.socialpromoapp.databinding.FragmentCadastroBinding;
import com.example.socialpromoapp.models.UsuarioModel;
import com.example.socialpromoapp.models.utils.MaskEditUtil;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CadastroFragment extends Fragment {

    private FragmentCadastroBinding binding;
    TextInputEditText etRegEmail;
    TextInputEditText etRegPassword;
    TextInputEditText etRegDataNascimento;
    TextInputEditText etRegCelular;
    TextInputEditText etRegNome;
    TextView tvLoginHere;
    Button btnRegister;
    FirebaseAuth mAuth;
    private NavController navController;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        CadastroViewModel cadastroViewModel =
                new ViewModelProvider(this).get(CadastroViewModel.class);

        binding = FragmentCadastroBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        btnRegister = binding.btnRegister;

        etRegEmail = binding.etRegEmail;
        etRegPassword = binding.etRegPass;
        etRegDataNascimento = binding.etRegDataNascimento;
        etRegCelular = binding.etRegCelular;
        etRegNome = binding.etRegNome;
        tvLoginHere = binding.tvLoginHere;
        btnRegister = binding.btnRegister;

        mAuth = FirebaseAuth.getInstance();
        navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment_content_feed);

        btnRegister.setOnClickListener(view ->{
            createUser();
        });

        tvLoginHere.setOnClickListener(view ->{
            navController.navigate(R.id.nav_login);
        });

        etRegDataNascimento.addTextChangedListener(MaskEditUtil.mask(etRegDataNascimento, MaskEditUtil.FORMAT_DATE));
        etRegCelular.addTextChangedListener(MaskEditUtil.mask(etRegCelular, MaskEditUtil.FORMAT_FONE));

        return root;
    }

    private void createUser() {

        UsuarioModel usuarioModel = new UsuarioModel();

        usuarioModel.setNome(etRegNome.getText().toString());
        usuarioModel.setSenha(etRegPassword.getText().toString());
        usuarioModel.setEmail(etRegEmail.getText().toString());
        usuarioModel.setCelular(etRegCelular.getText().toString());
        Date data;

        try {
            usuarioModel.setDataNascimento(new SimpleDateFormat("dd/MM/yyyy").parse(etRegDataNascimento.getText().toString()));
        }
        catch (ParseException e){
            etRegDataNascimento.setError("Data de nascimento inv??lida");
            etRegDataNascimento.requestFocus();
            return;
        }
        if(usuarioModel.validarCadastro(etRegNome,etRegPassword,etRegEmail,etRegCelular)){
            usuarioModel.cadastrarUsuario(funcSucesso,funcFalha);
        }
    }

    Runnable funcSucesso = new Runnable() {
        public void run() {
            Toast.makeText(getActivity(), "Usu??rio cadastrado com sucesso", Toast.LENGTH_SHORT).show();
            navController.navigate(R.id.nav_feed);
        }
    };

    Runnable funcFalha = new Runnable() {
        public void run() {
            Toast.makeText(getActivity(), "Falha ao cadastrar o usu??rio", Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
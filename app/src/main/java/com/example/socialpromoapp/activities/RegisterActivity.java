package com.example.socialpromoapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.socialpromoapp.MainActivity;
import com.example.socialpromoapp.R;
import com.example.socialpromoapp.models.UsuarioModel;
import com.example.socialpromoapp.models.utils.MaskEditUtil;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class RegisterActivity extends AppCompatActivity {

    TextInputEditText etRegEmail;
    TextInputEditText etRegPassword;
    TextInputEditText etRegDataNascimento;
    TextInputEditText etRegCelular;
    TextInputEditText etRegNome;
    TextView tvLoginHere;
    Button btnRegister;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etRegEmail = findViewById(R.id.etRegEmail);
        etRegPassword = findViewById(R.id.etRegPass);
        etRegDataNascimento = findViewById(R.id.etRegDataNascimento);
        etRegCelular = findViewById(R.id.etRegCelular);
        etRegNome = findViewById(R.id.etRegNome);
        tvLoginHere = findViewById(R.id.tvLoginHere);
        btnRegister = findViewById(R.id.btnRegister);

        mAuth = FirebaseAuth.getInstance();

        btnRegister.setOnClickListener(view ->{
            createUser();
        });

        tvLoginHere.setOnClickListener(view ->{
            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
        });

        etRegDataNascimento.addTextChangedListener(MaskEditUtil.mask(etRegDataNascimento, MaskEditUtil.FORMAT_DATE));
        etRegCelular.addTextChangedListener(MaskEditUtil.mask(etRegCelular, MaskEditUtil.FORMAT_FONE));
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
            etRegDataNascimento.setError("Data de nascimento inválida");
            etRegDataNascimento.requestFocus();
            return;
        }
        if(usuarioModel.validarCadastro(etRegNome,etRegPassword,etRegEmail,etRegCelular)){
            usuarioModel.cadastrarUsuario(funcSucesso,funcFalha);
        }
    }

    Runnable funcSucesso = new Runnable() {
        public void run() {
            Toast.makeText(RegisterActivity.this, "Usuário cadastrado com sucesso", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
        }
    };

    Runnable funcFalha = new Runnable() {
        public void run() {
            Toast.makeText(RegisterActivity.this, "Falha ao cadastrar o usuário", Toast.LENGTH_SHORT).show();
        }
    };
}
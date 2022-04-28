package com.example.socialpromoapp.ui.modais;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import androidx.annotation.NonNull;

import com.example.socialpromoapp.MainActivity;
import com.example.socialpromoapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SairModal extends Dialog implements
        View.OnClickListener {

    public Activity c;
    public Dialog d;
    public Button sair, cancelar;
    private FirebaseAuth mAuth;
    private FirebaseUser user;

    public SairModal(Activity a) {
        super(a);
        // TODO Auto-generated constructor stub
        this.c = a;
    }

    public SairModal(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_sair);
        sair = (Button) findViewById(R.id.btnSair);
        cancelar = (Button) findViewById(R.id.btnCancelar);
        sair.setOnClickListener(this);
        cancelar.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSair:
                mAuth.signOut();
                d.dismiss();
                break;
            case R.id.btnCancelar:
                break;
            default:
                break;
        }
        dismiss();
    }
}

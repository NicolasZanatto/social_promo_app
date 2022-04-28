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
import com.example.socialpromoapp.activities.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class DeslogarModal extends Dialog implements
        android.view.View.OnClickListener {

    public Activity c;
    public Dialog d;
    public Button sair, cancelar;
    private FirebaseAuth mAuth;
    private FirebaseUser user;

    public DeslogarModal(Activity a) {
        super(a);
        // TODO Auto-generated constructor stub
        this.c = a;
    }

    public DeslogarModal(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_deslogar);
        sair = (Button) findViewById(R.id.btnDeslogar);
        cancelar = (Button) findViewById(R.id.btnCancelar);
        sair.setOnClickListener(this);
        cancelar.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnDeslogar:
                mAuth.signOut();
                c.finish();
                Intent i = new Intent(c, MainActivity.class);
                c.startActivity(i);
                break;
            case R.id.btnCancelar:
                dismiss();
                break;
            default:
                break;
        }
        dismiss();
    }
}

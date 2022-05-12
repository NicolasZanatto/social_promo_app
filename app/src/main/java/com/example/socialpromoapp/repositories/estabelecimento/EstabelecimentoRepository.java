package com.example.socialpromoapp.repositories.estabelecimento;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.socialpromoapp.models.EstabelecimentoModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Logger;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class EstabelecimentoRepository {
    private DatabaseReference databaseReference;

    public EstabelecimentoRepository(){
        databaseReference = FirebaseDatabase.getInstance().getReference("estabelecimentos");
    }

    public void getAll(MutableLiveData<List<EstabelecimentoModel>> listaEstabelecimentos){
        // Attach a listener to read the data at our posts reference
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<EstabelecimentoModel> estabelecimentosBD = new ArrayList<>();
                for (DataSnapshot data : dataSnapshot.getChildren()){
                    Log.w(EstabelecimentoRepository.this.toString(), data.toString());
                    estabelecimentosBD.add(data.getValue(EstabelecimentoModel.class));
                }

                Collections.sort(estabelecimentosBD, new Comparator<EstabelecimentoModel>() {

                    public int compare(EstabelecimentoModel o1, EstabelecimentoModel o2) {
                        // compare two instance of `Score` and return `int` as result.
                        return o1.getDescricao().compareTo(o2.getDescricao());
                    }
                });

                listaEstabelecimentos.setValue(estabelecimentosBD);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }
}

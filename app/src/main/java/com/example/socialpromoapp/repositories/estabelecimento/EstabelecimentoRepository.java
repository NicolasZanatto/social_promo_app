package com.example.socialpromoapp.repositories.estabelecimento;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.socialpromoapp.models.EstabelecimentoModel;
import com.example.socialpromoapp.models.PostagemModel;
import com.example.socialpromoapp.repositories.postagem.PostagemRepository;
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
    static EstabelecimentoRepository instance;
    private DatabaseReference databaseReference;
    private MutableLiveData<EstabelecimentoModel> estabelecimentoId = new MutableLiveData<>();

    public EstabelecimentoRepository(){
        databaseReference = FirebaseDatabase.getInstance().getReference("estabelecimentos");
    }

    public static EstabelecimentoRepository getInstance(){

        if(instance == null){
            instance = new EstabelecimentoRepository();
        }
        return instance;
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

    public MutableLiveData<EstabelecimentoModel> getEstabelecimentoById(String id){
        databaseReference.child(id).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    estabelecimentoId.setValue(task.getResult().getValue(EstabelecimentoModel.class));
                }
            }
        });

        return estabelecimentoId;
    }

    public void getDescricao(Integer id, StringBuilder descricaoBuilder){
        // Attach a listener to read the data at our posts reference
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()){
                    EstabelecimentoModel estabelecimentoModel = data.getValue(EstabelecimentoModel.class);

                    if(estabelecimentoModel.getId() == id){
                        descricaoBuilder.append(estabelecimentoModel.getDescricao());
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }
}

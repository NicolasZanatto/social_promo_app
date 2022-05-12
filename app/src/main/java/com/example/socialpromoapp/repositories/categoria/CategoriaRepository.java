package com.example.socialpromoapp.repositories.categoria;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.socialpromoapp.models.CategoriaModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class CategoriaRepository {
    private DatabaseReference databaseReference;

    public CategoriaRepository(){
        databaseReference = FirebaseDatabase.getInstance().getReference("categorias");
    }

    public void getAll(MutableLiveData<List<CategoriaModel>> listaCategorias){
        // Attach a listener to read the data at our posts reference
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<CategoriaModel> categoriasDB = new ArrayList<>();
                for (DataSnapshot data : dataSnapshot.getChildren()){
                    Log.w(CategoriaRepository.this.toString(), data.toString());
                    categoriasDB.add(data.getValue(CategoriaModel.class));
                }

                Collections.sort(categoriasDB, new Comparator<CategoriaModel>() {

                    public int compare(CategoriaModel o1, CategoriaModel o2) {
                        // compare two instance of `Score` and return `int` as result.
                        return o1.getDescricao().compareTo(o2.getDescricao());
                    }
                });

                listaCategorias.setValue(categoriasDB);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }
}

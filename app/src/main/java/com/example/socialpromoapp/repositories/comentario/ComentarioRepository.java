package com.example.socialpromoapp.repositories.comentario;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.MutableLiveData;

import com.example.socialpromoapp.models.CategoriaModel;
import com.example.socialpromoapp.models.ComentarioModel;
import com.example.socialpromoapp.models.PostagemModel;
import com.example.socialpromoapp.repositories.postagem.PostagemRepository;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

public class ComentarioRepository {
    static ComentarioRepository instance;
    private ArrayList<ComentarioModel> comentariosModels = new ArrayList<>();
    private MutableLiveData<ArrayList<ComentarioModel>> comentarios = new MutableLiveData<>();

    public static ComentarioRepository getInstance(){
        if(instance == null){
            instance = new ComentarioRepository();
        }
        return instance;
    }

    //Read
    public MutableLiveData<ArrayList<ComentarioModel>> getComentarios(String id){
        loadComentarios(id);
        comentarios.setValue(comentariosModels);
        return comentarios;
    }

    private void loadComentarios(String id){
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("comentarios");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                comentariosModels.clear();
                for (DataSnapshot data : dataSnapshot.getChildren()){
                    Log.w(ComentarioRepository.this.toString(), data.toString());
                    ComentarioModel comentarioModel = data.getValue(ComentarioModel.class);

                    if(id.equals(comentarioModel.getIdPostagem()))
                        comentariosModels.add(comentarioModel);

                }

                comentariosModels.sort((d1,d2) -> {
                    try {
                        return d1.getDataDateFormat().compareTo(d2.getDataDateFormat());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    return 0;
                });


                comentarios.postValue(comentariosModels);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }

    public void cadastrarComentario(ComentarioModel comentarioModel){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        reference.child("comentarios").child(comentarioModel.getId()).setValue(comentarioModel);
    }
}

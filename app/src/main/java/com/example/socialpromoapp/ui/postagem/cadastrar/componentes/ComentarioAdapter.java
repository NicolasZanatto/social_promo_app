package com.example.socialpromoapp.ui.postagem.cadastrar.componentes;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.socialpromoapp.R;
import com.example.socialpromoapp.models.ComentarioModel;
import com.example.socialpromoapp.models.PostagemModel;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;

public class ComentarioAdapter extends RecyclerView.Adapter<ComentarioAdapter.ViewHolder> {
    private ArrayList<ComentarioModel> comentarios;
    private Context context;
    private NavController navController;

    public ComentarioAdapter(Context context, ArrayList<ComentarioModel> comentarios, NavController navController) {
        this.comentarios = comentarios;
        this.context = context;
        this.navController = navController;
        setHasStableIds(true);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_comentario_item,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.setIsRecyclable(false);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ComentarioModel comentarioModel = comentarios.get(position);
        holder.itemView.setTag(comentarioModel);
        holder.txtComentario.setText(comentarioModel.getDescricao());

    }

    private void SetOnClickImage(ImageView imageView,PostagemModel postagemModel){
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("id",postagemModel.getId());
                navController.navigate(R.id.nav_visualizar_post, bundle);
            }
        });
    }

    @Override
    public int getItemCount() {
        return comentarios.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private MaterialTextView txtComentario;

        public ViewHolder(@NonNull View viewItem){
            super(viewItem);
            txtComentario = viewItem.findViewById(R.id.txtComentario);
        }
    }
}

package com.example.socialpromoapp.ui.feed;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.socialpromoapp.R;
import com.example.socialpromoapp.models.PostagemModel;
import com.google.android.material.textview.MaterialTextView;

import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.ViewHolder> {
    private ArrayList<PostagemModel> postagens;
    private Context context;
    private NavController navController;

    public FeedAdapter(Context context, ArrayList<PostagemModel> postagens, NavController navController) {
        this.postagens = postagens;
        this.context = context;
        this.navController = navController;
        setHasStableIds(true);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_feed_item,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.setIsRecyclable(false);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PostagemModel postagemModel = postagens.get(position);
        holder.itemView.setTag(postagemModel);
        holder.txtTitulo.setText(postagemModel.getTitulo());
        holder.txtEstabelecimento.setText(postagemModel.getEstabelecimentoDesc());
        holder.txtCategoria.setText(postagemModel.getCategoriaDesc());
        String preco = postagemModel.getPreco().toString().replace('.',',');
        holder.txtPreco.setText("R$ " + preco);

        Glide.with(this.context).load(postagemModel.getCaminhoImagemUrl()).into(holder.imagem);
        SetOnClickImage(holder.imagem, postagemModel);
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
        return postagens.size();
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

        private MaterialTextView txtTitulo;
        private MaterialTextView txtPreco;
        private MaterialTextView txtEstabelecimento;
        private MaterialTextView txtCategoria;
        private ImageView imagem;
        public ViewHolder(@NonNull View viewItem){
            super(viewItem);
            txtTitulo = viewItem.findViewById(R.id.txtTitulo);
            txtPreco = viewItem.findViewById(R.id.txtPreco);
            txtEstabelecimento = viewItem.findViewById(R.id.txtEstabelecimento);
            txtCategoria = viewItem.findViewById(R.id.txtCategoria);
            imagem = viewItem.findViewById(R.id.imgPostagem);
        }
    }
}

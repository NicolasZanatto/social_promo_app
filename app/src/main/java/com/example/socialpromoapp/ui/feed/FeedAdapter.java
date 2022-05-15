package com.example.socialpromoapp.ui.feed;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.socialpromoapp.R;
import com.example.socialpromoapp.models.PostagemModel;
import com.google.android.material.textview.MaterialTextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.ViewHolder> {
    private ArrayList<PostagemModel> postagens;

    public FeedAdapter(ArrayList<PostagemModel> postagens) {
        this.postagens = postagens;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_feed_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.itemView.setTag(postagens.get(position));
        holder.txtTitulo.setText(postagens.get(position).getTitulo());
    }

    @Override
    public int getItemCount() {
        return postagens.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        MaterialTextView txtTitulo;
        TextView preco;
        TextView estabelecimento;

        public ViewHolder(@NonNull View viewItem){
            super(viewItem);
            txtTitulo = viewItem.findViewById(R.id.txtTitulo);
        }
    }

}

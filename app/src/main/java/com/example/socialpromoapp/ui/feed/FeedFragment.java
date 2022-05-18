package com.example.socialpromoapp.ui.feed;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.socialpromoapp.databinding.FragmentFeedBinding;
import com.example.socialpromoapp.models.PostagemModel;

import java.util.ArrayList;

public class FeedFragment extends Fragment {

    private FragmentFeedBinding binding;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private FeedViewModel feedViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentFeedBinding.inflate(inflater, container, false);
        recyclerView = binding.rvFeed;
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        feedViewModel =
                new ViewModelProvider(this).get(FeedViewModel.class);

        feedViewModel.init();
        feedViewModel.getPostagens().observe(getViewLifecycleOwner(), new Observer<ArrayList<PostagemModel>>() {
            @Override
            public void onChanged(ArrayList<PostagemModel> postagemModels) {
                adapter.notifyDataSetChanged();
            }
        });
        adapter = new FeedAdapter(getContext(), feedViewModel.getPostagens().getValue());
        recyclerView.setAdapter(adapter);


        View root = binding.getRoot();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
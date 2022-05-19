package com.example.socialpromoapp.ui.filtrar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.socialpromoapp.databinding.FragmentDeslogarBinding;
import com.example.socialpromoapp.databinding.FragmentFiltroBinding;
import com.example.socialpromoapp.ui.modais.DeslogarModal;

public class FiltrarFragment extends Fragment {

    private FragmentFiltroBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        FiltrarViewModel deslogarViewModel =
                new ViewModelProvider(this).get(FiltrarViewModel.class);

        binding = FragmentFiltroBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
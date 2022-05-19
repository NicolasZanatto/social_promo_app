package com.example.socialpromoapp.ui.sobre;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.socialpromoapp.databinding.FragmentDeslogarBinding;
import com.example.socialpromoapp.databinding.FragmentSobreBinding;
import com.example.socialpromoapp.ui.modais.DeslogarModal;

public class SobreFragment extends Fragment {

    private FragmentSobreBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        SobreViewModel sobreViewModel =
                new ViewModelProvider(this).get(SobreViewModel.class);

        binding = FragmentSobreBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
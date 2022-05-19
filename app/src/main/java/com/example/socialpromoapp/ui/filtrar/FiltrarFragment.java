package com.example.socialpromoapp.ui.deslogar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.socialpromoapp.databinding.FragmentDeslogarBinding;
import com.example.socialpromoapp.ui.modais.DeslogarModal;

public class DeslogarFragment extends Fragment {

    private FragmentDeslogarBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DeslogarViewModel deslogarViewModel =
                new ViewModelProvider(this).get(DeslogarViewModel.class);

        binding = FragmentDeslogarBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        DeslogarModal deslogarModal = new DeslogarModal(getActivity());
        deslogarModal.show();
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
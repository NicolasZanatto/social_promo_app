package com.example.socialpromoapp.ui.postagem;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.socialpromoapp.R;
import com.example.socialpromoapp.databinding.FragmentPostagemBinding;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

public class PostagemFragment extends Fragment {

    private FragmentPostagemBinding binding;
    TextInputEditText etRegEmail;
    TextInputEditText etRegPassword;
    TextInputEditText etRegDataNascimento;
    TextInputEditText etRegCelular;
    TextInputEditText etRegNome;
    TextView tvLoginHere;
    Button btnRegister;
    FirebaseAuth mAuth;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        PostagemViewModel cadastroViewModel =
                new ViewModelProvider(this).get(PostagemViewModel.class);

        binding = FragmentPostagemBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        loadComboEstabelecimentos();

        return root;
    }

    private void loadComboEstabelecimentos(){
        String[] type = new String[] {"Bed-sitter", "Single", "1- Bedroom", "2- Bedroom","3- Bedroom"};

        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(
                        getActivity(),
                        R.layout.dropdown_menu_popup_item,
                        type);

        AutoCompleteTextView editTextFilledExposedDropdown =
                binding.estabelecimentos;
        editTextFilledExposedDropdown.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
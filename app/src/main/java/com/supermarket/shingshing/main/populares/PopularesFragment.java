package com.supermarket.shingshing.main.populares;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.supermarket.shingshing.R;
import com.supermarket.shingshing.databinding.FragmentPopularesBinding;
import com.supermarket.shingshing.models.ProductoModel;

import java.util.ArrayList;

public class PopularesFragment extends Fragment {
    public static final String KEY_PRODUCTOS = "productos";
    private FragmentPopularesBinding binding;
    private ArrayList<ProductoModel> productoModels;

    public PopularesFragment() {}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            productoModels = getArguments().getParcelableArrayList(KEY_PRODUCTOS);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_populares, container, false);

        iniciarVistas();

        return binding.getRoot();
    }

    private void iniciarVistas() {
        binding.rvPopulares.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        binding.rvPopulares.setAdapter(new PopularesAdapter(productoModels));
    }
}

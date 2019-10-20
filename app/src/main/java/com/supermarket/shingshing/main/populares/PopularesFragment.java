package com.supermarket.shingshing.main.populares;

import android.os.Bundle;

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
    private FragmentPopularesBinding binding;
    private ArrayList<ProductoModel> productoModels;

    public static PopularesFragment newInstance(ArrayList<ProductoModel> productoModels) {
        PopularesFragment fragment = new PopularesFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("productos", productoModels);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            productoModels = getArguments().getParcelableArrayList("productos");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_populares, container, false);

        iniciarVistas();

        return binding.getRoot();
    }

    private void iniciarVistas() {
        binding.rvPopulares.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        binding.rvPopulares.setAdapter(new PopularesAdapter(productoModels));
    }
}

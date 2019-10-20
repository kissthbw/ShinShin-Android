package com.supermarket.shingshing.main.producto;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.supermarket.shingshing.R;
import com.supermarket.shingshing.databinding.FragmentProductoBinding;
import com.supermarket.shingshing.main.principal.TiendaAdapter;
import com.supermarket.shingshing.models.ProductoModel;

import java.util.Locale;

public class ProductoFragment extends Fragment {
    private FragmentProductoBinding binding;
    private ProductoModel producto;

    public static ProductoFragment newInstance(ProductoModel productoModel) {
        ProductoFragment fragment = new ProductoFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("producto", productoModel);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            producto = getArguments().getParcelable("producto");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_producto, container, false);

        iniciarVista();

        return binding.getRoot();
    }

    private void iniciarVista() {
        if (producto != null) {
            binding.tvProductoNombre.setText(producto.getNombreProducto());
            binding.tvProductoCantidad.setText(producto.getContenido());
            binding.tvPopularesPrecio.setText(String.format(Locale.US, "$%d", producto.getCantidadBonificacion()));
            binding.tvProductoDescripcion.setText(producto.getDescripcion());

            binding.rvProductoTiendas.setLayoutManager(new GridLayoutManager(getActivity(), 5));
            TiendaAdapter tiendaAdapter = new TiendaAdapter();
            binding.rvProductoTiendas.setAdapter(tiendaAdapter);
        }
    }
}

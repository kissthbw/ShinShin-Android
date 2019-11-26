package com.supermarket.shingshing.main.producto;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
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
    public static final String KEY_PRODUCTO = "producto";
    private FragmentProductoBinding binding;
    private ProductoListener listener;
    private ProductoModel producto;

    public ProductoFragment() {}

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        listener = (ProductoListener) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            producto = getArguments().getParcelable(KEY_PRODUCTO);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
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

            if (producto.getColorBanner() != null && !producto.getColorBanner().trim().isEmpty()) {
                String[] colores = producto.getColorBanner().split(",");
                Drawable background = binding.vProductoHeader.getBackground();
                if (background instanceof ShapeDrawable) {
                    ((ShapeDrawable)background).getPaint().setColor(Color.argb(255, Integer.parseInt(colores[0].trim()), Integer.parseInt(colores[1].trim()), Integer.parseInt(colores[2].trim())));
                } else if (background instanceof GradientDrawable) {
                    ((GradientDrawable)background).setColor(Color.argb(255, Integer.parseInt(colores[0].trim()), Integer.parseInt(colores[1].trim()), Integer.parseInt(colores[2].trim())));
                } else if (background instanceof ColorDrawable) {
                    ((ColorDrawable)background).setColor(Color.argb(255, Integer.parseInt(colores[0].trim()), Integer.parseInt(colores[1].trim()), Integer.parseInt(colores[2].trim())));
                }
                listener.actualizarColorHeader(colores);
            }

            binding.rvProductoTiendas.setLayoutManager(new GridLayoutManager(getActivity(), 5));
            TiendaAdapter tiendaAdapter = new TiendaAdapter();
            binding.rvProductoTiendas.setAdapter(tiendaAdapter);
        }
    }

    @Override
    public void onDestroyView() {
        listener.actualizarColorHeader(null);
        super.onDestroyView();
    }
}

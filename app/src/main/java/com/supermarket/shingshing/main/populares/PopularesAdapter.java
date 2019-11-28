package com.supermarket.shingshing.main.populares;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.supermarket.shingshing.R;
import com.supermarket.shingshing.databinding.ItemPopularesBinding;
import com.supermarket.shingshing.main.MainListener;
import com.supermarket.shingshing.models.ProductoModel;

import java.util.ArrayList;
import java.util.Locale;

public class PopularesAdapter extends RecyclerView.Adapter<PopularesAdapter.PopularesViewHolder> {
    private ArrayList<ProductoModel> productoModels;
    private MainListener listener;

    PopularesAdapter(MainListener listener, ArrayList<ProductoModel> productoModels) {
        this.listener = listener;
        this.productoModels = productoModels;
    }

    @NonNull
    @Override
    public PopularesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemPopularesBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_populares, parent, false);
        return new PopularesViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull PopularesViewHolder holder, int position) {
        holder.setDatos(productoModels.get(position));
    }

    @Override
    public int getItemCount() {
        return productoModels.size();
    }

    class PopularesViewHolder extends RecyclerView.ViewHolder {
        private ItemPopularesBinding binding;

        PopularesViewHolder(ItemPopularesBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void setDatos(ProductoModel producto) {
            binding.tvPopularesNombre.setText(producto.getNombreProducto());
            binding.tvPopularesCantidad.setText(producto.getContenido());
            binding.tvPopularesPrecio.setText(String.format(Locale.US, "$%d", producto.getCantidadBonificacion()));
            binding.clPopularesContainer.setOnClickListener(v -> listener.onClickPopularProducto(producto));
        }
    }
}

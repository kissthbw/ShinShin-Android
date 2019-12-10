package com.supermarket.shingshing.main.principal;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.supermarket.shingshing.R;
import com.supermarket.shingshing.databinding.ItemMainPopularBinding;
import com.supermarket.shingshing.main.principal.listener.PopularListener;
import com.supermarket.shingshing.models.ProductoModel;

import java.util.ArrayList;
import java.util.Locale;

public class PopularAdapter extends RecyclerView.Adapter<PopularAdapter.PopularViewHolder> {
    private PopularListener listener;
    private ArrayList<ProductoModel> productoModels;

    public PopularAdapter(PopularListener listener) {
        this.listener = listener;
        this.productoModels = new ArrayList<>();
    }

    @NonNull
    @Override
    public PopularViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemMainPopularBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_main_popular, parent, false);
        return new PopularViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull PopularViewHolder holder, int position) {
        holder.setDatos(productoModels.get(position));
    }

    @Override
    public int getItemCount() {
        return productoModels.size();
    }

    public void setProductoModels(ArrayList<ProductoModel> productoModels) {
        this.productoModels = productoModels;
    }

    class PopularViewHolder extends RecyclerView.ViewHolder {
        private ItemMainPopularBinding binding;

        PopularViewHolder(ItemMainPopularBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void setDatos(ProductoModel producto) {
            if (producto.getImgUrl() != null && !producto.getImgUrl().trim().isEmpty()) {
                Picasso.get().load(producto.getImgUrl()).into(binding.ivItemPopularImagen);
            } else {
                binding.ivItemPopularImagen.setImageDrawable(ContextCompat.getDrawable(binding.ivItemPopularImagen.getContext(), R.drawable.img_bonafont));
            }
            binding.tvItemPopularNombre.setText(producto.getNombreProducto());
            binding.tvItemPopularCantidad.setText(producto.getContenido());
            binding.tvItemPopularPrecio.setText(String.format(Locale.US, "$%d", producto.getCantidadBonificacion()));
            binding.clMainContainer.setOnClickListener(v -> listener.clickPopularProducto(producto));
        }
    }
}

package com.supermarket.shingshing.main.principal;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.supermarket.shingshing.R;
import com.supermarket.shingshing.databinding.ItemMainOfertaBinding;
import com.supermarket.shingshing.main.principal.listener.PopularListener;
import com.supermarket.shingshing.models.ProductoModel;

import java.util.ArrayList;
import java.util.Locale;

public class OfertaAdapter extends RecyclerView.Adapter<OfertaAdapter.OfertaViewHolder> {
    private PopularListener listener;
    private ArrayList<ProductoModel> productoModels;

    public OfertaAdapter(PopularListener listener) {
        this.listener = listener;
        productoModels = new ArrayList<>();
    }

    @NonNull
    @Override
    public OfertaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemMainOfertaBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_main_oferta, parent, false);
        return new OfertaViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull OfertaViewHolder holder, int position) {
        holder.setDatos(productoModels.get(position));
    }

    @Override
    public int getItemCount() {
        return productoModels.size();
    }

    public void setProductoModels(ArrayList<ProductoModel> productoModels) {
        this.productoModels = productoModels;
    }

    class OfertaViewHolder extends RecyclerView.ViewHolder {
        private ItemMainOfertaBinding binding;

        OfertaViewHolder(ItemMainOfertaBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void setDatos(ProductoModel producto) {
            if (producto.getImgUrl() != null && !producto.getImgUrl().trim().isEmpty()) {
                Picasso.get().load(producto.getImgUrl()).into(binding.ivItemOfertaImagen);
            } else {
                binding.ivItemOfertaImagen.setImageDrawable(ContextCompat.getDrawable(binding.ivItemOfertaImagen.getContext(), R.drawable.img_bonafont));
            }
            binding.tvItemOfertaNombre.setText(producto.getNombreProducto());
            binding.tvItemOfertaCantidad.setText(producto.getContenido());
            binding.tvItemOfertaPrecio.setText(String.format(Locale.US, "$%d", producto.getCantidadBonificacion()));
            binding.clMainContainer.setOnClickListener(v -> listener.clickPopularProducto(producto));
            if (producto.getColorBanner() != null && !producto.getColorBanner().trim().isEmpty()) {
                String[] colores = producto.getColorBanner().split(",");
                binding.ivItemOfertaImagen.setBackgroundColor(Color.argb(255, Integer.parseInt(colores[0].trim()), Integer.parseInt(colores[1].trim()), Integer.parseInt(colores[2].trim())));
                binding.clItemOfertaDescripcion.setBackgroundColor(Color.argb(220, Integer.parseInt(colores[0].trim()), Integer.parseInt(colores[1].trim()), Integer.parseInt(colores[2].trim())));
            }
        }
    }
}

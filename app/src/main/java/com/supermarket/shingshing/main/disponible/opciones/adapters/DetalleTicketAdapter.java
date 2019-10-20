package com.supermarket.shingshing.main.disponible.opciones.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.supermarket.shingshing.R;
import com.supermarket.shingshing.databinding.ItemDetalleTicketBinding;
import com.supermarket.shingshing.models.ProductoModel;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

public class DetalleTicketAdapter extends RecyclerView.Adapter<DetalleTicketAdapter.DetalleTicketViewHolder> {
    private ArrayList<ProductoModel> productos;

    public DetalleTicketAdapter(ArrayList<ProductoModel> productos) {
        this.productos = productos;
    }

    @NonNull
    @Override
    public DetalleTicketViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemDetalleTicketBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_detalle_ticket, parent, false);
        return new DetalleTicketViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull DetalleTicketViewHolder holder, int position) {
        holder.setDatos(productos.get(position));
    }

    @Override
    public int getItemCount() {
        return productos.size();
    }

    class DetalleTicketViewHolder extends RecyclerView.ViewHolder {
        private ItemDetalleTicketBinding binding;
        private NumberFormat formatter;

        DetalleTicketViewHolder(ItemDetalleTicketBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            formatter = new DecimalFormat("#,###");
        }

        void setDatos(ProductoModel producto) {
            binding.tvItemDetalleTicketNombre.setText(producto.getNombreProducto());
            binding.tvItemDetalleTicketCantidad.setText(producto.getContenido());
            String money = "$ " + formatter.format(producto.getCantidadBonificacion());
            binding.tvItemTicketChildPrecio.setText(money);
        }
    }
}

package com.supermarket.shingshing.resultado;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.supermarket.shingshing.R;
import com.supermarket.shingshing.databinding.ItemResultadoProductoBinding;
import com.supermarket.shingshing.models.ProductoModel;

import java.util.ArrayList;
import java.util.Locale;

public class ResultadoAdapter extends RecyclerView.Adapter<ResultadoAdapter.ResultadoViewHolder> {
    private ArrayList<ProductoModel> productos;

    ResultadoAdapter(ArrayList<ProductoModel> productos) {
        this.productos = productos;
    }

    @NonNull
    @Override
    public ResultadoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemResultadoProductoBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_resultado_producto, parent, false);
        return new ResultadoViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ResultadoViewHolder holder, int position) {
        holder.setDatos(productos.get(position));
    }

    @Override
    public int getItemCount() {
        return productos.size();
    }

    class ResultadoViewHolder extends RecyclerView.ViewHolder {
        private ItemResultadoProductoBinding binding;

        ResultadoViewHolder(ItemResultadoProductoBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void setDatos(ProductoModel producto) {
            binding.tvItemResultadoNombre.setText(producto.getNombreProducto());
            binding.tvItemResultadoCantidad.setText(producto.getContenido());
            binding.tvResultadoPrecio.setText(String.format(Locale.US, "$%d", producto.getCantidadBonificacion()));
            binding.tvItemResultadoID.setText(producto.getCodigoBarras());
        }
    }
}

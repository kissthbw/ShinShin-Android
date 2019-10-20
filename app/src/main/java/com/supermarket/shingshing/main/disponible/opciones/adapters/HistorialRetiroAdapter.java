package com.supermarket.shingshing.main.disponible.opciones.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.supermarket.shingshing.R;
import com.supermarket.shingshing.databinding.ItemHistorialRetiroBinding;
import com.supermarket.shingshing.models.HistorialBonificacionModel;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

public class HistorialRetiroAdapter extends RecyclerView.Adapter<HistorialRetiroAdapter.HistorialViewHolder> {
    private ArrayList<HistorialBonificacionModel> lista;

    public HistorialRetiroAdapter(ArrayList<HistorialBonificacionModel> lista) {
        this.lista = lista;
    }

    @NonNull
    @Override
    public HistorialViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemHistorialRetiroBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_historial_retiro, parent, false);
        return new HistorialViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull HistorialViewHolder holder, int position) {
        holder.setDatos(lista.get(position));
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    class HistorialViewHolder extends RecyclerView.ViewHolder {
        private ItemHistorialRetiroBinding binding;
        private NumberFormat formatter;

        HistorialViewHolder(ItemHistorialRetiroBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            formatter = new DecimalFormat("#,###");
        }

        void setDatos(HistorialBonificacionModel item) {
            binding.tvItemHistorialRetiroTipo.setText(item.getMediosBonificacion().getAliasMedioBonificacion());
            binding.tvItemHistorialRetiroFecha.setText(item.getFechaBonificacion());
            String money = "$ " + formatter.format(item.getCantidadBonificacion());
            binding.tvItemHistorialRetiroCantidad.setText(money);
        }
    }
}

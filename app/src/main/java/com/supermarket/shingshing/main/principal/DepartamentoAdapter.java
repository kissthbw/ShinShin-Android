package com.supermarket.shingshing.main.principal;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.supermarket.shingshing.R;
import com.supermarket.shingshing.databinding.ItemMainDepartamentoBinding;
import com.supermarket.shingshing.models.TipoProductoModel;

import java.util.ArrayList;

public class DepartamentoAdapter extends RecyclerView.Adapter<DepartamentoAdapter.DepartamentoViewHolder> {
    private ArrayList<TipoProductoModel> tipoProductoModels;

    public DepartamentoAdapter() {
        tipoProductoModels = new ArrayList<>();
    }

    @NonNull
    @Override
    public DepartamentoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemMainDepartamentoBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_main_departamento, parent, false);
        return new DepartamentoViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull DepartamentoViewHolder holder, int position) {
        holder.setDatos(position);
        //holder.setDatos(tipoProductoModels.get(position));
    }

    @Override
    public int getItemCount() {
        return 6;
    }

    public void setTipoProductoModels(ArrayList<TipoProductoModel> tipoProductoModels) {
        this.tipoProductoModels = tipoProductoModels;
    }

    class DepartamentoViewHolder extends RecyclerView.ViewHolder {
        private ItemMainDepartamentoBinding binding;

        DepartamentoViewHolder(ItemMainDepartamentoBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void setDatos(TipoProductoModel tipoProducto) {
            binding.tvItemDeptoNombre.setText(tipoProducto.getNombreTipoProducto());
            binding.ivItemDeptoImagen.setImageDrawable(ContextCompat.getDrawable(binding.ivItemDeptoImagen.getContext(), R.drawable.ic_belleza));
        }

        void setDatos(int position) {
            switch (position) {
                case 0:
                    binding.tvItemDeptoNombre.setText("Abarrotes");
                    binding.ivItemDeptoImagen.setImageDrawable(ContextCompat.getDrawable(binding.ivItemDeptoImagen.getContext(), R.drawable.ic_abarrotes));
                    break;
                case 1:
                    binding.tvItemDeptoNombre.setText("Autos");
                    binding.ivItemDeptoImagen.setImageDrawable(ContextCompat.getDrawable(binding.ivItemDeptoImagen.getContext(), R.drawable.ic_auto));
                    break;
                case 2:
                    binding.tvItemDeptoNombre.setText("Bebé");
                    binding.ivItemDeptoImagen.setImageDrawable(ContextCompat.getDrawable(binding.ivItemDeptoImagen.getContext(), R.drawable.ic_baby));
                    break;
                case 3:
                    binding.tvItemDeptoNombre.setText("Belleza");
                    binding.ivItemDeptoImagen.setImageDrawable(ContextCompat.getDrawable(binding.ivItemDeptoImagen.getContext(), R.drawable.ic_belleza));
                    break;
                case 4:
                    binding.tvItemDeptoNombre.setText("Bebidas");
                    binding.ivItemDeptoImagen.setImageDrawable(ContextCompat.getDrawable(binding.ivItemDeptoImagen.getContext(), R.drawable.ic_bebidas));
                    break;
                case 5:
                    binding.tvItemDeptoNombre.setText("Deportes");
                    binding.ivItemDeptoImagen.setImageDrawable(ContextCompat.getDrawable(binding.ivItemDeptoImagen.getContext(), R.drawable.ic_deportes));
                    break;
            }
        }
    }
}

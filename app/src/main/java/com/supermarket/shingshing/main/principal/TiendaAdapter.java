package com.supermarket.shingshing.main.principal;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.supermarket.shingshing.R;
import com.supermarket.shingshing.databinding.ItemMainTiendaBinding;
import com.supermarket.shingshing.models.TiendaModel;

import java.util.ArrayList;

public class TiendaAdapter extends RecyclerView.Adapter<TiendaAdapter.TiendaViewHoder> {
    private ArrayList<TiendaModel> tiendaModels;

    public TiendaAdapter() {
        tiendaModels = new ArrayList<>();
    }

    @NonNull
    @Override
    public TiendaViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemMainTiendaBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_main_tienda, parent, false);
        return new TiendaViewHoder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull TiendaViewHoder holder, int position) {
        holder.setDatos(position);
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public void setTiendaModels(ArrayList<TiendaModel> tiendaModels) {
        this.tiendaModels = tiendaModels;
    }

    class TiendaViewHoder extends RecyclerView.ViewHolder {
        private ItemMainTiendaBinding binding;

        TiendaViewHoder(ItemMainTiendaBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void setDatos(TiendaModel tienda) {
        }

        void setDatos(int position) {
            switch (position) {
                case 0:
                    binding.ivItemTiendaImagen.setImageDrawable(ContextCompat.getDrawable(binding.ivItemTiendaImagen.getContext(), R.drawable.ic_oxxo));
                    break;
                case 1:
                    binding.ivItemTiendaImagen.setImageDrawable(ContextCompat.getDrawable(binding.ivItemTiendaImagen.getContext(), R.drawable.ic_7eleven));
                    break;
                case 2:
                    binding.ivItemTiendaImagen.setImageDrawable(ContextCompat.getDrawable(binding.ivItemTiendaImagen.getContext(), R.drawable.ic_aurera));
                    break;
                case 3:
                    binding.ivItemTiendaImagen.setImageDrawable(ContextCompat.getDrawable(binding.ivItemTiendaImagen.getContext(), R.drawable.ic_waltmart));
                    break;
                case 4:
                    binding.ivItemTiendaImagen.setImageDrawable(ContextCompat.getDrawable(binding.ivItemTiendaImagen.getContext(), R.drawable.ic_superama));
                    break;
                case 5:
                    binding.ivItemTiendaImagen.setImageDrawable(ContextCompat.getDrawable(binding.ivItemTiendaImagen.getContext(), R.drawable.ic_comercial));
                    break;
                case 6:
                    binding.ivItemTiendaImagen.setImageDrawable(ContextCompat.getDrawable(binding.ivItemTiendaImagen.getContext(), R.drawable.ic_soriana));
                    break;
                case 7:
                    binding.ivItemTiendaImagen.setImageDrawable(ContextCompat.getDrawable(binding.ivItemTiendaImagen.getContext(), R.drawable.ic_chedraui));
                    break;
                case 8:
                    binding.ivItemTiendaImagen.setImageDrawable(ContextCompat.getDrawable(binding.ivItemTiendaImagen.getContext(), R.drawable.ic_sams));
                    break;
                case 9:
                    binding.ivItemTiendaImagen.setImageDrawable(ContextCompat.getDrawable(binding.ivItemTiendaImagen.getContext(), R.drawable.ic_costco));
                    break;
            }
        }
    }
}

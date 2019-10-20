package com.supermarket.shingshing.main.menu.opciones.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.supermarket.shingshing.R;
import com.supermarket.shingshing.databinding.ItemCuentaBancariaBinding;
import com.supermarket.shingshing.main.menu.opciones.agregar.NuevoListener;
import com.supermarket.shingshing.models.MedioBonificacionModel;

import java.util.ArrayList;

import static com.supermarket.shingshing.main.menu.opciones.CuentasFragment.NUEVO_BANCARIA;

public class BancariaAdapter extends RecyclerView.Adapter<BancariaAdapter.BancariaViewHolder> {
    private ArrayList<MedioBonificacionModel> lista;
    private NuevoListener listener;

    public BancariaAdapter(NuevoListener listener) {
        this.listener = listener;
        lista = new ArrayList<>();
    }

    @NonNull
    @Override
    public BancariaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCuentaBancariaBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_cuenta_bancaria, parent, false);
        return new BancariaViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull BancariaViewHolder holder, int position) {
        holder.setDatos(lista.get(position));
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public void setDatos(ArrayList<MedioBonificacionModel> lista) {
        this.lista = lista;
        notifyDataSetChanged();
    }

    class BancariaViewHolder extends RecyclerView.ViewHolder {
        private ItemCuentaBancariaBinding binding;

        BancariaViewHolder(ItemCuentaBancariaBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void setDatos(MedioBonificacionModel item) {
            binding.tvItemCuentaBancariaAlias.setText(item.getAliasMedioBonificacion());
            if (item.getCuentaMedioBonificacion() != null && !item.getCuentaMedioBonificacion().trim().isEmpty() && item.getCuentaMedioBonificacion().length() >= 5) {
                String cuenta = String.format("....%s", item.getCuentaMedioBonificacion().substring(item.getCuentaMedioBonificacion().length() - 4));
                binding.tvItemCuentaBancariaNumero.setText(cuenta);
            } else {
                binding.tvItemCuentaBancariaNumero.setText("");
            }
            binding.tvItemCuentaBancariaTipo.setText(item.getCompaniaMedioBonificacion());
            binding.clItemCuentaBancariaContainer.setOnClickListener(v -> listener.onClickItemMedio(NUEVO_BANCARIA, item));
        }
    }
}

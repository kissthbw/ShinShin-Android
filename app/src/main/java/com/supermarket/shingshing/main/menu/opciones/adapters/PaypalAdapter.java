package com.supermarket.shingshing.main.menu.opciones.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.supermarket.shingshing.R;
import com.supermarket.shingshing.databinding.ItemCuentaPaypalBinding;
import com.supermarket.shingshing.main.menu.opciones.agregar.NuevoListener;
import com.supermarket.shingshing.models.MedioBonificacionModel;

import java.util.ArrayList;

import static com.supermarket.shingshing.main.menu.opciones.CuentasFragment.NUEVO_CUENTA;

public class PaypalAdapter extends RecyclerView.Adapter<PaypalAdapter.PaypalViewHolder> {
    private ArrayList<MedioBonificacionModel> lista;
    private NuevoListener listener;

    public PaypalAdapter(NuevoListener listener) {
        this.listener = listener;
        lista = new ArrayList<>();
    }

    @NonNull
    @Override
    public PaypalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCuentaPaypalBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_cuenta_paypal, parent, false);
        return new PaypalViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull PaypalViewHolder holder, int position) {
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

    class PaypalViewHolder extends RecyclerView.ViewHolder {
        private ItemCuentaPaypalBinding binding;

        PaypalViewHolder(ItemCuentaPaypalBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void setDatos(MedioBonificacionModel item) {
            binding.tvItemCuentaPaypalAlias.setText(item.getAliasMedioBonificacion());
            String cuenta = String.format("....%s", item.getIdCuentaMedioBonificacion().substring(item.getIdCuentaMedioBonificacion().length() - 4));
            binding.tvItemCuentaPaypalNumero.setText(cuenta);
            binding.clItemCuentaPaypalContainer.setOnClickListener(v -> listener.onClickItemMedio(NUEVO_CUENTA, item));
        }
    }
}

package com.supermarket.shingshing.main.menu.opciones.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.supermarket.shingshing.R;
import com.supermarket.shingshing.databinding.ItemCuentaRecargaBinding;
import com.supermarket.shingshing.main.menu.opciones.agregar.NuevoListener;
import com.supermarket.shingshing.models.MedioBonificacionModel;

import java.util.ArrayList;

import static com.supermarket.shingshing.main.menu.opciones.CuentasFragment.NUEVO_NUMERO;

public class RecargaAdapter extends RecyclerView.Adapter<RecargaAdapter.RecargaViewHolder> {
    private ArrayList<MedioBonificacionModel> lista;
    private NuevoListener listener;

    public RecargaAdapter(NuevoListener listener) {
        this.listener = listener;
        lista = new ArrayList<>();
    }

    @NonNull
    @Override
    public RecargaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCuentaRecargaBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_cuenta_recarga, parent, false);
        return new RecargaViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecargaViewHolder holder, int position) {
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

    class RecargaViewHolder extends RecyclerView.ViewHolder {
        private ItemCuentaRecargaBinding binding;

        RecargaViewHolder(ItemCuentaRecargaBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void setDatos(MedioBonificacionModel item) {
            binding.tvItemCuentaRecargaAlias.setText(item.getAliasMedioBonificacion());
            String cuenta = String.format("....%s", item.getCuentaMedioBonificacion().substring(item.getCuentaMedioBonificacion().length() - 4));
            binding.tvItemCuentaRecargaNumero.setText(cuenta);
            binding.tvItemCuentaRecargaTipo.setText(item.getCompaniaMedioBonificacion());
            binding.clItemCuentaRecargaContainer.setOnClickListener(v -> listener.onClickItemMedio(NUEVO_NUMERO, item));
        }
    }
}

package com.supermarket.shingshing.main.disponible.opciones.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.supermarket.shingshing.R;
import com.supermarket.shingshing.databinding.ItemHistorialTicketBinding;
import com.supermarket.shingshing.main.disponible.opciones.HistorialListener;
import com.supermarket.shingshing.models.TicketModel;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

public class HistorialTicketAdapter extends RecyclerView.Adapter<HistorialTicketAdapter.HistorialTicketViewHolder> {
    private HistorialListener listener;
    private ArrayList<TicketModel> tickets;

    public HistorialTicketAdapter(HistorialListener listener, ArrayList<TicketModel> tickets) {
        this.listener = listener;
        this.tickets = tickets;
    }

    @NonNull
    @Override
    public HistorialTicketViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemHistorialTicketBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_historial_ticket, parent, false);
        return new HistorialTicketViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull HistorialTicketViewHolder holder, int position) {
        holder.setDatos(tickets.get(position));
    }

    @Override
    public int getItemCount() {
        return tickets.size();
    }

    class HistorialTicketViewHolder extends RecyclerView.ViewHolder {
        private ItemHistorialTicketBinding binding;
        private NumberFormat formatter;

        HistorialTicketViewHolder(ItemHistorialTicketBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            formatter = new DecimalFormat("#,###");
        }

        void setDatos(TicketModel item) {
            binding.tvItemHistorialTicketTienda.setText(item.getNombreTienda());
            binding.tvItemHistorialTicketFecha.setText(item.getFecha());
            String money = "$ " + formatter.format(item.getTotal());
            binding.tvItemHistorialTicketSaldo.setText(money);
            binding.clItemHistorialTicketContainer.setOnClickListener(v -> listener.onClickTicket(item.getIdTicket()));
        }
    }
}

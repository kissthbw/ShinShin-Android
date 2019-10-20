package com.supermarket.shingshing.main.alerta;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.supermarket.shingshing.R;
import com.supermarket.shingshing.databinding.ItemAlertaContenidoBinding;
import com.supermarket.shingshing.databinding.ItemAlertaTituloBinding;
import com.supermarket.shingshing.models.AlertaModel;
import com.supermarket.shingshing.models.NotificacionModel;

import java.util.ArrayList;

public class AlertaAdapter extends RecyclerView.Adapter {
    static final int TYPE_PRODUCTO = 1;
    static final int TYPE_AGREGAR = 2;
    static final int TYPE_RETIRO = 3;
    private static final int TYPE_TITLE = 1;
    private static final int TYPE_BODY = 2;
    private ArrayList<NotificacionModel> notificaciones;

    AlertaAdapter(ArrayList<NotificacionModel> notificaciones) {
        this.notificaciones = notificaciones;
    }

    @Override
    public int getItemViewType(int position) {
        return notificaciones.get(position).getTitulo() != null ? TYPE_TITLE : TYPE_BODY;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_TITLE) {
            ItemAlertaTituloBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_alerta_titulo, parent, false);
            return new TituloViewHolder(binding);
        } else {
            ItemAlertaContenidoBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_alerta_contenido, parent, false);
            return new ContenidoViewHolder(binding);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == TYPE_TITLE) {
            ((TituloViewHolder)holder).setDatos(notificaciones.get(position).getTitulo());
        } else {
            ((ContenidoViewHolder)holder).setDatos(notificaciones.get(position).getAlerta());
        }
    }

    @Override
    public int getItemCount() {
        return notificaciones.size();
    }

    class TituloViewHolder extends RecyclerView.ViewHolder {
        private ItemAlertaTituloBinding binding;

        TituloViewHolder(ItemAlertaTituloBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        private void setDatos(String titulo) {
            binding.tvItemAlertaTitulo.setText(titulo);
        }
    }

    class ContenidoViewHolder extends RecyclerView.ViewHolder {
        private ItemAlertaContenidoBinding binding;

        ContenidoViewHolder(ItemAlertaContenidoBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        private void setDatos(AlertaModel alerta) {
            binding.tvItemAlertaPrecio.setVisibility(View.VISIBLE);
            binding.tvItemAlertaDescripcion.setText(alerta.getDescripcion());
            binding.tvItemAlertaCodigo.setText(alerta.getCodigo());

            switch (alerta.getTipo()) {
                case TYPE_PRODUCTO:
                    binding.ivItemAlertaImagen.setBackground(ContextCompat.getDrawable(binding.ivItemAlertaImagen.getContext(), R.drawable.round_corner_gris));
                    binding.ivItemAlertaImagen.setImageDrawable(ContextCompat.getDrawable(binding.ivItemAlertaImagen.getContext(), R.drawable.img_barrilito));
                    binding.tvItemAlertaPrecio.setText(alerta.getPrecio());
                    break;
                case TYPE_AGREGAR:
                    binding.ivItemAlertaImagen.setBackground(ContextCompat.getDrawable(binding.ivItemAlertaImagen.getContext(), R.drawable.round_corner_naranja));
                    binding.ivItemAlertaImagen.setImageDrawable(ContextCompat.getDrawable(binding.ivItemAlertaImagen.getContext(), R.drawable.ic_check_white));
                    binding.tvItemAlertaPrecio.setVisibility(View.GONE);
                    break;
                case TYPE_RETIRO:
                    binding.ivItemAlertaImagen.setBackground(ContextCompat.getDrawable(binding.ivItemAlertaImagen.getContext(), R.drawable.round_corner_mamey));
                    binding.ivItemAlertaImagen.setImageDrawable(ContextCompat.getDrawable(binding.ivItemAlertaImagen.getContext(), R.drawable.ic_arrow_right_orange));
                    binding.tvItemAlertaPrecio.setVisibility(View.GONE);
                    break;
            }
        }
    }
}

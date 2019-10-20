package com.supermarket.shingshing.main.menu.opciones.tour;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.supermarket.shingshing.R;
import com.supermarket.shingshing.databinding.ItemViewpagerAyudatourBinding;

import java.util.ArrayList;
import java.util.Arrays;

public class AyudaTourAdapter extends RecyclerView.Adapter<AyudaTourAdapter.AyudaTourViewHolder> {
    private ArrayList<String> textos;
    private ArrayList<Integer> imagenes;

    AyudaTourAdapter(Context context) {
        textos = new ArrayList<>(Arrays.asList(context.getString(R.string.ayuda_tour_msg1), context.getString(R.string.ayuda_tour_msg2), context.getString(R.string.ayuda_tour_msg3), context.getString(R.string.ayuda_tour_msg4)));
        imagenes = new ArrayList<>(Arrays.asList(R.drawable.img_tour_1, R.drawable.img_tour_2, R.drawable.img_tour_3, R.drawable.img_intro_4));
    }

    @NonNull
    @Override
    public AyudaTourViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemViewpagerAyudatourBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_viewpager_ayudatour, parent, false);
        return new AyudaTourViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull AyudaTourViewHolder holder, int position) {
        holder.setDatos(position);
    }

    @Override
    public int getItemCount() {
        return textos.size();
    }

    class AyudaTourViewHolder extends RecyclerView.ViewHolder {
        private ItemViewpagerAyudatourBinding binding;

        AyudaTourViewHolder(ItemViewpagerAyudatourBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void setDatos(int position) {
            binding.ivItemViewPagerAyudaTourFoto.setImageDrawable(ContextCompat.getDrawable(binding.ivItemViewPagerAyudaTourFoto.getContext(), imagenes.get(position)));
            binding.tvItemViewPagerAyudaTourTexto.setText(textos.get(position));
        }
    }
}

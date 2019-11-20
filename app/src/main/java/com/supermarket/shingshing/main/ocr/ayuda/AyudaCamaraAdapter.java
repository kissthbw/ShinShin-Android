package com.supermarket.shingshing.main.ocr.ayuda;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.supermarket.shingshing.R;
import com.supermarket.shingshing.databinding.ItemViewpagerIntroBinding;

import java.util.ArrayList;
import java.util.Arrays;

public class AyudaCamaraAdapter extends RecyclerView.Adapter<AyudaCamaraAdapter.AyudaCamaraViewHolder> {
    private ArrayList<String> textos;
    private ArrayList<Integer> imagenes;

    AyudaCamaraAdapter(Context context) {
        textos = new ArrayList<>(Arrays.asList(context.getString(R.string.ayuda_camara_msg1), context.getString(R.string.ayuda_camara_msg2), context.getString(R.string.ayuda_camara_msg3), context.getString(R.string.ayuda_camara_msg4)));
        imagenes = new ArrayList<>(Arrays.asList(R.drawable.img_camara_1, R.drawable.img_camara_2, R.drawable.img_camara_3, R.drawable.img_lentesucio));
    }

    @NonNull
    @Override
    public AyudaCamaraViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemViewpagerIntroBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_viewpager_intro, parent, false);
        return new AyudaCamaraAdapter.AyudaCamaraViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull AyudaCamaraViewHolder holder, int position) {
        holder.setDatos(position);
    }

    @Override
    public int getItemCount() {
        return textos.size();
    }

    class AyudaCamaraViewHolder extends RecyclerView.ViewHolder {
        private ItemViewpagerIntroBinding binding;

        AyudaCamaraViewHolder(ItemViewpagerIntroBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void setDatos(int position) {
            binding.ivItemViewPagerIntroFoto.setImageDrawable(ContextCompat.getDrawable(binding.ivItemViewPagerIntroFoto.getContext(), imagenes.get(position)));
            binding.tvItemViewPagerIntroTexto.setText(textos.get(position));
            binding.tvItemViewPagerIntroTexto.setTextColor(ContextCompat.getColor(binding.tvItemViewPagerIntroTexto.getContext(), R.color.blanco));
        }
    }
}

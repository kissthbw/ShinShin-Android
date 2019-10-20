package com.supermarket.shingshing.login;

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

public class IntroAdapter extends RecyclerView.Adapter<IntroAdapter.IntroViewHolder> {
    private ArrayList<String> textos;
    private ArrayList<Integer> imagenes;

    IntroAdapter(Context context) {
        textos = new ArrayList<>(Arrays.asList(context.getString(R.string.intro_msg1), context.getString(R.string.intro_msg2), context.getString(R.string.intro_msg3), context.getString(R.string.intro_msg4)));
        imagenes = new ArrayList<>(Arrays.asList(R.drawable.img_intro_1, R.drawable.img_intro_2, R.drawable.img_intro_3, R.drawable.img_intro_4));
    }

    @NonNull
    @Override
    public IntroViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemViewpagerIntroBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_viewpager_intro, parent, false);
        return new IntroViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull IntroViewHolder holder, int position) {
        holder.setDatos(position);
    }

    @Override
    public int getItemCount() {
        return textos.size();
    }

    class IntroViewHolder extends RecyclerView.ViewHolder {
        private ItemViewpagerIntroBinding binding;

        IntroViewHolder(ItemViewpagerIntroBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void setDatos(int position) {
            binding.ivItemViewPagerIntroFoto.setImageDrawable(ContextCompat.getDrawable(binding.ivItemViewPagerIntroFoto.getContext(), imagenes.get(position)));
            binding.tvItemViewPagerIntroTexto.setText(textos.get(position));
        }
    }
}

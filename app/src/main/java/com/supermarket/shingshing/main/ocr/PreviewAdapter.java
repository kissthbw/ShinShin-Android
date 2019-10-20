package com.supermarket.shingshing.main.ocr;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.supermarket.shingshing.R;
import com.supermarket.shingshing.databinding.ItemViewpagerPreviewBinding;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Locale;

public class PreviewAdapter extends RecyclerView.Adapter<PreviewAdapter.PreviewViewHolder> {
    private static final String TAG = PreviewAdapter.class.getSimpleName();
    private Context context;
    private int contador;

    PreviewAdapter(Context context, int contador) {
        this.context = context;
        this.contador = contador;
    }

    @NonNull
    @Override
    public PreviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemViewpagerPreviewBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_viewpager_preview, parent, false);
        return new PreviewViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull PreviewViewHolder holder, int position) {
        holder.setImage(position + 1);
    }

    @Override
    public int getItemCount() {
        return contador;
    }

    class PreviewViewHolder extends RecyclerView.ViewHolder {
        private ItemViewpagerPreviewBinding binding;

        PreviewViewHolder(ItemViewpagerPreviewBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void setImage(int position) {
            ContextWrapper cw = new ContextWrapper(context);
            File directorio = cw.getDir("imagenes", Context.MODE_PRIVATE);
            String nombre = String.format(Locale.US, "img_%d.png", position);
            cargarImagen(directorio, nombre);
        }

        private void cargarImagen(File directorio, String nombre) {
            try {
                File f = new File(directorio, nombre);
                Bitmap bitmap = BitmapFactory.decodeStream(new FileInputStream(f));
                binding.ivItemPreview.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                Log.e(TAG, "Error al leer archivo: " + e.getLocalizedMessage());
            }
        }
    }
}

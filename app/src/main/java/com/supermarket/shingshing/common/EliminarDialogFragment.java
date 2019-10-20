package com.supermarket.shingshing.common;

import android.app.Dialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.supermarket.shingshing.R;

import java.util.Objects;

public class EliminarDialogFragment extends DialogFragment {
    private EliminarDialogListener listener;
    private String titulo, subtitulo;
    private String eliminar, cancelar;

    public EliminarDialogFragment(EliminarDialogListener listener) {
        this.listener = listener;
    }

    public void setDatos(String titulo, String subtitulo, String eliminar, String cancelar) {
        this.titulo = titulo;
        this.subtitulo = subtitulo;
        this.eliminar = eliminar;
        this.cancelar = cancelar;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.dialog_fragment_eliminar_cuenta, container, false);

        TextView tvTitulo = view.findViewById(R.id.tvEliminarTitulo);
        TextView tvSubTtitulo = view.findViewById(R.id.tvEliminarSubtitulo);
        Button btnEliminar = view.findViewById(R.id.btnEliminarEliminar);
        Button btnCancelar = view.findViewById(R.id.btnEliminarCancelar);

        if (titulo != null) {
            tvTitulo.setText(titulo);
        }

        if (subtitulo != null) {
            tvSubTtitulo.setText(subtitulo);
        }

        if (eliminar != null) {
            btnEliminar.setText(eliminar);
        }

        if (cancelar != null) {
            btnCancelar.setText(cancelar);
        }

        btnEliminar.setOnClickListener(v -> {
            btnEliminar.setEnabled(false);
            btnCancelar.setEnabled(false);
            listener.onClickEliminar();
            dismiss();
        });
        btnCancelar.setOnClickListener(v -> dismiss());

        setDialogPosition();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.WRAP_CONTENT;
            Objects.requireNonNull(dialog.getWindow()).setLayout(width, height);
        }
    }

    private void setDialogPosition() {
        Window window = Objects.requireNonNull(getDialog()).getWindow();
        if (window != null) {
            window.setGravity(Gravity.BOTTOM);
        }
    }
}

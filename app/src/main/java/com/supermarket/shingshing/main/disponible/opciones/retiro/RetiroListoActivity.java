package com.supermarket.shingshing.main.disponible.opciones.retiro;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.supermarket.shingshing.R;
import com.supermarket.shingshing.databinding.ActivityRetiroListoBinding;

public class RetiroListoActivity extends AppCompatActivity {
    private ActivityRetiroListoBinding binding;
    private boolean recarga;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_retiro_listo);

        if (getIntent().getExtras() != null) {
            recarga = getIntent().getBooleanExtra("recarga", false);
        }

        iniciarVista();
    }

    private void iniciarVista() {
        if (recarga) {
            binding.tvRetiroListoTitulo.setText(getString(R.string.retiro_listo_recarga_title));
            binding.tvRetiroListoMensaje.setText(getString(R.string.retiro_listo_recarga_msg));
            binding.ivRetiroListoImagen.setImageDrawable(getResources().getDrawable(R.drawable.img_recargaenviada));
        }

        binding.btnRetiroListoVale.setOnClickListener(v -> finish());
    }
}

package com.supermarket.shingshing.resultado;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;

import com.supermarket.shingshing.R;
import com.supermarket.shingshing.databinding.ActivityResultadoListoBinding;
import com.supermarket.shingshing.main.MainActivity;

public class ResultadoListoActivity extends AppCompatActivity {
    private ActivityResultadoListoBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_resultado_listo);
        iniciarVistas();
    }

    private void iniciarVistas() {
        binding.btnResultadoListoVale.setOnClickListener(v -> {
            binding.btnResultadoListoVale.setEnabled(false);
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
        });

        if (getIntent().getExtras() != null) {
            int bonificacion = getIntent().getIntExtra("bonificacion", 0);
            binding.tvResultadoListoSaldo.setText(String.format(getString(R.string.listo_msg_sumaste), bonificacion));
        }
    }
}

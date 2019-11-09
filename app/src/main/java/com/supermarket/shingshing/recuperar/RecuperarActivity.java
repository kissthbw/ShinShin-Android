package com.supermarket.shingshing.recuperar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;

import com.supermarket.shingshing.R;
import com.supermarket.shingshing.databinding.ActivityRecuperarBinding;

public class RecuperarActivity extends AppCompatActivity {
    private ActivityRecuperarBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_recuperar);

        binding.btnRecuperarEnviar.setOnClickListener(v -> recuperarDatos());
    }

    private void recuperarDatos() {
        String recuperarCorreo = binding.etRecuperarCorreo.getText().toString();

        if(validarDatos(recuperarCorreo)) {
            startActivity(new Intent(this, EnviadoActivity.class));
        }
    }

    private boolean validarDatos(String correo) {
        if (correo.trim().isEmpty() || (!correo.matches("\\d+") && !android.util.Patterns.EMAIL_ADDRESS.matcher(correo).matches())) {
            binding.etRecuperarCorreo.setError(getString(R.string.login_error_correo));
            binding.etRecuperarCorreo.requestFocus();
            return false;
        } else if (correo.matches("\\d+") && !correo.matches("\\d{2,10}")) {
            binding.etRecuperarCorreo.setError(getString(R.string.login_error_correo));
            binding.etRecuperarCorreo.requestFocus();
            return false;
        }

        return true;
    }
}

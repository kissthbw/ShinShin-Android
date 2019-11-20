package com.supermarket.shingshing.recuperar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.JsonObject;
import com.supermarket.shingshing.R;
import com.supermarket.shingshing.databinding.ActivityRecuperarBinding;
import com.supermarket.shingshing.network.ApiClient;
import com.supermarket.shingshing.network.ApiService;
import com.supermarket.shingshing.util.UtilsView;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class RecuperarActivity extends AppCompatActivity {
    private static final String TAG = RecuperarActivity.class.getSimpleName();
    private ActivityRecuperarBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_recuperar);

        binding.btnRecuperarEnviar.setOnClickListener(v -> recuperarDatos());
    }

    private void recuperarDatos() {
        binding.btnRecuperarEnviar.setEnabled(false);
        String recuperarCorreo = binding.etRecuperarCorreo.getText().toString();

        if (validarDatos(recuperarCorreo)) {
            JsonObject json = new JsonObject();
            json.addProperty("correoElectronico", recuperarCorreo);

            ejecutarServicio(json);
        } else {
            binding.btnRecuperarEnviar.setEnabled(true);
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

    private void ejecutarServicio(JsonObject json) {
        UtilsView.mostrarProgress(this, getString(R.string.general_msg_esperar));
        ApiService apiService = ApiClient.getClient(getApplicationContext()).create(ApiService.class);
        Disposable disposable = apiService.recuperarContrasena(json)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> {
                    UtilsView.esconderProgress();
                    binding.btnRecuperarEnviar.setEnabled(true);
                    if (result.getCode() == 200) {
                        startActivity(new Intent(this, EnviadoActivity.class));
                        finish();
                    } else {
                        UtilsView.mostrarAlerta(this, null, getString(R.string.general_error), getString(R.string.general_button_aceptar));
                    }
                }, throwable -> {
                    UtilsView.esconderProgress();
                    binding.btnRecuperarEnviar.setEnabled(true);
                    Log.e(TAG, "Recuperar contraseña error: " + throwable.getLocalizedMessage());
                });
    }
}

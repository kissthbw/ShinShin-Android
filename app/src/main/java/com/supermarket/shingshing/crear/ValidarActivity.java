package com.supermarket.shingshing.crear;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;

import com.google.gson.JsonObject;
import com.supermarket.shingshing.R;
import com.supermarket.shingshing.databinding.ActivityValidarBinding;
import com.supermarket.shingshing.network.ApiClient;
import com.supermarket.shingshing.network.ApiService;
import com.supermarket.shingshing.util.UtilsView;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ValidarActivity extends AppCompatActivity {
    private static final String TAG = ValidarActivity.class.getSimpleName();
    private ActivityValidarBinding binding;
    private ApiService apiService;
    private int idUsuario;
    private String telefono;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_validar);

        if (getIntent() != null && getIntent().getExtras() != null) {
            idUsuario = getIntent().getIntExtra("id", 0);
            telefono = getIntent().getStringExtra("telefono");
        }

        iniciarVistas();
    }

    private void iniciarVistas() {
        apiService = ApiClient.getClient(getApplicationContext()).create(ApiService.class);

        binding.tvValidarMensaje.setText(String.format(getString(R.string.validar_msg), telefono));
        binding.btnValidar.setOnClickListener(v -> obtenerDatos());
        binding.tvValidarEnviar.setOnClickListener(v -> servicioReenviar());

        binding.etValidar1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count >= 1) {
                    binding.etValidar2.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        binding.etValidar2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count >= 1) {
                    binding.etValidar3.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        binding.etValidar3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count >= 1) {
                    binding.etValidar4.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private void obtenerDatos() {
        binding.btnValidar.setEnabled(false);
        binding.tvValidarEnviar.setEnabled(false);
        String num1 = binding.etValidar1.getText().toString();
        String num2 = binding.etValidar2.getText().toString();
        String num3 = binding.etValidar3.getText().toString();
        String num4 = binding.etValidar4.getText().toString();

        if (validarDatos(num1, num2, num3, num4)) {
            String codigo = String.format("%s%s%s%s", num1, num2, num3, num4);

            JsonObject json = new JsonObject();
            json.addProperty("idUsuario", idUsuario);
            json.addProperty("codigoVerificacion", codigo);

            servicioValidar(json);
        } else {
            binding.btnValidar.setEnabled(true);
            binding.tvValidarEnviar.setEnabled(true);
        }
    }

    private boolean validarDatos(String num1, String num2, String num3, String num4) {
        if (num1.trim().isEmpty()) {
            binding.etValidar1.setError(getString(R.string.validar_error));
            binding.etValidar1.requestFocus();
            return false;
        }

        if (num2.trim().isEmpty()) {
            binding.etValidar2.setError(getString(R.string.validar_error));
            binding.etValidar2.requestFocus();
            return false;
        }

        if (num3.trim().isEmpty()) {
            binding.etValidar3.setError(getString(R.string.validar_error));
            binding.etValidar3.requestFocus();
            return false;
        }

        if (num4.trim().isEmpty()) {
            binding.etValidar4.setError(getString(R.string.validar_error));
            binding.etValidar4.requestFocus();
            return false;
        }

        return true;
    }

    private void servicioValidar(JsonObject json) {
        UtilsView.mostrarProgress(this, getString(R.string.general_msg_esperar));
        Disposable disposable = apiService.validarCuenta(json)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> {
                    UtilsView.esconderProgress();
                    binding.btnValidar.setEnabled(true);
                    binding.tvValidarEnviar.setEnabled(true);
                    if (result.getCode() == 200) {
                        startActivity(new Intent(this, BienvenidoActivity.class));
                    }
                }, throwable -> {
                    UtilsView.esconderProgress();
                    binding.btnValidar.setEnabled(true);
                    binding.tvValidarEnviar.setEnabled(true);
                    Log.e(TAG, "Validar cuenta error: " + throwable.getLocalizedMessage());
                });
    }

    private void servicioReenviar() {
        UtilsView.mostrarProgress(this, getString(R.string.general_msg_esperar));
        binding.btnValidar.setEnabled(false);
        binding.tvValidarEnviar.setEnabled(false);
        JsonObject json = new JsonObject();
        json.addProperty("idUsuario", idUsuario);

        Disposable disposable = apiService.reenviar(json)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> {
                    UtilsView.esconderProgress();
                    binding.btnValidar.setEnabled(true);
                    binding.tvValidarEnviar.setEnabled(true);
                    Log.e(TAG, "Reenviado");
                }, throwable -> {
                    UtilsView.esconderProgress();
                    binding.btnValidar.setEnabled(true);
                    binding.tvValidarEnviar.setEnabled(true);
                    Log.e(TAG, "Reenviar error: " + throwable.getLocalizedMessage());
                });
    }
}

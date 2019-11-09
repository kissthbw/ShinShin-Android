package com.supermarket.shingshing.main.menu.opciones.eliminar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.JsonObject;
import com.supermarket.shingshing.R;
import com.supermarket.shingshing.databinding.ActivityEliminarCuentaBinding;
import com.supermarket.shingshing.network.ApiClient;
import com.supermarket.shingshing.network.ApiService;
import com.supermarket.shingshing.util.UsuarioSingleton;
import com.supermarket.shingshing.util.UtilsView;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class EliminarCuentaActivity extends AppCompatActivity {
    private static final String TAG = EliminarCuentaActivity.class.getSimpleName();
    private ActivityEliminarCuentaBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_eliminar_cuenta);

        iniciarVistas();
    }

    private void iniciarVistas() {
        binding.btnEliminarCuentaEliminar.setOnClickListener(v -> obtenerDatos());
        binding.tvEliminarCuentaRegresar.setOnClickListener(v -> finish());
    }

    private void obtenerDatos() {
        binding.btnEliminarCuentaEliminar.setEnabled(false);
        String motivo = binding.etEliminarCuentaMotivo.getText().toString();
        String comentarios = binding.etEliminarCuentaComentarios.getText().toString();

        if (validarDatos(motivo, comentarios)) {
            JsonObject json = new JsonObject();
            json.addProperty("idUsuario", UsuarioSingleton.getUsuario().getIdUsuario());
            ejecutarServicio(json);
        } else {
            binding.btnEliminarCuentaEliminar.setEnabled(true);
        }
    }

    private boolean validarDatos(String motivo, String comentarios) {
        if (motivo.trim().isEmpty()) {
            binding.etEliminarCuentaMotivo.setError("Favor de ingresar un motivo");
            binding.etEliminarCuentaMotivo.requestFocus();
            return false;
        }

//        if (comentarios.trim().isEmpty()) {
//            binding.etEliminarCuentaComentarios.setError("Favor de ingresar un comentario");
//            binding.etEliminarCuentaComentarios.requestFocus();
//            return false;
//        }

        return true;
    }

    private void ejecutarServicio(JsonObject json) {
        UtilsView.mostrarProgress(this, getString(R.string.general_msg_esperar));
        ApiService apiService = ApiClient.getClient(this).create(ApiService.class);
        Disposable disposable = apiService.eliminarCuenta(json)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> {
                    UtilsView.esconderProgress();
                    binding.btnEliminarCuentaEliminar.setEnabled(true);
                    if (result.getCode() == 200) {
                        startActivity(new Intent(this, AdiosActivity.class));
                    }
                }, throwable -> {
                    UtilsView.esconderProgress();
                    binding.btnEliminarCuentaEliminar.setEnabled(true);
                    Log.e(TAG, "Eliminar perfil error: " + throwable.getLocalizedMessage());
                });
    }
}

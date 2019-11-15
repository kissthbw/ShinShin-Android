package com.supermarket.shingshing.main.menu.opciones;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.google.gson.JsonObject;
import com.supermarket.shingshing.R;
import com.supermarket.shingshing.databinding.FragmentContactoBinding;
import com.supermarket.shingshing.network.ApiClient;
import com.supermarket.shingshing.network.ApiService;
import com.supermarket.shingshing.util.UsuarioSingleton;
import com.supermarket.shingshing.util.UtilsView;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ContactoFragment extends Fragment {
    private static final String TAG = ContactoFragment.class.getSimpleName();
    private FragmentContactoBinding binding;

    public ContactoFragment() {}

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_contacto, container, false);

        binding.btnAyudaEnviar.setOnClickListener(v -> obtenerDatos());

        return binding.getRoot();
    }

    private void obtenerDatos() {
        binding.btnAyudaEnviar.setEnabled(false);
        String topico = binding.etAyudaAyudarte.getText().toString();
        String detalle = binding.etAyudaCuentanos.getText().toString();

        if (validarDatos(topico, detalle)) {
            JsonObject json = new JsonObject();
            json.addProperty("topico", topico);
            json.addProperty("detalle", detalle);
            json.addProperty("idUsuario", UsuarioSingleton.getUsuario().getIdUsuario());

            ejecutarServicio(json);
        } else {
            binding.btnAyudaEnviar.setEnabled(true);
        }
    }

    private boolean validarDatos(String topico, String detalle) {
        if (topico.trim().isEmpty()) {
            binding.etAyudaAyudarte.setError(getString(R.string.contacto_error_topico));
            binding.etAyudaAyudarte.requestFocus();
            return false;
        }

        if (detalle.trim().isEmpty() || detalle.length() < 2) {
            binding.etAyudaCuentanos.setError(getString(R.string.contacto_error_detalle));
            binding.etAyudaCuentanos.requestFocus();
            return false;
        }

        return true;
    }

    private void ejecutarServicio(JsonObject json) {
        UtilsView.mostrarProgress(getContext(), getString(R.string.general_msg_esperar));
        ApiService apiService = ApiClient.getClient(getActivity()).create(ApiService.class);
        Disposable disposable = apiService.registrarContacto(json)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> {
                    UtilsView.esconderProgress();
                    binding.btnAyudaEnviar.setEnabled(true);
                    if (result.getCode() == 200 ) {
                        binding.etAyudaAyudarte.setText("");
                        binding.etAyudaCuentanos.setText("");
                        mostrarSnackbar(getString(R.string.contacto_msg_enviado));
                    } else {
                        Log.e(TAG, "Error al registrar contacto");
                    }
                }, throwable -> {
                    UtilsView.esconderProgress();
                    binding.btnAyudaEnviar.setEnabled(true);
                    Log.e(TAG, "Error contacto: " + throwable.getLocalizedMessage());
                });
    }

    private void mostrarSnackbar(String mensaje) {
        Snackbar snackbar = Snackbar.make(binding.clContactoContainer, mensaje, Snackbar.LENGTH_LONG);
        View snackbarLayout = snackbar.getView();
        TextView textView = snackbarLayout.findViewById(com.google.android.material.R.id.snackbar_text);
        textView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_check, 0, 0, 0);
        textView.setCompoundDrawablePadding(getResources().getDimensionPixelOffset(R.dimen.dimen_icon));
        snackbar.setBackgroundTint(getResources().getColor(R.color.verde_mensaje));
        snackbar.show();
    }
}

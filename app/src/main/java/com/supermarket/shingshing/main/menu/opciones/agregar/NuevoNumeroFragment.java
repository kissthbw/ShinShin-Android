package com.supermarket.shingshing.main.menu.opciones.agregar;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.google.gson.JsonObject;
import com.supermarket.shingshing.R;
import com.supermarket.shingshing.common.EliminarDialogFragment;
import com.supermarket.shingshing.common.EliminarDialogListener;
import com.supermarket.shingshing.databinding.FragmentNuevoNumeroBinding;
import com.supermarket.shingshing.main.menu.opciones.CuentasFragment;
import com.supermarket.shingshing.models.MedioBonificacionModel;
import com.supermarket.shingshing.network.ApiClient;
import com.supermarket.shingshing.network.ApiService;
import com.supermarket.shingshing.util.UsuarioSingleton;
import com.supermarket.shingshing.util.UtilsView;

import java.util.Objects;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.supermarket.shingshing.main.menu.opciones.CuentasFragment.NUEVO_NUMERO;

public class NuevoNumeroFragment extends Fragment implements EliminarDialogListener {
    private static final String TAG = NuevoNumeroFragment.class.getSimpleName();
    private FragmentNuevoNumeroBinding binding;
    private NuevoListener listener;
    private ApiService apiService;

    private MedioBonificacionModel numero;
    private int idUsuario;

    public NuevoNumeroFragment() {}

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        listener = (NuevoListener) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        apiService = ApiClient.getClient(getActivity()).create(ApiService.class);
        if (getArguments() != null) {
            numero = getArguments().getParcelable(CuentasFragment.KEY_NUMERO);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_nuevo_numero, container, false);

        iniciarVistas();

        return binding.getRoot();
    }

    @Override
    public void onClickEliminar() {
        servicioEliminar();
    }

    private void iniciarVistas() {
        idUsuario = UsuarioSingleton.getUsuario().getIdUsuario();

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(Objects.requireNonNull(getActivity()), R.array.options_telefono, R.layout.item_spinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.sNumeroCompania.setAdapter(adapter);

        binding.btnNumeroGuardar.setOnClickListener(v -> obtenerDatos());

        if (numero != null) {
            binding.etNumeroTelefono.setText(numero.getCuentaMedioBonificacion());
            int position = getPositionArray(getResources().getStringArray(R.array.options_telefono), numero.getCompaniaMedioBonificacion());
            binding.sNumeroCompania.setSelection(position);
            binding.tvNumeroTituloConfirmar.setVisibility(View.GONE);
            binding.etNumeroConfirmar.setVisibility(View.GONE);
            binding.tvNumeroTituloNuevo.setText(numero.getAliasMedioBonificacion());
            binding.etNumeroNombreCorto.setText(numero.getAliasMedioBonificacion());
            binding.tvNumeroEliminar.setVisibility(View.VISIBLE);
            binding.tvNumeroEliminar.setOnClickListener(v -> {
                EliminarDialogFragment dialogFragment = new EliminarDialogFragment(this);
                dialogFragment.setDatos(getString(R.string.nuevo_title_eliminar_numero), getString(R.string.nuevo_subtitle_eliminar_medio), getString(R.string.general_button_si_eliminar), getString(R.string.general_button_cancelar));
                dialogFragment.show(getFragmentManager().beginTransaction(), EliminarDialogFragment.class.getSimpleName());
            });
        }
    }

    private int getPositionArray(String[] array, String mes) {
        for (int x = 0; x < array.length; x++) {
            if (mes.equalsIgnoreCase(array[x])) {
                return x;
            }
        }

        return 0;
    }

    private void obtenerDatos() {
        binding.btnNumeroGuardar.setEnabled(false);
        String alias = binding.etNumeroNombreCorto.getText().toString();
        String cuenta = binding.etNumeroTelefono.getText().toString();
        String confirmar = binding.etNumeroConfirmar.getText().toString();
        String compania = binding.sNumeroCompania.getSelectedItem().toString();

        if (validarDatos(alias, cuenta, confirmar)) {
            JsonObject medioBonificacion = new JsonObject();
            medioBonificacion.addProperty("idCatalogoMedioBonificacion", NUEVO_NUMERO);
            JsonObject usuario = new JsonObject();
            usuario.addProperty("idUsuario", idUsuario);

            JsonObject json = new JsonObject();
            if (numero != null) {
                json.addProperty("idMediosBonificacion", numero.getIdMediosBonificacion());
            }
            json.addProperty("aliasMedioBonificacion", alias);
            json.addProperty("cuentaMedioBonificacion", cuenta);
            json.addProperty("companiaMedioBonificacion", compania);
            json.add("catalogoMediosBonificacion", medioBonificacion);
            json.add("usuario", usuario);

            if (numero == null) {
                servicioGuardar(json);
            } else {
                servicioActualizar(json);
            }
        } else {
            binding.btnNumeroGuardar.setEnabled(true);
        }
    }

    private boolean validarDatos(String alias, String cuenta, String confirmar) {
        if (cuenta.trim().isEmpty() || cuenta.length() < 10) {
            binding.etNumeroTelefono.setError(getString(R.string.crear_error_telefono));
            binding.etNumeroTelefono.requestFocus();
            return false;
        }

        if (numero == null) {
            if (confirmar.trim().isEmpty() || confirmar.length() < 10) {
                binding.etNumeroConfirmar.setError(getString(R.string.crear_error_confirmar));
                binding.etNumeroConfirmar.requestFocus();
                return false;
            }

            if (!cuenta.trim().equalsIgnoreCase(confirmar.trim())) {
                binding.etNumeroConfirmar.setError(getString(R.string.crear_error_coincidir));
                binding.etNumeroConfirmar.requestFocus();
                return false;
            }
        }

        if (alias.trim().isEmpty()) {
            binding.etNumeroNombreCorto.setError(getString(R.string.nuevo_error_alias));
            binding.etNumeroNombreCorto.requestFocus();
            return false;
        }

        return true;
    }

    private void servicioGuardar(JsonObject json) {
        UtilsView.mostrarProgress(getActivity(), getString(R.string.general_msg_esperar));
        Disposable disposable = apiService.guardarMedioBonificacion(json)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> {
                    UtilsView.esconderProgress();
                    binding.btnNumeroGuardar.setEnabled(true);
                    if (result.getCode() == 200) {
                        listener.onClickMostrarSnackbar(getString(R.string.nuevo_msg_agregar_numero));
                    }
                }, throwable -> {
                    UtilsView.esconderProgress();
                    binding.btnNumeroGuardar.setEnabled(true);
                    Log.e(TAG, "Guardar telefono medio error: " + throwable.getLocalizedMessage());
                });
    }

    private void servicioActualizar(JsonObject json) {
        UtilsView.mostrarProgress(getActivity(), getString(R.string.general_msg_esperar));
        Disposable disposable = apiService.actualizarMedioBonificacion(json)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> {
                    UtilsView.esconderProgress();
                    binding.btnNumeroGuardar.setEnabled(true);
                    if (result.getCode() == 200) {
                        listener.onClickMostrarSnackbar(getString(R.string.nuevo_msg_actualizar));
                    }
                }, throwable -> {
                    UtilsView.esconderProgress();
                    binding.btnNumeroGuardar.setEnabled(true);
                    Log.e(TAG, "Actualizar numero medio error: " + throwable.getLocalizedMessage());
                });
    }

    private void servicioEliminar() {
        UtilsView.mostrarProgress(getActivity(), getString(R.string.general_msg_esperar));
        JsonObject json = new JsonObject();
        json.addProperty("idMediosBonificacion", numero.getIdMediosBonificacion());

        Disposable disposable = apiService.eliminarMedioBonificacion(json)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> {
                    UtilsView.esconderProgress();
                    if (result.getCode() == 200) {
                        listener.onClickMostrarSnackbar(getString(R.string.nuevo_msg_eliminada_numero));
                    }
                }, throwable -> {
                    UtilsView.esconderProgress();
                    Log.e(TAG, "Eliminar número medio error: " + throwable.getLocalizedMessage());
                });
    }
}

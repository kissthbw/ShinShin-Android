package com.supermarket.shingshing.main.menu.opciones.agregar;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.JsonObject;
import com.supermarket.shingshing.R;
import com.supermarket.shingshing.common.EliminarDialogFragment;
import com.supermarket.shingshing.common.EliminarDialogListener;
import com.supermarket.shingshing.databinding.FragmentNuevaCuentaBinding;
import com.supermarket.shingshing.main.menu.opciones.CuentasFragment;
import com.supermarket.shingshing.models.MedioBonificacionModel;
import com.supermarket.shingshing.network.ApiClient;
import com.supermarket.shingshing.network.ApiService;
import com.supermarket.shingshing.util.UsuarioSingleton;
import com.supermarket.shingshing.util.UtilsView;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.supermarket.shingshing.main.menu.opciones.CuentasFragment.NUEVO_CUENTA;

public class NuevaCuentaFragment extends Fragment implements EliminarDialogListener {
    private static final String TAG = NuevaCuentaFragment.class.getSimpleName();
    private FragmentNuevaCuentaBinding binding;
    private NuevoListener listener;
    private NoGuardadoListener noGuardadoListener;
    private ApiService apiService;

    private MedioBonificacionModel cuenta;
    private int idUsuario;

    public NuevaCuentaFragment() {}

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        listener = (NuevoListener) context;
        noGuardadoListener = (NoGuardadoListener) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        apiService = ApiClient.getClient(getActivity()).create(ApiService.class);
        if (getArguments() != null) {
            cuenta = getArguments().getParcelable(CuentasFragment.KEY_CUENTA);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_nueva_cuenta, container, false);

        iniciarVistas();

        return binding.getRoot();
    }

    @Override
    public void onClickEliminar() {
        servicioEliminar();
    }

    private void iniciarVistas() {
        idUsuario = UsuarioSingleton.getUsuario().getIdUsuario();

        binding.btnPaypalGuardar.setOnClickListener(v -> obtenerDatos());
        if (cuenta != null) {
            binding.etPaypalID.setText(cuenta.getIdCuentaMedioBonificacion());
            binding.etPaypalCorreo.setText(cuenta.getCuentaMedioBonificacion());
            binding.tvPaypalTituloNuevo.setText(cuenta.getAliasMedioBonificacion());
            binding.etPaypalNombreCorto.setText(cuenta.getAliasMedioBonificacion());
            binding.tvPaypalEliminar.setVisibility(View.VISIBLE);
            binding.tvPaypalEliminar.setOnClickListener(v -> {
                EliminarDialogFragment dialogFragment = new EliminarDialogFragment(this);
                dialogFragment.setDatos(getString(R.string.nuevo_title_eliminar_cuenta), getString(R.string.nuevo_subtitle_eliminar_medio), getString(R.string.general_button_si_eliminar), getString(R.string.general_button_cancelar));
                dialogFragment.show(getFragmentManager().beginTransaction(), EliminarDialogFragment.class.getSimpleName());
            });
        }
        binding.etPaypalID.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {
                noGuardadoListener.onEditar(!editable.toString().isEmpty());
            }
        });
    }

    private void obtenerDatos() {
        binding.btnPaypalGuardar.setEnabled(false);
        String alias = binding.etPaypalNombreCorto.getText().toString();
        String cuenta = binding.etPaypalID.getText().toString();
        String medio = binding.etPaypalCorreo.getText().toString();

        if (validarDatos(alias, cuenta, medio)) {
            JsonObject medioBonificacion = new JsonObject();
            medioBonificacion.addProperty("idCatalogoMedioBonificacion", NUEVO_CUENTA);
            JsonObject usuario = new JsonObject();
            usuario.addProperty("idUsuario", idUsuario);

            JsonObject json = new JsonObject();
            if (this.cuenta != null) {
                json.addProperty("idMediosBonificacion", this.cuenta.getIdMediosBonificacion());
            }
            json.addProperty("aliasMedioBonificacion", alias);
            json.addProperty("idCuentaMedioBonificacion", cuenta);
            json.addProperty("cuentaMedioBonificacion", medio);
            json.add("catalogoMediosBonificacion", medioBonificacion);
            json.add("usuario", usuario);

            if (this.cuenta == null) {
                servicioGuardar(json);
            } else {
                servicioActualizar(json);
            }
        } else {
            binding.btnPaypalGuardar.setEnabled(true);
        }
    }

    private boolean validarDatos(String alias, String cuenta, String medio) {
        if (cuenta.trim().isEmpty() || cuenta.length() < 13) {
            binding.etPaypalID.setError(getString(R.string.nuevo_error_cuenta));
            binding.etPaypalID.requestFocus();
            return false;
        }

        if (medio.trim().isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(medio).matches()) {
            binding.etPaypalCorreo.setError(getString(R.string.login_error_correo));
            binding.etPaypalCorreo.requestFocus();
            return false;
        }

        if (alias.trim().isEmpty()) {
            binding.etPaypalNombreCorto.setError(getString(R.string.nuevo_error_alias));
            binding.etPaypalNombreCorto.requestFocus();
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
                    binding.btnPaypalGuardar.setEnabled(true);
                    if (result.getCode() == 200) {
                        noGuardadoListener.onEditar(false);
                        listener.onClickMostrarSnackbar(getString(R.string.nuevo_msg_agregar_cuenta));
                    }
                }, throwable -> {
                    UtilsView.esconderProgress();
                    binding.btnPaypalGuardar.setEnabled(true);
                    Log.e(TAG, "Guardar cuenta medio error: " + throwable.getLocalizedMessage());
                });
    }

    private void servicioActualizar(JsonObject json) {
        UtilsView.mostrarProgress(getActivity(), getString(R.string.general_msg_esperar));
        Disposable disposable = apiService.actualizarMedioBonificacion(json)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> {
                    UtilsView.esconderProgress();
                    binding.btnPaypalGuardar.setEnabled(true);
                    if (result.getCode() == 200) {
                        noGuardadoListener.onEditar(false);
                        listener.onClickMostrarSnackbar(getString(R.string.nuevo_msg_actualizar));
                    }
                }, throwable -> {
                    UtilsView.esconderProgress();
                    binding.btnPaypalGuardar.setEnabled(true);
                    Log.e(TAG, "Actualizar cuenta medio error: " + throwable.getLocalizedMessage());
                });
    }

    private void servicioEliminar() {
        UtilsView.mostrarProgress(getActivity(), getString(R.string.general_msg_esperar));
        JsonObject json = new JsonObject();
        json.addProperty("idMediosBonificacion", cuenta.getIdMediosBonificacion());

        Disposable disposable = apiService.eliminarMedioBonificacion(json)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> {
                    UtilsView.esconderProgress();
                    if (result.getCode() == 200) {
                        noGuardadoListener.onEditar(false);
                        listener.onClickMostrarSnackbar(getString(R.string.nuevo_msg_eliminada_cuenta));
                    }
                }, throwable -> {
                    UtilsView.esconderProgress();
                    Log.e(TAG, "Eliminar cuenta medio error: " + throwable.getLocalizedMessage());
                });
    }
}

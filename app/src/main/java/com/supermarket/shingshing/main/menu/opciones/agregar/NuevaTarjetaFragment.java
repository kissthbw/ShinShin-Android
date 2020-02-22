package com.supermarket.shingshing.main.menu.opciones.agregar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import com.getbouncer.cardscan.CreditCard;
import com.getbouncer.cardscan.ScanActivity;
import com.google.gson.JsonObject;
import com.supermarket.shingshing.R;
import com.supermarket.shingshing.common.EliminarDialogFragment;
import com.supermarket.shingshing.common.EliminarDialogListener;
import com.supermarket.shingshing.databinding.FragmentNuevaTarjetaBinding;
import com.supermarket.shingshing.main.menu.opciones.CuentasFragment;
import com.supermarket.shingshing.models.MedioBonificacionModel;
import com.supermarket.shingshing.models.TipoBancariaModel;
import com.supermarket.shingshing.network.ApiClient;
import com.supermarket.shingshing.network.ApiService;
import com.supermarket.shingshing.util.UsuarioSingleton;
import com.supermarket.shingshing.util.UtilsView;

import java.util.ArrayList;
import java.util.Objects;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.supermarket.shingshing.main.menu.opciones.CuentasFragment.NUEVO_BANCARIA;

public class NuevaTarjetaFragment extends Fragment implements EliminarDialogListener {
    private static final String TAG = NuevaTarjetaFragment.class.getSimpleName();
    private static final int VISA = 1;
    private static final int MASTERCARD = 2;
    private static final int AMERICAN = 3;

    private FragmentNuevaTarjetaBinding binding;
    private NuevoListener listener;
    private NoGuardadoListener noGuardadoListener;
    private ApiService apiService;

    private ArrayList<TipoBancariaModel> tipoBancarias;
    private MedioBonificacionModel bancaria;
    private String tipoCuenta;
    private int idUsuario;

    public NuevaTarjetaFragment() {}

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
            bancaria = getArguments().getParcelable(CuentasFragment.KEY_BANCARIA);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_nueva_tarjeta, container, false);

        iniciarVistas();

        return binding.getRoot();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (ScanActivity.isScanResult(requestCode)) {
            if (resultCode == ScanActivity.RESULT_OK && data != null) {
                CreditCard scanResult = ScanActivity.creditCardFromResult(data);
                if (scanResult != null) {
                    binding.etBancariaNumero.setText(scanResult.number);
                }
            }
        }
    }

    @Override
    public void onClickEliminar() {
        servicioEliminar();
    }

    private void iniciarVistas() {
        idUsuario = UsuarioSingleton.getUsuario().getIdUsuario();

        binding.ivBancariaCamara.setOnClickListener(v -> ScanActivity.start(getActivity(), getString(R.string.nuevo_tarjeta_msg_titulo), getString(R.string.nuevo_tarjeta_msg_subtitulo)));
        binding.etBancariaNumero.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {
                noGuardadoListener.onEditar(!editable.toString().isEmpty());
            }
        });
        obtenerTipoBancaria();
    }

    private void obtenerTipoBancaria() {
        Disposable disposable = apiService.getTipoBancaria()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> {
                    if (result.getCode() == 200) {
                        tipoBancarias = result.getTiposBancarias();

                        String[] tipos = new String[result.getTiposBancarias().size()];
                        for (int x = 0; x < result.getTiposBancarias().size(); x++) {
                            tipos[x] = result.getTiposBancarias().get(x).getDescripcionBancaria();
                        }

                        ArrayAdapter<String> adapterTipo = new ArrayAdapter<>(Objects.requireNonNull(getActivity()), R.layout.item_spinner, tipos);
                        adapterTipo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        binding.sBancariaTipo.setAdapter(adapterTipo);

                        iniciarVistaSinDatos();
                    }
                }, throwable -> {
                    Log.e(TAG, "Error tipo bancaria: " + throwable.getLocalizedMessage());
                });
    }

    private void iniciarVistaSinDatos() {
        ArrayAdapter<CharSequence> adapterBanco = ArrayAdapter.createFromResource(Objects.requireNonNull(getActivity()), R.array.options_banco, R.layout.item_spinner);
        adapterBanco.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.sBancariaBancoDestino.setAdapter(adapterBanco);

        if (bancaria != null) {
            iniciarVistaConDatos();
        }
        
        iniciarListeners();
    }

    private void iniciarVistaConDatos() {
        int position = getPositionTipo();
        binding.sBancariaTipo.setSelection(position);

        binding.etBancariaNumero.setText(bancaria.getCuentaMedioBonificacion());

        binding.tvBancariaAlias.setText(bancaria.getAliasMedioBonificacion());
        binding.etBancariaNombreCorto.setText(bancaria.getAliasMedioBonificacion());

        int positionBanco = getPositionArray(getResources().getStringArray(R.array.options_banco), bancaria.getBanco());
        binding.sBancariaBancoDestino.setSelection(positionBanco);

        binding.tvBancariaEliminar.setVisibility(View.VISIBLE);
        binding.tvBancariaEliminar.setOnClickListener(v -> {
            EliminarDialogFragment dialogFragment = new EliminarDialogFragment(this);
            dialogFragment.setDatos(getString(R.string.nuevo_title_eliminar_tarjeta), getString(R.string.nuevo_subtitle_eliminar_medio), getString(R.string.general_button_si_eliminar), getString(R.string.general_button_cancelar));
            dialogFragment.show(getFragmentManager().beginTransaction(), EliminarDialogFragment.class.getSimpleName());
        });
    }

    private void iniciarListeners() {
        binding.sBancariaTipo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tipoCuenta = tipoBancarias.get(position).getDescripcionBancaria();
                if(tipoCuenta.equalsIgnoreCase("tarjeta")) {
                    binding.ivBancariaCamara.setVisibility(View.VISIBLE);
                    binding.tvBancariaTituloNuevo.setText(R.string.nuevo_title_no_tarjeta);
                    setEditTextMaxLength(binding.etBancariaNumero, 16);
                } else {
                    binding.ivBancariaCamara.setVisibility(View.GONE);
                    binding.tvBancariaTituloNuevo.setText(tipoCuenta.equalsIgnoreCase("tarjeta") ? R.string.nuevo_title_no_tarjeta : R.string.nuevo_title_clabe);
                    if (tipoCuenta.equalsIgnoreCase("cuenta")) {
                        setEditTextMaxLength(binding.etBancariaNumero, 11);
                    } else {
                        setEditTextMaxLength(binding.etBancariaNumero, 18);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        binding.btnBancariaGuardar.setOnClickListener(v -> obtenerDatos());
    }

    private void setEditTextMaxLength(EditText editText, int length) {
        InputFilter[] FilterArray = new InputFilter[1];
        FilterArray[0] = new InputFilter.LengthFilter(length);
        editText.setFilters(FilterArray);
    }

    private int getPositionTipo() {
        for (int x = 0; x < tipoBancarias.size(); x++) {
            if (bancaria.getIdTipo() == tipoBancarias.get(x).getIdTipo()) {
                return x;
            }
        }

        return 0;
    }

    private int getPositionArray(String[] array, String nombre) {
        if (nombre != null && !nombre.trim().isEmpty()) {
            for (int x = 0; x < array.length; x++) {
                if (nombre.equalsIgnoreCase(array[x])) {
                    return x;
                }
            }
        }

        return 0;
    }

    private void obtenerDatos() {
        binding.btnBancariaGuardar.setEnabled(false);
        String alias = binding.etBancariaNombreCorto.getText().toString();
        String cuenta = binding.etBancariaNumero.getText().toString();
        int idTipo = tipoBancarias.get(binding.sBancariaTipo.getSelectedItemPosition()).getIdTipo();
        int tipo = getTipoCompania(cuenta);
        String compania = tipo == VISA ? "VISA" : tipo == MASTERCARD ? "MASTERCARD" : tipo == AMERICAN ? "AMERICAN EXPRESS" : "";
        String banco = binding.sBancariaBancoDestino.getSelectedItem().toString();

        if (validarDatos(alias, cuenta)) {
            JsonObject medioBonificacion = new JsonObject();
            medioBonificacion.addProperty("idCatalogoMedioBonificacion", NUEVO_BANCARIA);
            JsonObject usuario = new JsonObject();
            usuario.addProperty("idUsuario", idUsuario);

            JsonObject json = new JsonObject();
            if (bancaria != null) {
                json.addProperty("idMediosBonificacion", bancaria.getIdMediosBonificacion());
            }
            json.addProperty("aliasMedioBonificacion", alias);
            json.addProperty("cuentaMedioBonificacion", cuenta);
            json.addProperty("idTipo", idTipo);
            json.addProperty("banco", banco);
            json.addProperty("companiaMedioBonificacion", tipoCuenta.equalsIgnoreCase("tarjeta") ? compania : "");
            json.add("catalogoMediosBonificacion", medioBonificacion);
            json.add("usuario", usuario);

            if (bancaria == null) {
                servicioGuardar(json);
            } else {
                servicioActualizar(json);
            }
        } else {
            binding.btnBancariaGuardar.setEnabled(true);
        }
    }

    private boolean validarDatos(String alias, String cuenta) {
        boolean errorTarjeta = false;
        int compania = getTipoCompania(cuenta);

        if(tipoCuenta.equalsIgnoreCase("tarjeta")) {
            errorTarjeta = cuenta.trim().isEmpty();
            if (compania == AMERICAN && cuenta.length() < 16) errorTarjeta = true;
            if ((compania == VISA || compania == MASTERCARD) && cuenta.length() < 15)
                errorTarjeta = true;
        } else if(tipoCuenta.equalsIgnoreCase("cuenta")) {
            errorTarjeta = cuenta.trim().isEmpty() || cuenta.length() < 10;
        } else {
            errorTarjeta = cuenta.trim().isEmpty() || cuenta.length() < 18 || cuenta.length() > 19;
        }

        if (errorTarjeta) {
            String error = tipoCuenta.equalsIgnoreCase("tarjeta") ? getString(R.string.nuevo_error_tarjeta) :
                    tipoCuenta.equalsIgnoreCase("cuenta") ? getString(R.string.nuevo_error_cuenta) : getString(R.string.nuevo_error_clabe);
            binding.etBancariaNumero.setError(error);
            binding.etBancariaNumero.requestFocus();
            return false;
        }

        if (alias.trim().isEmpty()) {
            binding.etBancariaNombreCorto.setError(getString(R.string.nuevo_error_alias));
            binding.etBancariaNombreCorto.requestFocus();
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
                    binding.btnBancariaGuardar.setEnabled(true);
                    if (result.getCode() == 200) {
                        noGuardadoListener.onEditar(false);
                        listener.onClickMostrarSnackbar(getString(R.string.nuevo_msg_agregar_tarjeta));
                    }
                }, throwable -> {
                    UtilsView.esconderProgress();
                    binding.btnBancariaGuardar.setEnabled(true);
                    Log.e(TAG, "Guardar tarjeta medio error: " + throwable.getLocalizedMessage());
                });
    }

    private void servicioActualizar(JsonObject json) {
        UtilsView.mostrarProgress(getActivity(), getString(R.string.general_msg_esperar));
        Disposable disposable = apiService.actualizarMedioBonificacion(json)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> {
                    UtilsView.esconderProgress();
                    binding.btnBancariaGuardar.setEnabled(true);
                    if (result.getCode() == 200) {
                        noGuardadoListener.onEditar(false);
                        listener.onClickMostrarSnackbar(getString(R.string.nuevo_msg_actualizar));
                    }
                }, throwable -> {
                    UtilsView.esconderProgress();
                    binding.btnBancariaGuardar.setEnabled(true);
                    Log.e(TAG, "Actualizar tarjeta medio error: " + throwable.getLocalizedMessage());
                });
    }

    private void servicioEliminar() {
        UtilsView.mostrarProgress(getActivity(), getString(R.string.general_msg_esperar));
        JsonObject json = new JsonObject();
        json.addProperty("idMediosBonificacion", bancaria.getIdMediosBonificacion());

        Disposable disposable = apiService.eliminarMedioBonificacion(json)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> {
                    UtilsView.esconderProgress();
                    if (result.getCode() == 200) {
                        noGuardadoListener.onEditar(false);
                        listener.onClickMostrarSnackbar(getString(R.string.nuevo_msg_eliminada_tarjeta));
                    }
                }, throwable -> {
                    UtilsView.esconderProgress();
                    Log.e(TAG, "Eliminar tarjeta medio error: " + throwable.getLocalizedMessage());
                });
    }

    private int getTipoCompania(String cuenta) {
        if (cuenta.startsWith("4")) {
            return VISA;
        }
        else if (cuenta.startsWith("51") || cuenta.startsWith("52") || cuenta.startsWith("53") || cuenta.startsWith("54") || cuenta.startsWith("55")) {
            return MASTERCARD;
        }
        else if (cuenta.startsWith("34") || cuenta.startsWith("37")) {
            return AMERICAN;
        }
        else {
            return 0;
        }
    }
}

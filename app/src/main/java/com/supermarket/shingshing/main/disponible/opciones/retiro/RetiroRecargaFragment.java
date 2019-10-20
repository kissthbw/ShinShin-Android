package com.supermarket.shingshing.main.disponible.opciones.retiro;

import android.content.Context;
import android.content.Intent;
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
import com.supermarket.shingshing.databinding.FragmentRetiroRecargaBinding;
import com.supermarket.shingshing.main.disponible.DisponibleFragment;
import com.supermarket.shingshing.main.disponible.RetiroListener;
import com.supermarket.shingshing.main.menu.opciones.CuentasFragment;
import com.supermarket.shingshing.models.MedioBonificacionModel;
import com.supermarket.shingshing.network.ApiClient;
import com.supermarket.shingshing.network.ApiService;
import com.supermarket.shingshing.util.UsuarioSingleton;
import com.supermarket.shingshing.util.UtilsView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class RetiroRecargaFragment extends Fragment {
    private static final String TAG = RetiroRecargaFragment.class.getSimpleName();
    private FragmentRetiroRecargaBinding binding;
    private RetiroListener listener;

    private ArrayList<MedioBonificacionModel> lista;
    private int idUsuario;

    public RetiroRecargaFragment() {}

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        listener = (RetiroListener) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            lista = getArguments().getParcelableArrayList(DisponibleFragment.LISTA_NUMERO);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_retiro_recarga, container, false);

        iniciarVistas();

        return binding.getRoot();
    }

    private void iniciarVistas() {
        idUsuario = UsuarioSingleton.getUsuario().getIdUsuario();

        ArrayAdapter<CharSequence> adapterBanco = ArrayAdapter.createFromResource(Objects.requireNonNull(getActivity()), R.array.options_recargas, R.layout.item_spinner);
        adapterBanco.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.sRetiroRecargaCantidad.setAdapter(adapterBanco);

        String[] tipos = new String[lista.size()];
        for (int x = 0; x < lista.size(); x++) {
            String alias = lista.get(x).getAliasMedioBonificacion();
            String numero = lista.get(x).getCuentaMedioBonificacion();
            tipos[x] = alias + " - " + numero;
        }

        ArrayAdapter<String> adapterTipo = new ArrayAdapter<>(Objects.requireNonNull(getActivity()), R.layout.item_spinner, tipos);
        adapterTipo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.sRetiroRecargaNumero.setAdapter(adapterTipo);

        binding.tvRetiroRecargaAgregar.setOnClickListener(v -> listener.agregarCuenta(CuentasFragment.NUEVO_NUMERO));
        binding.btnRetiroRecargaSolicitar.setOnClickListener(v -> obtenerDatos());
    }

    private void obtenerDatos() {
        binding.btnRetiroRecargaSolicitar.setEnabled(false);
        String cantidadTexto = binding.sRetiroRecargaCantidad.getSelectedItem().toString();
        int cantidad = Integer.parseInt(cantidadTexto.substring(1));
        int idMedioBonificacion = lista.get(binding.sRetiroRecargaNumero.getSelectedItemPosition()).getIdMediosBonificacion();
        String fecha = new SimpleDateFormat("yyyy-MM-dd", Locale.US).format(new Date());
        String hora = new SimpleDateFormat("HH:mm:ss", Locale.US).format(new Date());

        if (validarDatos()) {
            JsonObject medioBonificacion = new JsonObject();
            medioBonificacion.addProperty("idMediosBonificacion", idMedioBonificacion);
            JsonObject usuario = new JsonObject();
            usuario.addProperty("idUsuario", idUsuario);

            JsonObject json = new JsonObject();
            json.addProperty("fechaBonificacion", fecha);
            json.addProperty("horaBonificacion", fecha + "T" + hora);
            json.addProperty("cantidadBonificacion", cantidad);
            json.add("mediosBonificacion", medioBonificacion);
            json.add("usuario", usuario);

            servicioRetiro(json);
        } else {
            binding.btnRetiroRecargaSolicitar.setEnabled(true);
        }
    }

    private boolean validarDatos() {
        return true;
    }

    private void servicioRetiro(JsonObject json) {
        UtilsView.mostrarProgress(getContext(), getString(R.string.general_msg_esperar));
        ApiService apiService = ApiClient.getClient(getActivity()).create(ApiService.class);
        Disposable disposable = apiService.retiroMedioBonificacion(json)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> {
                    UtilsView.esconderProgress();
                    binding.btnRetiroRecargaSolicitar.setEnabled(false);
                    if (result.getCode() == 200) {
                        UsuarioSingleton.getUsuario().setBonificacion(result.getBonificacion());
                        Intent intent = new Intent(getActivity(), RetiroListoActivity.class);
                        intent.putExtra("recarga", true);
                        startActivity(intent);
                    }
                }, throwable -> {
                    UtilsView.esconderProgress();
                    binding.btnRetiroRecargaSolicitar.setEnabled(false);
                    Log.e(TAG, "Retiro recarga error: " + throwable.getLocalizedMessage());
                });
    }
}

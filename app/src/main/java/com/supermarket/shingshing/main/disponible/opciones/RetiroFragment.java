package com.supermarket.shingshing.main.disponible.opciones;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.JsonObject;
import com.supermarket.shingshing.R;
import com.supermarket.shingshing.databinding.FragmentRetiroBinding;
import com.supermarket.shingshing.main.disponible.DisponibleFragment;
import com.supermarket.shingshing.main.disponible.DisponibleListener;
import com.supermarket.shingshing.models.ItemMedioBonificacionModel;
import com.supermarket.shingshing.models.MedioBonificacionModel;
import com.supermarket.shingshing.network.ApiClient;
import com.supermarket.shingshing.network.ApiService;
import com.supermarket.shingshing.util.UsuarioSingleton;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class RetiroFragment extends Fragment {
    private static final String TAG = RetiroFragment.class.getSimpleName();
    private FragmentRetiroBinding binding;
    private DisponibleListener listener;

    private ArrayList<MedioBonificacionModel> bancariaList;
    private ArrayList<MedioBonificacionModel> paypalList;
    private ArrayList<MedioBonificacionModel> recargaList;
    private int idUsuario;

    public RetiroFragment() {}

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        listener = (DisponibleListener) getParentFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_retiro, container, false);

        iniciarVistas();
        getMediosBonificacion();

        return binding.getRoot();
    }

    private void iniciarVistas() {
        idUsuario = UsuarioSingleton.getUsuario().getIdUsuario();
        binding.tvRetiroCuentas.setOnClickListener(v -> listener.mostrarCuentas());
        binding.clRetiroBancaria.setOnClickListener(v -> listener.opcionRetiro(DisponibleFragment.RETIRO_BANCARIO, bancariaList));
        binding.clRetiroPaypal.setOnClickListener(v -> listener.opcionRetiro(DisponibleFragment.RETIRO_PAYPAL, paypalList));
        binding.clRetiroRecarga.setOnClickListener(v -> listener.opcionRetiro(DisponibleFragment.RECARGA_TELEFONICA, recargaList));
    }

    private void getMediosBonificacion() {
        JsonObject json = new JsonObject();
        json.addProperty("idUsuario", idUsuario);

        ApiService apiService = ApiClient.getClient(getActivity()).create(ApiService.class);
        Disposable disposable = apiService.getMediosBonificacion(json)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> {
                    for (ItemMedioBonificacionModel item : result.getMediosBonificacion()) {
                        switch (item.getNombreMedioBonificacion()) {
                            case "BANCARIA":
                                bancariaList = item.getList() != null ? item.getList() : new ArrayList<>();
                                break;
                            case "PAYPAL":
                                paypalList = item.getList() != null ? item.getList() : new ArrayList<>();
                                break;
                            case "RECARGA TELEFÓNICA":
                                recargaList = item.getList() != null ? item.getList() : new ArrayList<>();
                                break;
                        }
                    }
                }, throwable -> {
                    Log.e(TAG, "Error medios bonificacion: " + throwable.getLocalizedMessage());
                });
    }
}

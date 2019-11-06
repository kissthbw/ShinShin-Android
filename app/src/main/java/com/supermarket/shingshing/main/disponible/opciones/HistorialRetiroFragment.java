package com.supermarket.shingshing.main.disponible.opciones;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.JsonObject;
import com.supermarket.shingshing.R;
import com.supermarket.shingshing.databinding.FragmentHistorialRetiroBinding;
import com.supermarket.shingshing.main.disponible.opciones.adapters.HistorialRetiroAdapter;
import com.supermarket.shingshing.network.ApiClient;
import com.supermarket.shingshing.network.ApiService;
import com.supermarket.shingshing.util.UsuarioSingleton;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class HistorialRetiroFragment extends Fragment {
    private static final String TAG = HistorialRetiroFragment.class.getSimpleName();
    private FragmentHistorialRetiroBinding binding;

    public HistorialRetiroFragment() { }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_historial_retiro, container, false);

        getHistorialRetiros();

        return binding.getRoot();
    }

    private void getHistorialRetiros() {
        JsonObject json = new JsonObject();
        json.addProperty("idUsuario", UsuarioSingleton.getUsuario().getIdUsuario());

        ApiService apiService = ApiClient.getClient(getActivity()).create(ApiService.class);
        Disposable disposable = apiService.getHistorialRetiros(json)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> {
                    if (result.getCode() == 200 && result.getHistoricoMediosBonificaciones() != null && result.getHistoricoMediosBonificaciones().size() > 0) {
                        binding.clHistorialSinDatos.setVisibility(View.GONE);
                        binding.clHistorialConDatos.setVisibility(View.VISIBLE);
                        binding.rvHistorialProductos.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
                        binding.rvHistorialProductos.addItemDecoration(new DividerItemDecoration(binding.rvHistorialProductos.getContext(), DividerItemDecoration.VERTICAL));
                        binding.rvHistorialProductos.setAdapter(new HistorialRetiroAdapter(result.getHistoricoMediosBonificaciones()));
                    }
                }, throwable -> {
                    Log.e(TAG, "Error historial retiros: " + throwable.getLocalizedMessage());
                });
    }
}

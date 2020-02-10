package com.supermarket.shingshing.main.disponible.opciones;

import android.content.Context;
import android.content.Intent;
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
import com.supermarket.shingshing.databinding.FragmentHistorialTicketBinding;
import com.supermarket.shingshing.main.disponible.opciones.adapters.HistorialTicketAdapter;
import com.supermarket.shingshing.main.ocr.CameraActivity;
import com.supermarket.shingshing.network.ApiClient;
import com.supermarket.shingshing.network.ApiService;
import com.supermarket.shingshing.util.UsuarioSingleton;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class HistorialTicketFragment extends Fragment {
    private static final String TAG = HistorialTicketFragment.class.getSimpleName();
    private FragmentHistorialTicketBinding binding;
    private HistorialListener listener;

    public HistorialTicketFragment() { }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        listener = (HistorialListener) getParentFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_historial_ticket, container, false);

        getHistorialTickets();

        return binding.getRoot();
    }

    private void getHistorialTickets() {
        JsonObject json = new JsonObject();
        json.addProperty("idUsuario", UsuarioSingleton.getUsuario().getIdUsuario());

        ApiService apiService = ApiClient.getClient(getActivity()).create(ApiService.class);
        Disposable disposable = apiService.getHistorialTickets(json)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> {
                    if (result.getCode() == 200 && result.getTickets() != null && result.getTickets().size() > 0) {
                        binding.clTicketsSinDatos.setVisibility(View.GONE);
                        binding.clTicketsConDatos.setVisibility(View.VISIBLE);
                        binding.rvTicketsLista.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
                        binding.rvTicketsLista.addItemDecoration(new DividerItemDecoration(binding.rvTicketsLista.getContext(), DividerItemDecoration.VERTICAL));
                        binding.rvTicketsLista.setAdapter(new HistorialTicketAdapter(listener, result.getTickets()));
                    } else {
                        binding.btnTicketsSubir.setOnClickListener(v -> startActivity(new Intent(getActivity(), CameraActivity.class)));
                    }
                }, throwable -> {
                    Log.e(TAG, "Error historial tickets: " + throwable.getLocalizedMessage());
                });
    }
}

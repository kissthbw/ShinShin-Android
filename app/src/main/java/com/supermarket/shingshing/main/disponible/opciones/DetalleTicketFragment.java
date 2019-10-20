package com.supermarket.shingshing.main.disponible.opciones;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.supermarket.shingshing.R;
import com.supermarket.shingshing.databinding.FragmentDetalleTicketBinding;
import com.supermarket.shingshing.main.disponible.opciones.adapters.DetalleTicketAdapter;
import com.supermarket.shingshing.network.ApiClient;
import com.supermarket.shingshing.network.ApiService;
import com.supermarket.shingshing.util.UtilsView;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class DetalleTicketFragment extends Fragment {
    private static final String TAG = DetalleTicketFragment.class.getSimpleName();
    private FragmentDetalleTicketBinding binding;
    private int idTicket;

    public DetalleTicketFragment() {}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            idTicket = getArguments().getInt("idTicket");
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_detalle_ticket, container, false);

        getDetalleTicket();

        return binding.getRoot();
    }

    private void getDetalleTicket() {
        UtilsView.mostrarProgress(getContext(), getString(R.string.general_msg_esperar));
        ApiService apiService = ApiClient.getClient(getActivity()).create(ApiService.class);
        Disposable disposable = apiService.getDetalleTicket(idTicket)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> {
                    UtilsView.esconderProgress();
                    if (result.getCode() == 200 && result.getProductos() != null && result.getProductos().size() > 0) {
                        binding.rvDetalleTicketLista.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
                        binding.rvDetalleTicketLista.addItemDecoration(new DividerItemDecoration(binding.rvDetalleTicketLista.getContext(), DividerItemDecoration.VERTICAL));
                        binding.rvDetalleTicketLista.setAdapter(new DetalleTicketAdapter(result.getProductos()));
                    }
                }, throwable -> {
                    UtilsView.esconderProgress();
                    Log.e(TAG, "Error detalle ticket: " + throwable.getLocalizedMessage());
                });
    }
}

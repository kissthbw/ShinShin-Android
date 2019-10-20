package com.supermarket.shingshing.main.menu.opciones;

import android.content.Context;
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
import com.supermarket.shingshing.databinding.FragmentCuentasBinding;
import com.supermarket.shingshing.main.menu.opciones.adapters.BancariaAdapter;
import com.supermarket.shingshing.main.menu.opciones.adapters.PaypalAdapter;
import com.supermarket.shingshing.main.menu.opciones.adapters.RecargaAdapter;
import com.supermarket.shingshing.main.menu.opciones.agregar.NuevoListener;
import com.supermarket.shingshing.models.ItemMedioBonificacionModel;
import com.supermarket.shingshing.models.MedioBonificacionModel;
import com.supermarket.shingshing.network.ApiClient;
import com.supermarket.shingshing.network.ApiService;
import com.supermarket.shingshing.util.UsuarioSingleton;
import com.supermarket.shingshing.util.UtilsView;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class CuentasFragment extends Fragment {
    private static final String TAG = CuentasFragment.class.getSimpleName();
    public static final int NUEVO_BANCARIA = 1;
    public static final int NUEVO_CUENTA = 2;
    public static final int NUEVO_NUMERO = 3;

    public static final String KEY_BANCARIA = "bancaria";
    public static final String KEY_CUENTA = "cuenta";
    public static final String KEY_NUMERO = "numero";

    private FragmentCuentasBinding binding;
    private NuevoListener listener;

    private BancariaAdapter bancariaAdapter;
    private PaypalAdapter paypalAdapter;
    private RecargaAdapter recargaAdapter;

    private ArrayList<MedioBonificacionModel> bancariaList;
    private ArrayList<MedioBonificacionModel> paypalList;
    private ArrayList<MedioBonificacionModel> recargaList;
    private int idUsuario;

    public CuentasFragment() {}

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        listener = (NuevoListener) context;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_cuentas, container, false);

        iniciarVistas();
        getMediosBonificacion();

        return binding.getRoot();
    }

    private void iniciarVistas() {
        idUsuario = UsuarioSingleton.getUsuario().getIdUsuario();

        bancariaAdapter = new BancariaAdapter(listener);
        binding.rvCuentasBancaria.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        binding.rvCuentasBancaria.addItemDecoration(new DividerItemDecoration(binding.rvCuentasBancaria.getContext(), DividerItemDecoration.VERTICAL));
        binding.rvCuentasBancaria.setAdapter(bancariaAdapter);

        paypalAdapter = new PaypalAdapter(listener);
        binding.rvCuentasPaypal.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        binding.rvCuentasPaypal.addItemDecoration(new DividerItemDecoration(binding.rvCuentasPaypal.getContext(), DividerItemDecoration.VERTICAL));
        binding.rvCuentasPaypal.setAdapter(paypalAdapter);

        recargaAdapter = new RecargaAdapter(listener);
        binding.rvCuentasRecarga.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        binding.rvCuentasRecarga.addItemDecoration(new DividerItemDecoration(binding.rvCuentasRecarga.getContext(), DividerItemDecoration.VERTICAL));
        binding.rvCuentasRecarga.setAdapter(recargaAdapter);

        binding.tvCuentasBancaria.setOnClickListener(v -> listener.onClickNuevo(NUEVO_BANCARIA));
        binding.tvCuentasPaypal.setOnClickListener(v -> listener.onClickNuevo(NUEVO_CUENTA));
        binding.tvCuentasRecarga.setOnClickListener(v -> listener.onClickNuevo(NUEVO_NUMERO));
    }

    private void getMediosBonificacion() {
        UtilsView.mostrarProgress(getContext(), getString(R.string.general_msg_esperar));
        JsonObject json = new JsonObject();
        json.addProperty("idUsuario", idUsuario);

        ApiService apiService = ApiClient.getClient(getActivity()).create(ApiService.class);
        Disposable disposable = apiService.getMediosBonificacion(json)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> {
                    UtilsView.esconderProgress();
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

                    setListasAdapters();
                }, throwable -> {
                    UtilsView.esconderProgress();
                    Log.e(TAG, "Error medios bonificacion: " + throwable.getLocalizedMessage());
                });
    }

    private void setListasAdapters() {
        bancariaAdapter.setDatos(bancariaList);
        paypalAdapter.setDatos(paypalList);
        recargaAdapter.setDatos(recargaList);
    }
}

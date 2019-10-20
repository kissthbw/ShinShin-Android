package com.supermarket.shingshing.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.JsonObject;
import com.supermarket.shingshing.R;
import com.supermarket.shingshing.databinding.FragmentMainBinding;
import com.supermarket.shingshing.main.ocr.CameraActivity;
import com.supermarket.shingshing.main.principal.DepartamentoAdapter;
import com.supermarket.shingshing.main.principal.OfertaAdapter;
import com.supermarket.shingshing.main.principal.PopularAdapter;
import com.supermarket.shingshing.main.principal.listener.PopularListener;
import com.supermarket.shingshing.models.ProductoModel;
import com.supermarket.shingshing.network.ApiClient;
import com.supermarket.shingshing.network.ApiService;
import com.supermarket.shingshing.util.UsuarioSingleton;
import com.supermarket.shingshing.util.UtilsView;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainFragment extends Fragment implements PopularListener {
    private static final String TAG = MainFragment.class.getSimpleName();
    private FragmentMainBinding binding;
    private MainListener listener;
    private ApiService apiService;

    private OfertaAdapter ofertaAdapter;
    private PopularAdapter popularAdapter;
    private DepartamentoAdapter departamentoAdapter;

    private ArrayList<ProductoModel> productoModels;

    public MainFragment() {}

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        listener = (MainListener) context;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false);

        iniciarVistas();
        obtenerDatos();

        return binding.getRoot();
    }

    private void iniciarVistas() {
        binding.rvMainOferta.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        ofertaAdapter = new OfertaAdapter(this);
        binding.rvMainOferta.setAdapter(ofertaAdapter);

        binding.rvMainPopular.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        popularAdapter = new PopularAdapter(this);
        binding.rvMainPopular.setAdapter(popularAdapter);

        binding.rvMainDepto.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        departamentoAdapter = new DepartamentoAdapter();
        binding.rvMainDepto.setAdapter(departamentoAdapter);

        binding.btnMainEnviar.setOnClickListener(v -> obtenerDatoSugerencia());

        binding.ivMainHeaderSalir.setOnClickListener(v -> {
            binding.clMainHeader.setVisibility(View.GONE);
            binding.clMainContainer.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.blanco));
        });
        binding.tvMainVerTodos.setOnClickListener(v -> listener.onClickPopulares(productoModels));
        binding.tvMainHeaderFoto.setOnClickListener(v -> lanzarCamaraTicket());
        binding.ivMainCamara.setOnClickListener(v -> lanzarCamaraTicket());
    }

    private void obtenerDatos() {
        apiService = ApiClient.getClient(getActivity()).create(ApiService.class);
        obtenerBanner();
        obtenerPopulares();
        //obtenerDepartamentos(apiService);
        //obtenerTiendas(apiService);
    }

    private void obtenerBanner() {
        Disposable disposable = apiService.getProductosBanner()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> {
                    if (result.getCode() == 200 && result.getMessage().equalsIgnoreCase("Exitoso")) {
                        ofertaAdapter.setProductoModels(result.getProductos());
                        ofertaAdapter.notifyDataSetChanged();
                    } else {
                        Log.e(TAG, "error al descargar productos banner");
                    }
                }, throwable -> {
                    Log.e(TAG, "error productos banner: " + throwable.getLocalizedMessage());
                });
    }

    private void obtenerPopulares() {
        Disposable disposable = apiService.getProductosPopulares()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> {
                    if (result.getCode() == 200 && result.getMessage().equalsIgnoreCase("Exitoso")) {
                        productoModels = result.getProductos();
                        popularAdapter.setProductoModels(result.getProductos());
                        popularAdapter.notifyDataSetChanged();
                    } else {
                        Log.e(TAG, "error al descargar productos populares");
                    }
                }, throwable -> {
                    Log.e(TAG, "error productos populares: " + throwable.getLocalizedMessage());
                });
    }

    private void obtenerDepartamentos(ApiService apiService) {
        Disposable disposable = apiService.getTipoProductos()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> {
                    if (result.getCode() == 200 && result.getMessage().equalsIgnoreCase("Exitoso")) {
                        departamentoAdapter.setTipoProductoModels(result.getTipoProductos());
                        departamentoAdapter.notifyDataSetChanged();
                    } else {
                        Log.e(TAG, "error al descargar departamentos");
                    }
                }, throwable -> {
                    Log.e(TAG, "error departamentos: " + throwable.getLocalizedMessage());
                });
    }

    @Override
    public void clickPopularProducto(ProductoModel producto) {
        listener.onClickPopularProducto(producto);
    }

    private void lanzarCamaraTicket() {
        startActivity(new Intent(getActivity(), CameraActivity.class));
    }

    private void obtenerDatoSugerencia() {
        binding.btnMainEnviar.setEnabled(false);
        String sugerencia = binding.etMainProducto.getText().toString();

        if (validarDatos(sugerencia)) {
            JsonObject json = new JsonObject();
            json.addProperty("nombreProducto", sugerencia);
            json.addProperty("idUsuario", UsuarioSingleton.getUsuario().getIdUsuario());

            ejecutarServicioSugerencia(json);
        } else {
            binding.btnMainEnviar.setEnabled(true);
        }
    }

    private boolean validarDatos(String sugerencia) {
        if (sugerencia.trim().isEmpty()) {
            binding.etMainProducto.setError(getString(R.string.main_error_producto));
            binding.etMainProducto.requestFocus();
            return false;
        }

        return true;
    }

    private void ejecutarServicioSugerencia(JsonObject json) {
        UtilsView.mostrarProgress(getContext(), getString(R.string.general_msg_esperar));
        Disposable disposable = apiService.registrarSugerencia(json)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> {
                    UtilsView.esconderProgress();
                    binding.btnMainEnviar.setEnabled(true);
                    if (result.getCode() == 200 ) {
                        binding.etMainProducto.setText("");
                        listener.onMostrarSnackbar(getString(R.string.main_msg_producto_enviado));
                    } else {
                        Log.e(TAG, "Error al registrar sugerencia");
                    }
                }, throwable -> {
                    UtilsView.esconderProgress();
                    binding.btnMainEnviar.setEnabled(true);
                    Log.e(TAG, "Error sugerencia: " + throwable.getLocalizedMessage());
                });
    }
}

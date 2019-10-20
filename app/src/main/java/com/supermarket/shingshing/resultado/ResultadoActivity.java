package com.supermarket.shingshing.resultado;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.supermarket.shingshing.R;
import com.supermarket.shingshing.databinding.ActivityResultadoBinding;
import com.supermarket.shingshing.main.ocr.TicketErrorActivity;
import com.supermarket.shingshing.models.ProductoModel;
import com.supermarket.shingshing.models.ResponseAnalizarTicket;
import com.supermarket.shingshing.network.ApiClient;
import com.supermarket.shingshing.network.ApiService;
import com.supermarket.shingshing.util.UsuarioSingleton;
import com.supermarket.shingshing.util.UtilsView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ResultadoActivity extends AppCompatActivity {
    private ActivityResultadoBinding binding;
    private ResponseAnalizarTicket response;
    private String codigoBarras;
    private int idUsuario;
    private int total;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_resultado);

        if (getIntent() != null && getIntent().getExtras() != null) {
            response = getIntent().getParcelableExtra("ticket");
            if (getIntent().getStringExtra("codigoBarras") != null) {
                codigoBarras = getIntent().getStringExtra("codigoBarras");
                Log.e("CODIGO", codigoBarras);
            }
        }

        iniciarVistas();
    }

    private void iniciarVistas() {
        idUsuario = UsuarioSingleton.getUsuario().getIdUsuario();

        binding.rvResultadoProductos.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        binding.rvResultadoProductos.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        binding.rvResultadoProductos.setAdapter(new ResultadoAdapter(response.getProductos()));

        binding.tvResultadoTomarNuevo.setOnClickListener(v -> finish());

        total = getTotal();
        binding.tvResultadoTotal.setText(String.format(Locale.US, "$%d", total));

        binding.btnResultadoEnviar.setOnClickListener(v -> registrarTicket());
    }

    private int getTotal() {
        int total = 0;
        for (ProductoModel producto : response.getProductos()) {
            total += producto.getCantidadBonificacion();
        }
        return total;
    }

    private void registrarTicket() {
        UtilsView.mostrarProgress(this, getString(R.string.general_msg_esperar));
        binding.btnResultadoEnviar.setEnabled(false);
        JsonObject jsonObject = getJsonRequest();
        ApiService apiService = ApiClient.getClient(this).create(ApiService.class);
        Disposable disposable = apiService.registrarTicket(jsonObject)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> {
                    UtilsView.esconderProgress();
                    binding.btnResultadoEnviar.setEnabled(true);
                    if (result.getCode() == 200) {
                        UsuarioSingleton.getUsuario().setBonificacion(result.getBonificacion());
                        Intent intent = new Intent(this, ResultadoListoActivity.class);
                        intent.putExtra("bonificacion", result.getBonificacion());
                        startActivity(intent);
                    } else {
                        verVistaError();
                    }
                }, throwable -> {
                    UtilsView.esconderProgress();
                    Log.e("RESULT registrar ticket", "error: " + throwable.getLocalizedMessage());
                    binding.btnResultadoEnviar.setEnabled(true);
                    verVistaError();
                });
    }

    private JsonObject getJsonRequest() {
        String nombreTienda = (response.getTienda() != null && !response.getTienda().trim().isEmpty()) ? response.getTienda() : "";
        String sucursal = (response.getSubTienda() != null && !response.getSubTienda().trim().isEmpty()) ? response.getSubTienda() : "";
        String fechaAhora = new SimpleDateFormat("yyyy-MM-dd", Locale.US).format(new Date());
        String transaccion = (response.getTransaccion() != null && !response.getTransaccion().trim().isEmpty()) ? response.getTransaccion() : "";
        String fecha = (response.getFecha() != null && !response.getFecha().trim().isEmpty()) ? response.getFecha() : "";
        String hora = (response.getHora() != null && !response.getHora().trim().isEmpty()) ? response.getHora() : "";

        JsonObject tickets = new JsonObject();
        tickets.add("idTicket", null);
        tickets.addProperty("nombreTienda", nombreTienda);
        tickets.addProperty("sucursal", sucursal);
        tickets.addProperty("fecha", fechaAhora);
        tickets.addProperty("hora", "");
        tickets.addProperty("subtotal", total);
        tickets.addProperty("iva", 0.0);
        tickets.addProperty("total", total);
        tickets.addProperty("ticket_tienda", nombreTienda);
        tickets.addProperty("ticket_subTienda", sucursal);
        tickets.addProperty("ticket_transaccion", transaccion);
        tickets.addProperty("ticket_fecha", fecha);
        tickets.addProperty("ticket_hora", hora);
        /*if (fecha != null && hora != null && !fecha.trim().isEmpty() && !hora.trim().isEmpty()) {
            tickets.addProperty("hora", String.format("%sT%s", fecha, hora));
        }*/

        JsonArray array = new JsonArray();
        for (ProductoModel producto : response.getProductos()) {
            JsonObject object = new JsonObject();
            object.addProperty("idProducto", producto.getIdProducto());
            array.add(object);
        }

        tickets.add("productos", array);
        JsonArray jsonArray = new JsonArray();
        jsonArray.add(tickets);

        JsonObject request = new JsonObject();
        request.addProperty("idUsuario", idUsuario);
        request.add("tickets", jsonArray);

        return request;
    }

    private void verVistaError() {
        startActivity(new Intent(this, TicketErrorActivity.class));
    }
}

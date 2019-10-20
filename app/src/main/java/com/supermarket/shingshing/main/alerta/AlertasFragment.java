package com.supermarket.shingshing.main.alerta;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.supermarket.shingshing.R;
import com.supermarket.shingshing.databinding.FragmentAlertasBinding;
import com.supermarket.shingshing.models.AlertaModel;
import com.supermarket.shingshing.models.NotificacionModel;

import java.util.ArrayList;

public class AlertasFragment extends Fragment {
    private FragmentAlertasBinding binding;

    public AlertasFragment() {}

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_alertas, container, false);
        iniciarVistas();
        return binding.getRoot();
    }

    private void iniciarVistas() {
        ArrayList<NotificacionModel> notificaciones = new ArrayList<>();
        notificaciones.add(new NotificacionModel("Hoy"));

        notificaciones.add(new NotificacionModel(new AlertaModel("Llego agua Bonafont", AlertaAdapter.TYPE_PRODUCTO)));
        notificaciones.add(new NotificacionModel(new AlertaModel("Se agregaron $25 a tu cuenta, ¡Sigue asi!", AlertaAdapter.TYPE_AGREGAR)));
        notificaciones.add(new NotificacionModel(new AlertaModel("Solicitaste un retiro por $500.00, pronto se vera reflejado en tu cuenta **6753", AlertaAdapter.TYPE_RETIRO)));

        notificaciones.add(new NotificacionModel("Ayer"));
        notificaciones.add(new NotificacionModel(new AlertaModel("Llego barrilito", AlertaAdapter.TYPE_PRODUCTO)));
        notificaciones.add(new NotificacionModel(new AlertaModel("Solicitaste un retiro por $300.00, pronto se vera reflejado en tu cuenta **1234", AlertaAdapter.TYPE_RETIRO)));

        binding.rvAlertas.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        binding.rvAlertas.setAdapter(new AlertaAdapter(notificaciones));
    }
}

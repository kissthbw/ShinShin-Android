package com.supermarket.shingshing.main.menu.opciones;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.supermarket.shingshing.R;
import com.supermarket.shingshing.databinding.FragmentAyudaBinding;
import com.supermarket.shingshing.main.menu.opciones.adapters.AyudaAdapter;
import com.supermarket.shingshing.main.menu.opciones.tour.AyudaTourActivity;
import com.supermarket.shingshing.models.AyudaModel;

import java.util.ArrayList;

public class AyudaFragment extends Fragment {
    private FragmentAyudaBinding binding;

    public AyudaFragment() {}

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_ayuda, container, false);

        iniciarVistas();

        return binding.getRoot();
    }

    private void iniciarVistas() {
        binding.clAyudaTour.setOnClickListener(v -> startActivity(new Intent(getContext(), AyudaTourActivity.class)));

        ArrayList<AyudaModel> lista = new ArrayList<>();
        ArrayList<String> subtitulos = new ArrayList<>();

        subtitulos.add("Es la app donde te damos $ por comprar tus productos favoritos en las principales tiendas de autoservicio,\n\n" +
                "Así que, no lo olvides, guarda tu Ticket y tómale una foto desde la app para comenzar a ganar $ por tus compras.\n\n" +
                "¡Has Shing Shing!");
        lista.add(new AyudaModel("¿Qué es Shing Shing?", subtitulos));

        subtitulos = new ArrayList<>();
        subtitulos.add("Respuesta 2");
        lista.add(new AyudaModel("¿Qué productos participan?", subtitulos));

        subtitulos = new ArrayList<>();
        subtitulos.add("Respuesta 3");
        lista.add(new AyudaModel("¿Qué Tickets puedo subir?", subtitulos));

        AyudaAdapter adapter = new AyudaAdapter(getContext(), lista);
        binding.elvAyudaPreguntas.setAdapter(adapter);
    }
}

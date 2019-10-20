package com.supermarket.shingshing.main.disponible;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.supermarket.shingshing.R;
import com.supermarket.shingshing.databinding.FragmentDisponibleBinding;
import com.supermarket.shingshing.main.disponible.opciones.HistorialRetiroFragment;
import com.supermarket.shingshing.main.disponible.opciones.HistorialListener;
import com.supermarket.shingshing.main.disponible.opciones.RetiroFragment;
import com.supermarket.shingshing.main.disponible.opciones.HistorialTicketFragment;
import com.supermarket.shingshing.main.menu.MenuFragment;
import com.supermarket.shingshing.models.MedioBonificacionModel;
import com.supermarket.shingshing.util.UsuarioSingleton;

import java.util.ArrayList;
import java.util.Locale;

public class DisponibleFragment extends Fragment implements DisponibleListener, HistorialListener {
    public static final int RETIRO_BANCARIO = 1;
    public static final int RETIRO_PAYPAL = 2;
    public static final int RECARGA_TELEFONICA = 3;
    public static final String LISTA_BANCARIA = "lista_bancaria";
    public static final String LISTA_PAYPAL = "lista_paypal";
    public static final String LISTA_NUMERO = "lista_numero";
    private FragmentDisponibleBinding binding;
    private RetiroListener listener;
    private int opcion;

    public DisponibleFragment() {}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            opcion = getArguments().getInt(MenuFragment.KEY_OPCION_DISPONIBLE, 0);
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        listener = (RetiroListener) context;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_disponible, container, false);
        iniciarVistas();
        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        listener.actualizarHeader(false);
    }

    @Override
    public void onPause() {
        listener.actualizarHeader(true);
        super.onPause();
    }

    private void iniciarVistas() {
        switch (opcion) {
            case MenuFragment.OPCION_RETIRO:
                seleccionarOpcion(LISTA_BANCARIA);
                cargarFragment(new RetiroFragment());
                break;
            case MenuFragment.OPCION_TICKETS:
                seleccionarOpcion(LISTA_PAYPAL);
                cargarFragment(new HistorialTicketFragment());
                break;
            case MenuFragment.OPCION_HISTORIAL:
                seleccionarOpcion(LISTA_NUMERO);
                cargarFragment(new HistorialRetiroFragment());
                break;
        }

        binding.tvDisponibleSaldo.setText(String.format(Locale.US, "$ %d", UsuarioSingleton.getUsuario().getBonificacion()));

        binding.llDisponibleRetiro.setOnClickListener(v -> {
            seleccionarOpcion(LISTA_BANCARIA);
            cargarFragment(new RetiroFragment());
        });
        binding.llDisponibleTickets.setOnClickListener(v -> {
            seleccionarOpcion(LISTA_PAYPAL);
            cargarFragment(new HistorialTicketFragment());
        });
        binding.llDisponibleHistorial.setOnClickListener(v -> {
            seleccionarOpcion(LISTA_NUMERO);
            cargarFragment(new HistorialRetiroFragment());
        });
    }

    private void seleccionarOpcion(String opcion) {
        binding.llDisponibleRetiro.setAlpha(1);
        binding.llDisponibleTickets.setAlpha(1);
        binding.llDisponibleHistorial.setAlpha(1);
        binding.ivDisponibleArrowRetiro.setVisibility(View.VISIBLE);
        binding.ivDisponibleArrowHistorialTicket.setVisibility(View.VISIBLE);
        binding.ivDisponibleArrowHistorialRetiro.setVisibility(View.VISIBLE);
        binding.ivDisponibleRetiro.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_moneda_white));
        binding.ivDisponibleTicket.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_ticket_white));
        binding.ivDisponibleAbonos.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_reloj_white));
        switch (opcion) {
            case LISTA_BANCARIA:
                binding.llDisponibleTickets.setAlpha(0.6f);
                binding.llDisponibleHistorial.setAlpha(0.6f);
                binding.ivDisponibleArrowHistorialTicket.setVisibility(View.INVISIBLE);
                binding.ivDisponibleArrowHistorialRetiro.setVisibility(View.INVISIBLE);
                binding.ivDisponibleRetiro.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_moneda_bold_white));
                break;
            case LISTA_PAYPAL:
                binding.llDisponibleRetiro.setAlpha(0.6f);
                binding.llDisponibleHistorial.setAlpha(0.6f);
                binding.ivDisponibleArrowRetiro.setVisibility(View.INVISIBLE);
                binding.ivDisponibleArrowHistorialRetiro.setVisibility(View.INVISIBLE);
                binding.ivDisponibleTicket.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_ticket_bold_white));
                break;
            case LISTA_NUMERO:
                binding.llDisponibleRetiro.setAlpha(0.6f);
                binding.llDisponibleTickets.setAlpha(0.6f);
                binding.ivDisponibleArrowRetiro.setVisibility(View.INVISIBLE);
                binding.ivDisponibleArrowHistorialTicket.setVisibility(View.INVISIBLE);
                binding.ivDisponibleAbonos.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_reloj_bold_white));
                break;
        }
    }

    private void cargarFragment(Fragment fragment) {
        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment fragmentAux = fragmentManager.findFragmentByTag(fragment.getClass().getSimpleName());
        if (fragmentAux == null) {
            fragmentAux = fragment;
        }
        fragmentTransaction.replace(binding.flDisponibleFragment.getId(), fragmentAux, fragment.getClass().getSimpleName());
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.commit();
    }

    @Override
    public void mostrarCuentas() {
        listener.mostrarCuentas();
    }

    @Override
    public void opcionRetiro(int opcion, ArrayList<MedioBonificacionModel> listaMedio) {
        listener.opcionRetiro(opcion, listaMedio);
    }

    @Override
    public void onClickTicket(int id) {
        listener.mostrarDetalleTicket(id);
    }
}

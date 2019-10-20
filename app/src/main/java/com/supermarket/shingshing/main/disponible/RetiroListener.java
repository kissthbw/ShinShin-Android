package com.supermarket.shingshing.main.disponible;

import com.supermarket.shingshing.models.MedioBonificacionModel;

import java.util.ArrayList;

public interface RetiroListener {
    void agregarCuenta(int tipo);
    void mostrarCuentas();
    void opcionRetiro(int opcion, ArrayList<MedioBonificacionModel> listaMedio);
    void mostrarDetalleTicket(int id);
    void actualizarHeader(boolean visible);
}

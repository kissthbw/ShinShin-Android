package com.supermarket.shingshing.main.disponible;

import com.supermarket.shingshing.models.MedioBonificacionModel;

import java.util.ArrayList;

public interface DisponibleListener {
    void mostrarCuentas();
    void opcionRetiro(int opcion, ArrayList<MedioBonificacionModel> listaMedio);
}

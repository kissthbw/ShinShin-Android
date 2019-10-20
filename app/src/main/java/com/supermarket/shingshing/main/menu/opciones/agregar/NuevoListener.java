package com.supermarket.shingshing.main.menu.opciones.agregar;

import com.supermarket.shingshing.models.MedioBonificacionModel;

public interface NuevoListener {
    void onClickNuevo(int opcion);
    void onClickItemMedio(int opcion, MedioBonificacionModel item);
    void onClickMostrarSnackbar(String mensaje);
}

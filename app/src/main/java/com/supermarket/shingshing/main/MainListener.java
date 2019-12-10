package com.supermarket.shingshing.main;

import com.supermarket.shingshing.models.ProductoModel;

import java.util.ArrayList;

public interface MainListener {
    void onClickPopulares(ArrayList<ProductoModel> productoModels);
    void onClickPopularProducto(ProductoModel producto);
    void onClickTicketResult();
    void onMostrarSnackbar(String mensaje);
    void onHeaderWhite();
}

package com.supermarket.shingshing.network;

import com.google.gson.JsonObject;
import com.supermarket.shingshing.models.CatalogoTiendaModel;
import com.supermarket.shingshing.models.CatalogoTipoProductoModel;
import com.supermarket.shingshing.models.ProductosBannerModel;
import com.supermarket.shingshing.models.ResponseActualizarModel;
import com.supermarket.shingshing.models.ResponseAnalizarTicket;
import com.supermarket.shingshing.models.ResponseDetalleTicketModel;
import com.supermarket.shingshing.models.ResponseHistorialRetiroModel;
import com.supermarket.shingshing.models.ResponseHistorialTicketModel;
import com.supermarket.shingshing.models.ResponseModel;
import com.supermarket.shingshing.models.ResponseLoginModel;
import com.supermarket.shingshing.models.ResponseMediosBonificacionModel;
import com.supermarket.shingshing.models.ResponseRegistrarTicket;
import com.supermarket.shingshing.models.ResponseRetiroModel;
import com.supermarket.shingshing.models.ResponseTipoBancaria;

import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService {
    @Headers({"Content-type:application/json"})
    @POST("/usuarios/usuario/login2")
    Single<ResponseLoginModel> login(@Body JsonObject json);

    @Headers({"Content-type:application/json"})
    @POST("/usuarios/usuario/login2")
    Single<ResponseLoginModel> loginInBack(@Body JsonObject json);

    @Headers({"Content-type:application/json"})
    @POST("/usuarios/usuario/registrar")
    Single<ResponseModel> registrarCuenta(@Body JsonObject json);

    @Headers({"Content-type:application/json"})
    @POST("/usuarios/usuario/eliminar")
    Single<ResponseModel> eliminarCuenta(@Body JsonObject json);

    @Headers({"Content-type:application/json"})
    @POST("/usuarios/usuario/activar")
    Single<ResponseModel> validarCuenta(@Body JsonObject json);

    @Headers({"Content-type:application/json"})
    @POST("/usuarios/usuario/reenviar")
    Single<ResponseModel> reenviar(@Body JsonObject json);

    @Headers({"Content-type:application/json"})
    @POST("/usuarios/solicitarRestaurarPassword")
    Single<ResponseModel> recuperarContrasena(@Body JsonObject json);

    @Headers({"Content-type:application/json"})
    @POST("/usuarios/usuario/actualizar")
    Single<ResponseActualizarModel> actualizarPerfil(@Body JsonObject json);

    @Headers({"Content-type:application/json"})
    @POST("/usuarios/social/registrar")
    Single<ResponseLoginModel> registrarSocial(@Body JsonObject json);

    @Headers({"Content-type:application/json"})
    @GET("/productos/listBanner")
    Single<ProductosBannerModel> getProductosBanner();

    @Headers({"Content-type:application/json"})
    @GET("/productos/list")
    Single<ProductosBannerModel> getProductosPopulares();

    @Headers({"Content-type:application/json"})
    @GET("/catalogoTipoProductos/list")
    Single<CatalogoTipoProductoModel> getTipoProductos();

    @Headers({"Content-type:application/json"})
    @GET("catalogoTiendas/list")
    Single<CatalogoTiendaModel> getTiendas();

    @Headers({"Content-type:application/json"})
    @GET("/catalogoMediosBonificacion/listTiposBancarias")
    Single<ResponseTipoBancaria> getTipoBancaria();

    @Headers({"Content-type:application/json"})
    @POST("/mediosBonificacion/mediosBonificacion/guardar")
    Single<ResponseModel> guardarMedioBonificacion(@Body JsonObject json);

    @Headers({"Content-type:application/json"})
    @POST("/mediosBonificacion/mediosBonificacion/actualizar")
    Single<ResponseModel> actualizarMedioBonificacion(@Body JsonObject json);

    @Headers({"Content-type:application/json"})
    @POST("/mediosBonificacion/mediosBonificacion/eliminar")
    Single<ResponseModel> eliminarMedioBonificacion(@Body JsonObject json);

    @Headers({"Content-type:application/json"})
    @POST("/usuarios/usuario/mediosBonificacion")
    Single<ResponseMediosBonificacionModel> getMediosBonificacion(@Body JsonObject json);

    @Headers({"Content-type:application/json"})
    @POST("/tickets/analizar2")
    Single<ResponseAnalizarTicket> getAnalizarTicket(@Body JsonObject json);

    @Headers({"Content-type:application/json"})
    @POST("/usuarios/usuario/registrar/ticket")
    Single<ResponseRegistrarTicket> registrarTicket(@Body JsonObject json);

    @Headers({"Content-type:application/json"})
    @POST("/historicoMediosBonificacion/historicoMediosBonificacion/registrar")
    Single<ResponseRetiroModel> retiroMedioBonificacion(@Body JsonObject json);

    @Headers({"Content-type:application/json"})
    @POST("/usuarios/usuario/historicoTickets")
    Single<ResponseHistorialTicketModel> getHistorialTickets(@Body JsonObject json);

    @GET("/tickets/detalle/{ticket}")
    Single<ResponseDetalleTicketModel> getDetalleTicket(@Path("ticket") int ticket);

    @Headers({"Content-type:application/json"})
    @POST("/usuarios/usuario/historicoBonificaciones")
    Single<ResponseHistorialRetiroModel> getHistorialRetiros(@Body JsonObject json);

    @Headers({"Content-type:application/json"})
    @POST("/productos/sugerencias")
    Single<ResponseModel> registrarSugerencia(@Body JsonObject json);

    @Headers({"Content-type:application/json"})
    @POST("/usuarios/contacto")
    Single<ResponseModel> registrarContacto(@Body JsonObject json);
}

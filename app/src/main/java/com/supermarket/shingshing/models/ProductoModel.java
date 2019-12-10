package com.supermarket.shingshing.models;

import android.os.Parcel;
import android.os.Parcelable;

public class ProductoModel implements Parcelable {
    private int idProducto;
    private String nombreProducto;
    private float precio;
    private String codigoBarras;
    private String presentacion;
    private String contenido;
    private String descripcion;
    private boolean aplicaPromocion;
    private String vigenciaPromocion;
    private String urlImagenProducto;
    private int cantidadBonificacion;
    private boolean banner;
    private String colorBanner;
    private CatalogoMarcaModel catalogoMarca;
    private TipoProductoModel catalogoTipoProducto;
    private TiendaModel catalogoTienda;
    private String imgUrl;

    public ProductoModel() {}

    public int getIdProducto() {
        return idProducto;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public float getPrecio() {
        return precio;
    }

    public String getCodigoBarras() {
        return codigoBarras;
    }

    public String getPresentacion() {
        return presentacion;
    }

    public String getContenido() {
        return contenido;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public boolean isAplicaPromocion() {
        return aplicaPromocion;
    }

    public String getVigenciaPromocion() {
        return vigenciaPromocion;
    }

    public String getUrlImagenProducto() {
        return urlImagenProducto;
    }

    public int getCantidadBonificacion() {
        return cantidadBonificacion;
    }

    public boolean isBanner() {
        return banner;
    }

    public String getColorBanner() {
        return colorBanner;
    }

    public CatalogoMarcaModel getCatalogoMarca() {
        return catalogoMarca;
    }

    public TipoProductoModel getCatalogoTipoProducto() {
        return catalogoTipoProducto;
    }

    public TiendaModel getCatalogoTienda() {
        return catalogoTienda;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    protected ProductoModel(Parcel in) {
        idProducto = in.readInt();
        nombreProducto = in.readString();
        precio = in.readFloat();
        codigoBarras = in.readString();
        presentacion = in.readString();
        contenido = in.readString();
        descripcion = in.readString();
        aplicaPromocion = in.readByte() != 0;
        vigenciaPromocion = in.readString();
        urlImagenProducto = in.readString();
        cantidadBonificacion = in.readInt();
        banner = in.readByte() != 0;
        colorBanner = in.readString();
        catalogoMarca = in.readParcelable(CatalogoMarcaModel.class.getClassLoader());
        catalogoTipoProducto = in.readParcelable(TipoProductoModel.class.getClassLoader());
        catalogoTienda = in.readParcelable(TiendaModel.class.getClassLoader());
        imgUrl = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(idProducto);
        dest.writeString(nombreProducto);
        dest.writeFloat(precio);
        dest.writeString(codigoBarras);
        dest.writeString(presentacion);
        dest.writeString(contenido);
        dest.writeString(descripcion);
        dest.writeByte((byte) (aplicaPromocion ? 1 : 0));
        dest.writeString(vigenciaPromocion);
        dest.writeString(urlImagenProducto);
        dest.writeInt(cantidadBonificacion);
        dest.writeByte((byte) (banner ? 1 : 0));
        dest.writeString(colorBanner);
        dest.writeParcelable(catalogoMarca, flags);
        dest.writeParcelable(catalogoTipoProducto, flags);
        dest.writeParcelable(catalogoTienda, flags);
        dest.writeString(imgUrl);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ProductoModel> CREATOR = new Creator<ProductoModel>() {
        @Override
        public ProductoModel createFromParcel(Parcel in) {
            return new ProductoModel(in);
        }

        @Override
        public ProductoModel[] newArray(int size) {
            return new ProductoModel[size];
        }
    };
}

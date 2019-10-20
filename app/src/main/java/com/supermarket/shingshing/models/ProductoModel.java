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

    public ProductoModel() {}

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public String getCodigoBarras() {
        return codigoBarras;
    }

    public void setCodigoBarras(String codigoBarras) {
        this.codigoBarras = codigoBarras;
    }

    public String getPresentacion() {
        return presentacion;
    }

    public void setPresentacion(String presentacion) {
        this.presentacion = presentacion;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public boolean isAplicaPromocion() {
        return aplicaPromocion;
    }

    public void setAplicaPromocion(boolean aplicaPromocion) {
        this.aplicaPromocion = aplicaPromocion;
    }

    public String getVigenciaPromocion() {
        return vigenciaPromocion;
    }

    public void setVigenciaPromocion(String vigenciaPromocion) {
        this.vigenciaPromocion = vigenciaPromocion;
    }

    public String getUrlImagenProducto() {
        return urlImagenProducto;
    }

    public void setUrlImagenProducto(String urlImagenProducto) {
        this.urlImagenProducto = urlImagenProducto;
    }

    public int getCantidadBonificacion() {
        return cantidadBonificacion;
    }

    public void setCantidadBonificacion(int cantidadBonificacion) {
        this.cantidadBonificacion = cantidadBonificacion;
    }

    public boolean isBanner() {
        return banner;
    }

    public void setBanner(boolean banner) {
        this.banner = banner;
    }

    public String getColorBanner() {
        return colorBanner;
    }

    public void setColorBanner(String colorBanner) {
        this.colorBanner = colorBanner;
    }

    public CatalogoMarcaModel getCatalogoMarca() {
        return catalogoMarca;
    }

    public void setCatalogoMarca(CatalogoMarcaModel catalogoMarca) {
        this.catalogoMarca = catalogoMarca;
    }

    public TipoProductoModel getCatalogoTipoProducto() {
        return catalogoTipoProducto;
    }

    public void setCatalogoTipoProducto(TipoProductoModel catalogoTipoProducto) {
        this.catalogoTipoProducto = catalogoTipoProducto;
    }

    public TiendaModel getCatalogoTienda() {
        return catalogoTienda;
    }

    public void setCatalogoTienda(TiendaModel catalogoTienda) {
        this.catalogoTienda = catalogoTienda;
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

    @Override
    public int describeContents() {
        return 0;
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
    }

    @Override
    public String toString() {
        return "ProductoModel{" +
                "idProducto=" + idProducto +
                ", nombreProducto='" + nombreProducto + '\'' +
                ", precio=" + precio +
                ", codigoBarras='" + codigoBarras + '\'' +
                ", presentacion='" + presentacion + '\'' +
                ", contenido='" + contenido + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", aplicaPromocion=" + aplicaPromocion +
                ", vigenciaPromocion='" + vigenciaPromocion + '\'' +
                ", urlImagenProducto='" + urlImagenProducto + '\'' +
                ", cantidadBonificacion=" + cantidadBonificacion +
                ", banner=" + banner +
                ", colorBanner='" + colorBanner + '\'' +
                ", catalogoMarca=" + catalogoMarca +
                ", catalogoTipoProducto=" + catalogoTipoProducto +
                ", catalogoTienda=" + catalogoTienda +
                '}';
    }
}

package com.supermarket.shingshing.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;

public class UsuarioModel implements Parcelable, Serializable {
    private static final long serialVersionUID = 1L;
    private int idUsuario;
    private String nombre;
    private String apPaterno;
    private String apMaterno;
    private String fechaNac;
    private String fotoUsuario;
    private String telMovil;
    private String correoElectronico;
    private String usuario;
    private String contrasenia;
    private String calle;
    private String numeroExt;
    private String numeroInt;
    private String colonia;
    private String codigoPostal;
    private String delMun;
    private String estado;
    private boolean estatusActivacion;
    private String codigoVerificacion;
    private int idCatalogoSexo;
    private int idRedSocial;
    private int estatus;
    private int bonificacion;
    private String hash;
    private String imgUrl;
    private ArrayList productosFavoritos;
    private ArrayList tickets;

    public int getIdUsuario() {
        return idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApPaterno() {
        return apPaterno;
    }

    public String getApMaterno() {
        return apMaterno;
    }

    public String getFechaNac() {
        return fechaNac;
    }

    public String getFotoUsuario() {
        return fotoUsuario;
    }

    public String getTelMovil() {
        return telMovil;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public String getCalle() {
        return calle;
    }

    public String getNumeroExt() {
        return numeroExt;
    }

    public String getNumeroInt() {
        return numeroInt;
    }

    public String getColonia() {
        return colonia;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public String getDelMun() {
        return delMun;
    }

    public String getEstado() {
        return estado;
    }

    public boolean isEstatusActivacion() {
        return estatusActivacion;
    }

    public String getCodigoVerificacion() {
        return codigoVerificacion;
    }

    public int getIdCatalogoSexo() {
        return idCatalogoSexo;
    }

    public int getIdRedSocial() {
        return idRedSocial;
    }

    public void setIdRedSocial(int idRedSocial) {
        this.idRedSocial = idRedSocial;
    }

    public int getEstatus() {
        return estatus;
    }

    public int getBonificacion() {
        return bonificacion;
    }

    public void setBonificacion(int bonificacion) {
        this.bonificacion = bonificacion;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public String getHash() {
        return hash;
    }

    public ArrayList getProductosFavoritos() {
        return productosFavoritos;
    }

    public ArrayList getTickets() {
        return tickets;
    }

    protected UsuarioModel(Parcel in) {
        idUsuario = in.readInt();
        nombre = in.readString();
        apPaterno = in.readString();
        apMaterno = in.readString();
        fechaNac = in.readString();
        fotoUsuario = in.readString();
        telMovil = in.readString();
        correoElectronico = in.readString();
        usuario = in.readString();
        contrasenia = in.readString();
        calle = in.readString();
        numeroExt = in.readString();
        numeroInt = in.readString();
        colonia = in.readString();
        codigoPostal = in.readString();
        delMun = in.readString();
        estado = in.readString();
        estatusActivacion = in.readByte() != 0;
        codigoVerificacion = in.readString();
        idCatalogoSexo = in.readInt();
        idRedSocial = in.readInt();
        estatus = in.readInt();
        bonificacion = in.readInt();
        hash = in.readString();
        imgUrl = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(idUsuario);
        dest.writeString(nombre);
        dest.writeString(apPaterno);
        dest.writeString(apMaterno);
        dest.writeString(fechaNac);
        dest.writeString(fotoUsuario);
        dest.writeString(telMovil);
        dest.writeString(correoElectronico);
        dest.writeString(usuario);
        dest.writeString(contrasenia);
        dest.writeString(calle);
        dest.writeString(numeroExt);
        dest.writeString(numeroInt);
        dest.writeString(colonia);
        dest.writeString(codigoPostal);
        dest.writeString(delMun);
        dest.writeString(estado);
        dest.writeByte((byte) (estatusActivacion ? 1 : 0));
        dest.writeString(codigoVerificacion);
        dest.writeInt(idCatalogoSexo);
        dest.writeInt(idRedSocial);
        dest.writeInt(estatus);
        dest.writeInt(bonificacion);
        dest.writeString(hash);
        dest.writeString(imgUrl);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<UsuarioModel> CREATOR = new Creator<UsuarioModel>() {
        @Override
        public UsuarioModel createFromParcel(Parcel in) {
            return new UsuarioModel(in);
        }

        @Override
        public UsuarioModel[] newArray(int size) {
            return new UsuarioModel[size];
        }
    };

    @Override
    public String toString() {
        return "UsuarioModel{" +
                "idUsuario=" + idUsuario +
                ", nombre='" + nombre + '\'' +
                ", apPaterno='" + apPaterno + '\'' +
                ", apMaterno='" + apMaterno + '\'' +
                ", fechaNac='" + fechaNac + '\'' +
                ", fotoUsuario='" + fotoUsuario + '\'' +
                ", telMovil='" + telMovil + '\'' +
                ", correoElectronico='" + correoElectronico + '\'' +
                ", usuario='" + usuario + '\'' +
                ", contrasenia='" + contrasenia + '\'' +
                ", calle='" + calle + '\'' +
                ", numeroExt='" + numeroExt + '\'' +
                ", numeroInt='" + numeroInt + '\'' +
                ", colonia='" + colonia + '\'' +
                ", codigoPostal='" + codigoPostal + '\'' +
                ", delMun='" + delMun + '\'' +
                ", estado='" + estado + '\'' +
                ", estatusActivacion=" + estatusActivacion +
                ", codigoVerificacion='" + codigoVerificacion + '\'' +
                ", idCatalogoSexo=" + idCatalogoSexo +
                ", idRedSocial=" + idRedSocial +
                ", estatus=" + estatus +
                ", bonificacion=" + bonificacion +
                ", hash='" + hash + '\'' +
                ", imgUrl='" + imgUrl + '\'' +
                ", productosFavoritos=" + productosFavoritos +
                ", tickets=" + tickets +
                '}';
    }
}

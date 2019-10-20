package com.supermarket.shingshing.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class ResponseAnalizarTicket implements Parcelable {
    private String message;
    private int code;
    private int id;
    private String tienda;
    private String subTienda;
    private String fecha;
    private String hora;
    private String transaccion;
    private boolean tieneCB;
    private ArrayList<ProductoModel> productos;
    private ArrayList<String> lineas;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTienda() {
        return tienda;
    }

    public void setTienda(String tienda) {
        this.tienda = tienda;
    }

    public String getSubTienda() {
        return subTienda;
    }

    public void setSubTienda(String subTienda) {
        this.subTienda = subTienda;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getTransaccion() {
        return transaccion;
    }

    public void setTransaccion(String transaccion) {
        this.transaccion = transaccion;
    }

    public boolean getTieneCB() {
        return tieneCB;
    }

    public void setTieneCB(boolean tieneCB) {
        this.tieneCB = tieneCB;
    }

    public ArrayList<ProductoModel> getProductos() {
        return productos;
    }

    public void setProductos(ArrayList<ProductoModel> productos) {
        this.productos = productos;
    }

    public ArrayList<String> getLineas() {
        return lineas;
    }

    public void setLineas(ArrayList<String> lineas) {
        this.lineas = lineas;
    }

    protected ResponseAnalizarTicket(Parcel in) {
        message = in.readString();
        code = in.readInt();
        id = in.readInt();
        tienda = in.readString();
        subTienda = in.readString();
        fecha = in.readString();
        hora = in.readString();
        transaccion = in.readString();
        tieneCB = in.readByte() != 0;
        productos = in.createTypedArrayList(ProductoModel.CREATOR);
        lineas = in.createStringArrayList();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(message);
        dest.writeInt(code);
        dest.writeInt(id);
        dest.writeString(tienda);
        dest.writeString(subTienda);
        dest.writeString(fecha);
        dest.writeString(hora);
        dest.writeString(transaccion);
        dest.writeByte((byte) (tieneCB ? 1 : 0));
        dest.writeTypedList(productos);
        dest.writeStringList(lineas);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ResponseAnalizarTicket> CREATOR = new Creator<ResponseAnalizarTicket>() {
        @Override
        public ResponseAnalizarTicket createFromParcel(Parcel in) {
            return new ResponseAnalizarTicket(in);
        }

        @Override
        public ResponseAnalizarTicket[] newArray(int size) {
            return new ResponseAnalizarTicket[size];
        }
    };

    @Override
    public String toString() {
        return "ResponseAnalizarTicket{" +
                "message='" + message + '\'' +
                ", code=" + code +
                ", id=" + id +
                ", tienda='" + tienda + '\'' +
                ", subTienda='" + subTienda + '\'' +
                ", fecha='" + fecha + '\'' +
                ", hora='" + hora + '\'' +
                ", transaccion='" + transaccion + '\'' +
                ", tieneCB=" + tieneCB +
                ", productos=" + productos +
                ", lineas=" + lineas +
                '}';
    }
}

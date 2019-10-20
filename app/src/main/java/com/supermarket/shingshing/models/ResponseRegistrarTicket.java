package com.supermarket.shingshing.models;

public class ResponseRegistrarTicket {
    private String message;
    private int code;
    private int bonificacion;

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

    public int getBonificacion() {
        return bonificacion;
    }

    public void setBonificacion(int bonificacion) {
        this.bonificacion = bonificacion;
    }

    @Override
    public String toString() {
        return "ResponseRegistrarTicket{" +
                "message='" + message + '\'' +
                ", code=" + code +
                ", bonificacion=" + bonificacion +
                '}';
    }
}

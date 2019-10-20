package com.supermarket.shingshing.models;

public class TicketModel {
    private int idTicket;
    private String nombreTienda;
    private String sucursal;
    private String fecha;
    private String hora;
    private int subtotal;
    private int iva;
    private int total;
    private String ticket_tienda;
    private String ticket_subTienda;
    private String ticket_transaccion;
    private String ticket_fecha;
    private String ticket_hora;

    public int getIdTicket() {
        return idTicket;
    }

    public String getNombreTienda() {
        return nombreTienda;
    }

    public String getSucursal() {
        return sucursal;
    }

    public String getFecha() {
        return fecha;
    }

    public String getHora() {
        return hora;
    }

    public int getSubtotal() {
        return subtotal;
    }

    public int getIva() {
        return iva;
    }

    public int getTotal() {
        return total;
    }

    public String getTicket_tienda() {
        return ticket_tienda;
    }

    public String getTicket_subTienda() {
        return ticket_subTienda;
    }

    public String getTicket_transaccion() {
        return ticket_transaccion;
    }

    public String getTicket_fecha() {
        return ticket_fecha;
    }

    public String getTicket_hora() {
        return ticket_hora;
    }
}

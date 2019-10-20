package com.supermarket.shingshing.models;

import java.util.ArrayList;

public class ResponseHistorialTicketModel {
    private String message;
    private int code;
    private ArrayList<TicketModel> tickets;

    public String getMessage() {
        return message;
    }

    public int getCode() {
        return code;
    }

    public ArrayList<TicketModel> getTickets() {
        return tickets;
    }
}

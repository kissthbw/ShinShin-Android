package com.supermarket.shingshing.models;

public class ResponseModel {
    private int id;
    private int code;
    private String message;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "ResponseModel{" +
                "id=" + id +
                ", code=" + code +
                ", message='" + message + '\'' +
                '}';
    }
}

package com.supermarket.shingshing.models;

public class HistorialBonificacionModel {
    private int idHistoricoMediosBonificacion;
    private String fechaBonificacion;
    private int horaBonificacion;
    private int cantidadBonificacion;
    private MedioBonificacionModel mediosBonificacion;
    private UsuarioModel usuario;

    public int getIdHistoricoMediosBonificacion() {
        return idHistoricoMediosBonificacion;
    }

    public String getFechaBonificacion() {
        return fechaBonificacion;
    }

    public int getHoraBonificacion() {
        return horaBonificacion;
    }

    public int getCantidadBonificacion() {
        return cantidadBonificacion;
    }

    public MedioBonificacionModel getMediosBonificacion() {
        return mediosBonificacion;
    }

    public UsuarioModel getUsuario() {
        return usuario;
    }
}

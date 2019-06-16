package com.level6ninja.crashify.beans;

import java.sql.Date;

public class ReporteResumido {
    private Integer idReporte;
    private Date hora;
    private String numPlacas;
    private Integer estado;

    public ReporteResumido(Integer idReporte, Date hora, String numPlacas, Integer estado) {
        this.idReporte = idReporte;
        this.hora = hora;
        this.numPlacas = numPlacas;
        this.estado = estado;
    }

    public ReporteResumido() {
    }

    public Integer getIdReporte() {
        return idReporte;
    }

    public void setIdReporte(Integer idReporte) {
        this.idReporte = idReporte;
    }

    public Date getHora() {
        return hora;
    }

    public void setHora(Date hora) {
        this.hora = hora;
    }

    public String getNumPlacas() {
        return numPlacas;
    }

    public void setNumPlacas(String numPlacas) {
        this.numPlacas = numPlacas;
    }

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }
}

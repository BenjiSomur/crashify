package com.level6ninja.crashify.beans;

import java.sql.Date;

public class ReporteDictamen {
    private Integer idReporte;
    private String descripcion;
    private String numPlacas;
    private String hora;
    private Float latitud;
    private Float longitud;
    private String nombrePerito;
    private String dictamen;

    public ReporteDictamen(Integer idReporte, String descripcion, String numPlacas, String hora, Float latitud, Float longitud, String nombrePerito, String dictamen) {
        this.idReporte = idReporte;
        this.descripcion = descripcion;
        this.numPlacas = numPlacas;
        this.hora = hora;
        this.latitud = latitud;
        this.longitud = longitud;
        this.nombrePerito = nombrePerito;
        this.dictamen = dictamen;
    }

    public String getDictamen() {
        return dictamen;
    }

    public void setDictamen(String dictamen) {
        this.dictamen = dictamen;
    }

    public ReporteDictamen() {
    }

    public Integer getIdReporte() {
        return idReporte;
    }

    public void setIdReporte(Integer idReporte) {
        this.idReporte = idReporte;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getNumPlacas() {
        return numPlacas;
    }

    public void setNumPlacas(String numPlacas) {
        this.numPlacas = numPlacas;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public Float getLatitud() {
        return latitud;
    }

    public void setLatitud(Float latitud) {
        this.latitud = latitud;
    }

    public Float getLongitud() {
        return longitud;
    }

    public void setLongitud(Float longitud) {
        this.longitud = longitud;
    }

    public String getNombrePerito() {
        return nombrePerito;
    }

    public void setNombrePerito(String nombrePerito) {
        this.nombrePerito = nombrePerito;
    }

}

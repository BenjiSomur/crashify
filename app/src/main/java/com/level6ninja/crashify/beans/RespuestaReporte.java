package com.level6ninja.crashify.beans;

public class RespuestaReporte extends Respuesta{
    private Reporte reporte;

    public RespuestaReporte() {
        super();
    }

    public void setReporte(Reporte reporte) {
        this.reporte = reporte;
    }

    public Reporte getReporte() {
        return reporte;
    }
}

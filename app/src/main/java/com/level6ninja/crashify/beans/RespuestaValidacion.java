package com.level6ninja.crashify.beans;

public class RespuestaValidacion {

    private Respuesta error;
    private Conductor conductor;

    public RespuestaValidacion(){

    }

    /**
     * @return the error
     */
    public Respuesta getError() {
        return error;
    }

    /**
     * @param error the error to set
     */
    public void setError(Respuesta error) {
        this.error = error;
    }

    /**
     * @return the usuario
     */
    public Conductor getConductor() {
        return conductor;
    }

    /**
     * @param conductor the usuario to set
     */
    public void setConductor(Conductor conductor) {
        this.conductor = conductor;
    }

}

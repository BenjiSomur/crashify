package com.level6ninja.crashify.beans;

public class Reporte {

    private Integer idReporte;
    private Float latitud;
    private Float longitud;
    private Integer estado;
    private Integer idConductor;
    private String dictamen;
    private String descripcion;
    private String placasVehiculos;
    private Integer idSiniestro;
    private String hora;
    private Integer resultado;

    public Reporte() {
    }

    public Reporte(Float latitud, Float longitud, Integer estado) {
        this.latitud = latitud;
        this.longitud = longitud;
        this.estado = estado;
    }

    public Reporte(Integer idReporte, Float latitud, Float longitud, Integer estado) {
        this.idReporte = idReporte;
        this.latitud = latitud;
        this.longitud = longitud;
        this.estado = estado;
    }

    public Integer getResultado() {
        return resultado;
    }

    public void setResultado(Integer resultado) {
        this.resultado = resultado;
    }

    /**
     * @return the idReporte
     */
    public Integer getIdReporte() {
        return idReporte;
    }

    /**
     * @param idReporte the idReporte to set
     */
    public void setIdReporte(Integer idReporte) {
        this.idReporte = idReporte;
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

    /**
     * @return the estado
     */
    public int getEstado() {
        return estado;
    }

    /**
     * @param estado the estado to set
     */
    public void setEstado(int estado) {
        this.estado = estado;
    }

    public int getIdConductor() {
        return this.idConductor;
    }

    public void setIdConductor(int idConductor) {
        this.idConductor = idConductor;
    }

    /**
     * @return the dictamen
     */
    public String getDictamen() {
        return dictamen;
    }

    /**
     * @param dictamen the dictamen to set
     */
    public void setDictamen(String dictamen) {
        this.dictamen = dictamen;
    }

    /**
     * @return the descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * @param descripcion the dictamen to set
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * @return the placasVehiculos
     */
    public String getPlacasVehiculos() {
        return placasVehiculos;
    }

    /**
     * @param placasVehiculos the placasVehiculos to set
     */
    public void setPlacasVehiculos(String placasVehiculos) {
        this.placasVehiculos = placasVehiculos;
    }

    /**
     * @return the idSiniestro
     */
    public Integer getIdSiniestro() {
        return idSiniestro;
    }

    /**
     * @param idSiniestro the idSiniestro to set
     */
    public void setIdSiniestro(Integer idSiniestro) {
        this.idSiniestro = idSiniestro;
    }

    /**
     * @return the hora
     */
    public String getHora() {
        return hora;
    }

    /**
     * @param hora the hora to set
     */
    public void setHora(String hora) {
        this.hora = hora;
    }

}

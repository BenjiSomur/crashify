package com.level6ninja.crashify.beans;

public class Vehiculo {
    private Integer idVehiculo;
    private String numPlacas;
    private String modelo;
    private Integer idMarca;
    private String year;
    private String color;
    private String numPoliza;

    public Vehiculo() {
    }

    public Vehiculo(Integer idVehiculo, String numPlacas, String modelo, int idMarca, String year, String color, String numPoliza) {
        this.idVehiculo = idVehiculo;
        this.numPlacas = numPlacas;
        this.modelo = modelo;
        this.idMarca = idMarca;
        this.year = year;
        this.color = color;
        this.numPoliza = numPoliza;
    }


    /**
     * @return the idVehiculo
     */
    public Integer getIdVehiculo() {
        return idVehiculo;
    }

    /**
     * @param idVehiculo the idVehiculo to set
     */
    public void setIdVehiculo(Integer idVehiculo) {
        this.idVehiculo = idVehiculo;
    }

    /**
     * @return the numPlacas
     */
    public String getNumPlacas() {
        return numPlacas;
    }

    /**
     * @param numPlacas the numPlacas to set
     */
    public void setNumPlacas(String numPlacas) {
        this.numPlacas = numPlacas;
    }

    /**
     * @return the modelo
     */
    public String getModelo() {
        return modelo;
    }

    /**
     * @param modelo the modelo to set
     */
    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    /**
     * @return the idMarca
     */
    public int getIdMarca() {
        return idMarca;
    }

    /**
     * @param idMarca the idMarca to set
     */
    public void setIdMarca(int idMarca) {
        this.idMarca = idMarca;
    }

    /**
     * @return the year
     */
    public String getYear() {
        return year;
    }

    /**
     * @param year the year to set
     */
    public void setYear(String year) {
        this.year = year;
    }

    /**
     * @return the color
     */
    public String getColor() {
        return color;
    }

    /**
     * @param color the color to set
     */
    public void setColor(String color) {
        this.color = color;
    }

    public String getNumPoliza(){
        return this.numPoliza;
    }

    public void setNumPoliza(String numPoliza){
        this.numPoliza = numPoliza;
    }
}
